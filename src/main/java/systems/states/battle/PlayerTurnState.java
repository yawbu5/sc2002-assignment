package systems.states.battle;

import commands.ActionSelfCommand;
import commands.ActionToCommand;
import commands.Command;
import commands.ItemCommand;
import data.ActionTemplate;
import systems.BattleEngine;
import systems.states.GameState;
import ui.GameView;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsibility: Wait for and validate player input, and handle selecting
 */
public class PlayerTurnState implements GameState {
    @Override
    public GameState onUpdate(BattleEngine engine, GameView view) {
        /*
        1. Get abilities from entity.
        2. Attach related commands to those abilities
        3. Once ability is selected, depending on command, display target dialogue
        4. Once confirmed, add action to actionManager to handle outcome
        5. Move to Resolve
        */
        List<ActionTemplate> actions = new ArrayList<>();

        for (String id : engine.getEntityManager().getEntity(engine.getFirstTurnEntity()).getAbilities()) {
            actions.add(engine.retrieveDbAbility(id));
        }

        List<Command> commands = new ArrayList<>();
        for (ActionTemplate a : actions) {
            switch (a.type) {
                case ACTION_TO:
                    commands.add(new ActionToCommand(a.name));
                    break;
                case ACTION_SELF:
                    commands.add(new ActionSelfCommand(a.name));
                    break;
                case ITEM:
                    commands.add(new ItemCommand(a.name));
                    break;
            }
        }

       Command choice = view.PromptUserChoice("Select an action: ", commands);
       choice.execute(engine);

       return this;
    }
}
