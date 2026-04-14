package ui.console;

import commands.Command;
import observable.BattleObserver;
import observable.MenuObserver;
import systems.BattleEngine;
import ui.GameView;

import java.util.List;
import java.util.Scanner;

public class ConsoleView implements GameView, MenuObserver, BattleObserver {
    private BattleEngine engine;
    private final Scanner sc;
    private boolean running;

    public ConsoleView() {
        this.sc = new Scanner(System.in);
    }

    public void connectEngine(BattleEngine engine) {
        if (engine == null) {
            return;
        }

        this.engine = engine;
        this.engine.addMenuObserver(this);
        this.engine.addBattleObserver(this);
    }

    public void DisplayMessage(String txt) {
        System.out.println(txt);
    }

    public void DisplayList(List<String> txts) {
        for (String t : txts) {
            DisplayMessage(t);
        }
    }

    public Command PromptUserChoice(String msg, List<Command> options) {
        int input = -1;
        boolean entered = false;
        while (input < 1 || input > options.size()) {
            if (!entered) {
                entered = true;
            } else {
                System.out.println("Erroneous input, try again!");
            }
            System.out.println(msg);

            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ": " + options.get(i).getDisplayText());
            }
            System.out.print("> ");
            try {
                input = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException ignored) {
            }
        }

        return options.get(input - 1);
    }

    @Override
    public void OnLogAction(String msg) {

    }

    @Override
    public void OnWaveSpawn(int waveNo) {

    }

    @Override
    public void OnEntitySpawn(String entId) {

    }

    @Override
    public void onChoicePrompt(String msg, List<Command> options) {
        int input = -1;
        boolean entered = false;
        while (input < 1 || input > options.size()) {
            if (!entered) {
                entered = true;
            } else {
                System.out.println("Erroneous input, try again!");
            }
            System.out.println(msg);

            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ": " + options.get(i).getDisplayText());
            }
            System.out.print("> ");
            try {
                input = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException ignored) {
            }

            engine.queueNextCommand(options.get(input - 1));
        }
    }

    @Override
    public void onDisplayMessage(String msg) {
        System.out.println(msg);
    }
}
