package systems.states.menu;

import commands.Command;
import commands.ItemCommand;
import commands.MenuCommand;
import data.ActionTemplate;
import data.ActionType;
import systems.BattleEngine;
import systems.states.GameState;

import java.util.ArrayList;
import java.util.List;

public class SelectItemState implements GameState {
    private final static int itemAmount = 2;
    private boolean initalised = false;
    private boolean allItemsCollected = false;

    @Override
    public GameState onUpdate(BattleEngine engine) {
        if (!allItemsCollected || !initalised) {
            List<Command> itemChoices = new ArrayList<>();
            List<ActionTemplate> actions = engine.retrieveDbActions();

            for (ActionTemplate a : actions) {
                if (a.type == ActionType.ITEM || a.type == ActionType.ITEM_TO) {
                    itemChoices.add(new MenuCommand(a.name, () -> {
                        engine.addToInventory(a.id);
                        ItemCommand.updateInventoryObs(engine);
                    }));
                }
            }

            engine.notifyMenuObservers(o -> o.onChoicePrompt("Select item " + (engine.getPlayerInventory().size() + 1) + ":", itemChoices));

            if (engine.getPlayerInventory().size() + 1 >= itemAmount)
                allItemsCollected = true;

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

        if (allItemsCollected)
            return new SelectDifficultyState();
        else {
            initalised = false;
            return this;
        }

    }
}
