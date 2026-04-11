package src;

import src.data.EntityTemplate;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    public enum EntityType {
        ENEMY,
        PLAYER
    }

    public final String name;
    public EntityType type;
    public final int maxHp;
    public final int attack;
    public final int defence;
    public final int speed;
    public final List<String> abilities;
    public int currHp;

    public transient List<Action> actions;

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

    public Entity(EntityTemplate e) {
        this.name = e.name;
        this.type = e.type;
        this.maxHp = e.hp;
        this.currHp = e.hp;
        this.attack = e.attack;
        this.defence = e.defence;
        this.speed = e.speed;
        this.abilities = e.abilities;

        // Transforming ability templates into dynamic actions
        this.actions = new ArrayList<>();
        for (String id : this.abilities) {
            this.actions.add(new Action(id));
        }
    }

    /**
     * probably shaky deep copy
     */
    public static Entity CreateEntity(Entity e) {
        return new Entity(e.name, e.type, e.maxHp, e.attack, e.defence, e.speed, e.abilities);
    }
}
