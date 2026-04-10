package src.view;

import src.BattleEngine;
import java.util.Scanner;

public class ConsoleView implements GameView{
    private BattleEngine engine;
    private Scanner sc;
    private boolean running;

    public ConsoleView() {
        this.sc = new Scanner(System.in);
    }

    @Override
    public void connectEngine(BattleEngine engine) {
        if (engine == null) {
           return;
        }

        this.engine = engine;
    }

    @Override
    public void DisplayMessage(String txt) {
        System.out.println(txt);
    }

    @Override
    public String GetUserInput(String msg) {
        System.out.print("> " + msg);

        return sc.nextLine();
    }
}
