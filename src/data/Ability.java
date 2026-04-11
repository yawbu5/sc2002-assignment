package src.data;

public class Ability {
    public enum AbilityType {
        ACTION_TO,
        ACTION_SELF,
        ABILITY,
    }

    public final String id;
    public final String name;
    public final String desc;
    public final AbilityType type;
    public final Boolean aoe;
    public final int val;
    public final int cooldown;

    /**
     * Data template claass for Action Abilities
     */
    public Ability(String id, String name, String desc, AbilityType type, Boolean aoe, int val, int cooldown) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.aoe = aoe;
        this.val = val;
        this.cooldown = cooldown;
    }
}