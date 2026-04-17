package systems.states.menu;

import commands.Command;
import commands.MenuCommand;
import data.EntityTemplate;
import systems.BattleEngine;
import systems.Entity;
import systems.EntityType;
import systems.states.GameState;

import java.util.ArrayList;
import java.util.List;

public class SelectCharacterState implements GameState {
    private boolean initialised = false;

    @Override
    public GameState onUpdate(BattleEngine engine) {
        if (!initialised) {
            List<EntityTemplate> entities = engine.retrieveDbEntities();
            engine.clearInventory(); // clear inventory in case of new runs

            List<Command> characterChoices = new ArrayList<>();
            for (EntityTemplate e : entities) {
                if (e.type == EntityType.PLAYER) {
                    characterChoices.add(new MenuCommand(e.name, () -> engine.setSelectedPlayer(e)));
                }
            }

            engine.notifyMenuObservers(o -> o.onChoicePrompt("Select a character: ", characterChoices));
            initialised = true;
        }

        Command result = engine.retrieveLatestCommand();

        if (result == null) {
            return this;
        }

        // ensure command didn't come from somewhere else by accident
        if (!(result instanceof MenuCommand)) {
            return this;
        }
        result.execute(engine);

        return new SelectItemState();
    }
}
