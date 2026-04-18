package systems.states.menu;

import commands.Command;
import commands.MenuCommand;
import data.EntityTemplate;
import systems.BattleEngine;
import systems.EntityType;
import systems.states.GameState;
import systems.states.battle.BattleData;

import java.util.ArrayList;
import java.util.List;

public class SelectCharacterState implements GameState {
    private boolean initialised = false;

    @Override
    public GameState onUpdate(BattleEngine engine) {
        if (!initialised) {
            List<EntityTemplate> entities = engine.retrieveDbEntities();

            List<Command> characterChoices = new ArrayList<>();
            for (EntityTemplate e : entities) {
                if (e.type == EntityType.PLAYER) {
                    String attr = String.format(": HP: %d | ATK: %d | DEF: %d | SPD %d", e.hp, e.attack, e.defence, e.speed);
                    characterChoices.add(new MenuCommand(e.name + attr, () -> engine.setSelectedPlayer(e)));
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
