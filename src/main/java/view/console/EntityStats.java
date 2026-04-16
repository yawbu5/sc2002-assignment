package view.console;

/**
 * Holds the entity data for display
 */
public class EntityStats {
    public final int id;
    public final String name;
    public final int maxHP;
    public int currHP;
    public int attack;
    public int defence;
    public int speed;

    public EntityStats(int id, String name, int hp, int attack, int defence, int speed) {
        this.id = id;
        this.name = name;
        this.currHP = this.maxHP = hp;
        this.attack = attack;
        this.defence = defence;
        this.speed = speed;
    }
}
