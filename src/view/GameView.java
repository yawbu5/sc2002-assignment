package src.view;

import src.BattleEngine;

public interface GameView {
    default void connectEngine(BattleEngine engine) {}
    default void DisplayMessage(String txt) {}

    default String GetUserInput(String msg) { return null; }
}
