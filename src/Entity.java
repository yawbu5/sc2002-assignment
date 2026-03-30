package src;

public class Entity {
    public enum EntityType {
        ENEMY,
        PLAYER
    }
    private int id;
    private EntityType type;
    private int currHp, maxHp, attack, defence, speed;

    public Entity(int id, EntityType type, int hp, int attack, int defence, int speed) {
        this.id = id;
        this.type = type;
        this.maxHp = hp;
        this.attack = attack;
        this.defence = defence;
        this.speed = speed;
    }

    void Update(Action a) {
        // TODO: loop over effects
    }
}
