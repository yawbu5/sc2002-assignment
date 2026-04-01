package src;

public class Entity {
    public enum EntityType {
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

    public Entity(String name, EntityType type, int hp, int attack, int defence, int speed) {
        this.name = name;
        this.type = type;
        this.maxHp = this.currHp = hp;
        this.attack = attack;
        this.defence = defence;
        this.speed = speed;
    }

    void Update(Action a) {
        // TODO: loop over effects
    }
}
