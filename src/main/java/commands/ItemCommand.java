package commands;

import systems.BattleEngine;
import systems.states.battle.BattleData;

import java.util.Collections;

public class ItemCommand implements Command {
    private final String msg;
    private final int casterId;
    private final int targetId;
    private final String itemid;
    private final BattleData data;

    public ItemCommand(String msg, int casterId, int targetId, String itemid, BattleData data) {
        this.msg = msg;
        this.casterId = casterId;
        this.targetId = targetId;
        this.itemid = itemid;
        this.data = data;
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
