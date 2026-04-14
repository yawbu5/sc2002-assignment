package systems.states;

import commands.ActionSelfCommand;
import commands.ActionToCommand;
import commands.Command;
import commands.ItemCommand;
import data.ActionTemplate;
import systems.BattleEngine;

import java.util.ArrayList;
import java.util.List;

public class BattleState implements GameState {
    @Override
    public GameState onUpdate(BattleEngine engine) {
        return null;
    }

    public static List<Command> buildActionsList(BattleEngine engine) {
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
                default:
                    continue;
            }
        }
        commands.add(new ItemCommand("Use an item"));

        return commands;
    }
}
