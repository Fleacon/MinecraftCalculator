package dao;

import DB.DatabaseManager;
import model.Mob;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Datenzugriffsklasse für Mob-Entitäten.
 * Ermöglicht das Abrufen von Mob-Informationen aus der Datenbank.
 */
public class MobDAO {

    /**
     * Sucht einen Mob anhand seiner ID in der Datenbank.
     *
     * @param id Die ID des gesuchten Mobs
     * @return Mob-Objekt oder null, falls nicht gefunden
     * @throws SQLException Bei Datenbankfehlern
     */
    public Mob getMobByID(int id) throws SQLException {
        String sql = "SELECT bezeichnung, hp, basisRüstungsPunkte, typ\n" +
                "FROM Mob\n" +
                "JOIN MobTyp USING(mobTypID)\n" +
                "WHERE mobID = ?";
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
     * Sucht einen Mob anhand seiner Bezeichnung in der Datenbank.
     *
     * @param bez Die Bezeichnung des gesuchten Mobs
     * @return Mob-Objekt oder null, falls nicht gefunden
     * @throws SQLException Bei Datenbankfehlern
     */
    public Mob getMobByBezeichnung(String bez) throws SQLException {
        String sql = "SELECT bezeichnung, hp, basisRüstungsPunkte, typ\n" +
                "FROM Mob\n" +
                "JOIN MobTyp USING(mobTypID)\n" +
                "WHERE bezeichnung = ?";
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
     * Gibt alle Mobs aus der Datenbank zurück.
     *
     * @return Liste aller Mobs
     * @throws SQLException Bei Datenbankfehlern
     */
    public ArrayList<Mob> getAllMobs() throws SQLException {
        String sql = "SELECT bezeichnung, hp, basisRüstungsPunkte, typ\n" +
                "FROM Mob\n" +
                "JOIN MobTyp USING(mobTypID)";
        ArrayList<Mob> mobs = new ArrayList<>();
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mobs.add(readAndCreate(rs));
            }
            return mobs;
        }
    }

    /**
     * Erstellt ein Mob-Objekt aus einem ResultSet.
     *
     * @param rs Das ResultSet, aus dem die Daten gelesen werden
     * @return Ein neues Mob-Objekt
     * @throws SQLException Bei Fehlern beim Lesen aus dem ResultSet
     */
    private Mob readAndCreate(ResultSet rs) throws SQLException {
        String bezeichnung = rs.getString("bezeichnung");
        int hp = rs.getInt("hp");
        int brp = rs.getInt("basisRüstungsPunkte");
        String typ = rs.getString("typ");
        return new Mob(bezeichnung, hp, brp, typ);
    }
}
