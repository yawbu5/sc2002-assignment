package commands;

import systems.BattleEngine;

/**
 * For most cases, a Command is a User Input that relates directly to
 * the player-side gameplay. This may be extended for the AI enemy's usage as well.
 */
public interface Command {
    String getDisplayText();

    void execute(BattleEngine engine);
}
