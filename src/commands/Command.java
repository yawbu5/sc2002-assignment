package src.commands;

import src.BattleEngine;

public interface Command {
    String getDisplayText();

    void execute(BattleEngine engine);
}
