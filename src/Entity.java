package src;

import java.util.List;

public class Entity {
    private enum EntityType {
        ENEMY,
        PLAYER
    }

    public String name;
    public EntityType type;
    public int currHp;
    public int maxHp;
    public int attack;
    public int defence;
    public int speed;
    public List<String> abilities;

    public Entity(String name, EntityType type, int hp, int attack, int defence, int speed, List<String> abilities) {
        this.name = name;
        this.type = type;
        this.maxHp = this.currHp = hp;
        this.attack = attack;
        this.defence = defence;
        this.speed = speed;
        this.abilities = abilities;
    }

    public static Entity CreateEntity(Entity e) {
        return new Entity(e.name, e.type, e.maxHp, e.attack, e.defence, e.speed, e.abilities);
    }

    void Update(Action a) {
        // TODO: loop over effects
    }
}
