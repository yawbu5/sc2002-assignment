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
    private final EntityType type;
    private final int maxHp;
    private final int attack;
    private final int defence;
    private final int speed;
    private final List<String> abilities;
    private final int currHp;

    private final transient List<Cooldown> cooldowns;

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
        this.cooldowns = new ArrayList<>();
        for (String id : this.abilities) {
            this.cooldowns.add(new Cooldown(id));
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
