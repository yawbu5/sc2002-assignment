package systems.states.battle;

import systems.BattleEngine;
import systems.Entity;
import systems.states.GameState;
import ui.GameView;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Responsibility: Determines turn order
 */
public class StartTurnState implements GameState {
    @Override
    public GameState onUpdate(BattleEngine engine, GameView view) {
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
        List<Integer> currentTurnOrder = engine.getTurnOrder();
        if (currentTurnOrder == null) {
            // Handle very first turn, order turns by SPD
            alive.sort(Comparator.comparing(Entity::getSpeed).reversed());

            // Transforms list of Entity data into a simple list of Entity Ids. Smaller signature, more performant.
            List<Integer> newTurnOrder = alive.stream().map(Entity::getId).collect(Collectors.toList());

            engine.setTurnOrder(newTurnOrder);
        } else {
            // What do we expect after EndTurn? Win conditions checked.
            // Possible scenarios after that point: Either an enemy killed or not killed.

            // Either way we just queue and dequeue the first element (i.e., the last played)
            currentTurnOrder.add(currentTurnOrder.get(0));
            currentTurnOrder.remove(0);

            // set to new turn order: next in line.
            engine.setTurnOrder(currentTurnOrder);
        }

        view.DisplayMessage("State of battle: ");
        //List<String> displayMsgs = new ArrayList<>();

        for (Entity e : engine.getEntityManager().getAliveEntities()) {
            String msg = "";
            String playerStatus = (e.getType() == Entity.EntityType.PLAYER) ? " (YOU)" : "";

            msg += e.getName() + playerStatus + " | HP: " + e.getCurrHp() + "/" + e.getMaxHp() + ", DEF: " + e.getDefence() + ", SPD: " + e.getSpeed();
            view.DisplayMessage(msg);
        }

        // if player is first, go to player
        // if not, go to enemy
        // A bit lengthy, but it does the job
        if (engine.getEntityManager().getEntity(engine.getFirstTurnEntity()).getType() == Entity.EntityType.PLAYER) {
            return new PlayerTurnState();
        } else {
            return new EnemyTurnState();
        }
    }
}
