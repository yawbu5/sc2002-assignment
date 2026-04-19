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

        // inform observers to update their local entity database
        engine.getEntityManager().getAllEntities().forEach(e -> engine.notifyBattleObservers(o -> o.onUpdateStats(e.getId(), e.getType().toString(), e.getName(), e.getCurrHp(), e.getMaxHp(), e.getDefence(), e.getSpeed(), e.getAttack())));
        engine.notifyBattleObservers(o ->o.onRoundStart(data.getRoundCounter()));

        return new StartTurnState();
    }
}
