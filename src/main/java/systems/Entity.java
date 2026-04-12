package systems;

import data.EntityTemplate;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    public enum EntityType {
        ENEMY,
        PLAYER
    }

    private transient int id;

    private final String name;
    private EntityType type;
    private final int maxHp;
    private int attack;
    private int defence;
    private int speed;
    private final List<String> abilities;
    private int currHp;

    private transient List<Action> actions;

    /**
     * Entity builder blueprint from JSON
     */
    public Entity(String name, EntityType type, int hp, int attack, int defence, int speed, List<String> abilities) {
        this.name = name;
        this.type = type;
        this.maxHp = hp;
        this.currHp = hp;
        this.attack = attack;
        this.defence = defence;
        this.speed = speed;
        this.abilities = abilities;

        // Transforming ability templates into dynamic actions
        this.actions = new ArrayList<>();
        for (String id : this.abilities) {
            this.actions.add(new Action(id));
        }
    }

    /**
     * Primary method for creating gameplay Entities with trackable UID
     * Shallow copy ability list, don't need so many strings in memory
     */
    public static Entity CreateEntity(int id, EntityTemplate e) {
        Entity newEntity = new Entity(e.name, e.type, e.hp, e.attack, e.defence, e.speed, e.abilities);
        newEntity.id = id;
        return newEntity;
    }

    public String getName() {
        return this.name;
    }
}
