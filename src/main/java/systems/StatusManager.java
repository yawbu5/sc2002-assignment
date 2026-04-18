package systems;

import data.ActionEffectTemplate;
import data.EffectTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Tracks persistent status effects during battles.
 */
public class StatusManager {
    // Map of durations to entity IDs
    private final Map<Integer, Map<String, Integer>> activeEffects = new HashMap<>();
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

    public Map<String, Integer> getActiveEffects(int entityId) {
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
    public void processEffect(int targetId, ActionEffectTemplate effect) {
        engine.getEntityManager().getEntity(targetId);

        // if entity is not registered, then register.
        if (!entityRegistered(targetId)) {
            registerEntity(targetId);
        }

        if (activeEffects.get(targetId).getOrDefault(effect.id, 0) > 0) {
            // If exists, refresh duration
            activeEffects.get(targetId).replace(effect.id, effect.duration);
        } else {
            // if not just put
            activeEffects.get(targetId).put(effect.id, effect.duration);
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
                int duration = e.getValue();

                EffectTemplate eff = engine.retrieveDbEffect(effectId);
                applyEffect(entity, eff);

                if (--duration <= 0) {
                    engine.notifyBattleObservers(o -> o.onEffectExpired(eff.name, entity.getName()));
                    return true;
                } else {
                    e.setValue(duration);
                    return false;
                }
            });
        });
    }

    // handles recurring (per round) effects (i.e., DOT)
    // effectively pointless for now, but better to have a handle
    public void applyEffect(Entity e, EffectTemplate effect) {
        switch (effect.effect) {
            // For now, these effects at the bottom don't have a recurring effect
            // (because these change stats passively or are a general check)
            case "SKIP_TURN":
            case "DAMAGE_REDUCTION":
            case "CASTER_DAMAGE_BUFF":
            case "INVULNERABLE":
                break;
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
            case SPEED:
                base = e.getSpeed();
        }

        Map<String, Integer> activeEffects = getActiveEffects(e.getId());
        if (activeEffects.isEmpty()) {
            return base;
        }

        int bonus_stat = 0;

        for (String id : activeEffects.keySet()) {
            EffectTemplate eff = engine.retrieveDbEffect(id);

            if (stat == StatType.ATTACK) {
                if (eff.effect.equals("CASTER_DAMAGE_BUFF")) {
                    bonus_stat += eff.val;
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
