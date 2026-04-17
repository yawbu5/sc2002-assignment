package systems.states.battle;

import commands.*;
import data.ActionTemplate;
import data.ActionType;
import systems.BattleEngine;
import systems.Entity;
import systems.EntityType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TargetSelectState implements BattleState {
    private final ActionType actionType;
    private final String actionId;
    private boolean initialised = false;

    public TargetSelectState(String actionId, ActionType type) {
        this.actionId = actionId;
        this.actionType = type;
    }

    @Override
    public BattleState transition(BattleData data, BattleEngine engine) {
        // List of targets
        // Back option

        if (!initialised) {
            ActionTemplate selectedAction = engine.retrieveDbAction(this.actionId);
            List<Command> commands = new ArrayList<>();

            if (this.actionType == ActionType.ACTION_TO) {
                // 1. pick a target. 2. go back to selection.
                for (Entity e : engine.getEntityManager().getEntityByType(EntityType.ENEMY)) {
                    commands.add(new ActionToCommand(e.getName(), actionId));
                }
                commands.add(new MenuCommand("Go back", () -> {}));
            } else {
                // 1. apply to self. 2. go back to selection
                commands.add(new ActionSelfCommand("You"));
                commands.add(new MenuCommand("Go back", () -> {}));
            }

            engine.notifyMenuObservers(o -> o.onChoicePrompt("Select a target: ", commands));

            initialised = true;
        }

        Command result = engine.retrieveLatestCommand();

        if (result == null) {
            return this;
        }

        if (result instanceof MenuCommand) {
            if (data.currentTurnIsPlayer())
                return new PlayerTurnState();
            else
                return new EnemyTurnState();
        } else if (result instanceof ActionSelfCommand || result instanceof ActionToCommand) {
            return new ResolveTurnState();
        }

        return this;
    }
}
