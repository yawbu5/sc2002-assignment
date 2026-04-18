package systems.states;

import commands.Command;
import commands.MenuCommand;
import commands.OpenTargetMenuCommand;
import data.ActionTemplate;
import data.Wave;
import systems.BattleEngine;
import systems.Entity;
import systems.states.battle.BattleData;
import systems.states.battle.BattleState;
import systems.states.battle.InitialiseState;
import systems.states.menu.ExitGameState;
import systems.states.menu.SelectCharacterState;

import java.util.ArrayList;
import java.util.List;

public class BattleSession implements GameState {

    BattleData game;

    public BattleSession(Wave selectedDifficulty) {
        this.game = new BattleData();
        game.setDifficulty(selectedDifficulty);
    }

    public static List<Command> buildActionsList(BattleData data, BattleEngine engine) {
        List<ActionTemplate> actions = new ArrayList<>();
        Entity e = engine.getEntityManager().getEntity(data.getCurrentTurnEntityId());

        for (String id : e.getAbilities()) {
            actions.add(engine.retrieveDbAction(id));
        }

        List<Command> commands = new ArrayList<>();
        for (ActionTemplate a : actions) {
            if (e.activeActions.getOrDefault(a.id, 0) > 0) {
                commands.add(new MenuCommand(a.name + " (COOLDOWN: " + e.activeActions.get(a.id) + " TURNS)", () -> {
                }));
                continue;
            }
            commands.add(new OpenTargetMenuCommand(a.name, a.id, a.type));
        }

        return commands;
    }

    @Override
    public GameState onUpdate(BattleEngine engine) {
        BattleState currentState = new InitialiseState().transition(game, engine);

        while (!(currentState == null)) {
            BattleState nextState = currentState.transition(this.game, engine);

            if (nextState != currentState) {
                currentState = nextState;
            }
        }

        if (game.requestRestart) {
            return new SelectCharacterState();
        } else {
            return new ExitGameState();
        }
    }
}
