package model;

import model.verzauberungen.*;

import java.util.ArrayList;

/**
 * Repräsentiert eine Waffe im Minecraft-Spiel.
 * Waffen können verzaubert werden und verursachen Schaden.
 */
public class Waffe {
    private String bezeichnung;
    private String waffentyp;
    private String material;
    private int schaden;
    private ArrayList<Verzauberung> verzauberungen = new ArrayList<Verzauberung>();

    private String textur;

    /**
     * Erstellt eine neue Waffe mit den angegebenen Eigenschaften.
     *
     * @param bezeichnung Der Name der Waffe
     * @param waffentyp Der Typ der Waffe
     * @param material Das Material, aus dem die Waffe besteht
     * @param schaden Der Basisschaden der Waffe
     */
    public Waffe(String bezeichnung, String waffentyp, String material, int schaden, String textur) {
        this.bezeichnung = bezeichnung;
        this.waffentyp = waffentyp;
        this.material = material;
        this.schaden = schaden;
        this.textur = textur;
    }

    /**
     * Fügt der Waffe eine Verzauberung hinzu.
     *
     * @param verzauberung Die hinzuzufügende Verzauberung
     */
    public void verzaubern(Verzauberung verzauberung) {
        verzauberungen.add(verzauberung);
    }

    /**
     * Berechnet den Schaden, den die Waffe verursacht.
     *
     * @return Der berechnete Schaden
     */
    public int berechneSchaden() {

        return schaden;
    }

    /**
     * Setzt die Verzauberungen der Waffe.
     *
     * @param verzauberungen Die neuen Verzauberungen
     */
    public void setVerzauberungen(ArrayList<Verzauberung> verzauberungen) {
        this.verzauberungen = verzauberungen;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public String getTextur() {
        return textur;
    }

    /**
     * Gibt eine String-Darstellung der Waffe zurück.
     *
     * @return Eine textuelle Repräsentation der Waffe
     */
    public String toString() {
        return "Bezeichnung: " + bezeichnung + "\nMaterial: " + material + "\nSchaden: " + schaden;
    }
}
