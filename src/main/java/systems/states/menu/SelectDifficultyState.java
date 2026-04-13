package systems.states.menu;

import commands.Command;
import commands.StartMenuCommand;
import data.EntityTemplate;
import data.Wave;
import systems.BattleEngine;
import systems.states.GameState;
import systems.states.battle.InitialiseState;
import ui.GameView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectDifficultyState implements GameState {
    @Override
    public void onEnter(BattleEngine engine, GameView view) {

    }

    @Override
    public GameState onUpdate(BattleEngine engine, GameView view) {
        List<Wave> waves = engine.retrieveDbWaves();
        List<Command> difficultyChoices = new ArrayList<>();

        List<String> enemyAttributes = new ArrayList<>();
        for (Wave w : waves) {
            int enemyCount = 0;

            List<String> uniqueEntities = new ArrayList<>();
            for (List<String> l : w.waves) {
                for (String s : l) {
                    if (!uniqueEntities.contains(s)) {
                        uniqueEntities.add(s);
                    }
                    enemyCount++;
                }
            }

            StringBuilder enemies = new StringBuilder();

            for (String s : uniqueEntities) {
                EntityTemplate ent = engine.retrieveDbEntity(s);
                enemies.append(ent.name).append(", ");
                enemyAttributes.add(ent.name + " | HP: " + ent.hp + ", DEF: " + ent.defence + ", SPD: " + ent.speed);
            }

            difficultyChoices.add(new StartMenuCommand((w.name + " | Number of Enemies: " + enemyCount + "| Enemies: " + enemies), () -> engine.setDifficulty(w)));
        }

        Command selected = view.PromptUserChoice("Select a difficulty: ", difficultyChoices);

        //view.DisplayMessage("Enemy attributes: ");
        //for (String s : enemyAttributes) {
        //    view.DisplayMessage(s);
        //}

        selected.execute(null);

        return new InitialiseState();
    }

    @Override
    public void onExit(BattleEngine engine, GameView view) {

    }
}
