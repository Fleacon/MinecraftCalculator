import Verzauberungen.*;

import java.util.ArrayList;

public class Waffe {
    private String bezeichnung;
    private String material;
    private int schaden;
    private ArrayList<Verzauberung> verzauberungen = new ArrayList<Verzauberung>();

    public Waffe(String bezeichnung, String material, int schaden) {
        this.bezeichnung = bezeichnung;
        this.material = material;
        this.schaden = schaden;
    }

    public void verzaubern(Verzauberung verzauberung) {

    }

    public int berechneSchaden() {
        return 0;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setSchaden(int schaden) {
        this.schaden = schaden;
    }

    public String toString() {
        return "Bezeichnung: " + bezeichnung + "\nMaterial: " + material + "\nSchaden: " + schaden;
    }
}
