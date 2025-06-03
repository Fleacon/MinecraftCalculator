package model;

import model.effekte.*;

import java.util.ArrayList;

/**
 * Repräsentiert den Spieler im Minecraft-Spiel.
 * Der Spieler kann Waffen nutzen und Effekten unterliegen.
 */
public class Player {
    private int basisSchaden;
    private Waffe waffe;

    private ArrayList<Effekt> effekte = new ArrayList<Effekt>();

    /**
     * Erstellt einen neuen Spieler mit Standardwerten.
     */
    public Player() {
        basisSchaden = 2;
    }

    /**
     * Wechselt die vom Spieler geführte Waffe.
     *
     * @param waffe Die neue Waffe
     */
    public void waffeWechseln(Waffe waffe) {
        this.waffe = waffe;
    }

    /**
     * Fügt dem Spieler einen Effekt hinzu.
     *
     * @param effekt Der hinzuzufügende Effekt
     */
    public void effektHinzufügen(Effekt effekt){
        effekte.add(effekt);
    }

    /**
     * Berechnet die Angriffskraft des Spielers basierend auf seiner Waffe.
     *
     * @return Die berechnete Angriffskraft
     */
    public int berechneAngriffskraft(){
        if(waffe != null)
            return waffe.berechneSchaden();
        return basisSchaden;
    }

    public int getBasisSchaden() {
        return basisSchaden;
    }

    public Waffe getWaffe() {
        return waffe;
    }

    public ArrayList<Effekt> getEffekte() {
        return effekte;
    }

    public void setEffekte(ArrayList<Effekt> effekte) {
        this.effekte = effekte;
    }

    /**
     * Gibt eine String-Darstellung des Spielers zurück.
     *
     * @return Eine textuelle Repräsentation des Spielers
     */
    public String toString() {
        return "BasisSchaden: " + basisSchaden;
    }
}
