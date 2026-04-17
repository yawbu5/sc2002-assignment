package data;

import java.util.List;

public class ActionTemplate {
    public final String id;
    public final String name;
    public final ActionType type;
    public final Boolean aoe;
    public final int cooldown;
    public final List<EffectTemplate> effects;

    /**
     * Data template class for Action Abilities
     */
    public ActionTemplate(String id, String name, ActionType type, Boolean aoe, int cooldown, List<EffectTemplate> effects) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.aoe = aoe;
        this.cooldown = cooldown;
        this.effects = effects;
    }
}