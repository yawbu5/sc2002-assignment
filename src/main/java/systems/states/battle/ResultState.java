package systems.states.battle;

import commands.Command;
import commands.MenuCommand;
import systems.BattleEngine;

import java.util.ArrayList;
import java.util.List;

public class ResultState implements BattleState {
    private boolean initialised = false;

    @Override
    public BattleState transition(BattleData data, BattleEngine engine) {
        if (!initialised) {
            List<Command> options = new ArrayList<>();

            options.add(new MenuCommand("Restart", () -> {
                data.requestRestart = true;
            }));
            options.add(new MenuCommand("Exit", () -> {
                data.requestExit = true;
            }));

            engine.notifyMenuObservers(o -> o.onChoicePrompt("What would you like to do next?", options));
            initialised = true;
        }

        Command result = engine.retrieveLatestCommand();

        if (result == null) {
            return this;
        }

        result.execute(engine);

        if (data.requestExit || data.requestRestart) {
            return null;
        }

        return this;
    }
}
