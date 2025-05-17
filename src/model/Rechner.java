package model;

/**
 * Berechnet die Schadensauswirkung zwischen Spieler und Mob.
 * Wendet Minecraft-Kampfmechaniken für die Schadensberechnung an.
 */
public class Rechner {
    private Player player;
    private Mob mob;

    /**
     * Erstellt einen neuen Rechner mit einem Mob und einem Spieler.
     *
     * @param mob Der Mob, der Schaden erleidet
     * @param player Der Spieler, der Schaden verursacht
     */
    public Rechner(Mob mob, Player player) {
        this.mob = mob;
        this.player = player;
    }

    /**
     * Berechnet den tatsächlichen Schaden, den der Spieler dem Mob zufügt
     *
     * @return Der berechnete Schaden
     */
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

    /**
     * Wechselt den Mob, der im Rechner verwendet wird.
     *
     * @param mob Der neue Mob
     */
    public void changeMob(Mob mob) {
        this.mob = mob;
    }
}
