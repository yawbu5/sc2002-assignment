package systems.states.menu;

import commands.Command;
import commands.StartMenuCommand;
import data.ActionTemplate;
import systems.BattleEngine;
import systems.states.GameState;
import ui.GameView;

import java.util.ArrayList;
import java.util.List;

public class SelectItemState implements GameState {
    @Override
    public void onEnter(BattleEngine engine, GameView view) {

    }

    @Override
    public GameState onUpdate(BattleEngine engine, GameView view) {
        List<Command> itemChoices = new ArrayList<>();
        List<ActionTemplate> abilities = engine.retrieveDbAbilities();

        for (ActionTemplate a : abilities) {
            if (a.type == ActionTemplate.AbilityType.ITEM) {
                itemChoices.add(new StartMenuCommand(a.name, () -> engine.addToInventory(a.name)));
            }
        }

        Command selected = view.PromptUserChoice("Select your 1st item: ", itemChoices);
        selected.execute(null);

        selected = view.PromptUserChoice("Select your 2nd item: ", itemChoices);
        selected.execute(null);

        return new SelectDifficultyState();
    }

    @Override
    public void onExit(BattleEngine engine, GameView view) {

    }
}
