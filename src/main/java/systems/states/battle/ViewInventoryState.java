package systems.states.battle;

import commands.Command;
import commands.MenuCommand;
import commands.OpenTargetMenuCommand;
import data.ActionTemplate;
import systems.BattleEngine;

import java.util.ArrayList;
import java.util.List;

public class ViewInventoryState implements BattleState {
    private boolean initialised = false;

    @Override
    public BattleState transition(BattleData data, BattleEngine engine) {
        if (!initialised) {
            List<Command> commands = new ArrayList<>();

            for (String s : engine.getPlayerInventory()) {
                ActionTemplate action = engine.retrieveDbAction(s);
                commands.add(new OpenTargetMenuCommand("Use " + action.name, action.id, action.type));
            }

            commands.add(new MenuCommand("Go back", () -> {
            }));

            engine.notifyMenuObservers(o -> o.onChoicePrompt("Choose an item: ", commands));
            initialised = true;
        }

        Command result = engine.retrieveLatestCommand();

        if (result == null) {
            return this;
        }

        if (result instanceof MenuCommand) {
            return new PlayerTurnState();
        }

        if (result instanceof OpenTargetMenuCommand) {
            OpenTargetMenuCommand res = (OpenTargetMenuCommand) result;
            return new TargetSelectState(res.actionId, res.actionType, true);
        }

        return this;
    }
}
