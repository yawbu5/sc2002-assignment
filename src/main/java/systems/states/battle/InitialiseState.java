package systems.states.battle;

import data.EntityTemplate;
import systems.BattleEngine;
import systems.Entity;
import systems.EntityType;

import java.util.ArrayList;
import java.util.List;

/**
 * Prepares all required systems and data and prepare player for 1st turn
 */
public class InitialiseState implements BattleState {
    @Override
    public BattleState transition(BattleData data, BattleEngine engine) {
        List<EntityTemplate> entities = new ArrayList<>();

        entities.add(engine.getSelectedPlayer());

        for (String s : data.getWaves().get(0)) {
            entities.add(engine.retrieveDbEntity(s));
        }

        engine.startEntityManager(entities);
        engine.startActionManager();

        // Print field enemy stats for player to see
        BattleData.printWaveInfo(engine);

        return new StartTurnState();
    }
}
