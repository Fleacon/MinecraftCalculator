package dao;

import DB.DatabaseManager;
import model.Rüstung;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RüstungDAO {

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

    public Rüstung getRüstungByBezeichnung(String bez) throws SQLException {
        String sql = "SELECT Rüstungsteil.bezeichnung AS bezeichnung, typ, Material.bezeichnung AS material, rüstungspunkte, härte\n" +
                "FROM Rüstungsteil\n" +
                "JOIN Material USING(materialID)\n" +
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

        }
    }

    private Rüstung readAndCreate(ResultSet rs) throws SQLException{
        String bezeichnung = rs.getString("bezeichnung");
        String körperteil = rs.getString("typ");
        String material = rs.getString("material");
        int rüstungsPunkte = rs.getInt("rüstungsPunkte");
        int härte = rs.getInt("härte");
        return new Rüstung(bezeichnung, körperteil, material, rüstungsPunkte, härte);
    }
}
