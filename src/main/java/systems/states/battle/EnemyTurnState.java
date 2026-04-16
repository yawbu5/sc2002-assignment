package systems.states.battle;

import commands.Command;
import systems.BattleEngine;
import systems.Entity;
import systems.states.GameState;

import java.util.List;

import static systems.states.BattleSession.buildActionsList;

public class EnemyTurnState implements BattleState {
    @Override
    public BattleState transition(BattleData data, BattleEngine engine) {
        /*
        Same ideas as the PlayerTurnState, without the need for input dialogues.
        */
        List<Command> commands = buildActionsList(data, engine);

        commands.get(0).execute(engine);

        Entity current = engine.getEntityManager().getEntity(data.getTurnOrder().get(data.currentTurn - 1));
        engine.notifyBattleObservers(o -> o.onLogAction( current.getName() + " " +current.getId()+ "'s turn"));

        return new ResolveTurnState();
    }

}
