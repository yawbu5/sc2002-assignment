package systems;

import data.Ability;
import data.EntityTemplate;
import data.GameResources;
import data.Wave;
import systems.states.GameState;
import systems.states.battle.EndState;
import systems.states.InitialiseState;
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
    private GameResources db;
    private EntityManager em;
    private ActionManager am;
    private GameState gameState;
    private GameView view;
    private Wave difficulty;
    private EntityTemplate selectedPlayer;
    private ArrayList playerInventory;

    public BattleEngine(GameResources db) {
        this.db = db;
    }

    /**
     * Game initialisation prompt
     */
    public void start() {
        gameState = new InitialiseState();

        while (!(gameState instanceof EndState)) {
            GameState potentialState = gameState.onUpdate(this, this.view);

            if (potentialState != gameState) {
                gameState = potentialState;
                potentialState.onEnter(this, this.view);
            }
        }
    }

    public void startEnitityManager(List<EntityTemplate> entities) {
        this.em = new EntityManager();
        em.AddEntitiesFromList(entities);
    }

    public List<Ability> retrieveDbAbilities() {
        return db.abilities;
    }

    public List<EntityTemplate> retrieveDbEntities() {
        return db.entityTemplates;
    }

    public List<Wave> retrieveDbWaves() {
        return db.waves;
    }

    public void setDifficulty(Wave wave) {
        this.difficulty = wave;
    }

    public void setSelectedPlayer(EntityTemplate e) {
        this.selectedPlayer = e;
    }

    public void subscribe(GameView view) {
        this.view = view;
    }
}
