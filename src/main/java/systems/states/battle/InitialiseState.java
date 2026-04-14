package systems.states.battle;

import data.EntityTemplate;
import systems.BattleEngine;
import systems.states.GameState;

import java.util.ArrayList;
import java.util.List;

/**
 * Prepares all required systems and data and prepare player for 1st turn
 */
public class InitialiseState implements GameState {
    @Override
    public GameState onUpdate(BattleEngine engine) {
        List<EntityTemplate> entities = new ArrayList<>();

        entities.add(engine.getSelectedPlayer());

        for (String s : engine.getWaves().get(0)) {
            entities.add(engine.retrieveDbEntity(s));
        }

        engine.startEntityManager(entities);

        return new StartTurnState();
    }
}
