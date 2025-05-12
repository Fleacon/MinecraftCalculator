import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import DB.DatabaseManager;
import dao.MobDAO;
import dao.RüstungDAO;
import dao.WaffeDAO;
import model.Mob;
import model.Player;
import model.Rechner;
import model.Rüstung;
import model.Waffe;

public class MinecraftConsoleApp {
    private static Scanner scanner = new Scanner(System.in);
    private static MobDAO mobDAO = new MobDAO();
    private static WaffeDAO waffeDAO = new WaffeDAO();
    private static RüstungDAO rüstungDAO = new RüstungDAO();

    private static Player player = new Player();
    private static Mob currentMob = null;
    private static Rechner rechner = null;

    public static void main(String[] args) {
        System.out.println("=== Minecraft Schaden-Rechner Konsolenanwendung ===");

        try {
            // Test database connection
            DatabaseManager.getConnection();
            System.out.println("Datenbankverbindung erfolgreich hergestellt.");
        } catch (SQLException e) {
            System.out.println("Fehler bei der Datenbankverbindung: " + e.getMessage());
            System.exit(1);
        }

        int choice = 0;
        do {
            displayMainMenu();
            choice = getIntInput("Bitte wählen Sie eine Option: ");

            try {
                switch (choice) {
                    case 1:
                        selectMob();
                        break;
                    case 2:
                        selectWeapon();
                        break;
                    case 3:
                        manageArmor();
                        break;
                    case 4:
                        calculateDamage();
                        break;
                    case 5:
                        displayCurrentState();
                        break;
                    case 0:
                        System.out.println("Programm wird beendet. Auf Wiedersehen!");
                        break;
                    default:
                        System.out.println("Ungültige Eingabe. Bitte erneut versuchen.");
                }
            } catch (SQLException e) {
                System.out.println("Datenbankfehler: " + e.getMessage());
            }

        } while (choice != 0);

        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n=== Hauptmenü ===");
        System.out.println("1. Mob auswählen");
        System.out.println("2. Waffe auswählen");
        System.out.println("3. Rüstung verwalten");
        System.out.println("4. Schaden berechnen");
        System.out.println("5. Aktuellen Status anzeigen");
        System.out.println("0. Beenden");
    }

    private static void selectMob() throws SQLException {
        System.out.println("\n=== Mob auswählen ===");
        System.out.println("1. Nach ID suchen");
        System.out.println("2. Nach Bezeichnung suchen");
        System.out.println("3. Alle Mobs anzeigen");

        int choice = getIntInput("Bitte wählen Sie eine Option: ");

        switch (choice) {
            case 1:
                int mobId = getIntInput("Mob ID eingeben: ");
                currentMob = mobDAO.getMobByID(mobId);
                break;
            case 2:
                System.out.print("Mob Bezeichnung eingeben: ");
                String mobName = scanner.nextLine();
                currentMob = mobDAO.getMobByBezeichnung(mobName);
                break;
            case 3:
                ArrayList<Mob> mobs = mobDAO.getAllMobs();
                if (mobs.isEmpty()) {
                    System.out.println("Keine Mobs in der Datenbank gefunden.");
                    return;
                }

                System.out.println("Verfügbare Mobs:");
                for (int i = 0; i < mobs.size(); i++) {
                    System.out.println((i + 1) + ". " + mobs.get(i).toString());
                    System.out.println("-----------------------");
                }

                int mobSelection = getIntInput("Mob auswählen (Nummer): ");
                if (mobSelection > 0 && mobSelection <= mobs.size()) {
                    currentMob = mobs.get(mobSelection - 1);
                } else {
                    System.out.println("Ungültige Auswahl.");
                    return;
                }
                break;
            default:
                System.out.println("Ungültige Eingabe.");
                return;
        }

        if (currentMob != null) {
            System.out.println("Mob ausgewählt:");
            System.out.println(currentMob);

            if (rechner == null) {
                rechner = new Rechner(currentMob, player);
            } else {
                rechner.changeMob(currentMob);
            }
        } else {
            System.out.println("Kein Mob mit diesen Kriterien gefunden.");
        }
    }

