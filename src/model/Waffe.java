package model;

import model.verzauberungen.*;

import java.util.ArrayList;

public class Waffe {
    private String bezeichnung;
    private String waffentyp;
    private String material;
    private int schaden;
    private ArrayList<Verzauberung> verzauberungen = new ArrayList<Verzauberung>();

    public Waffe(String bezeichnung, String waffentyp, String material, int schaden) {
        this.bezeichnung = bezeichnung;
        this.waffentyp = waffentyp;
        this.material = material;
        this.schaden = schaden;
    }

    public void verzaubern(Verzauberung verzauberung) {
        verzauberungen.add(verzauberung);
    }

    public int berechneSchaden() {

        return schaden;
    }

    public void setVerzauberungen(ArrayList<Verzauberung> verzauberungen) {
        this.verzauberungen = verzauberungen;
    }

    public String toString() {
        return "Bezeichnung: " + bezeichnung + "\nMaterial: " + material + "\nSchaden: " + schaden;
    }
}
