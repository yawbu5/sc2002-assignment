package src;

public class Ability {
    public enum AbilityType {
        ACTION_TO,
        ACTION_SELF,
        ABILITY,
        ITEM
    }

    public String id;
    public String name;
    public String desc;
    public AbilityType type;
    public Boolean aoe;
    public int val;
    public int cooldown;

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