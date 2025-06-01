package model;

import model.verzauberungen.Verzauberung;

import java.util.ArrayList;

/**
 * Repräsentiert ein Rüstungsteil im Minecraft-Spiel.
 * Rüstungsteile können verzaubert werden und bieten Schutz vor Schaden.
 */
public class Rüstung {
    private String bezeichnung;
    private String körperteil;
    private String material;
    private int rüstungsPunkte;
    private int härte;
    private String textur;

    private ArrayList<Verzauberung> verzauberungen= new ArrayList<Verzauberung>();

    /**
     * Erstellt ein neues Rüstungsteil mit den angegebenen Eigenschaften.
     *
     * @param bezeichnung Der Name des Rüstungsteils
     * @param körperteil Die Position des Rüstungsteils (Helm, Brustplatte, Hose, Schuhe)
     * @param material Das Material, aus dem das Rüstungsteil besteht
     * @param rüstungsPunkte Die Rüstungspunkte, die das Teil bietet
     * @param härte Die Härte des Rüstungsteils
     */
    public Rüstung(String bezeichnung, String körperteil, String material, int rüstungsPunkte, int härte, String textur){
        this.bezeichnung = bezeichnung;
        this.körperteil = körperteil;
        this.material = material;
        this.rüstungsPunkte = rüstungsPunkte;
        this.härte = härte;
        this.textur = textur;
    }

    /**
     * Fügt dem Rüstungsteil eine Verzauberung hinzu.
     *
     * @param verzauberung Die hinzuzufügende Verzauberung
     */
    public void verzaubern(Verzauberung verzauberung){
        verzauberungen.add(verzauberung);
    }

    /**
     * Berechnet die Schadensreduzierung durch das Rüstungsteil.
     *
     * @return Die berechnete Schadensreduzierung
     */
    public static double berechneSchadensreduzierung(){
        return 0;
    }
    public String getBezeichnung() {
        return bezeichnung;
    }

    public String getKörperteil() {
        return körperteil;
    }

    public String getMaterial() {
        return material;
    }

    public int getRüstungsPunkte() {
        return rüstungsPunkte;
    }

    public int getHärte() {
        return härte;
    }
    public String getTextur() {
        return textur;
    }

    public ArrayList<Verzauberung> getVerzauberungen() {
        return verzauberungen;
    }

    public void setVerzauberungen(ArrayList<Verzauberung> verzauberungen) {
        this.verzauberungen = verzauberungen;
    }

    /**
     * Gibt eine String-Darstellung des Rüstungsteils zurück.
     *
     * @return Eine textuelle Repräsentation des Rüstungsteils
     */
    public String toString() {
        String a = "Körperteil : " + körperteil + "\nMaterial : " + material + "\nRüstungspunkte : " + rüstungsPunkte;
        return a;
    }
}
