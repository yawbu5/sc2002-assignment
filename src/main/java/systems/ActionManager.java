package systems;

import data.ActionEffectTemplate;
import data.ActionTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps track of available abilities for entities on the field and their cooldowns.
 */
public class ActionManager {
    // We keep track by entityId and a list of ActiveActions (holds cooldowns)
    // private final Map<Integer, Map<String, Integer>> activeActions = new HashMap<>();
    private final BattleEngine engine;

    public ActionManager(BattleEngine engine) {
        this.engine = engine;
    }

    // true -> action has been processed.
    // false -> for whatever reason the action cannot be processed, 99% should be because of cooldowns.
    public void processAction(int casterId, List<Integer> targetIds, String actionId) {
        Entity caster = engine.getEntityManager().getEntity(casterId);
        List<Entity> targets = new ArrayList<>();
        ActionTemplate action = engine.retrieveDbAction(actionId);

        // check with the entity if action has been used and has a cooldown
        int cooldownTime;
        if ((cooldownTime = caster.activeActions.getOrDefault(actionId, 0)) > 0) {
            engine.notifyBattleObservers(o -> o.onLogAction(action.name + " is on cooldown! " + "(" + cooldownTime + " turns remaining)"));
            return;
        }

        // nominally this can just be a single int rather than a list
        // however we want to open the possibility of targeting less than all but multiple.
        // i.e., multi-target abilities that don't hit everyone as an AOE.
        for (int id : targetIds) {
            targets.add(engine.getEntityManager().getEntity(id));
        }

        for (ActionEffectTemplate effect : action.effects) {
            // calculate effective values relative to current applied status effects
            int effectveAttack = engine.getStatusManager().getEffectiveStat(caster, StatusManager.StatType.ATTACK);
            switch (effect.type) {
                case "DAMAGE":
                    // deals caster's attack val. worth of damage to the targets * some multiplier
                    for (Entity e : targets) {
                        int calculated_damage = Math.toIntExact(Math.round(effectveAttack * effect.val));
                        int effectiveDefence = engine.getStatusManager().getEffectiveStat(e, StatusManager.StatType.DEFENCE);
                        int old_hp = e.getCurrHp();
                        // clamp damage dealt - defense to make sure we don't accidentally heal the entity
                        e.setCurrHp(e.getCurrHp() - Math.max(0, calculated_damage - effectiveDefence));
                        if (e.isDead())
                            engine.notifyBattleObservers(o -> o.onLogAction("DAMAGED: " + caster.getName() + " -> " + action.name + " -> " + e.getName() + ": HP: " + old_hp + " -> " + e.getCurrHp() + " X ELIMINATED"));
                        else
                            engine.notifyBattleObservers(o -> o.onLogAction("DAMAGED: " + caster.getName() + " -> " + action.name + " -> " + e.getName() + ": HP: " + old_hp + " -> " + e.getCurrHp()));
                    }
                    break;
                case "HEAL":
                    for (Entity e : targets) {
                        e.setCurrHp(e.getCurrHp() + (int) effect.val);
                        engine.notifyBattleObservers(o -> o.onLogAction("HEALED: " + e.getName() + " healed for " + (int) effect.val + " up to " + e.getCurrHp() + "/" + e.getMaxHp()));
                    }
                    break;
                case "APPLY_STATUS":
                    // ask status manager to apply status effect on the target
                    for (Entity e : targets) {
                        engine.getStatusManager().processEffect(e.getId(), effect);
                        String eff_name = engine.retrieveDbEffect(effect.id).name;
                        engine.notifyBattleObservers(o -> o.onEffectApplied(eff_name, caster.getName(), e.getName(), effect.duration));
                    }
                    break;
                case "ACTIVATE_ABILITY":
                    String id = caster.getSpecialAbilityId();
                    if (!id.isEmpty()) {
                        processAction(casterId, targetIds, id);
                    }
                    break;
            }

            // apply cooldown to abilities that have a set cooldown value
            if (action.cooldown > 0) {
                caster.activeActions.put(actionId, action.cooldown);
            }
        }
    }

    public void handleEffect(ActionEffectTemplate effect, int casterId, List<Integer> targetIds, String actionId) {
        // TODO: implement
    }
}
