package data;

import systems.EntityType;

import java.util.List;

public class EntityTemplate {
    public final String name;
    public final int hp;
    public final int attack;
    public final int defence;
    public final int speed;
    public final List<String> abilities;
    public final String specialAbilityId;
    public EntityType type;

    /**
     * Data template class for Entities
     */
    public EntityTemplate(String name, int hp, int attack, int defence, int speed, List<String> abilities, String specialAbilityId, EntityType type) {
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defence = defence;
        this.speed = speed;
        this.abilities = abilities;
        this.specialAbilityId = specialAbilityId;
        this.type = type;
    }
}
