package systems.states.battle;

import systems.BattleEngine;
import systems.Entity;
import systems.EntityType;
import systems.states.GameState;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Responsibility: Determines turn order
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

            data.setTurnOrder(newTurnOrder);
        }

        // If our current entity turn pointer is larger than the size of the entities, we've completed a round.
        if (data.currentTurn >= data.getTurnOrder().size()) {
            data.incrementRoundCounter();
            data.currentTurn = 0;

            engine.notifyBattleObservers(o -> o.onRoundStart(data.getRoundCounter()));
            // tick cooldowns per round
            for (Entity e : engine.getEntityManager().getAllEntities()) {
                e.activeActions.replaceAll((abilityId, cooldownTimer) -> cooldownTimer - 1);
            }
            // tick whatever effects
        }

        // check if entity we're about to push is already dead?
        Entity currentEntity = engine.getEntityManager().getEntity(data.currentTurn);


        if (currentEntity.isDead()) {
            // we skip and check next entity in line
            // ensures that we do not unnecessarily check if entities are dead during the turn state
            return this;
        }

        // if player is first, go to player
        // if not, go to enemy
        // A bit lengthy, but it does the job
        if (engine.getEntityManager().getEntity(data.getTurnOrder().get(data.currentTurn)).getType() == EntityType.PLAYER) {
            // Print field enemy stats for player to see
            for (Entity e : engine.getEntityManager().getAliveEntities()) {
                String playerStatus = (e.getType() == EntityType.PLAYER) ? " (YOU)" : "";

                String msg = e.getName() + playerStatus + " | HP: " + e.getCurrHp() + "/" + e.getMaxHp() + ", DEF: " + e.getDefence() + ", SPD: " + e.getSpeed();
                engine.notifyMenuObservers(o -> o.onDisplayMessage(msg));
            }

            // post increment turn counter/pointer to prepare for the next check.
            data.currentTurn++;
            return new PlayerTurnState();
        } else {
            data.currentTurn++;
            return new EnemyTurnState();
        }
    }
}
