package systems.states.menu;

import commands.Command;
import commands.MenuCommand;
import data.EntityTemplate;
import data.Wave;
import systems.BattleEngine;
import systems.states.GameState;
import systems.states.battle.InitialiseState;

import java.util.ArrayList;
import java.util.List;

public class SelectDifficultyState implements GameState {
    private boolean initalised = false;

    @Override
    public GameState onUpdate(BattleEngine engine) {
        if (!initalised) {
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

                difficultyChoices.add(new MenuCommand((w.name + " | Number of Enemies: " + enemyCount + " | Enemies: " + enemies), () -> engine.setDifficulty(w)));
            }

            engine.notifyMenuObservers(o -> o.onChoicePrompt("Select a difficulty:", difficultyChoices));
            initalised = true;
        }

        Command selected = engine.retrieveLatestCommand();

        if (selected == null) {
            return this;
        }

        if (!(selected instanceof MenuCommand)) {
            return this;
        }

        selected.execute(engine);
        return new InitialiseState();
    }
}
