package src;

public class Game {
    public static enum GAME_STATE {
        MENU,
        BATTLE,
        RESULT
    }

    final EntityManager eManager;
    final ActionManager aManager;
    private GAME_STATE gState = GAME_STATE.MENU;

    public Game() {

        while (true) {

        }
    }

    private void setGameState(GAME_STATE new_state) {
       gState = new_state; 
    }

    private void getGameState() {
        return gState;
    }
}
