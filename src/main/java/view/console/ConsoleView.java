package view.console;

import commands.Command;
import observable.BattleObserver;
import observable.MenuObserver;
import systems.BattleEngine;

import java.util.*;
import java.util.stream.Collectors;


public class ConsoleView implements MenuObserver, BattleObserver {
    private final Scanner sc;
    private final Map<Integer, EntityStats> entities = new HashMap<>();
    private List<String> playerInventory = new ArrayList<>();
    private BattleEngine engine;

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
        this.entities.clear();

        System.out.println();
        System.out.println("WAVE " + (waveNo - 1) + " CLEARED!");
        System.out.println("SPAWNING WAVE " + waveNo + "...");
    }

    @Override
    public void onRoundStart(int roundCount) {
        // Game information tracking

        System.out.println();
        System.out.println("Start of round " + roundCount);

        for (EntityStats e : entities.values()) {
            String playerStatus = (e.type.equals("PLAYER")) ? " (YOU)" : "";
            String killedStatus = (e.currHP <= 0) ? " (KILLED)" : "";

            String msg = e.name + playerStatus + " | HP: " + e.currHP + "/" + e.maxHP + ", DEF: " + e.defence + ", SPD: " + e.speed + killedStatus;
            System.out.printf("%n%s%n", msg);
        }
    }

    @Override
    public void onDamage(int casterId, int targetId, String actionName, int damageDealt, int oldHp, boolean killed) {
        EntityStats caster = this.entities.get(casterId);
        EntityStats target = this.entities.get(targetId);

        if (killed) {
            System.out.printf("%nKILLED: %s -> %s -> %s: HP: %d - %d -> %d X ELIMINATED%n", caster.name, actionName, target.name, oldHp, damageDealt, target.currHP);
        } else {
            System.out.printf("%nDAMAGED: %s -> %s -> %s: HP: %d - %d -> %d%n", caster.name, actionName, target.name, oldHp, damageDealt, target.currHP);
        }
    }

    @Override
    public void onHeal(int targetId, int healAmount) {
        EntityStats target = this.entities.get(targetId);
        System.out.printf("%nHEALED: %s healed for %d up to %d / %d HP.%n", target.name, healAmount, target.currHP, target.maxHP);
    }

    @Override
    public void onUpdateStats(int id, String type, String name, int currHp, int maxHp, int def, int spd, int atk) {
        // if entity doesn't exist, insert. otherwise just update its HP since it's the only thing that can change.
        this.entities.compute(id, (k, v) -> (v == null) ? new EntityStats(name, type, currHp, maxHp, atk, def, spd) : v.update(currHp));
    }

    @Override
    public void onUpdateInventory(List<String> items) {
        this.playerInventory = new ArrayList<>(items);
    }

    @Override
    public void onEffectExpired(String effect, String name) {
        System.out.printf("%nEFFECT EXPIRED: %s on %s %n", effect, name);
    }

    @Override
    public void onEffectApplied(String effect, String caster, String target, int duration) {
        if (duration == -1) {
            System.out.printf("%nEFFECT APPLIED: %s applies %s on %s %n", caster, effect, target);
        } else {
            System.out.printf("%nEFFECT APPLIED: %s applies %s on %s for %d turns%n", caster, effect, target, duration);
        }
    }

    @Override
    public void onGameStart() {
        EntityStats player = entities.get(0);
        System.out.printf("%nPlayer: %s, Player Stats: HP: %d, ATK: %d, DEF: %d, SPD: %d%n", player.name, player.maxHP, player.attack, player.defence, player.speed);
        System.out.printf("Items: %s%n", buildInventoryString());
    }

    @Override
    public void onGameWin(int roundCount) {
        EntityStats player = entities.get(0);
        System.out.println();
        System.out.println("Congratulations, you have defeated all your enemies.");
        String result = String.format("Result: Player Victory | Remaining HP: %d / %d | Total Rounds: %d | Remaining items: %s", player.currHP, player.maxHP, roundCount, buildInventoryCountString());
        System.out.println(result);
        this.entities.clear();
    }

    @Override
    public void onGameLose(int roundCount) {
        EntityStats player = entities.get(0);
        System.out.println();
        System.out.println("Don't give up, try again!");
        String result = String.format("Result: Player Defeat | Remaining HP: %d / %d | Total Rounds: %d | Remaining items: %s", player.currHP, player.maxHP, roundCount, buildInventoryCountString());
        System.out.println(result);
        this.entities.clear();
    }

    private String buildInventoryString() {
        return String.join(" + ", this.playerInventory);
    }

    private String buildInventoryCountString() {
        String result = playerInventory.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
                .entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining(", "));
        return result.isEmpty() ? "None" : result;
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
        System.out.println(msg);
    }
}
