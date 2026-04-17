package systems.states.battle;

import commands.ActionCommand;
import systems.BattleEngine;
import systems.Entity;

public class EnemyTurnState implements BattleState {
    @Override
    public BattleState transition(BattleData data, BattleEngine engine) {
        /*
        Same ideas as the PlayerTurnState, without the need for input dialogues.
        */
        Entity current = engine.getEntityManager().getEntity(data.getTurnOrder().get(data.currentTurn - 1));
        engine.queueNextCommand(new ActionCommand("", data.getCurrentTurnEntityId(), 0, current.getAbilities().get(0)));

        return new ResolveTurnState();
    }

}
