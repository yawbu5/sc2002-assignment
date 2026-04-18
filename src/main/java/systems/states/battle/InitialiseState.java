package systems.states.battle;

import data.EntityTemplate;
import systems.BattleEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Prepares all required systems and data and prepare player for 1st turn
 */
public class InitialiseState implements BattleState {
    @Override
    public BattleState transition(BattleData data, BattleEngine engine) {
        if (data.requestRestartSameSettings) {
            engine.notifyMenuObservers(o -> o.onDisplayMessage("Game restarted with same settings"));
            data.reset();
            engine.setPlayerInventory(new ArrayList<>(data.getPlayerInventory()));
            data.requestRestartSameSettings = false;
        }

        List<EntityTemplate> entities = new ArrayList<>();

        entities.add(engine.getSelectedPlayer());

        for (String s : data.getWaves().get(0)) {
            entities.add(engine.retrieveDbEntity(s));
        }

        engine.startEntityManager(entities);
        engine.startActionManager();
        engine.startStatusManager();

        // save initial inventory in case of restarts
        data.setPlayerInventory(new ArrayList<>(engine.getPlayerInventory()));

        // Print field enemy stats for player to see
        BattleData.printWaveInfo(engine);

        return new StartTurnState();
    }
}
