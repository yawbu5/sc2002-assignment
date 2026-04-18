package view.console;

import commands.Command;
import observable.BattleObserver;
import observable.MenuObserver;
import systems.BattleEngine;
import systems.states.battle.BattleData;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleView implements MenuObserver, BattleObserver {
    private final Scanner sc;
    private BattleEngine engine;
    /**
     * Game information tracking
     */
    private int roundCount = 1;
    private int waveCount = 1;
    private final ArrayList<EntityStats> entities = new ArrayList<>();

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

    @Override
    public void onLogAction(String msg) {
        System.out.println();
        System.out.println(msg);
    }

    @Override
    public void onWaveSpawn(int waveNo) {
        this.waveCount = waveNo;

        System.out.println();
        System.out.println("WAVE " + (this.waveCount - 1) + " CLEARED!");
        System.out.println("SPAWNING WAVE " + this.waveCount);
    }

    @Override
    public void onEntitySpawn(String entId) {

    }

    @Override
    public void onRoundStart(int roundCount) {
        this.roundCount = roundCount;

        System.out.println();
        System.out.println("Start of round " + roundCount);
    }

    @Override
    public void onRoundEnd(int roundCount) {
        this.roundCount = roundCount;

        System.out.println();
        System.out.println("End of round " + roundCount);
    }

    @Override
    public void onUpdateStats(List<Integer> stats) {

    }

    @Override
    public void onEffectExpired(String effect, String name) {
        System.out.printf("%nEFFECT EXPIRED: %s on %s %n", effect, name);
    }

    @Override
    public void onEffectApplied(String effect, String caster, String target, int duration) {
        System.out.printf("%nEFFECT APPLIED: %s applies %s on %s for %d turns%n", caster, effect, target, duration);
    }

    @Override
    public void onGameWin(BattleData data) {
        System.out.println();
        System.out.println("Congratulations, you have defeated all your enemies.");
        String result = String.format("Result: Player Victory | Remaining HP: %d | Total Rounds: %d | Remaining items: %d", 69, data.getRoundCounter(), 69);
        System.out.println(result);
    }

    @Override
    public void onGameLose(BattleData data) {
        System.out.println();
        System.out.println("Don't give up, try again!");
        String result = String.format("Result: Player Defeat | Remaining HP: %d | Total Rounds: %d | Remaining items: %d", 69, data.getRoundCounter(), 69);
        System.out.println(result);
    }

    @Override
    public void onChoicePrompt(String msg, List<Command> options) {
        int input = -1;
        boolean entered = false;
        while (input < 1 || input > options.size()) {
            System.out.println();
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

        engine.queueNextCommand(options.get(input - 1));
    }

    @Override
    public void onDisplayMessage(String msg) {
        System.out.println();
        System.out.println(msg);
    }
}
