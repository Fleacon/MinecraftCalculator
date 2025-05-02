package dao;

import DB.DatabaseManager;
import model.Mob;
import model.Waffe;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WaffeDAO {
    public Waffe getWaffeByID(int id) throws SQLException {
        String sql = "SELECT Waffe.bezeichnung AS waffenbezeichnung, schaden, Material.bezeichnung AS material, Waffentyp.typ AS typ\n" +
                "FROM Waffe \n" +
                "JOIN Material USING(materialID)\n" +
                "JOIN Waffentyp USING(waffentypID)\n" +
                "WHERE waffeID = ?\n";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String bezeichnung = rs.getString("waffenbezeichnung");
                String typ = rs.getString("typ");
                int schaden = rs.getInt("schaden");
                String material = rs.getString("material");
                return new Waffe(bezeichnung, typ, material, schaden);
            }
        }
        return null;
    }

    public Waffe getWaffeByBezeichnung(String bez) throws SQLException {
        String sql = "SELECT Waffe.bezeichnung AS waffenbezeichnung, schaden, Material.bezeichnung AS material, Waffentyp.typ AS typ\n" +
                "FROM Waffe \n" +
                "JOIN Material USING(materialID)\n" +
                "JOIN Waffentyp USING(waffentypID)\n" +
                "WHERE Waffe.bezeichnung = ?\n";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, bez);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String bezeichnung = rs.getString("waffenbezeichnung");
                String typ = rs.getString("typ");
                int schaden = rs.getInt("schaden");
                String material = rs.getString("material");
                return new Waffe(bezeichnung, typ, material, schaden);
            }
        }
        return null;
    }
}
