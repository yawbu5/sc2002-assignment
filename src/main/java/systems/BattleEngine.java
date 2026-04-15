package systems;

import commands.Command;
import data.ActionTemplate;
import data.EntityTemplate;
import data.GameResources;
import data.Wave;
import observable.BattleObserver;
import observable.MenuObserver;
import systems.states.GameState;
import systems.states.menu.ExitGameState;
import systems.states.menu.SelectCharacterState;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class BattleEngine {

    private final GameResources database;
    private EntityManager em;
    private ActionManager am;
    private StatusManager sm;

    private EntityTemplate selectedPlayer;
    private final ArrayList<String> playerInventory = new ArrayList<>();

    private Wave difficulty;
    private List<Integer> turnOrder;

    /**
     * Wave count is 1-based.
     * Starts at 1 after InitialiseState loads waves.get(0).
     * Incremented by SendNextWaveState before loading the next wave.
     */
    private int waveCount = 1;

    // ─── Round tracking ─────────────────────────────────────────────────────────
    private int roundCount            = 0;
    private int turnsCompletedInRound = 0;
    private int aliveCountAtRoundStart = 0;

    // ─── Observers + command queue ───────────────────────────────────────────────
    private final List<BattleObserver> battleObservers = new ArrayList<>();
    private final List<MenuObserver>   menuObservers   = new ArrayList<>();

    // ConcurrentLinkedQueue: thread-safe for potential future JavaFX/GUI migration.
    private final Queue<Command> commandQueue = new ConcurrentLinkedQueue<>();

    public BattleEngine(GameResources db) {
        this.database = db;
    }

    // ─── Game loop ───────────────────────────────────────────────────────────────

    /**
     * Game initialisation prompt — drives the full state-machine loop.
     */
    public void start() {
        GameState gameState = new SelectCharacterState();

        while (!(gameState instanceof ExitGameState)) {
            GameState potentialState = gameState.onUpdate(this);

            if (potentialState == null) break; // safety guard

            if (potentialState != gameState) {
                gameState.onExit(this);
                clearCommands(); // prevent stale commands reaching the next state
                gameState = potentialState;
                gameState.onEnter(this);
            }
        }

        clearBattleObservers();
        clearMenuObservers();
    }

    // ─── Observer management ─────────────────────────────────────────────────────

    public void addBattleObserver(BattleObserver obs)    { this.battleObservers.add(obs); }
    public void removeBattleObserver(BattleObserver obs) { this.battleObservers.remove(obs); }
    private void clearBattleObservers()                  { this.battleObservers.clear(); }

    public void notifyBattleObservers(Consumer<BattleObserver> event) {
        for (BattleObserver obs : battleObservers) event.accept(obs);
    }

    public void addMenuObserver(MenuObserver obs)    { this.menuObservers.add(obs); }
    public void removeMenuObserver(MenuObserver obs) { this.menuObservers.remove(obs); }
    private void clearMenuObservers()                { this.menuObservers.clear(); }

    public void notifyMenuObservers(Consumer<MenuObserver> event) {
        for (MenuObserver obs : menuObservers) event.accept(obs);
    }

    // ─── Command queue ───────────────────────────────────────────────────────────

    public void queueNextCommand(Command cmd)  { this.commandQueue.offer(cmd); }
    public Command retrieveLatestCommand()     { return this.commandQueue.poll(); }
    private void clearCommands()               { this.commandQueue.clear(); }

    // ─── Entity management ───────────────────────────────────────────────────────

    public void startEntityManager(List<EntityTemplate> entities) {
        this.em = new EntityManager();
        this.sm = new StatusManager();  // fresh status slate for each battle
        this.em.addEntitiesFromList(entities);
    }

    public EntityManager getEntityManager()  { return this.em; }
    public ActionManager getActionManager()  { return this.am; }
    public StatusManager getStatusManager()  { return this.sm; }

    // ─── Turn order ──────────────────────────────────────────────────────────────

    public List<Integer> getTurnOrder()                      { return this.turnOrder; }
    public void setTurnOrder(List<Integer> newTurnOrder)     { this.turnOrder = newTurnOrder; }
    public int getFirstTurnEntity()                          { return this.turnOrder.get(0); }

    // ─── Inventory ───────────────────────────────────────────────────────────────

    public ArrayList<String> getPlayerInventory()  { return this.playerInventory; }
    public void addToInventory(String a)           { this.playerInventory.add(a); }
    public void removeFromInventory(int index)     { this.playerInventory.remove(index); }

    /** Clears inventory — used when starting a fresh game. */
    public void clearInventory()                   { this.playerInventory.clear(); }

    // ─── Database accessors ──────────────────────────────────────────────────────

    public List<ActionTemplate> retrieveDbAbilities() { return database.abilities; }

    public ActionTemplate retrieveDbAbility(String actionId) {
        if (database.abilities == null) return null;
        return database.abilities.stream()
                .filter(a -> actionId.equals(a.id))
                .findFirst().orElse(null);
    }

    public List<EntityTemplate> retrieveDbEntities() { return database.entityTemplates; }

    public EntityTemplate retrieveDbEntity(String enemyName) {
        if (database.entityTemplates == null) return null;
        return database.entityTemplates.stream()
                .filter(e -> enemyName.equals(e.name))
                .findFirst().orElse(null);
    }

    public List<Wave> retrieveDbWaves() { return database.waves; }

    // ─── Difficulty / Wave management ────────────────────────────────────────────

    public void setDifficulty(Wave wave) { this.difficulty = wave; }
    public Wave getDifficulty()          { return this.difficulty; }
    public List<List<String>> getWaves() { return this.difficulty.waves; }
    public int getWaveCount()            { return this.waveCount; }

    /**
     * Returns true when all waves for the current difficulty have been cleared.
     * waveCount is 1-based; waves.size() is the total number of waves.
     */
    public boolean checkWavesCleared() {
        return this.waveCount >= difficulty.waves.size();
    }

    /** Increments the wave counter. Called by SendNextWaveState before loading the next wave. */
    public void incrementWaveCount() {
        this.waveCount++;
    }

    // ─── Round tracking ──────────────────────────────────────────────────────────

    public int getRoundCount()              { return roundCount; }
    public void incrementRound()            { roundCount++; }

    public int getTurnsCompletedInRound()   { return turnsCompletedInRound; }
    public void incrementTurnsCompleted()   { turnsCompletedInRound++; }
    public void setTurnsCompleted(int n)    { turnsCompletedInRound = n; }

    public int getAliveCountAtRoundStart()       { return aliveCountAtRoundStart; }
    public void setAliveCountAtRoundStart(int n) { aliveCountAtRoundStart = n; }

    /**
     * Resets round-tracking counters for a new wave (called by SendNextWaveState).
     * Does NOT reset the round number — the round display continues from where it left off.
     */
    public void resetRoundTrackingForNewWave() {
        this.turnsCompletedInRound = 0;
        this.aliveCountAtRoundStart = 0;
    }

    // ─── Player template ─────────────────────────────────────────────────────────

    public void setSelectedPlayer(EntityTemplate e) { this.selectedPlayer = e; }
    public EntityTemplate getSelectedPlayer()        { return this.selectedPlayer; }

    /**
     * Resets all battle-session state for a fresh battle (Play Again / New Game).
     * Does NOT reset player selection or difficulty if called mid-flow.
     */
    public void resetBattleState() {
        this.em        = null;
        this.sm        = null;
        this.turnOrder = null;
        this.waveCount = 1;
        this.roundCount = 0;
        this.turnsCompletedInRound  = 0;
        this.aliveCountAtRoundStart = 0;
        this.playerInventory.clear();
        clearCommands();
    }
}
