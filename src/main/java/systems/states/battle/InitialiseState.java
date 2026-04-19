package systems.states.battle;

import data.EntityTemplate;
import observable.BattleObserver;
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

        // inform observers to update their local entity database
        engine.getEntityManager().getAllEntities().forEach(e -> engine.notifyBattleObservers(o -> o.onUpdateStats(e.getId(), e.getType().toString(), e.getName(), e.getCurrHp(), e.getMaxHp(), e.getDefence(), e.getSpeed(), e.getAttack())));

        engine.notifyBattleObservers(BattleObserver::onGameStart);

        BattleData.printGameInfo(data, engine);
        BattleData.printTurnOrder(data, engine);

        return new StartTurnState();
    }
}
