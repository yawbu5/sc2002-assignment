package systems.states.battle;

import commands.Command;
import commands.ItemCommand;
import commands.MenuCommand;
import data.ActionTemplate;
import systems.BattleEngine;
import systems.states.GameState;

import java.util.List;

import static systems.states.BattleState.buildActionsList;

/**
 * Responsibility: Wait for and validate player input, and handle selecting
 */
public class PlayerTurnState implements GameState {
    private boolean initalised = false;
    @Override
    public GameState onUpdate(BattleEngine engine) {
        /*
        1. Get abilities from entity.
        2. Attach related commands to those abilities
        3. Once ability is selected, depending on command, display target dialogue
        4. Once confirmed, add action to actionManager to handle outcome
        5. Move to Resolve
        */
        if (!initalised) {
            List<Command> commands = buildActionsList(engine);

            for (String s : engine.getPlayerInventory()) {
                ActionTemplate action = engine.retrieveDbAbility(s);
                commands.add(new ItemCommand("Use " + action.name));
            }

            engine.notifyMenuObservers(o -> o.onChoicePrompt("Select an action: ", commands));
            initalised = true;
        }

        Command result = engine.retrieveLatestCommand();

        if (result == null) {
            return this;
        }

        if (!(result instanceof MenuCommand)) {
            return this;
        }

        result.execute(engine);

        return new ResolveTurnState();
    }
}
