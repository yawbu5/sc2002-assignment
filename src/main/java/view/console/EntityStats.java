package view.console;

/**
 * Holds the entity data for display
 */
public class EntityStats {
    public final String name;
    public final String type;
    public final int maxHP;
    public final int attack;
    public final int defence;
    public final int speed;
    public int currHP;

    public EntityStats(String name, String type, int currHp, int maxHP, int attack, int defence, int speed) {
        this.type = type;
        this.name = name;
        this.currHP = currHp;
        this.maxHP = maxHP;
        this.attack = attack;
        this.defence = defence;
        this.speed = speed;
    }

    public EntityStats update(int currHP) {
        this.currHP = currHP;
        return this;
    }
}
