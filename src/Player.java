import Effekte.*;

public class Player {
    private int basisSchaden;
    private Waffe waffe;

    public Player() {
        basisSchaden = 1;
    }

    public void waffeWechseln(Waffe waffe) {

    }

    public void effektHinzufügen(Effekt effekt){

    }

    public int berechneAngriffskraft(){
        return 0;
    }

    public int getBasisSchaden() {
        return basisSchaden;
    }

    public Waffe getWaffe() {
        return waffe;
    }

    public void setBasisSchaden(int basisSchaden) {
        this.basisSchaden = basisSchaden;
    }

    public void setWaffe(Waffe waffe) {
        this.waffe = waffe;
    }

    public String toString() {
        return "BasisSchaden: " + basisSchaden;
    }
}
