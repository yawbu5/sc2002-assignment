package systems.states.menu;

import commands.Command;
import commands.StartMenuCommand;
import data.EntityTemplate;
import systems.BattleEngine;
import systems.Entity;
import systems.states.GameState;
import ui.GameView;

import java.util.ArrayList;
import java.util.List;

public class SelectCharacterState implements GameState {
    @Override
    public void onEnter(BattleEngine engine, GameView view) {

    }

    @Override
    public GameState onUpdate(BattleEngine engine, GameView view) {
        List<EntityTemplate> entities = engine.retrieveDbEntities();

        List<Command> characterChoices = new ArrayList<>();
        for (EntityTemplate e : entities) {
            if (e.type == Entity.EntityType.PLAYER) {
                characterChoices.add(new StartMenuCommand(e.name, () -> engine.setSelectedPlayer(e)));
            }
        }

        Command selected = view.PromptUserChoice("Select a character: ", characterChoices);
        selected.execute(null);

        return new SelectItemState();
    }

    @Override
    public void onExit(BattleEngine engine, GameView view) {

    }
}
