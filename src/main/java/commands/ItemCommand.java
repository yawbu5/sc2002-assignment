package commands;

import systems.BattleEngine;

import java.util.Collections;
import java.util.List;

public class ItemCommand implements Command {
    private final String msg;
    private final int casterId;
    private final List<Integer> targetIds;
    private final String itemid;

    public ItemCommand(String msg, int casterId, int targetId, String itemid) {
        this.msg = msg;
        this.casterId = casterId;
        this.targetIds = Collections.singletonList(targetId);
        this.itemid = itemid;
    }

    public ItemCommand(String msg, int casterId, List<Integer> targetIds, String itemid) {
        this.msg = msg;
        this.casterId = casterId;
        this.targetIds = targetIds;
        this.itemid = itemid;
    }

    @Override
    public String getDisplayText() {
        return this.msg;
    }

    @Override
    public void execute(BattleEngine engine) {
        engine.removeFromInventory(itemid);
        engine.getActionManager().processAction(this.casterId, this.targetIds, this.itemid);
    }
}
