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
            engine.notifyBattleObservers(o -> o.onGameLose(data));
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
                engine.notifyBattleObservers(o -> o.onGameWin(data));
                return new ResultState();
            }

            return new StartTurnState();
        }
    }
}
