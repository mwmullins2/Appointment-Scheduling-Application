package DAO;

import Controllers.LoginController;
import Controllers.UpdateCustomerController;
import Utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scheduling_app.mattmullinsc195.Countries;
import scheduling_app.mattmullinsc195.Customers;
import scheduling_app.mattmullinsc195.Divisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/** This class is used to access the database and retrieve customer information.
 *
 */
public class DAOCustomer {

    /** This method is used to retrieve all customers from the database.
     * @return ObservableList of all customers.
     */
    public static ObservableList<Customers> getAllCustomers() {
        ObservableList<Customers> allCustomers = FXCollections.observableArrayList();
        try {
            String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, customers.Division_ID, first_level_divisions.Division  FROM customers " +
                    "INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                allCustomers.add(new Customers(
                        Integer.parseInt(rs.getString("Customer_ID")),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        rs.getString("Division")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomers;
    }

    /** This method is used to add a new customer to the database.
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param divisionID
     */
    public static void addCustomer(String customerName, String address, String postalCode, String phone, int divisionID) {
        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, NOW(), ?, NOW(), ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, LoginController.userName);
            ps.setString(6, LoginController.userName);
            ps.setInt(7, divisionID);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** This method is used to update customer information in the database.
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param divisionID
     */
    public static void updateCustomer(String customerName, String address, String postalCode, String phone, int divisionID) {
        try {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = NOW(), Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, LoginController.userName);
            ps.setInt(6, divisionID);
            ps.setInt(7, UpdateCustomerController.customerID);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** This method is used to retrieve all countries from the database.
     * @return
     * @throws SQLException
     */
    public static ObservableList<Countries> getAllCountries() throws SQLException {
        ObservableList<Countries> allCountries = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                allCountries.add(new Countries(
                        Integer.parseInt(rs.getString("Country_ID")),
                        rs.getString("Country")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allCountries;
    }

    /** This method is used to retrieve all  first level divisions from the database.
     * @return ObservableList of all first level divisions.
     * @throws SQLException
     */
    public static ObservableList<Divisions> getAllDivisions() throws SQLException {
        ObservableList<Divisions> allDivisions = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM first_level_divisions";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                allDivisions.add(new Divisions(
                        Integer.parseInt(rs.getString("Division_ID")),
                        rs.getString("Division"),
                        Integer.parseInt(rs.getString("Country_ID"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allDivisions;
    }

    /** This method is used to retrieve all first level divisions from the database based on the country ID.
     * @param countryID
     * @return ObservableList of divisions filtered by country ID.
     * @throws SQLException
     */
    public static ObservableList<Divisions> filterDivisions(int countryID) throws SQLException {
        ObservableList<Divisions> filteredDivisions = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                filteredDivisions.add(new Divisions(
                        Integer.parseInt(rs.getString("Division_ID")),
                        rs.getString("Division"),
                        Integer.parseInt(rs.getString("Country_ID"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filteredDivisions;
    }

    /** This method is used to retrieve the country of a customer from the database based on the customer ID
     * @param customerID
     * @return The customer's country.
     * @throws SQLException
     */
    public static ObservableList<Countries> getCustomerCountry(int customerID) throws SQLException {
        ObservableList<Countries> customerCountry = FXCollections.observableArrayList();
        try {
            String sql = "SELECT countries.Country FROM customers " +
                    "INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " +
                    "INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID " +
                    "WHERE customers.Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, UpdateCustomerController.customerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UpdateCustomerController.customerCountry = rs.getString("Country");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerCountry;
    }

    /** This method is used to retrieve the division of a customer from the database based on the customer ID.
     * @param customerID
     * @return The customer's division.
     * @throws SQLException
     */
    public static ObservableList<Divisions> getCustomerDivision(int customerID) throws SQLException {
        ObservableList<Divisions> customerCountry = FXCollections.observableArrayList();
        try {
            String sql = "SELECT first_level_divisions.Division FROM customers " +
                    "INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " +
                    "INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID " +
                    "WHERE customers.Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, UpdateCustomerController.customerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UpdateCustomerController.customerDivision = rs.getString("Division");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerCountry;
    }

    /** This method is used to delete a customer within the database using the customer ID.
     * @param customerID
     */
    public static void deleteCustomer(int customerID) {
        try {
            String sql = "DELETE FROM appointments WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerID);
            ps.execute();
        } catch (SQLException e) {
        }
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerID);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** This method is used to retrieve a customer with a specific customer ID from the database.
     * @param customerId
     * @return The customer with the specified customer ID.
     */
    public static Customers getCustomerById(int customerId) {
        Customers customer = null;
        try {
            String sql = "SELECT * FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int newCustomerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                String division = rs.getString("Division_ID");
                customer = new Customers(customerId, customerName, address, postalCode, phone, division);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return customer;
    }

}