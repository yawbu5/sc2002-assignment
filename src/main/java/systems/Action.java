package systems;

public class Action {
    public final String abilityId;
    public int cooldownTimer;

    public Action(String abilityId) {
        this.abilityId = abilityId;
        this.cooldownTimer = 0;
    }

    public void setCooldown(int cooldown) {
        this.cooldownTimer = cooldown;
    }

    public void onTick() {
        if (this.cooldownTimer > 0)
            this.cooldownTimer--;
    }

    public boolean ready() {
        return cooldownTimer == 0;
    }
}
