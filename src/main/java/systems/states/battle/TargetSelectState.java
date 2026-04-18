package systems.states.battle;

import commands.ActionCommand;
import commands.Command;
import commands.ItemCommand;
import commands.MenuCommand;
import data.ActionEffectTemplate;
import data.ActionTemplate;
import data.ActionType;
import systems.BattleEngine;
import systems.entities.Entity;
import systems.entities.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TargetSelectState implements BattleState {
    private final ActionType actionType;
    private final String actionId;
    private final boolean isItem;
    private boolean initialised = false;

    public TargetSelectState(String actionId, ActionType type, boolean isItem) {
        this.actionId = actionId;
        this.actionType = type;
        this.isItem = isItem;
    }

    @Override
    public BattleState transition(BattleData data, BattleEngine engine) {
        // Option structure:
        // List of targets
        // Back option

        if (!initialised) {
            List<Command> commands = new ArrayList<>();

            int casterId = data.getCurrentTurnEntityId();
            Entity caster = engine.getEntityManager().getEntity(casterId);
            ActionTemplate action = engine.retrieveDbAction(actionId);
            boolean isAoe = (action != null && action.aoe);

            // messed up, hacky(?) fix for AOE targeting UX problem
            if (action != null && action.effects != null) {
                for (ActionEffectTemplate effect : action.effects) {
                    if ("ACTIVATE_ABILITY".equals(effect.type)) {
                        String specialAbilityId = caster.getSpecialAbilityId();

                        if (specialAbilityId != null && !specialAbilityId.isEmpty()) {
                            ActionTemplate specialAction = engine.retrieveDbAction(specialAbilityId);

                            if (specialAction != null && specialAction.aoe != null) {
                                isAoe = specialAction.aoe;
                            }
                        }
                    }
                }
            }

            if (this.actionType == ActionType.ACTION_TO || this.actionType == ActionType.ITEM_TO) {
                // 1. pick a target. 2. go back to selection.
                List<Entity> aliveEnemies = engine.getEntityManager().getAliveEntitiesByType(EntityType.ENEMY);

                if (isAoe) {
                   List<Integer> targetIds = aliveEnemies.stream()
                           .map(Entity::getId)
                           .collect(Collectors.toList());

                   Command command = isItem
                           ? new ItemCommand("All enemies", casterId, targetIds, this.actionId)
                           : new ActionCommand("All enemies", casterId, targetIds, this.actionId);

                   commands.add(command);
                } else {
                    for (Entity e : aliveEnemies) {
                        Command command = isItem
                                ? new ItemCommand(e.getName(), casterId, e.getId(), this.actionId)
                                : new ActionCommand(e.getName(), casterId, e.getId(), this.actionId);
                        commands.add(command);
                    }
                }

                commands.add(new MenuCommand("Go back", () -> {}));
            } else {
                // 1. apply to self. 2. go back to selection
                int player = data.getCurrentTurnEntityId();
                Command command = isItem
                        ? new ItemCommand("You", player, player, this.actionId)
                        : new ActionCommand("You", player, player, this.actionId);
                commands.add(command);
                commands.add(new MenuCommand("Go back", () -> {
                }));
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
        } else if (result instanceof ActionCommand || result instanceof ItemCommand) {
            engine.queueNextCommand(result);
            return new ResolveTurnState();
        }

        return this;
    }
}
