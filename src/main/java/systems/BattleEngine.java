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

    private final List<BattleObserver> battleObservers = new ArrayList<>();
    private final List<MenuObserver> menuObservers = new ArrayList<>();

    // Why use ConcurrentLinkedQueue?
    // Because the guy on stackoverflow told me to.
    // ...but apparently it's a thread-safe method in case we implement graphics libraries for the UI like JavaFX.
    private final Queue<Command> commandQueue = new ConcurrentLinkedQueue<>();

    // TODO: ITEMS, ABILITIES ETC.
    public BattleEngine(GameResources db) {
        this.database = db;
    }

    /**
     * Game initialisation prompt
     */
    public void start() {
        GameState gameState = new SelectCharacterState();

        while (!(gameState instanceof ExitGameState)) {
            GameState potentialState = gameState.onUpdate(this);

            if (potentialState != gameState) {
                gameState.onExit(this);

                gameState = potentialState;
                gameState.onEnter(this);
            }
        }

        clearBattleObservers();
        clearMenuObservers();
    }

    public void addBattleObserver(BattleObserver obs) {
        this.battleObservers.add(obs);
    }

    private void clearBattleObservers() {
        this.battleObservers.clear();
    }

    public void notifyBattleObservers(Consumer<BattleObserver> event) {
        for (BattleObserver obs : battleObservers) {
            event.accept(obs);
        }
    }

    public void addMenuObserver(MenuObserver obs) {
        this.menuObservers.add(obs);
    }

    private void clearMenuObservers() {
        this.menuObservers.clear();
    }

    public void notifyMenuObservers(Consumer<MenuObserver> event) {
        for (MenuObserver obs : menuObservers) {
            event.accept(obs);
        }
    }

    /**
     * Prepare a new command to be executed sequentially.
     */
    public void queueNextCommand(Command cmd) {
        this.commandQueue.offer(cmd);
    }

    public Command retrieveLatestCommand() {
        return this.commandQueue.poll();
    }

    public void clearCommands() {
        this.commandQueue.clear();
    }

    public void startEntityManager(List<EntityTemplate> entities) {
        this.em = new EntityManager();
        this.em.addEntitiesFromList(entities);
    }

    public void startActionManager() {
        this.am = new ActionManager(this);
    }

    public EntityManager getEntityManager() {
        return this.em;
    }

    public ActionManager getActionManager() {
        return this.am;
    }

    public StatusManager getStatusManager() {
        return this.sm;
    }

    public ArrayList<String> getPlayerInventory() {
        return this.playerInventory;
    }

    public void addToInventory(String a) {
        this.playerInventory.add(a);
    }

    public void removeFromInventory(String id) {
        this.playerInventory.remove(id);
    }

    public void clearInventory() {
        this.playerInventory.clear();
    }

    public List<ActionTemplate> retrieveDbActions() {
        return database.abilities;
    }

    public ActionTemplate retrieveDbAction(String actionId) {
        if (database.abilities != null) {
            return database.abilities.stream()
                    .filter(a -> actionId.equals(a.id))
                    .findFirst()
                    .orElse(null);
        } else {
            return null;
        }
    }

    public List<EntityTemplate> retrieveDbEntities() {
        return database.entityTemplates;
    }

    public EntityTemplate retrieveDbEntity(String enemyName) {
        if (database.entityTemplates != null) {
            return database.entityTemplates.stream()
                    .filter(e -> enemyName.equals(e.name))
                    .findFirst()
                    .orElse(null);
        } else {
            return null;
        }
    }

    public List<Wave> retrieveDbWaves() {
        return database.waves;
    }

    public void setSelectedPlayer(EntityTemplate e) {
        this.selectedPlayer = e;
    }

    public EntityTemplate getSelectedPlayer() {
        return this.selectedPlayer;
    }
}
