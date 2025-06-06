package com.calculator.app;

import model.*;
import java.text.DecimalFormat;

public class MainConnector {

    private Mob currentMob;
    private Waffe weapon;
    private Rüstung helmet;
    private Rüstung chestplate;
    private Rüstung leggings;
    private Rüstung boots;



    public void setCurrentMob(Mob currentMob) {
        this.currentMob = currentMob;
    }

    public void setWeapon(Waffe weapon) {
        if (weapon != null) {
            System.out.println("Waffe gesetzt: " + weapon.getBezeichnung());
        } else {
            System.out.println("Waffe abgewählt");
        }
        this.weapon = weapon;
    }

    public void setHelmet(Rüstung helmet) {
        if (helmet != null) {
            System.out.println("Helm gesetzt: " + helmet.getBezeichnung());
        } else {
            System.out.println("Helm ausgezogen");
        }
        this.helmet = helmet;
    }

    public void setChestplate(Rüstung chestplate) {
        if (chestplate != null) {
            System.out.println("Brustplatte gesetzt: " + chestplate.getBezeichnung());
        } else {
            System.out.println("Brustplatte ausgezogen");
        }
        this.chestplate = chestplate;
    }

    public void setLeggings(Rüstung leggings) {
        if (leggings != null) {
            System.out.println("Hose gesetzt: " + leggings.getBezeichnung());
        } else {
            System.out.println("Hose ausgezogen");
        }
        this.leggings = leggings;
    }

    public void setBoots(Rüstung boots) {
        if (boots != null) {
            System.out.println("Schuhe gesetzt: " + boots.getBezeichnung());
        } else {
            System.out.println("Schuhe ausgezogen");
        }
        this.boots = boots;
    }

    public boolean isCalculatable() {
        if (currentMob == null) {
            return false;
        }
        return true;
    }

    public double calculateDamageAsDouble() {
        if (currentMob == null) {
            throw new IllegalStateException("Mob wurde nicht gesetzt.");
        }

        // Create new player and assign weapon
        Player player = new Player();
        player.waffeWechseln(weapon);

        // Clone the mob if you don't want to modify the original
        Mob mobCopy = new Mob(currentMob); // Assumes a copy constructor exists
        if (helmet != null) mobCopy.rüstungAnlegen(helmet);
        if (chestplate != null) mobCopy.rüstungAnlegen(chestplate);
        if (leggings != null) mobCopy.rüstungAnlegen(leggings);
        if (boots != null) mobCopy.rüstungAnlegen(boots);

        Rechner rechner = new Rechner(mobCopy, player);
        double d = rechner.berechneSchaden();
        return Math.round(d * 100.0) / 100.0;
    }

    public String calculateDamageAsString() {
        return String.valueOf(calculateDamageAsDouble());
    }

    public String getHp() {
        return String.valueOf(currentMob.getHp());
    }

    public String getArmorPoints() {
        Mob mobCopy = new Mob(currentMob);
        if (helmet != null) mobCopy.rüstungAnlegen(helmet);
        if (chestplate != null) mobCopy.rüstungAnlegen(chestplate);
        if (leggings != null) mobCopy.rüstungAnlegen(leggings);
        if (boots != null) mobCopy.rüstungAnlegen(boots);

        RedWerte rw = mobCopy.berechneSchadensreduktion();
        return String.valueOf(rw.rüstungspunkte());
    }

    public String getArmorToughness() {
        Mob mobCopy = new Mob(currentMob);
        if (helmet != null) mobCopy.rüstungAnlegen(helmet);
        if (chestplate != null) mobCopy.rüstungAnlegen(chestplate);
        if (leggings != null) mobCopy.rüstungAnlegen(leggings);
        if (boots != null) mobCopy.rüstungAnlegen(boots);

        RedWerte rw = mobCopy.berechneSchadensreduktion();
        return String.valueOf(rw.härte());
    }

    public String calculateHealthLeft() {
        double d = currentMob.getHp() - calculateDamageAsDouble();
        double r = Math.round(d * 100.0) / 100.0;
        return String.valueOf(r);
    }
}