    private static void selectWeapon() throws SQLException {
        System.out.println("\n=== Waffe auswählen ===");
        System.out.println("1. Nach ID suchen");
        System.out.println("2. Nach Bezeichnung suchen");
        System.out.println("3. Alle Waffen anzeigen");

        int choice = getIntInput("Bitte wählen Sie eine Option: ");

        Waffe waffe = null;

        switch (choice) {
            case 1:
                int waffeId = getIntInput("Waffen ID eingeben: ");
                waffe = waffeDAO.getWaffeByID(waffeId);
                break;
            case 2:
                System.out.print("Waffen Bezeichnung eingeben: ");
                String waffeName = scanner.nextLine();
                waffe = waffeDAO.getWaffeByBezeichnung(waffeName);
                break;
            case 3:
                ArrayList<Waffe> waffen = waffeDAO.getAllWaffen();
                if (waffen.isEmpty()) {
                    System.out.println("Keine Waffen in der Datenbank gefunden.");
                    return;
                }

                System.out.println("Verfügbare Waffen:");
                for (int i = 0; i < waffen.size(); i++) {
                    System.out.println((i + 1) + ". " + waffen.get(i).toString());
                    System.out.println("-----------------------");
                }

                int selection = getIntInput("Waffe auswählen (Nummer): ");
                if (selection > 0 && selection <= waffen.size()) {
                    waffe = waffen.get(selection - 1);
                } else {
                    System.out.println("Ungültige Auswahl.");
                    return;
                }
                break;
            default:
                System.out.println("Ungültige Eingabe.");
                return;
        }

        if (waffe != null) {
            player.waffeWechseln(waffe);
            System.out.println("Waffe ausgewählt:");
            System.out.println(player.getWaffe());
        } else {
            System.out.println("Keine Waffe mit diesen Kriterien gefunden.");
        }
    }

    private static void manageArmor() throws SQLException {
        if (currentMob == null) {
            System.out.println("Bitte wählen Sie zuerst einen Mob aus.");
            return;
        }

        System.out.println("\n=== Rüstung verwalten ===");
        System.out.println("1. Rüstung anlegen");
        System.out.println("2. Rüstung ablegen");
        System.out.println("3. Aktuelle Rüstung anzeigen");

        int choice = getIntInput("Bitte wählen Sie eine Option: ");

        switch (choice) {
            case 1:
                equipeArmor();
                break;
            case 2:
                removeArmor();
                break;
            case 3:
                displayCurrentArmor();
                break;
            default:
                System.out.println("Ungültige Eingabe.");
        }
    }

    private static void equipeArmor() throws SQLException {
        System.out.println("\n=== Rüstung anlegen ===");
        System.out.println("1. Nach ID suchen");
        System.out.println("2. Nach Bezeichnung suchen");
        System.out.println("3. Alle Rüstungen anzeigen");

        int choice = getIntInput("Bitte wählen Sie eine Option: ");

        Rüstung rüstung = null;

        switch (choice) {
            case 1:
                int ruestungId = getIntInput("Rüstungs ID eingeben: ");
                rüstung = rüstungDAO.getRüstungByID(ruestungId);
                break;
            case 2:
                System.out.print("Rüstungs Bezeichnung eingeben: ");
                String ruestungName = scanner.nextLine();
                rüstung = rüstungDAO.getRüstungByBezeichnung(ruestungName);
                break;
            case 3:
                ArrayList<Rüstung> rüstungen = rüstungDAO.getAllRüstungen();
                if (rüstungen.isEmpty()) {
                    System.out.println("Keine Rüstungen in der Datenbank gefunden.");
                    return;
                }

                System.out.println("Verfügbare Rüstungen:");
                for (int i = 0; i < rüstungen.size(); i++) {
                    System.out.println((i + 1) + ". " + rüstungen.get(i).toString());
                    System.out.println("-----------------------");
                }

                int auswahl = getIntInput("Rüstung auswählen (Nummer): ");
                if (auswahl > 0 && auswahl <= rüstungen.size()) {
                    rüstung = rüstungen.get(auswahl - 1);
                } else {
                    System.out.println("Ungültige Auswahl.");
                    return;
                }
                break;
            default:
                System.out.println("Ungültige Eingabe.");
                return;
        }

        if (rüstung != null) {
            boolean success = currentMob.rüstungAnlegen(rüstung);
            if (success) {
                System.out.println("Rüstung erfolgreich angelegt: " + rüstung.getBezeichnung());
            } else {
                System.out.println("Rüstung konnte nicht angelegt werden.");
            }
        } else {
            System.out.println("Keine Rüstung mit diesen Kriterien gefunden.");
        }
    }

