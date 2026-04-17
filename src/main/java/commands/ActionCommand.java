package commands;

import systems.BattleEngine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ActionCommand implements Command {
    private final String msg;
    private final int casterId;
    private final int targetId;
    private final String actionId;

    public ActionCommand(String msg, int casterId, int targetId, String actionId) {
        this.msg = msg;
        this.casterId = casterId;
        this.targetId = targetId;
        this.actionId = actionId;
    }

    @Override
    public String getDisplayText() {
        return this.msg;
    }

    @Override
    public void execute(BattleEngine engine) {
        List<Integer> targetlist = Collections.singletonList(this.targetId);
        engine.getActionManager().processAction(this.casterId, targetlist, this.actionId);
    }
}
