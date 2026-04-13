package commands;

import systems.BattleEngine;

public class ActionToCommand implements Command {
    private final String msg;

    public ActionToCommand(String msg) {
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
