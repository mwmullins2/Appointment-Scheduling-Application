package DAO;

import Utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scheduling_app.mattmullinsc195.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class is used to access the database and retrieve contact information.
 *
 */
public class DAOContact {
    /** This method is used to retrieve all contacts from the database.
     * @return
     */
    public static ObservableList<Contacts> getAllContacts() {
        ObservableList<Contacts> allContacts = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM contacts";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                allContacts.add(new Contacts(
                        Integer.parseInt(rs.getString("Contact_ID")),
                        rs.getString("Contact_Name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allContacts;
    }

    /** This method retrieves contacts from the database based on the contact ID.
     * @param contactId
     * @return contact
     */
    public static Contacts getContactById(int contactId) {
        Contacts contact = null;
        try {
            String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contactId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                contact = new Contacts(
                        Integer.parseInt(rs.getString("Contact_ID")),
                        rs.getString("Contact_Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contact;
    }
}
