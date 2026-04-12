package systems.states.menu;

import commands.Command;
import commands.StartMenuCommand;
import data.Ability;
import data.EntityTemplate;
import data.Wave;
import systems.BattleEngine;
import systems.Entity;
import systems.states.BattleState;
import systems.states.GameState;
import ui.GameView;

import java.util.ArrayList;
import java.util.List;

public class SelectDifficultyState implements GameState {
    @Override
    public void onEnter(BattleEngine engine, GameView view) {

    }

    @Override
    public GameState onUpdate(BattleEngine engine, GameView view) {
        List<Wave> waves = engine.retrieveDbWaves();
        List<Command> difficultyChoices = new ArrayList<>();

        for (Wave w : waves) {
            difficultyChoices.add(new StartMenuCommand(w.name, () -> engine.setDifficulty(w)));
        }

        Command selected = view.PromptUserChoice("Select a difficulty: ", difficultyChoices);
        selected.execute(null);

        return new SelectCharacterState();
    }

    @Override
    public void onExit(BattleEngine engine, GameView view) {

    }
}
