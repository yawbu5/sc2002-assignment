package data;

import systems.EntityType;

import java.util.List;

public class EntityTemplate {
    public final String name;
    public EntityType type;
    public final int hp;
    public final int attack;
    public final int defence;
    public final int speed;
    public final List<String> abilities;

    /**
     * Data template class for Entities
     */
    public EntityTemplate(String name, EntityType type, int hp, int attack, int defence, int speed, List<String> abilities) {
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defence = defence;
        this.speed = speed;
        this.abilities = abilities;
    }
}
