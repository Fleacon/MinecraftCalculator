package model;

import model.effekte.Effekt;

import java.util.ArrayList;

/**
 * Repräsentiert einen Mob im Minecraft-Spiel.
 * Mobs können Rüstung tragen und Effekten unterliegen.
 */
public class Mob {
    private String bezeichnung;
    private int hp;
    private int basisRüstungsPunkte;
    private String mobTyp;

    private Rüstung[] rüstungen = new Rüstung[4];
    private ArrayList<Effekt> effekte = new ArrayList<Effekt>();

    /**
     * Erstellt einen neuen Mob mit den angegebenen Eigenschaften.
     *
     * @param bezeichnung Der Name des Mobs
     * @param hp Die Lebenspunkte des Mobs
     * @param basisRüstungsPunkte Die Rüstungspunkte ohne zusätzliche Rüstungsteile
     * @param mobTyp Der Typ des Mobs
     */
    public Mob(String bezeichnung, int hp, int basisRüstungsPunkte, String mobTyp){
        this.bezeichnung = bezeichnung;
        this.hp = hp;
        this.basisRüstungsPunkte = basisRüstungsPunkte;
        this.mobTyp = mobTyp;
    }

    public Mob(Mob mob) {
        this.bezeichnung = mob.getBezeichnung();
        this.hp = mob.getHp();
        this.basisRüstungsPunkte = mob.getBasisRüstungsPunkte();
        this.mobTyp = mob.getMobTyp();
    }

    /**
     * Fügt dem Mob einen Effekt hinzu.
     *
     * @param effekt Der hinzuzufügende Effekt
     */
    public void effektHinzufügen(Effekt effekt){
        effekte.add(effekt);
    }

    /**
     * Legt dem Mob ein Rüstungsteil an.
     *
     * @param inRüstung Das anzulegende Rüstungsteil
     * @return true, wenn das Anlegen erfolgreich war, sonst false
     */
    public boolean rüstungAnlegen(Rüstung inRüstung){
        if (inRüstung == null)
            return false;

        switch (inRüstung.getKörperteil()) {
            case "Helm":
                rüstungen[0] = inRüstung;
                break;
            case "Brustplatte":
                rüstungen[1] = inRüstung;
                break;
            case "Hose":
                rüstungen[2] = inRüstung;
                break;
            case "Schuhe":
                rüstungen[3] = inRüstung;
                break;
            default:
                return false;
        }

        return true;
    }

    /**
     * Legt ein Rüstungsteil des Mobs ab.
     *
     * @param index Der Index des abzulegenden Rüstungsteils (0=Helm, 1=Brustplatte, 2=Hose, 3=Schuhe)
     * @return true, wenn das Ablegen erfolgreich war, sonst false
     */
    public boolean rüstungAblegen(int index) {
        if(index > rüstungen.length - 1 || index < 0)
            return false;
        rüstungen[index] = null;
        return true;
    }

    /**
     * Berechnet die Schadensreduktionswerte des Mobs basierend auf seiner Rüstung.
     *
     * @return Ein RedWerte-Objekt mit den berechneten Rüstungspunkten und Härtewerten
     */
    public RedWerte berechneSchadensreduktion(){
        int gesamtRüstung = basisRüstungsPunkte;
        int gesamtHärte = 0;
        double reduktion = 0;

        for (Rüstung rüstungsteil: rüstungen) {
            if (rüstungsteil != null) {
                gesamtRüstung += rüstungsteil.getRüstungsPunkte();
                gesamtHärte += rüstungsteil.getHärte();
            }
        }
        return new RedWerte(gesamtRüstung, gesamtHärte);
    }
    
    public String getBezeichnung() {
        return bezeichnung;
    }

    public int getHp() {
        return hp;
    }

    public String getMobTyp() {
        return mobTyp;
    }

    public Rüstung[] getRüstungen() {
        return rüstungen;
    }

    public ArrayList<Effekt> getEffekte() {
        return effekte;
    }

    public void setRüstungen(Rüstung[] rüstungen) {
        this.rüstungen = rüstungen;
    }

    public void setEffekte(ArrayList<Effekt> effekte) {
        this.effekte = effekte;
    }

    public int getBasisRüstungsPunkte() {
        return basisRüstungsPunkte;
    }

    /**
     * Gibt eine String-Darstellung des Mobs zurück.
     *
     * @return Eine textuelle Repräsentation des Mobs
     */
    public String toString() {
        String a = "Bezeichnung : " + bezeichnung + "\nHp : " + hp + "\nmob Typ : " + mobTyp;
        return a;
    }
}
