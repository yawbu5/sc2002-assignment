package systems;

import data.EntityTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entity {
    private final String name;
    private final EntityType type;
    private final int maxHp;
    private final int attack;
    private final int defence;
    private final int speed;
    private final List<String> abilities;
    // game-unique list of cooldowns during runtime
    public transient Map<String, Integer> activeActions = new HashMap<>();
    // unique identifier assigned at runtime
    private transient int id;
    // game unique current HP tracker;
    private transient int currHp = 0;

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

    public boolean isDead() {
        return currHp <= 0;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public EntityType getType() {
        return this.type;
    }

    public int getSpeed() {
        return speed;
    }

    public int getCurrHp() {
        return currHp;
    }

    public void setCurrHp(int newHp) {
        currHp = Math.max(newHp, 0);
    }

    public List<String> getAbilities() {
        return this.abilities;
    }

    public int getDefence() {
        return defence;
    }

    public int getAttack() {
        return attack;
    }

    public int getMaxHp() {
        return maxHp;
    }
}
