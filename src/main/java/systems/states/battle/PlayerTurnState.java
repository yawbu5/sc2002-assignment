package systems.states.battle;

import commands.Command;
import systems.BattleEngine;
import systems.states.GameState;
import ui.GameView;

import java.util.List;

import static systems.states.BattleState.buildActionsList;

/**
 * Responsibility: Wait for and validate player input, and handle selecting
 */
public class PlayerTurnState implements GameState {
    @Override
    public GameState onUpdate(BattleEngine engine, GameView view) {
        /*
        1. Get abilities from entity.
        2. Attach related commands to those abilities
        3. Once ability is selected, depending on command, display target dialogue
        4. Once confirmed, add action to actionManager to handle outcome
        5. Move to Resolve
        */
        List<Command> commands = buildActionsList(engine);

        Command choice = view.PromptUserChoice("Select an action: ", commands);
        choice.execute(engine);

        return this;
    }
}
