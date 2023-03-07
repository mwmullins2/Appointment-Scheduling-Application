package DAO;

import Controllers.LoginController;
import Utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scheduling_app.mattmullinsc195.Appointments;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;

/** This class is used to access the database and retrieve appointment information.
 *
 */
public class DAOAppointment {

    /** This method is used to retrieve all appointments from the database.
     * @return ObervableList of  All Appointments
     */
    public static ObservableList<Appointments> getAllAppointments() {
        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, " +
                    "appointments.Location, appointments.Type, appointments.Start, appointments.End, appointments.Customer_ID, " +
                    "appointments.User_ID, appointments.Contact_ID, contacts.Contact_Name FROM appointments " +
                    "INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Timestamp timestamp = rs.getTimestamp("Start");
                ZonedDateTime zonedDateTime = timestamp.toInstant().atZone(ZonedDateTime.now().getZone());
                LocalDateTime Start = zonedDateTime.toLocalDateTime();
                Timestamp timestamp2 = rs.getTimestamp("End");
                ZonedDateTime zonedDateTime2 = timestamp2.toInstant().atZone(ZonedDateTime.now().getZone());
                LocalDateTime End = zonedDateTime2.toLocalDateTime();
                allAppointments.add(new Appointments(
                        Integer.parseInt(rs.getString("Appointment_ID")),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Contact_Name"),
                        rs.getString("Type"),
                        Start,
                        End,
                        Integer.parseInt(rs.getString("Customer_ID")),
                        Integer.parseInt(rs.getString("User_ID")),
                        Integer.parseInt(rs.getString("Contact_ID"))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }


    /** This method is used to add an appointment to the database.
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactID
     */
    public static void addAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {
        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setString(7, LoginController.userName);
            ps.setString(8, LoginController.userName);
            ps.setInt(9, customerID);
            ps.setInt(10, userID);
            ps.setInt(11, contactID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** This method is used to delete an appointment from the database.
     * @param appointmentID
     */
    public static void deleteAppointment(int appointmentID) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, appointmentID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** This method is used to update an appointment in the database.
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactID
     * @param appointmentID
     */
    public static void updateAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID, int appointmentID) {
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = NOW(), Last_Updated_By = ?," +
                    " Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setString(7, LoginController.userName);
            ps.setInt(8, customerID);
            ps.setInt(9, userID);
            ps.setInt(10, contactID);
            ps.setInt(11, appointmentID);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** This method is used to convert the local system time to UTC time.
     * @param startDateTime
     * @return
     */
    public static LocalDateTime convertToUTC(LocalDateTime startDateTime) {
        ZoneId localZoneId = ZoneId.systemDefault();
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZonedDateTime localStartDateTime = startDateTime.atZone(localZoneId);
        ZonedDateTime utcStartDateTime = localStartDateTime.withZoneSameInstant(utcZoneId);
        LocalDateTime utcStart = utcStartDateTime.toLocalDateTime();
        return utcStart;
    }

    /**  This method is used to find appointments in the database using a contact ID.
     * @param contactID
     * @return ObersevableList of appointments for the contact.
     */
    public static ObservableList<Appointments> appointmentByContactID(int contactID) {
        ObservableList<Appointments> appointmentByContactID = FXCollections.observableArrayList();
        try {
            String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, " +
                    "appointments.Location, appointments.Type, appointments.Start, appointments.End, appointments.Customer_ID, " +
                    "appointments.User_ID, appointments.Contact_ID, contacts.Contact_Name FROM appointments " +
                    "INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE appointments.Contact_ID = ?";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contactID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Timestamp timestamp = rs.getTimestamp("Start");
                ZonedDateTime zonedDateTime = timestamp.toInstant().atZone(ZonedDateTime.now().getZone());
                LocalDate StartDate = zonedDateTime.toLocalDate();
                LocalTime StartTime = zonedDateTime.toLocalTime();
                Timestamp timestamp2 = rs.getTimestamp("End");
                ZonedDateTime zonedDateTime2 = timestamp2.toInstant().atZone(ZonedDateTime.now().getZone());
                LocalDate EndDate = zonedDateTime2.toLocalDate();
                LocalTime EndTime = zonedDateTime2.toLocalTime();
                LocalDateTime Start = LocalDateTime.of(StartDate, StartTime);
                LocalDateTime End = LocalDateTime.of(EndDate, EndTime);
                appointmentByContactID.add(new Appointments(
                        Integer.parseInt(rs.getString("Appointment_ID")),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Contact_Name"),
                        rs.getString("Type"),
                        Start,
                        End,
                        Integer.parseInt(rs.getString("Customer_ID")),
                        Integer.parseInt(rs.getString("User_ID")),
                        Integer.parseInt(rs.getString("Contact_ID"))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentByContactID;
    }

}