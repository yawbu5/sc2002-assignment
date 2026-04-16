package systems.states.menu;

import commands.Command;
import commands.ItemCommand;
import commands.MenuCommand;
import data.ActionTemplate;
import systems.BattleEngine;
import systems.states.GameState;
import systems.states.battle.BattleData;
import systems.states.battle.BattleState;
import systems.states.battle.PlayerTurnState;
import systems.states.battle.ResolveTurnState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ViewInventoryState implements BattleState {
    private boolean initialised = false;
    @Override
    public BattleState transition(BattleData data, BattleEngine engine) {
        if (!initialised) {
            List<Command> commands = new ArrayList<>();

            for (String s : engine.getPlayerInventory()) {
                ActionTemplate action = engine.retrieveDbAbility(s);
                commands.add(new ItemCommand("Use " + action.name));
            }

            commands.add(new MenuCommand("Go back", () -> {}));

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

        if (result instanceof ItemCommand) {
            engine.queueNextCommand(result);
            return new ResolveTurnState();
        }

        return this;
    }
}
