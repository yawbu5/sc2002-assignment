package systems.states.battle;

import systems.BattleEngine;
import systems.Entity;
import systems.states.GameState;
import systems.states.menu.ResultState;

import java.util.List;

/**
 * Responsibility: Check win/lose conditions, then process accordingly / move to next turn
 */
public class EndTurnState implements GameState {
    @Override
    public GameState onUpdate(BattleEngine engine) {
        List<Entity> aliveEntities = engine.getEntityManager().getAliveEntities();
        Entity player = engine.getEntityManager().getEntity(0);

        if (player.getCurrHp() <= 0) {
            return new ResultState();
        } else {
            boolean allEnemiesDead = true;
            for (Entity e : aliveEntities) {
                if (e.getType() == Entity.EntityType.ENEMY) {
                   allEnemiesDead = false;
                   break;
                }
            }

            if (allEnemiesDead) {
                return new ResultState();
            }

            return new StartTurnState();
        }
    }
}
