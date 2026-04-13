package systems;

import data.ActionTemplate;
import data.EntityTemplate;
import data.GameResources;
import data.Wave;
import systems.states.GameState;
import systems.states.battle.EndTurnState;
import systems.states.menu.SelectCharacterState;
import systems.states.menu.SelectDifficultyState;
import ui.GameView;

import java.util.ArrayList;
import java.util.List;

public class BattleEngine {
    //private enum BATTLE_STATE {
    //    START,
    //    PLAYER_TURN,
    //    RESOLVE,
    //    ENEMY_TURN,
    //    END,
    //    RESULT
    //}
    private final GameResources database;
    private EntityManager em;
    private ActionManager am;
    private StatusManager sm;
    private GameState gameState;
    private GameView view;
    private Wave difficulty;
    private EntityTemplate selectedPlayer;
    private final ArrayList<String> playerInventory = new ArrayList<>();
    private List<Integer> turnOrder;
    private int waveCount = 0;

    // TODO: ITEMS, ABILITIES, INVENTORY ETC.
    public BattleEngine(GameResources db) {
        this.database = db;
    }

    /**
     * Game initialisation prompt
     */
    public void start() {
        this.gameState = new SelectCharacterState();

        while (!(this.gameState instanceof EndTurnState)) {
            GameState potentialState = this.gameState.onUpdate(this, this.view);

            if (potentialState != this.gameState) {
                this.gameState = potentialState;
                potentialState.onEnter(this, this.view);
            }
        }
    }

    public void startEnitityManager(List<EntityTemplate> entities) {
        this.em = new EntityManager();
        this.em.addEntitiesFromList(entities);
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

    public List<Integer> getTurnOrder() {
        return this.turnOrder;
    }

    public void setTurnOrder(List newTurnOrder) {
        this.turnOrder = newTurnOrder;
    }

    public int getFirstTurnEntity() {
        return this.turnOrder.get(0);
    }

    public void addToInventory(String a) {
        this.playerInventory.add(a);
    }

    public void removeFromInventroy(int index) {
        this.playerInventory.remove(index);
    }

    public List<ActionTemplate> retrieveDbAbilities() {
        return database.abilities;
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

    public void setDifficulty(Wave wave) {
        this.difficulty = wave;
    }

    public List<List<String>> getWaves() { return this.difficulty.waves; }

    public boolean checkWavesCleared() {
        return this.waveCount == difficulty.waves.size();
    }

    /*
     * Wave counts are one-based.
     */
    public int incrementWaveCount() {
        if (this.waveCount - 1 >= difficulty.waves.size()) {
            return -1;
        } else {
            this.waveCount++;
            return this.waveCount;
        }
    }

    public void setSelectedPlayer(EntityTemplate e) {
        this.selectedPlayer = e;
    }
    public EntityTemplate getSelectedPlayer() { return this.selectedPlayer; }

    public void subscribe(GameView view) {
        this.view = view;
    }
}
