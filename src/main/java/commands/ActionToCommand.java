package commands;

import systems.BattleEngine;

public class ActionToCommand implements Command {
    private final String msg;
    private final String id;

    public ActionToCommand(String msg, String id) {
        this.msg = msg;
        this.id = id;
    }

    @Override
    public String getDisplayText() {
        return this.msg;
    }

    @Override
    public void execute(BattleEngine engine) {
    }
}
