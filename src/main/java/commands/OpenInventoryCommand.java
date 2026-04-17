package commands;

import systems.BattleEngine;

/**
 * Simple message to open the inventory, purely an identifier and no other function.
 */
public class OpenInventoryCommand implements Command {
    @Override
    public String getDisplayText() {
        return "Inventory";
    }

    @Override
    public void execute(BattleEngine engine) {
    }
}
