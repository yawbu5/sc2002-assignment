package src;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Entity {
    public enum EntityType {
        ENEMY,
        PLAYER
    }

    public final String name;
    public EntityType type;
    @SerializedName("hp")
    public final int maxHp;
    public final int attack;
    public final int defence;
    public final int speed;
    public final List<String> abilities;
    public int currHp;

    public Entity(String name, EntityType type, int hp, int attack, int defence, int speed, List<String> abilities) {
        this.name = name;
        this.type = type;
        this.maxHp = hp;
        this.currHp = hp;
        this.attack = attack;
        this.defence = defence;
        this.speed = speed;
        this.abilities = abilities;
    }

    public static Entity CreateEntity(Entity e) {
        // probably shaky deep copy used for abilities
        return new Entity(e.name, e.type, e.maxHp, e.attack, e.defence, e.speed, e.abilities);
    }
}
