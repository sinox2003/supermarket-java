package Backend.Produit;

import Backend.Dao.AbstractDao;
import Backend.Dao.IProduitDao;
import Backend.Dao.SingleConnection;
import Backend.Historique.Historique;
import Backend.Historique.HistoriqueDaoImpl;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProduitDaoImpl extends AbstractDao implements IProduitDao {
    @Override
    public void add(Produit obj) {
        PreparedStatement pst = null;
        ResultSet generatedKeys = null;
        String sql = "INSERT INTO produit (idCategorie, designation, quantite, prix, date, peremption) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, obj.getIdCategorie());
            pst.setString(2, obj.getDesignation());
            pst.setInt(3, obj.getQte());
            pst.setDouble(4, obj.getPrix());
            pst.setDate(5, Date.valueOf(obj.getDate()));
            if (obj.getPeremption() != null) {
                pst.setDate(6, Date.valueOf(obj.getPeremption()));
            } else {
                pst.setNull(6, Types.DATE);
            }
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                // Retrieve the generated keys
                generatedKeys = pst.getGeneratedKeys();

                if (generatedKeys.next()) {
                    // Set the generated ID to your object
                    obj.setId(generatedKeys.getInt(1)); // Assuming the ID column is the first column
                } else {
                    System.out.println("Failed to retrieve the generated ID.");
                }
            }

            System.out.println("Backend.Produit ajouté avec ID: " + obj.getId());
            Historique h = new Historique(obj.getId(),obj.getIdCategorie(), obj.getDesignation(), obj.getQte(),
                    obj.getPrix(), 1, LocalDate.now());
            HistoriqueDaoImpl hdao = new HistoriqueDaoImpl();
            hdao.add(h);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            // Close the ResultSet and PreparedStatement in the finally block
            try {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }




    @Override
    public void delete(int id) {
        PreparedStatement pst = null;
        String sql = "DELETE FROM produit WHERE id = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            int rowsDeleted = pst.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Product deleted successfully!");
            } else {
                System.out.println("Product with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public Produit getById(int id) {
        PreparedStatement pst = null;
        String sql = "SELECT * FROM produit WHERE id = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Date dateSql = rs.getDate("date");
                Date peremptionSql = rs.getDate("peremption");

                return new Produit(
                        rs.getInt("id"),
                        rs.getInt("idCategorie"),
                        rs.getString("designation"),
                        rs.getInt("quantite"),
                        rs.getDouble("prix"),
                        dateSql != null ? dateSql.toLocalDate() : null,
                        peremptionSql != null ? peremptionSql.toLocalDate() : null);
            } else {
                return null; // Aucun produit trouvé avec l'ID spécifié
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Fermer le PreparedStatement dans le bloc finally
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    @Override
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
                        rs.getInt("quantite"),
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

    @Override
    public List<Produit> getProduitByCategorie(int idCategorie){
        List<Produit> listeProduits = new ArrayList<Produit>();
        String sql = "SELECT * FROM produit WHERE idCategorie=?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, idCategorie);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Date dateSql = rs.getDate("date");
                    Date peremptionSql = rs.getDate("peremption");

                    listeProduits.add(new Produit(
                            rs.getInt("id"),
                            rs.getInt("idCategorie"),
                            rs.getString("designation"),
                            rs.getInt("quantite"),
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
    @Override
    public List<Produit> getProduitByKeyword(String designation, int idCategorie){
        if (designation.isEmpty()) return getProduitByCategorie(idCategorie);
        List<Produit> listeProduits = new ArrayList<Produit>();
        String sql = "SELECT * FROM produit WHERE idCategorie=? and designation LIKE ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, idCategorie);
            pst.setString(2, '%'+designation+'%');
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Date dateSql = rs.getDate("date");
                    Date peremptionSql = rs.getDate("peremption");

                    listeProduits.add(new Produit(
                            rs.getInt("id"),
                            rs.getInt("idCategorie"),
                            rs.getString("designation"),
                            rs.getInt("quantite"),
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
    @Override
    public void update(Produit obj, int action, int qte) {
        //action: 0 rien/1 ajout/ 2 retrait
        PreparedStatement pst = null;
        String sql = "UPDATE produit SET designation=?, quantite=?, prix=?, date=?, peremption=? WHERE id=?";
        try {
            pst = connection.prepareStatement(sql);
            //pst.setInt(1, obj.getIdCategorie());
            pst.setString(1, obj.getDesignation());
            pst.setInt(2, obj.getQte());
            pst.setDouble(3, obj.getPrix());
            pst.setDate(4, Date.valueOf(obj.getDate()));
            if (obj.getPeremption() != null) {
                pst.setDate(5, Date.valueOf(obj.getPeremption()));
            } else {
                pst.setNull(5, Types.DATE);
            }
            pst.setInt(6, obj.getId());
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Product updated successfully!");
            } else {
                System.out.println("Product with ID " + obj.getId() + " not found.");
            }
            if(action != 0){
                Historique h = new Historique(obj.getId(),obj.getIdCategorie(), obj.getDesignation(), qte,
                                             obj.getPrix(), action, LocalDate.now());
                HistoriqueDaoImpl hdao = new HistoriqueDaoImpl();
                hdao.add(h);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }


}
