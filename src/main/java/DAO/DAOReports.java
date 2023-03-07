package DAO;

import Utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scheduling_app.mattmullinsc195.NumCustomersReport;
import scheduling_app.mattmullinsc195.PreviousAppointmentsReport;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

/** This class is used to retrieve data from the database for the reports.
 *
 */
public class DAOReports {

    /** This method is used to retrieve the number of customers by type and month.
     * @return Observable list of Customers by type and month.
     */
    public static ObservableList<NumCustomersReport> getCustomersByTypeAndMonth() {
        ObservableList<NumCustomersReport> customersByTypeAndMonth = FXCollections.observableArrayList();
        try {
            String sql = "SELECT COUNT(customers.Customer_ID) AS numberOfCustomers, appointments.Type, MONTHNAME(appointments.Start) AS Month " +
                    "FROM customers " +
                    "INNER JOIN appointments ON customers.Customer_ID = appointments.Customer_ID " +
                    "GROUP BY appointments.Type, MONTHNAME(appointments.Start) " +
                    "ORDER BY appointments.Type, MONTHNAME(appointments.Start) ASC";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                customersByTypeAndMonth.add(new NumCustomersReport(
                        rs.getInt("numberOfCustomers"),
                        rs.getString("Type"),
                        rs.getString("Month")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customersByTypeAndMonth;
    }

    /** This method is used to retrieve all appointments that have already taken place.
     * @return Observable list of previous appointments.
     */
    public static ObservableList<PreviousAppointmentsReport> getPreviousAppointments() {
        ObservableList<PreviousAppointmentsReport> previousAppointments = FXCollections.observableArrayList();
        int numOfCustomers = 0;
        String type = "";
        String month = "";
        try {
            String sql = "SELECT appointments.Appointment_ID, appointments.End, appointments.Customer_ID " +
                    "FROM appointments " +
                    "WHERE appointments.End < NOW() " +
                    "ORDER BY appointments.End ASC";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("End");
                LocalDate End = timestamp.toLocalDateTime().toLocalDate();
                previousAppointments.add(new PreviousAppointmentsReport(
                        numOfCustomers,
                        type,
                        month,
                        rs.getInt("Appointment_ID"),
                        End,
                        rs.getInt("Customer_ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return previousAppointments;
    }
}

