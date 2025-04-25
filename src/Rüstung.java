import Verzauberungen.Verzauberung;

public class Rüstung {
    private String körperteil;
    private String material;
    private double rüstungsPunkte;
    public Rüstung(String körperteil, String material, double rüstungsPunkte){
        this.körperteil = körperteil;
        this.material = material;
        this.rüstungsPunkte = rüstungsPunkte;
    }
    public void verzaubern(Verzauberung verzauberung){

    }
    public static int berechneSchadensreduzierung(){

    }

    public String getKörperteil() {
        return körperteil;
    }

    public void setKörperteil(String körperteil) {
        this.körperteil = körperteil;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getRüstungsPunkte() {
        return rüstungsPunkte;
    }

    public void setRüstungsPunkte(double rüstungsPunkte) {
        this.rüstungsPunkte = rüstungsPunkte;
    }

    public String toString() {
        String a = "Körperteil : " + körperteil + "\nMaterial : " + material + "\nRüstungspunkte : " + rüstungsPunkte;
        return a;
    }
}
