package systems;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    public enum EntityType {
        ENEMY,
        PLAYER
    }

    public transient int id;

    public final String name;
    public EntityType type;
    public final int maxHp;
    public final int attack;
    public final int defence;
    public final int speed;
    public final List<String> abilities;
    public int currHp;

    public transient List<Action> actions;

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
    public static Entity CreateEntity(int id, Entity e) {
        Entity newEntity = new Entity(e.name, e.type, e.maxHp, e.attack, e.defence, e.speed, e.abilities);
        newEntity.id = id;
        return newEntity;
    }
}
