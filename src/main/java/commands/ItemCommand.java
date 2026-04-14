package commands;

import systems.BattleEngine;

public class ItemCommand implements Command {
    private final String msg;

    public ItemCommand(String msg) {
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
