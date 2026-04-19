package commands;

import systems.BattleEngine;

import java.util.ArrayList;
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
        updateInventoryObs(engine);
        engine.getActionManager().processAction(this.casterId, this.targetIds, this.itemid);
    }

    public static void updateInventoryObs(BattleEngine engine) {
        ArrayList<String> inventory = new ArrayList<>();
        for (String id : engine.getPlayerInventory()) {
            String name = engine.retrieveDbAction(id).name;
            inventory.add(name);
        }
        engine.notifyBattleObservers(o -> o.onUpdateInventory(inventory));
    }
}
