package Backend.User;

import java.sql.*;

import Backend.Dao.AbstractDao;
import Backend.Dao.IUserDao;
import Backend.Dao.SingleConnection;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends AbstractDao implements IUserDao {
    public void add(User obj) {
        PreparedStatement pst = null;
        ResultSet generatedKeys = null;
        String sql = "INSERT INTO user(username, password, type) VALUES (?, ?, ?)";

        try {
            pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, obj.getUsername());
            pst.setString(2, obj.getPassword());
            pst.setString(3, obj.getType());

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

            System.out.println("User ajouté avec ID: " + obj.getId());
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
        String sql = "DELETE FROM user WHERE id=?";

        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1,id);
            System.out.println("Succes d'exec de la requete!!");
            pst.executeUpdate();

            } catch(SQLException exp){
        System.out.println(exp.getMessage());
    }


    }

    @Override
    public User getById(int id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM user WHERE id=?";

        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1,id);
            System.out.println("Succes d'exec de la requete!!");
            rs = pst.executeQuery();
            if (rs.next()){
                //System.out.println(rs.getLong("id")+" "+rs.getString("email")+" "+rs.getString("password")+" "+rs.getString("type"));
                return new User(rs.getInt("id"),rs.getString("username"),rs.getString("password"),rs.getString("type"));
            }
        } catch(SQLException exp){
            System.out.println(exp.getMessage());
        }


        return null;

    }

    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM user";

        try {
            pst = connection.prepareStatement(sql);
            System.out.println("Succes d'exec de la requete!!");
            rs = pst.executeQuery();
            while(rs.next()){
                //System.out.println(rs.getLong("id")+" "+rs.getString("email")+" "+rs.getString("password")+" "+rs.getString("type"));
                list.add(new User(rs.getInt("id"),rs.getString("username"),rs.getString("password"),rs.getString("type")));
            }
        } catch(SQLException exp){
            System.out.println(exp.getMessage());
        }


        return list;


    }

    @Override
    public List<User> getUsersByInfo(String providedUsername, String providedPassword) {
        List<User> list = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM user WHERE username = ? AND BINARY password = ?";

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, providedUsername);
            pst.setString(2, providedPassword);

            rs = pst.executeQuery();

            while(rs.next()) {
                list.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("type")));
            }
        } catch(SQLException exp) {
            System.out.println(exp.getMessage());
        }

        return list;
    }



    @Override
    public List<User> getByType(String type) {
        List<User> list = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM user WHERE type LIKE ?";

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1,type+"%");
            System.out.println("Succes d'exec de la requete!!");
            rs = pst.executeQuery();
            while(rs.next()){
                //System.out.println(rs.getLong("id")+" "+rs.getString("email")+" "+rs.getString("password")+" "+rs.getString("type"));
                list.add(new User(rs.getInt("id"),rs.getString("username"),rs.getString("password"),rs.getString("type")));
            }
        } catch(SQLException exp){
            System.out.println(exp.getMessage());
        }


        return list;


    }
    @Override
    public void update(User user) {
        PreparedStatement pst = null;
        String sql = "UPDATE user SET username=?, password=?, type=? WHERE id=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getType());
            pst.setInt(4, user.getId());
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User updated successfully!");
            } else {
                System.out.println("User with ID " + user.getId() + " not found.");
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
