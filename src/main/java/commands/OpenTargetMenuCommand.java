package commands;

import data.ActionType;
import systems.BattleEngine;

public class OpenTargetMenuCommand implements Command {
    private final String msg;
    public final String actionId;
    public final ActionType actionType;

    public OpenTargetMenuCommand(String msg, String actionId, ActionType actionType) {
        this.msg = msg;
        this.actionId = actionId;
        this.actionType = actionType;
    }

    @Override
    public String getDisplayText() {
        return this.msg;
    }

    @Override
    public void execute(BattleEngine engine) {

    }
}
