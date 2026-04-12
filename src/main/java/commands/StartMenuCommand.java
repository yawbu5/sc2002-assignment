package commands;

import systems.BattleEngine;

public class StartMenuCommand implements Command {
    private String title;
    private Runnable action;

    public StartMenuCommand(String title, Runnable action) {
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
