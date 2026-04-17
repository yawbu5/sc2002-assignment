package commands;

import systems.BattleEngine;

import java.util.List;

public class SelectTargetCommand implements Command {
    private final Integer casterId;
    private List<Integer> targetIds;
    private final String actionId;

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
