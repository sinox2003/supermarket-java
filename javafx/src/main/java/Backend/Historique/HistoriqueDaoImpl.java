package Backend.Historique;

import Backend.Dao.AbstractDao;
import Backend.Dao.IHistoriqueDao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public class HistoriqueDaoImpl extends AbstractDao implements IHistoriqueDao {
    @Override
    public List<Historique> getAll() {
        List<Historique> listeHistoriques = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement("SELECT * FROM historique");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Date dateSql = rs.getDate("date");

                listeHistoriques.add(new Historique(
                        rs.getInt("id"),
                        rs.getInt("idCategorie"),
                        rs.getString("designation"),
                        rs.getInt("quantite"),
                        rs.getDouble("prix"),
                        rs.getInt("type"),
                        dateSql != null ? dateSql.toLocalDate() : null));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de tous les historiques");
            e.printStackTrace();
        }
        return listeHistoriques;
    }
    @Override
    public void add(Historique h){
        PreparedStatement pstAction = null;
        String sqlAction = "INSERT INTO historique  VALUES (?,?,?,?,?,?,?)";
        try {
            pstAction = connection.prepareStatement(sqlAction);
            pstAction.setInt(1, h.getId());
            pstAction.setInt(2, h.getIdCategorie());
            pstAction.setString(3, h.getDesignation());
            pstAction.setInt(4, h.getQte());
            pstAction.setDouble(5, h.getPrix());
            pstAction.setInt(6, h.getType());
            pstAction.setDate(7, Date.valueOf(h.getDate()));
            pstAction.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public void delete(int id) {
        PreparedStatement pst = null;
        String sql = "DELETE FROM historique WHERE id = ?";

        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            int rowsDeleted = pst.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Historique deleted successfully!");
            } else {
                System.out.println("Historique with ID " + id + " not found.");
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
