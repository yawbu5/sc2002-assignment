package commands;

import systems.BattleEngine;

public class MenuCommand implements Command {
    private final String title;
    private final Runnable action;

    public MenuCommand(String title, Runnable action) {
        this.title = title;
        this.action = action;
    }

    @Override
    public String getDisplayText() {
        return title;
    }

    @Override
    public void execute(BattleEngine engine) {
        action.run();
    }
}
