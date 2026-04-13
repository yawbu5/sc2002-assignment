package systems.states.battle;

import commands.Command;
import systems.BattleEngine;
import systems.states.GameState;
import ui.GameView;

import java.util.List;

import static systems.states.BattleState.buildActionsList;

public class EnemyTurnState implements GameState {
    @Override
    public GameState onUpdate(BattleEngine engine, GameView view) {
        /*
        Same ideas as the PlayerTurnState, without the need for input dialogues.
        */
        List<Command> commands = buildActionsList(engine);

        commands.get(0).execute(engine);

        return this;
    }

}
