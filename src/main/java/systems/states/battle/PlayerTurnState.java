package systems.states.battle;

import commands.Command;
import commands.MenuCommand;
import commands.OpenInventoryCommand;
import commands.OpenTargetMenuCommand;
import systems.BattleEngine;

import java.util.List;

import static systems.states.BattleSession.buildActionsList;

/**
 * Responsibility: Wait for and validate player input, and handle selecting
 */
public class PlayerTurnState implements BattleState {
    private boolean initialised = false;

    @Override
    public BattleState transition(BattleData data, BattleEngine engine) {
        /*
        1. Get abilities from entity.
        2. Attach related commands to those abilities
        3. Once ability is selected, depending on command, display target dialogue
        4. Once confirmed, add action to actionManager to handle outcome
        5. Move to Resolve
        */
        if (!initialised) {
            List<Command> commands = buildActionsList(data, engine);

            commands.add(new OpenInventoryCommand());

            engine.notifyMenuObservers(o -> o.onChoicePrompt("Select an action: ", commands));
            initialised = true;
        }

        // check if user has sent a response
        Command result = engine.retrieveLatestCommand();

        if (result == null) {
            return this;
        }

        if (result instanceof OpenInventoryCommand) {
            return new ViewInventoryState();
        } else if (result instanceof OpenTargetMenuCommand) {
            OpenTargetMenuCommand res = (OpenTargetMenuCommand) result;
            return new TargetSelectState(res.actionId, res.actionType);
        } else if (result instanceof MenuCommand) {
            return new PlayerTurnState();
        }

        return this;
    }
}
