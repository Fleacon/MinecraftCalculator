package dao;

import DB.DatabaseManager;
import model.Waffe;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Datenzugriffsklasse für Waffen.
 * Ermöglicht das Abrufen von Waffeninformationen aus der Datenbank.
 */
public class WaffeDAO {

    /**
     * Sucht eine Waffe anhand ihrer ID in der Datenbank.
     *
     * @param id Die ID der gesuchten Waffe
     * @return Waffe-Objekt oder null, falls nicht gefunden
     * @throws SQLException Bei Datenbankfehlern
     */
    public Waffe getWaffeByID(int id) throws SQLException {
        String sql = "SELECT Waffe.bezeichnung AS waffenbezeichnung, schaden, Material.bezeichnung AS material, " +
                "Waffentyp.typ AS typ, textur " +
                "FROM Waffe " +
                "JOIN Material USING(materialID) " +
                "JOIN Waffentyp USING(waffentypID) " +
                "WHERE waffeID = ?";
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
     * Sucht eine Waffe anhand ihrer Bezeichnung in der Datenbank.
     *
     * @param bez Die Bezeichnung der gesuchten Waffe
     * @return Waffe-Objekt oder null, falls nicht gefunden
     * @throws SQLException Bei Datenbankfehlern
     */
    public Waffe getWaffeByBezeichnung(String bez) throws SQLException {
        String sql = "SELECT Waffe.bezeichnung AS waffenbezeichnung, schaden, Material.bezeichnung AS material, " +
                "Waffentyp.typ AS typ, textur " +
                "FROM Waffe " +
                "JOIN Material USING(materialID) " +
                "JOIN Waffentyp USING(waffentypID) " +
                "WHERE Waffe.bezeichnung = ?";
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
     * Gibt alle Waffen aus der Datenbank zurück.
     *
     * @return Liste aller Waffen
     * @throws SQLException Bei Datenbankfehlern
     */
    public ArrayList<Waffe> getAllWaffen() throws SQLException {
        String sql = "SELECT Waffe.bezeichnung AS waffenbezeichnung, schaden, Material.bezeichnung AS material, " +
                "Waffentyp.typ AS typ, textur " +
                "FROM Waffe " +
                "JOIN Material USING(materialID) " +
                "JOIN Waffentyp USING(waffentypID)";
        ArrayList<Waffe> waffen = new ArrayList<>();
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                waffen.add(readAndCreate(rs));
            }
        }
        return waffen;
    }

    /**
     * Erstellt ein Waffe-Objekt aus einem ResultSet.
     *
     * @param rs Das ResultSet, aus dem die Daten gelesen werden
     * @return Ein neues Waffe-Objekt
     * @throws SQLException Bei Fehlern beim Lesen aus dem ResultSet
     */
    private Waffe readAndCreate(ResultSet rs) throws SQLException {
        String bezeichnung = rs.getString("waffenbezeichnung");
        String typ = rs.getString("typ");
        int schaden = rs.getInt("schaden");
        String material = rs.getString("material");
        String textur = rs.getString("textur");
        return new Waffe(bezeichnung, typ, material, schaden, textur);
    }
}
