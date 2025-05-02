package model;

import model.effekte.*;

import java.util.ArrayList;

public class Player {
    private int basisSchaden;
    private Waffe waffe;

    private ArrayList<Effekt> effekte = new ArrayList<Effekt>();

    public Player() {
        basisSchaden = 1;
    }

    public void waffeWechseln(Waffe waffe) {
        this.waffe = waffe;
    }

    public void effektHinzufügen(Effekt effekt){
        effekte.add(effekt);
    }

    public int berechneAngriffskraft(){
        int angriffskraft = waffe.berechneSchaden();
        return angriffskraft;
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

    public String toString() {
        return "BasisSchaden: " + basisSchaden;
    }
}
