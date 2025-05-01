package model;

public class Rechner {
    private static Rechner instance;
    private Player player;
    private Mob mob;

    private Rechner() {

    }

    public static Rechner getInstance() {
        if (instance == null)
            instance = new Rechner();
        return instance;
    }


}
