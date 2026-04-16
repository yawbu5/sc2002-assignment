package systems.states.battle;

import systems.BattleEngine;
import systems.Entity;
import systems.states.GameState;

import java.util.Comparator;
import java.util.List;
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

        if (data.currentTurn >= data.getTurnOrder().size()) {
            data.incrementRoundCounter();
            data.currentTurn = 0;

            engine.notifyBattleObservers(o -> o.onRoundStart(data.getRoundCounter()));
            // tick whatever effects
        }

        // check if entity we're about to push is already dead?
        Entity currentEntity = engine.getEntityManager().getEntity(data.currentTurn);


        if (currentEntity.isDead()) {
            // we skip and check next entity in line
            return this;
        }

        //List<String> displayMsgs = new ArrayList<>();


        // if player is first, go to player
        // if not, go to enemy
        // A bit lengthy, but it does the job
        if (engine.getEntityManager().getEntity(data.getTurnOrder().get(data.currentTurn)).getType() == Entity.EntityType.PLAYER) {
            // Print field enemy stats for player to see
            for (Entity e : engine.getEntityManager().getAliveEntities()) {
                String playerStatus = (e.getType() == Entity.EntityType.PLAYER) ? " (YOU)" : "";

                String msg = e.getName() + playerStatus + " | HP: " + e.getCurrHp() + "/" + e.getMaxHp() + ", DEF: " + e.getDefence() + ", SPD: " + e.getSpeed();
                engine.notifyMenuObservers(o -> o.onDisplayMessage(msg));
            }
            data.currentTurn++;
            return new PlayerTurnState();
        } else {
            data.currentTurn++;
            return new EnemyTurnState();
        }
    }
}
