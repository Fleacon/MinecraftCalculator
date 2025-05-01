import Verzauberungen.Verzauberung;

import java.util.ArrayList;

public class Rüstung {
    private String bezeichnung;
    private String körperteil;
    private String material;
    private int rüstungsPunkte;
    private int härte;

    private ArrayList<Verzauberung> verzauberungen= new ArrayList<Verzauberung>();

    public Rüstung(String bezeichnung, String körperteil, String material, int rüstungsPunkte, int härte){
        this.körperteil = körperteil;
        this.material = material;
        this.rüstungsPunkte = rüstungsPunkte;
    }
    public void verzaubern(Verzauberung verzauberung){

    }
    public static int berechneSchadensreduzierung(){
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

    public double getRüstungsPunkte() {
        return rüstungsPunkte;
    }

    public int getHärte() {
        return härte;
    }

    public ArrayList<Verzauberung> getVerzauberungen() {
        return verzauberungen;
    }

    public void setVerzauberungen(ArrayList<Verzauberung> verzauberungen) {
        this.verzauberungen = verzauberungen;
    }

    public String toString() {
        String a = "Körperteil : " + körperteil + "\nMaterial : " + material + "\nRüstungspunkte : " + rüstungsPunkte;
        return a;
    }
}
