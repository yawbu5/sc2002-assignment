package commands;

import systems.BattleEngine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemCommand implements Command {
    private final String msg;
    private final int casterId;
    private final int targetId;
    private final String itemid;

    public ItemCommand(String msg, int casterId, int targetId, String itemid) {
        this.msg = msg;
        this.casterId = casterId;
        this.targetId = targetId;
        this.itemid = itemid;
    }

    @Override
    public String getDisplayText() {
        return this.msg;
    }

    @Override
    public void execute(BattleEngine engine) {
        engine.removeFromInventory(itemid);
        engine.getActionManager().processAction(this.casterId, Collections.singletonList(this.targetId), this.itemid);
    }
}
