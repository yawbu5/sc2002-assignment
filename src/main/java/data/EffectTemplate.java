package data;

public class EffectTemplate {
    public final String id;               // optional id for status effect id to activate
    public final String name;
    public final String effect;
    public final int val;

    public EffectTemplate(String id, String name, String effect, int val) {
        this.id = id;
        this.name = name;
        this.effect = effect;
        this.val = val;
    }
}
