package Backend.Produit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitDataAccess {

    private static final String DB_NAME = "supermarche_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME;

    private Connection connection;

    public ProduitDataAccess() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion réussie!");
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données");
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connexion fermée avec succès!");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion");
            e.printStackTrace();
        }
    }

    public List<Produit> getAll() {
        List<Produit> listeProduits = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement("SELECT * FROM produit");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Date dateSql = rs.getDate("date");
                Date peremptionSql = rs.getDate("peremption");

                listeProduits.add(new Produit(
                        rs.getInt("id"),
                        rs.getInt("idCategorie"),
                        rs.getString("designation"),
                        rs.getInt("qte"),
                        rs.getDouble("prix"),
                        dateSql != null ? dateSql.toLocalDate() : null,
                        peremptionSql != null ? peremptionSql.toLocalDate() : null));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de tous les produits");
            e.printStackTrace();
        }
        return listeProduits;
    }

    public List<Produit> getProduitsByKeyword(String keyword) {
        List<Produit> listeProduits = new ArrayList<>();
        String sql = "SELECT * FROM produit WHERE designation LIKE ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, "%" + keyword + "%");
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Date dateSql = rs.getDate("date");
                    Date peremptionSql = rs.getDate("peremption");

                    listeProduits.add(new Produit(
                            rs.getInt("id"),
                            rs.getInt("idCategorie"),
                            rs.getString("designation"),
                            rs.getInt("qte"),
                            rs.getDouble("prix"),
                            dateSql != null ? dateSql.toLocalDate() : null,
                            peremptionSql != null ? peremptionSql.toLocalDate() : null));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des produits par mot-clé");
            e.printStackTrace();
        }
        return listeProduits;
    }
}
