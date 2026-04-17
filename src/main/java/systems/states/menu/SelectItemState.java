package systems.states.menu;

import commands.Command;
import commands.MenuCommand;
import data.ActionTemplate;
import data.ActionType;
import systems.BattleEngine;
import systems.states.GameState;

import java.util.ArrayList;
import java.util.List;

public class SelectItemState implements GameState {
    private boolean initalised = false;
    private boolean allItemsCollected = false;
    private final static int itemAmount = 2;
    @Override
    public GameState onUpdate(BattleEngine engine) {
        if (!allItemsCollected || !initalised) {
            List<Command> itemChoices = new ArrayList<>();
            List<ActionTemplate> abilities = engine.retrieveDbActions();

            for (ActionTemplate a : abilities) {
                if (a.type == ActionType.ITEM) {
                    itemChoices.add(new MenuCommand(a.name, () -> engine.addToInventory(a.id)));
                }
            }

            engine.notifyMenuObservers(o -> o.onChoicePrompt( "Select item " + (engine.getPlayerInventory().size() + 1) + ":", itemChoices));

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
