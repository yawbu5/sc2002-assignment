package commands;

import systems.BattleEngine;

import java.util.List;

public class SelectTargetCommand implements Command {
    private final Integer casterId;
    private final String actionId;
    private final List<Integer> targetIds;

    public SelectTargetCommand(Integer casterId, List<Integer> targetIds, String actionId) {
        this.casterId = casterId;
        this.targetIds = targetIds;
        this.actionId = actionId;
    }

    @Override
    public String getDisplayText() {
        return "";
    }

    @Override
    public void execute(BattleEngine engine) {

    }
}