    private static void removeArmor() {
        System.out.println("\n=== Rüstung ablegen ===");
        displayCurrentArmor();

        System.out.println("Welches Rüstungsteil möchten Sie ablegen?");
        System.out.println("1. Helm");
        System.out.println("2. Brustplatte");
        System.out.println("3. Hose");
        System.out.println("4. Schuhe");

        int choice = getIntInput("Bitte wählen Sie eine Option: ");

        if (choice >= 1 && choice <= 4) {
            boolean success = currentMob.rüstungAblegen(choice - 1);
            if (success) {
                System.out.println("Rüstungsteil erfolgreich abgelegt.");
            } else {
                System.out.println("Rüstungsteil konnte nicht abgelegt werden.");
            }
        } else {
            System.out.println("Ungültige Eingabe.");
        }
    }

    private static void displayCurrentArmor() {
        System.out.println("\n=== Aktuelle Rüstung ===");
        if (currentMob == null) {
            System.out.println("Kein Mob ausgewählt.");
            return;
        }

        Rüstung[] rüstungen = currentMob.getRüstungen();
        String[] rüstungstypen = {"Helm", "Brustplatte", "Hose", "Schuhe"};

        for (int i = 0; i < rüstungen.length; i++) {
            System.out.println(rüstungstypen[i] + ":");
            if (rüstungen[i] != null) {
                System.out.println(rüstungen[i]);
            } else {
                System.out.println("Nicht angelegt");
            }
            System.out.println("-----------------------");
        }
    }

    private static void calculateDamage() {
        if (rechner == null) {
            System.out.println("Bitte wählen Sie zuerst einen Mob und eine Waffe aus.");
            return;
        }

        if (player.getWaffe() == null) {
            System.out.println("Bitte wählen Sie zuerst eine Waffe aus.");
            return;
        }

        System.out.println("\n=== Schadensberechnung ===");
        double damage = rechner.berechneSchaden();

        System.out.println("Spieler mit " + player.getWaffe().toString());
        System.out.println("greift " + currentMob.getBezeichnung() + " an");
        System.out.println("Berechneter Schaden: " + String.format("%.2f", damage));
    }

    private static void displayCurrentState() {
        System.out.println("\n=== Aktueller Status ===");

        System.out.println("--- Player ---");
        System.out.println(player);

        if (player.getWaffe() != null) {
            System.out.println("\n--- Waffe ---");
            System.out.println(player.getWaffe());
        } else {
            System.out.println("\nKeine Waffe ausgerüstet");
        }

        if (currentMob != null) {
            System.out.println("\n--- Mob ---");
            System.out.println(currentMob);

            System.out.println("\n--- Rüstung ---");
            displayCurrentArmor();
        } else {
            System.out.println("\nKein Mob ausgewählt");
        }
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Bitte geben Sie eine Zahl ein.");
            scanner.nextLine(); // Clear input
            System.out.print(prompt);
        }
        int result = scanner.nextInt();
        scanner.nextLine(); // Clear input buffer
        return result;
    }
}