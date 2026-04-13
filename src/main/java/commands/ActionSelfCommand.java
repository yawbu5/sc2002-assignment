package commands;

import systems.BattleEngine;

public class ActionSelfCommand implements Command {
    private final String msg;

    public ActionSelfCommand(String msg) {
        this.msg = msg;
    }

    @Override
    public String getDisplayText() {
        return this.msg;
    }

    @Override
    public void execute(BattleEngine engine) {

    }
}
