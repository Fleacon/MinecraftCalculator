package dao;

import DB.DatabaseManager;
import model.Mob;
import model.Waffe;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MobDAO {
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

    private Mob readAndCreate(ResultSet rs) throws SQLException {
        if (rs.next()) {
            String bezeichnung = rs.getString("bezeichnung");
            int hp = rs.getInt("hp");
            int brp = rs.getInt("basisRüstungsPunkte");
            String typ = rs.getString("typ");
            return new Mob(bezeichnung, hp, brp, typ);
        }
        WaffeDAO wp = new WaffeDAO();
        return null;
    }
}
