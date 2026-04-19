package systems.states.battle;

import systems.BattleEngine;
import systems.actions.StatusManager;
import systems.entities.Entity;
import systems.entities.EntityType;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Responsibility: Determines turn order i.e., the TurnStrategyOrder
 */
public class StartTurnState implements BattleState {

    @Override
    public BattleState transition(BattleData data, BattleEngine engine) {

        /*
         Only go through entities that are alive.
         We try not to delete entities until a wave/battle is over.
         This allows for implementation of a potential revive function
         and prevents any potential weird side effects.
        */
        List<Entity> alive = engine.getEntityManager().getAliveEntities();
        /*
        We sort the playable (alive) entities by their current speed.
         */
        List<Integer> currentTurnOrder = data.getTurnOrder();
        if (currentTurnOrder == null) {
            // Handle very first turn, order turns by SPD
            alive.sort(Comparator.comparing(Entity::getSpeed).reversed());

            // Transforms list of Entity data into a simple list of Entity Ids. Smaller signature, more performant.
            List<Integer> newTurnOrder = alive.stream().map(Entity::getId).collect(Collectors.toList());

            engine.notifyBattleObservers(o -> o.onRoundStart(data.getRoundCounter()));

            data.setTurnOrder(newTurnOrder);
        }

        // If our current entity turn pointer is larger than the size of the entities, we've completed a round.
        if (data.currentTurn >= data.getTurnOrder().size()) {
            data.incrementRoundCounter();
            data.currentTurn = 0;

            engine.notifyBattleObservers(o -> o.onRoundStart(data.getRoundCounter()));
            // tick action cooldowns per round
            for (Entity e : engine.getEntityManager().getAllEntities()) {
                e.activeActions.replaceAll((abilityId, cooldownTimer) -> cooldownTimer - 1);
            }
            // tick whatever effects
            engine.getStatusManager().tickEffects();
        }

        // check if entity we're about to push is already dead?
        int currentEntityId = data.getTurnOrder().get(data.currentTurn);
        Entity currentEntity = engine.getEntityManager().getEntity(currentEntityId);
        Map<String, StatusManager.EffectInfo> activeEffects = engine.getStatusManager().getActiveEffects(currentEntityId);

        if (currentEntity.isDead() || !activeEffects.isEmpty()) {
            // we skip and check next entity in line
            // ensures that we do not unnecessarily check if entities are dead during the turn state
            if (currentEntity.isDead()) {
                data.currentTurn++;
                engine.notifyBattleObservers(o -> o.onLogAction(currentEntity.getName() + " -> ELIMINATED: Skipped"));
                return this;
            } else if (activeEffects.containsKey("stun")) {
                data.currentTurn++;
                engine.notifyBattleObservers(o -> o.onLogAction(currentEntity.getName() + " -> STUNNED: Skipped"));
                return this;
            }
        }

        // if player is first, go to player
        // if not, go to enemy
        // A bit lengthy, but it does the job
        if (engine.getEntityManager().getEntity(data.getTurnOrder().get(data.currentTurn)).getType() == EntityType.PLAYER) {
            // post increment turn counter/pointer to prepare for the next check.
            data.currentTurn++;
            return new PlayerTurnState();
        } else {
            data.currentTurn++;
            return new EnemyTurnState();
        }
    }
}
