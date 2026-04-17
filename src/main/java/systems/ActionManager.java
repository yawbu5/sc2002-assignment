package systems;

import data.ActionTemplate;
import data.EffectTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps track of available abilities for entities on the field and their cooldowns.
 */
public class ActionManager {
    // We keep track by entityId and a list of ActiveActions (holds cooldowns)
    //private final Map<Integer, List<ActiveAction>> activeActions = new HashMap<>();
    private final BattleEngine engine;

    public ActionManager(BattleEngine engine) {
        this.engine = engine;
    }

    // true -> action has been processed.
    // false -> for whatever reason the action cannot be processed, 99% should be because of cooldowns.
    public boolean processAction(int casterId, List<Integer> targetIds, String actionId) {
        Entity caster = engine.getEntityManager().getEntity(casterId);
        List<Entity> targets = new ArrayList<>();
        ActionTemplate action = engine.retrieveDbAction(actionId);

        // check with the entity if action has been used and has a cooldown
        int cooldownTime;
        if ((cooldownTime = caster.activeActions.getOrDefault(actionId, 0)) > 0) {
           engine.notifyBattleObservers(o -> o.onLogAction(action.name + " is on cooldown! " + "(" + cooldownTime + " turns remaining)"));
           return false;
        }

        // nominally this can just be a single int rather than a list
        // however we want to open the possibility of targeting less than all but multiple.
        // i.e., multi-target abilities that don't hit everyone as an AOE.
        for (int id : targetIds) {
            targets.add(engine.getEntityManager().getEntity(id));
        }

        for (EffectTemplate effect : action.effects) {
            switch (effect.type) {
                case "DAMAGE":
                    // deals caster's attack val. worth of damage to the targets * some multiplier
                    for (Entity e : targets) {
                        int calculated_damage = Math.toIntExact(Math.round(caster.getAttack() * effect.val));
                        e.setCurrHp(e.getCurrHp() - calculated_damage);
                        engine.notifyBattleObservers(o -> o.onLogAction(caster.getName() + " deals " + calculated_damage + " to " + e.getName()));
                    }
                    break;
                case "HEAL":
                    for (Entity e : targets) {
                        e.setCurrHp(Math.min(e.getCurrHp() + (int) effect.val, e.getMaxHp()));
                        engine.notifyBattleObservers(o -> o.onLogAction(e.getName() + " healed for " + effect.val));
                    }
                    break;
                case "APPLY_STATUS":
                    // ask status manager to apply status effect on the target
                    break;
                case "ACTIVATE_ABILITY":
                    // activate entity's special ability (if it exists)
                    break;
            }

            // apply cooldown to abilities that have a set cooldown value
            if (action.cooldown > 0) {
                caster.activeActions.put(actionId, action.cooldown);
            }
        }

        return true;
    }

    //public void registerEntityActions(int entityId, List<ActiveAction> actions) {
    //    activeActions.put(entityId, actions);
    //}

    //public void deregisterEntityActions(int entityId) {
    //    activeActions.remove(entityId);
    //}

    //public List<ActiveAction> getAllActions(int entityId) {
    //    return activeActions.getOrDefault(entityId, new ArrayList<>());
    //}

    //public List<ActiveAction> getAvailableActions(int entityId) {
    //    List<ActiveAction> actions = getAllActions(entityId);

    //    return actions.stream().filter(ActiveAction::isReady).collect(Collectors.toList());
    //}

    //public void incrementTick(int entityId) {
    //    List<ActiveAction> actions = getAllActions(entityId);
    //    for (ActiveAction a : actions) {
    //        a.tick();
    //    }
    //}
}
