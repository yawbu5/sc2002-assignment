package systems;

public class Cooldown {
    public final String abilityId;
    public int cooldownTimer;

    public Cooldown(String abilityId) {
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
