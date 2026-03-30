package src;

public class Ability {
    public enum AbilityType {
        ACTION_TO,
        ACTION_SELF,
        ABILITY,
        ITEM
    }
    public String name;
    public String desc;
    public AbilityType type;
    public Boolean aoe;
    public int val;
    public int cooldown;

    public Ability(String name, String desc, AbilityType type, Boolean aoe, int val, int cooldown) {
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.aoe = aoe;
        this.val = val;
        this.cooldown = cooldown;
    }

    //public Ability(AbilityConfig cfg) {
    //    this.name = cfg.name;
    //    this.desc = cfg.desc;
    //    this.type = cfg.type;
    //    this.aoe = cfg.aoe;
    //    this.val = cfg.val;
    //    this.cooldown = cfg.cooldown;
    //}
}