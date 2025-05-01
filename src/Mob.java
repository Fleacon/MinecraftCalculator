import Effekte.Effekt;

import java.util.ArrayList;

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

    }

    public void rüstungAnlegen(Rüstung rüstung){

    }

    public int berechneSchadensnigierung(){
        return 0;
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
