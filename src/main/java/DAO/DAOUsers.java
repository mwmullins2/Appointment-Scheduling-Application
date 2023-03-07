package DAO;

import Utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scheduling_app.mattmullinsc195.Users;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/** This class is used to access the database and retrieve the user information.
 *
 */
public class DAOUsers {

    /** This method is used to retrieve users from the database by user ID.
     * @param userId
     * @return User with the matching user ID.
     */
    public static Users getUserById(int userId) {
        Users user = null;
        try {
            String sql = "SELECT * FROM users WHERE User_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new Users(
                        Integer.parseInt(rs.getString("User_ID")),
                        rs.getString("User_Name"),
                        rs.getString("Password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /** This method is used to check if the inputted username and password match a user in the database.
     * @param userName
     * @param password
     * @return
     * @throws SQLException
     */
    public boolean checkUserCredentials(String userName, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /** This method is used to retrieve all users from the database.
     * @return ObservableList of all users.
     */
    public static ObservableList<Users> getAllUsers() {
        ObservableList<Users> allUsers = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                allUsers.add(new Users(
                        Integer.parseInt(rs.getString("User_ID")),
                        rs.getString("User_Name"),
                        rs.getString("Password")));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return allUsers;
    }

}
