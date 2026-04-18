package systems.actions;

import data.ActionEffectTemplate;
import data.EffectTemplate;
import systems.BattleEngine;
import systems.entities.Entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Tracks persistent status effects during battles.
 */
public class StatusManager {
    // makes it easier to track stacked effects like the wizard's kills
    public static class EffectInfo {
        public int duration;
        public int stacks;

        public EffectInfo(int duration) {
            this.duration = duration;
            this.stacks = 1;
        }
    }

    // Map of durations to entity IDs
    private final Map<Integer, Map<String, EffectInfo>> activeEffects = new HashMap<>();
    private final BattleEngine engine;

    public StatusManager(BattleEngine engine) {
        this.engine = engine;
    }

    public void registerEntity(int entityId) {
        this.activeEffects.putIfAbsent(entityId, new HashMap<>());
    }

    public void unRegisterEntity(int entityId) {
        this.activeEffects.remove(entityId);
    }

    public boolean entityRegistered(int entityId) {
        return this.activeEffects.containsKey(entityId);
    }

    public Map<String, EffectInfo> getActiveEffects(int entityId) {
        if (entityRegistered(entityId)) {
            return this.activeEffects.getOrDefault(entityId, new HashMap<>());
        } else {
            return new HashMap<>();
        }
    }

    public void removeEffect(int entityId, String effectId) {
        this.activeEffects.get(entityId).remove(effectId);
    }

    /**
     * ADDING effects
     * For adding entities and effect info we trust that the entity actually exists.
     */
    public void processEffect(int targetId, ActionEffectTemplate newEffect) {
        engine.getEntityManager().getEntity(targetId);

        // if entity is not registered, then register.
        if (!entityRegistered(targetId)) {
            registerEntity(targetId);
        }

        // effect stacking mechanism (allows stuff like the wizards' dmg buff to stack)
        Map<String, EffectInfo> entityEffects = activeEffects.get(targetId);

        if (entityEffects.containsKey(newEffect.id)) {
            EffectInfo effectInfo = entityEffects.get(newEffect.id);
            effectInfo.stacks++;

            // handle permanent effects (wizard)
            if (effectInfo.duration == -1 || newEffect.duration == -1) {
                effectInfo.duration = -1;
            } else {
                effectInfo.duration = Math.max(effectInfo.duration, newEffect.duration);
            }
        } else {
            entityEffects.put(newEffect.id, new EffectInfo(newEffect.duration));
        }
    }

    /**
     * TICKING (i.e., activating) effects
     * Direct interface with the engine, we trust that the entity actually exists.
     */
    public void tickEffects() {
        /*
         * for each effect assigned to an entity:
         * 1. apply effect
         * 2. if duration after tick <= 0: remove effect
         * 3. if not, update current duration
         */
        this.activeEffects.forEach((entityId, effectsMap) -> {
            Entity entity = engine.getEntityManager().getEntity(entityId);
            effectsMap.entrySet().removeIf(e -> {
                String effectId = e.getKey();
                EffectInfo effectInfo = e.getValue();

                EffectTemplate eff = engine.retrieveDbEffect(effectId);
                applyEffect(entity, eff);

                // -1 indicates effects that don't go away. We don't decrement if so.
                if (effectInfo.duration != -1) {
                    effectInfo.duration--;
                }

                if (effectInfo.duration == 0) {
                    engine.notifyBattleObservers(o -> o.onEffectExpired(eff.name, entity.getName()));
                    return true;
                } else {
                    return false;
                }
            });
        });
    }

    // handles recurring (per round) effects (i.e., DOT)
    // effectively pointless for now, but better to have a handle
    public void applyEffect(Entity e, EffectTemplate effect) {
        switch (effect.effect) {
            // these effects at the bottom don't have a recurring effect
            // (because these change stats passively or are a general check)
            case "SKIP_TURN":
            case "DAMAGE_REDUCTION":
            case "CASTER_DAMAGE_BUFF":
            case "INVULNERABLE":
                break;
        }
    }

    public void checkKillEffects(int casterId, int targetId, String actionId) {
        Map<String, EffectInfo> activeEffects = getActiveEffects(casterId);

        for (Map.Entry<String, EffectInfo> entry : activeEffects.entrySet()) {

        }
    }

    public int getEffectiveStat(Entity e, StatType stat) {
        // base value from entity + calculated from status effects
        int base = 0;

        switch (stat) {
            case ATTACK:
                base = e.getAttack();
                break;
            case DEFENCE:
                base = e.getDefence();
                break;
        }

        Map<String, EffectInfo> activeEffects = getActiveEffects(e.getId());
        if (activeEffects.isEmpty()) {
            return base;
        }

        int bonus_stat = 0;

        for (Map.Entry<String, EffectInfo> entry : activeEffects.entrySet()) {
            EffectTemplate eff = engine.retrieveDbEffect(entry.getKey());
            int stacks = entry.getValue().stacks;

            if (stat == StatType.ATTACK) {
                if (eff.effect.equals("CASTER_DAMAGE_BUFF")) {
                    bonus_stat += (eff.val * stacks);
                }
            } else if (stat == StatType.DEFENCE) {
                if (eff.effect.equals("DAMAGE_REDUCTION")) {
                    bonus_stat += eff.val;
                }
                if (eff.effect.equals("INVULNERABLE")) {
                    bonus_stat += 999;
                }
            }
        }

        return Math.max(0, base + bonus_stat);
    }

    public enum StatType {
        ATTACK,
        DEFENCE,
        SPEED
    }
}
