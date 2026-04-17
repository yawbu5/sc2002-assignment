package systems.states.battle;

import commands.ActionCommand;
import commands.Command;
import systems.BattleEngine;
import systems.states.GameState;

/**
 * Responsibility: Executes actions and other "concurrent" effects
 */
public class ResolveTurnState implements BattleState {
    @Override
    public BattleState transition(BattleData data, BattleEngine engine) {
        Command command = engine.retrieveLatestCommand();
        command.execute(engine);
        return new EndTurnState();
    }
}
