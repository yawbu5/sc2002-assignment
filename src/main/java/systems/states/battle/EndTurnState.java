package systems.states.battle;

import systems.BattleEngine;
import systems.Entity;
import systems.EntityType;

import java.util.List;

/**
 * Responsibility: Check win/lose conditions, then process accordingly / move to next turn
 */
public class EndTurnState implements BattleState {
    @Override
    public BattleState transition(BattleData data, BattleEngine engine) {
        List<Entity> aliveEntities = engine.getEntityManager().getAliveEntities();
        Entity player = engine.getEntityManager().getEntity(0);

        if (player.isDead()) {
            return new ResultState();
        } else {
            boolean allEnemiesDead = true;
            for (Entity e : aliveEntities) {
                if (e.getType() == EntityType.ENEMY) {
                    allEnemiesDead = false;
                    break;
                }
            }

            if (allEnemiesDead) {
                if (data.getWaveCount() < data.getWaves().size()) {
                    return new SendNextWaveState();
                }
                return new ResultState();
            }

            return new StartTurnState();
        }
    }
}
