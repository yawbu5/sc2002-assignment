package systems.states.battle;

import commands.*;
import data.ActionType;
import systems.BattleEngine;
import systems.Entity;
import systems.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            List<Command> commands = new ArrayList<>();

            if (this.actionType == ActionType.ACTION_TO) {
                // 1. pick a target. 2. go back to selection.
                for (Entity e : engine.getEntityManager().getAliveEntitiesByType(EntityType.ENEMY)) {
                    commands.add(new ActionCommand(e.getName(), data.getCurrentTurnEntityId(), e.getId(), this.actionId));
                }
                commands.add(new MenuCommand("Go back", () -> {}));
            } else {
                // 1. apply to self. 2. go back to selection
                int player = data.getTurnOrder().get(data.currentTurn);
                commands.add(new ActionCommand("You", player, player, this.actionId));
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
        } else if (result instanceof ActionCommand) {
            engine.queueNextCommand(result);
            return new ResolveTurnState();
        }

        return this;
    }
}
