package systems.states.battle;

import data.EntityTemplate;
import systems.BattleEngine;
import systems.entities.EntityType;

import java.util.ArrayList;
import java.util.List;

/**
 * If there is another wave, inform the player of the incoming wave and add the new wave into the entity manager.
 */
public class SendNextWaveState implements BattleState {
    @Override
    public BattleState transition(BattleData data, BattleEngine engine) {
        List<EntityTemplate> entities = new ArrayList<>();

        // clear all dead enemies
        engine.getEntityManager().removeEntityByType(EntityType.ENEMY);

        // reset relevant temp variables
        data.currentTurn = 0;
        data.incrementRoundCounter();
        data.setTurnOrder(null);        // Not resetting turn order caused constant crashes, through painful debugging took an hour to fix :)

        for (String s : data.getWaves().get(data.incrementWaveCount())) {
            entities.add(engine.retrieveDbEntity(s));
        }

        // add new enemies
        engine.getEntityManager().addEntitiesFromList(entities);

        // let player know current wave is finished and show current wave
        engine.notifyBattleObservers(o -> o.onWaveSpawn(data.getWaveCount()));

        // Print field enemy stats for player to see
        BattleData.printWaveInfo(engine);

        return new StartTurnState();
    }
}
