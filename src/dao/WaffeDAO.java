package dao;

import DB.DatabaseManager;
import model.Waffe;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
                return readAndCreate(rs);
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
                return readAndCreate(rs);
            }
        }
        return null;
    }

    public ArrayList<Waffe> getAllWaffen() throws SQLException {
        String sql = "SELECT Waffe.bezeichnung AS waffenbezeichnung, schaden, Material.bezeichnung AS material, Waffentyp.typ AS typ\n" +
                "FROM Waffe\n"+
                "JOIN Material USING(materialID)\n" +
                "JOIN Waffentyp USING(waffentypID)\n";
        ArrayList<Waffe> waffen = new ArrayList<>();
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                waffen.add(readAndCreate(rs));
            }
            return waffen;
        }
    }

    private Waffe readAndCreate(ResultSet rs) throws SQLException {
        String bezeichnung = rs.getString("waffenbezeichnung");
        String typ = rs.getString("typ");
        int schaden = rs.getInt("schaden");
        String material = rs.getString("material");
        return new Waffe(bezeichnung, typ, material, schaden);
    }
}
