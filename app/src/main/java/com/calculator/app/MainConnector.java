package com.calculator.app;

import model.*;

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
}
