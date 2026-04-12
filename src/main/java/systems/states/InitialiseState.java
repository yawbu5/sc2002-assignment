package systems.states;

import commands.Command;
import commands.StartMenuCommand;
import data.EntityTemplate;
import data.Wave;
import systems.BattleEngine;
import systems.Entity;
import systems.states.battle.StartState;
import ui.GameView;

import java.util.ArrayList;
import java.util.List;

public class InitialiseState implements GameState {
    @Override
    public void onEnter(BattleEngine engine, GameView view) {

    }

    @Override
    public GameState onUpdate(BattleEngine engine, GameView view) {
        List<EntityTemplate> entities = engine.retrieveDbEntities();
        List<Wave> waves = engine.retrieveDbWaves();

        List<Command> difficultyChoices = new ArrayList<>();
        for (Wave w : waves) {
            difficultyChoices.add(new StartMenuCommand(w.name, () -> engine.setDifficulty(w)));
        }

        Command selected = view.PromptUserChoice("Select a difficulty: ", difficultyChoices);
        selected.execute(null);

        List<Command> characterChoices = new ArrayList<>();
        for (EntityTemplate e : entities) {
            if (e.type == Entity.EntityType.PLAYER) {
                characterChoices.add(new StartMenuCommand(e.name, () -> engine.setSelectedPlayer(e)));
            }
        }

        selected = view.PromptUserChoice("Select a character: ", characterChoices);
        selected.execute(null);

        return new StartState();
    }

    @Override
    public void onExit(BattleEngine engine, GameView view) {

    }
}
