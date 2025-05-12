package model;

import model.effekte.Effekt;

import java.util.ArrayList;
import java.util.Arrays;

public class Mob {
    private String bezeichnung;
    private int hp;
    private int basisRüstungsPunkte;
    private String mobTyp;

    private Rüstung[] rüstungen = new Rüstung[4];
    private ArrayList<Effekt> effekte = new ArrayList<Effekt>();

    public Mob(String bezeichnung, int hp, int basisRüstungsPunkte, String mobTyp){
        this.bezeichnung = bezeichnung;
        this.hp = hp;
        this.basisRüstungsPunkte = basisRüstungsPunkte;
        this.mobTyp = mobTyp;
    }

    public void effektHinzufügen(Effekt effekt){
        effekte.add(effekt);
    }

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

    // index: 0 = Helm, 1 = Brustplatte, 2 = Hose, 3 = Schuhe
    public boolean rüstungAblegen(int index) {
        if(index > rüstungen.length - 1 || index < 0)
            return false;
        rüstungen[index] = null;
        return true;
    }

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

    public String toString() {
        String a = "Bezeichnung : " + bezeichnung + "\nHp : " + hp + "\nmob Typ : " + mobTyp;
        return a;
    }
}
