package model;

public class Rechner {
    private Player player;
    private Mob mob;

    public Rechner(Mob mob, Player player) {
        this.mob = mob;
        this.player = player;
    }

    public double berechneSchaden() {
        RedWerte dmgRedWerte = mob.berechneSchadensreduktion();
        int eingehenderSchaden = player.berechneAngriffskraft();

        if (dmgRedWerte.rüstungspunkte() <= 0) {
            return eingehenderSchaden; // Keine Rüstung -> voller Schaden
        }

        double baseDefense = dmgRedWerte.rüstungspunkte() / 5.0;
        double toughnessDefense = dmgRedWerte.rüstungspunkte() - (4.0 * eingehenderSchaden) / (dmgRedWerte.härte() + 8.0);
        double effectiveDefense = Math.max(baseDefense, toughnessDefense);
        effectiveDefense = Math.min(20.0, effectiveDefense);

        double damageMultiplier = 1.0 - (effectiveDefense / 25.0);

        return eingehenderSchaden * damageMultiplier;
    }

    public void changeMob(Mob mob) {
        this.mob = mob;
    }
}
