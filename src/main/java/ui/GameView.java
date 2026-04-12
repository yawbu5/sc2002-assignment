package ui;

import systems.BattleEngine;
import commands.Command;

import java.util.List;

public interface GameView {
    // TODO: updating stat view

    // Connects the view to the engine and enables messaging
    void connectEngine(BattleEngine engine);

    // Helper for displaying one point of data at a time
    // i.e., singular message, damage feedback, etc.
    void DisplayMessage(String txt);

    // Helper for displaying multiple points of info at the same time
    // i.e., menus, choices, etc.
    void DisplayList(List<String> txts);

    Command PromptUserInput(String msg, Command cmd);

    Command PromptUserChoice(String msg, List<Command> options);
}
