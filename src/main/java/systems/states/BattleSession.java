package systems.states;

import commands.ActionCommand;
import commands.ActionSelfCommand;
import commands.ActionToCommand;
import commands.Command;
import data.ActionTemplate;
import data.Wave;
import systems.BattleEngine;
import systems.states.battle.BattleData;
import systems.states.battle.BattleState;
import systems.states.battle.InitialiseState;
import systems.states.menu.ResultState;

import java.util.ArrayList;
import java.util.List;

public class BattleSession implements GameState {

    BattleData game;

    public BattleSession(Wave selectedDifficulty) {
        this.game = new BattleData();
        game.setDifficulty(selectedDifficulty);
    }

    @Override
    public GameState onUpdate(BattleEngine engine) {
        BattleState currentState = new InitialiseState().transition(game, engine);

        while (!(currentState == null)) {
            BattleState nextState = currentState.transition(this.game, engine);

            if (nextState != currentState) {
                engine.clearCommands();

                currentState = nextState;
            }
        }

        if (game.playerWins) {
            return new ResultState();
        } else {
            return new ResultState();
        }
    }


    public static List<Command> buildActionsList(BattleData data, BattleEngine engine) {
        List<ActionTemplate> actions = new ArrayList<>();

        for (String id : engine.getEntityManager().getEntity(0).getAbilities()) {
            actions.add(engine.retrieveDbAction(id));
        }


        List<Command> commands = new ArrayList<>();
        for (ActionTemplate a : actions) {
            commands.add(new ActionCommand(a.name, a.id, a.type));
        }

        return commands;
    }
}
