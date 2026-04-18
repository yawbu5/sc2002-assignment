package commands;

import systems.BattleEngine;

import java.util.Collections;
import java.util.List;

public class ActionCommand implements Command {
    private final String msg;
    private final int casterId;
    private final List<Integer> targetIds;
    private final String actionId;

    // default single-target (non aoe)
    public ActionCommand(String msg, int casterId, int targetId, String actionId) {
        this.msg = msg;
        this.casterId = casterId;
        this.targetIds = Collections.singletonList(targetId);
        this.actionId = actionId;
    }

    // multi-target (aoe)
    public ActionCommand(String msg, int casterId, List<Integer> targetIds, String actionId) {
        this.msg = msg;
        this.casterId = casterId;
        this.targetIds = targetIds;
        this.actionId = actionId;
    }

    @Override
    public String getDisplayText() {
        return this.msg;
    }

    @Override
    public void execute(BattleEngine engine) {
        engine.getActionManager().processAction(this.casterId, this.targetIds, this.actionId);
    }
}
