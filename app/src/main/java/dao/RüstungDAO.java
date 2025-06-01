package dao;

import DB.DatabaseManager;
import model.Rüstung;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Datenzugriffsklasse für Rüstungsteile.
 * Ermöglicht das Abrufen von Rüstungsinformationen aus der Datenbank.
 */
public class RüstungDAO {

    /**
     * Sucht ein Rüstungsteil anhand seiner ID in der Datenbank.
     *
     * @param id Die ID des gesuchten Rüstungsteils
     * @return Rüstung-Objekt oder null, falls nicht gefunden
     * @throws SQLException Bei Datenbankfehlern
     */
    public Rüstung getRüstungByID(int id) throws SQLException {
        String sql = "SELECT Rüstungsteil.bezeichnung AS bezeichnung, typ, Material.bezeichnung AS material, rüstungspunkte, härte\n" +
                "FROM Rüstungsteil\n" +
                "LEFT JOIN Material USING(materialID)\n" +
                "JOIN Rüstungstyp USING(rüstungstypID)\n" +
                "WHERE rüstungsID = ?";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return readAndCreate(rs);
            }
        }
        return null;
    }

    /**
     * Sucht ein Rüstungsteil anhand seiner Bezeichnung in der Datenbank.
     *
     * @param bez Die Bezeichnung des gesuchten Rüstungsteils
     * @return Rüstung-Objekt oder null, falls nicht gefunden
     * @throws SQLException Bei Datenbankfehlern
     */
    public Rüstung getRüstungByBezeichnung(String bez) throws SQLException {
        String sql = "SELECT Rüstungsteil.bezeichnung AS bezeichnung, typ, Material.bezeichnung AS material, rüstungspunkte, härte\n" +
                "FROM Rüstungsteil\n" +
                "LEFT JOIN Material USING(materialID)\n" +
                "JOIN Rüstungstyp USING(rüstungstypID)\n" +
                "WHERE Rüstungsteil.bezeichnung = ?";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, bez);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return readAndCreate(rs);
            }
        }
        return null;
    }

    /**
     * Gibt alle Rüstungsteile aus der Datenbank zurück.
     *
     * @return Liste aller Rüstungsteile
     * @throws SQLException Bei Datenbankfehlern
     */
    public ArrayList<Rüstung> getAllRüstungen() throws SQLException {
        String sql = "SELECT Rüstungsteil.bezeichnung AS bezeichnung, typ, Material.bezeichnung AS material, rüstungspunkte, härte\n" +
                "FROM Rüstungsteil\n" +
                "JOIN Material USING(materialID)\n" +
                "JOIN Rüstungstyp USING(rüstungstypID)";
        ArrayList<Rüstung> rüstungen = new ArrayList<>();
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rüstungen.add(readAndCreate(rs));
            }
            return rüstungen;
        }
    }

    /**
     * Gibt alle Rüstungsteile eines bestimmten Typs zurück.
     *
     * @param typ Der gesuchte Rüstungstyp
     * @return Liste aller Rüstungsteile des angegebenen Typs
     * @throws SQLException Bei Datenbankfehlern
     */
    public ArrayList<Rüstung> getAllRüstungByTyp(String typ) throws SQLException {
        String sql = "SELECT Rüstungsteil.bezeichnung AS bezeichnung, typ, Material.bezeichnung AS material, rüstungspunkte, härte\n" +
                "FROM Rüstungstyp\n" +
                "JOIN Rüstungsteil USING(rüstungstypID)\n" +
                "LEFT JOIN Material USING(materialID)\n" +
                "WHERE typ = ?";
        ArrayList<Rüstung> rüstungen = new ArrayList<>();
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rüstungen.add(readAndCreate(rs));
            }
            return rüstungen;
        }
    }

    /**
     * Erstellt ein Rüstung-Objekt aus einem ResultSet.
     *
     * @param rs Das ResultSet, aus dem die Daten gelesen werden
     * @return Ein neues Rüstung-Objekt
     * @throws SQLException Bei Fehlern beim Lesen aus dem ResultSet
     */
    private Rüstung readAndCreate(ResultSet rs) throws SQLException{
        String bezeichnung = rs.getString("bezeichnung");
        String körperteil = rs.getString("typ");
        String material = rs.getString("material");
        int rüstungsPunkte = rs.getInt("rüstungsPunkte");
        int härte = rs.getInt("härte");
        return new Rüstung(bezeichnung, körperteil, material, rüstungsPunkte, härte);
    }
}
