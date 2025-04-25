import Effekte.Effekt;

import java.util.ArrayList;

public class Mob {
    private String bezeichnung;
    private int hp;
    private String mobTyp;

    private Rüstung[] rüstungen = new Rüstung[4];
    private ArrayList<Effekt> effekte = new ArrayList<Effekt>();

    public Mob(String bezeichnung, int hp, String mobTyp){
        this.bezeichnung = bezeichnung;
        this.hp = hp;
        this.mobTyp = mobTyp;
    }

    public void effektHinzufügen(Effekt effekt){

    }

    public void rüstungAnlegen(Rüstung rüstung){

    }

    public int berechneSchadensnigierung(){

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

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public Rüstung[] getRüstungen() {
        return rüstungen;
    }

    public ArrayList<Effekt> getEffekte() {
        return effekte;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setMobTyp(String mobTyp) {
        this.mobTyp = mobTyp;
    }

    public String toString() {
        String a = "Bezeichnung : " + bezeichnung + "\nHp : " + hp + "\nmob Typ : " + mobTyp;
        return a;
    }

    public void setRüstungen(Rüstung[] rüstungen) {
        this.rüstungen = rüstungen;
    }

    public void setEffekte(ArrayList<Effekt> effekte) {
        this.effekte = effekte;
    }
}
