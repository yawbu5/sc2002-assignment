package data;

/**
 * Status effect sub-identifier data template for actions
 * <p>
 * i.e., Actions hold this type to identify what status in "statuses.json" they link to.
 */
public class EffectTemplate {
    public final String type;       // only required type (identifier for effect)
    public String id;               // optional id for status effect id to activate
    public int duration;
    public double val;

    public EffectTemplate(String type, String id, int duration, double val) {
        this.type = type;
        this.id = id;
        this.duration = duration;
        this.val = val;
    }
}
