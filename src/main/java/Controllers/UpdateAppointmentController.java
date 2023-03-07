package Controllers;

import DAO.DAOAppointment;
import DAO.DAOContact;
import DAO.DAOCustomer;
import DAO.DAOUsers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import scheduling_app.mattmullinsc195.*;

import java.io.IOException;
import java.time.*;
import java.util.Objects;

/** This class is the controller for the Update Appointment screen. It handles the
 * updating of appointment information.
 */
public class UpdateAppointmentController {

    /** ComboBox to display contact information.
     *
     */
    public ComboBox<Contacts> appointmentContactComboBox;

    /** TextField to enter appointment title.
     *
     */
    public TextField appointmentTitleField;

    /** TextField to enter appointment description.
     *
     */
    public TextField appointmentDescriptionField;

    /** TextField to enter appointment location.
     *
     */
    public TextField appointmentLocationField;

    /** TextField to enter appointment type.
     *
     */
    public TextField appointmentTypeField;

    /** DatePicker to select appointment Start Date.
     *
     */
    public DatePicker appointmentStartDate;

    /** DatePicker to select appointment End Date.
     *
     */
    public DatePicker appointmentEndDate;

    /** ComboBox to display customer information.
     *
     */
    public ComboBox<Customers> appointmentCustIDComboBox;

    /** ComboBox to display user information.
     *
     */
    public ComboBox<Users> appointmentUserIDComboBox;

    /** Button to initiate the update process.
     *
     */
    public Button updateAppointmentButton;

    /** Button to cancel and return to the main screen without and data being changed.
     *
     */
    public Button cancelButton;
    public Label userIDLabel;
    public Label customerIDLabel;

    /** TextField where the appointment ID is displayed.
     *
     */
    public TextField appointmentIdField;

    /** TextField where the start time is entered.
     *
     */
    public TextField startTimeField;

    /** TextField where the end time is entered.
     *
     */
    public TextField endTimeField;

    /** Int to hold the appointment ID
     *
     */
    public static int appointmentID;

    /** LocalDate to hold the start date.
     *
     */
    public LocalDate startDate;

    /** LocalDate to hold the end date.
     *
     */
    public LocalDate endDate;

    /** LocalTime to hold the start time.
     *
     */
    public LocalTime startTime;

    /** LocalTime to hold the end time.
     *
     */
    public LocalTime endTime;

    /** Combines the start date and stare time into a LocalDateTime.
     *
     */
    public LocalDateTime startDateTime;

    /** Combines the end date and end time into a LocalDateTime.
     *
     */
    public LocalDateTime endDateTime;

    /** Holds the startDateTime after conversion to EST.
     *
     */
    public LocalDateTime startDateTimeEst;

    /** Holds the endDateTime after converions to EST.
     *
     */
    public LocalDateTime endDateTimeEst;

    /** This Lambda expression is used to change the screen when a button is clicked.
     *
     */
    ScreenChange screenChange = (button, screen, width, height) -> {
        Stage stage = (Stage) button.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/scheduling_app/mattmullinsc195/" + screen + ".fxml")));
        stage.setTitle("C195 Scheduling App");
        stage.setScene(new Scene(root, width, height));
        stage.show();
    };

    /** This method is used to initialize the screen. It populates the combo boxes and fields with
     * the information from the selected appointment.
     */
    public void initialize() {
        Appointments selectedAppointment = MainScreenController.selectedAppointment;
        appointmentIdField.setText(String.valueOf(selectedAppointment.getAppointmentId()));
        appointmentContactComboBox.setItems(DAOContact.getAllContacts());
        appointmentContactComboBox.setValue(DAOContact.getContactById(selectedAppointment.getContactId()));
        appointmentCustIDComboBox.setItems(DAOCustomer.getAllCustomers());
        appointmentCustIDComboBox.setValue(DAOCustomer.getCustomerById(selectedAppointment.getCustomerId()));
        appointmentUserIDComboBox.setItems(DAOUsers.getAllUsers());
        appointmentUserIDComboBox.setValue(DAOUsers.getUserById(selectedAppointment.getUserId()));
        appointmentTitleField.setText(selectedAppointment.getTitle());
        appointmentDescriptionField.setText(selectedAppointment.getDescription());
        appointmentLocationField.setText(selectedAppointment.getLocation());
        appointmentTypeField.setText(selectedAppointment.getType());
        appointmentStartDate.setValue(selectedAppointment.getStart().toLocalDate());
        appointmentEndDate.setValue(selectedAppointment.getEnd().toLocalDate());
        startTimeField.setText(selectedAppointment.getStart().toLocalTime().toString());
        endTimeField.setText(selectedAppointment.getEnd().toLocalTime().toString());
        appointmentID = Integer.parseInt(appointmentIdField.getText());

    }

    /** This method is used to update the appointment information in the database. It contains the exception handling for
     * the update appointment functionality. It contains all the logic for the update appointment functionality.
     * @param actionEvent
     */
    public void updateButtonAction(ActionEvent actionEvent) {
        try {
            startDate = appointmentStartDate.getValue();
            endDate = appointmentEndDate.getValue();
            if(startTimeField.getText().length() == 5){
            startTime = LocalTime.parse(startTimeField.getText());
            endTime = LocalTime.parse(endTimeField.getText());
            startDateTime = LocalDateTime.of(startDate, startTime);
            endDateTime = LocalDateTime.of(endDate, endTime);}
            startDateTimeEst = startDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalDateTime();
            endDateTimeEst = endDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalDateTime();
            if (appointmentTitleField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Appointment Title is required");
                alert.setContentText("Please enter a title for the appointment");
                alert.showAndWait();
            } else if (appointmentDescriptionField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Appointment Description is required");
                alert.setContentText("Please enter a description for the appointment");
                alert.showAndWait();
            } else if (appointmentLocationField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Appointment Location is required");
                alert.setContentText("Please enter a location for the appointment");
                alert.showAndWait();
            } else if (appointmentTypeField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Appointment Type is required");
                alert.setContentText("Please enter a type for the appointment");
                alert.showAndWait();
            } else if (appointmentStartDate.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Appointment Start Date is required");
                alert.setContentText("Please enter a start date for the appointment");
                alert.showAndWait();
            } else if (appointmentEndDate.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Appointment End Date is required");
                alert.setContentText("Please enter an end date for the appointment");
                alert.showAndWait();
            } else if (startTimeField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Appointment Start Time is required");
                alert.setContentText("Please enter a valid start time for the appointment");
                alert.showAndWait();
            } else if (endTimeField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please enter a valid end time for the appointment");
            } else if (appointmentCustIDComboBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Appointment Customer ID is required");
                alert.setContentText("Please enter a customer ID for the appointment");
                alert.showAndWait();
            } else if (appointmentUserIDComboBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Appointment User ID is required");
                alert.setContentText("Please enter a user ID for the appointment");
                alert.showAndWait();
            } else if (appointmentContactComboBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Appointment Contact ID is required");
                alert.setContentText("Please enter a contact ID for the appointment");
                alert.showAndWait();
            } else if (appointmentStartDate.getValue().isAfter(appointmentEndDate.getValue())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Appointment Start Date is invalid");
                alert.setContentText("Start date cannot be after end date");
                alert.showAndWait();
            } else if (startDateTime.isAfter(endDateTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Appointment Start Time is invalid");
                alert.setContentText("Appointment start time cannot be after end time");
                alert.showAndWait();
            } else if (appointmentOverlaps()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Appointment overlaps with another appointment");
                alert.setContentText("Appointment overlaps with another appointment. Please select a different time");
                alert.showAndWait();
            } else {
                boolean businessHours = true;

                if (startDateTimeEst.toLocalTime().isBefore(LocalTime.of(8, 0)) || startDateTimeEst.toLocalTime().isAfter(LocalTime.of(22, 0))
                        || endDateTimeEst.toLocalTime().isBefore(LocalTime.of(8, 0)) || endDateTimeEst.toLocalTime().isAfter(LocalTime.of(22, 0))) {
                    businessHours = false;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Appointment is outside of business hours");
                    alert.setContentText("Appointment must be between 8:00 EST and 22:00 EST");
                    alert.showAndWait();
                }
                if (businessHours){
                    DAOAppointment.convertToUTC(startDateTime);
                    DAOAppointment.convertToUTC(endDateTime);
                    DAOAppointment.updateAppointment(appointmentTitleField.getText(), appointmentDescriptionField.getText(), appointmentLocationField.getText(), appointmentTypeField.getText(), startDateTime, endDateTime, appointmentCustIDComboBox.getValue().getCustomerId(), appointmentUserIDComboBox.getValue().getUserId(),
                            appointmentContactComboBox.getValue().getContactId(), appointmentID);
                   screenChange.setScreen(updateAppointmentButton, "mainView", 1000, 700);
                }
            }
        }
        catch (NumberFormatException | NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Appointment Time is invalid");
            alert.setContentText("Please enter a valid time for the appointment: Ex: 00:00, 05:00, 12:15, 15:45, 23:30");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** This method cancels the update appointment process and returns to the main screen.
     * No changes are made to the appointment.
     * @param actionEvent
     */
    public void cancelButtonAction (ActionEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Cancel");
        alert.setContentText("Are you sure you want to cancel?");
        alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                try {
                    screenChange.setScreen(cancelButton, "mainView", 1000, 700);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    /** This method checks if the appointment overlaps with another appointment.
     * @return true if the appointment overlaps with another appointment, false if it does not.
     */
    private boolean appointmentOverlaps() {
        for (Appointments appointment : DAOAppointment.getAllAppointments()) {
            if (appointment.getCustomerId() == appointmentCustIDComboBox.getValue().getCustomerId() && appointment.getAppointmentId() != appointmentID) {
                if (startDateTime.isAfter(appointment.getStart()) && startDateTime.isBefore(appointment.getEnd())) {
                    return true;
                } else if (endDateTime.isAfter(appointment.getStart()) && endDateTime.isBefore(appointment.getEnd())) {
                    return true;
                } else if (startDateTime.isBefore(appointment.getStart()) && endDateTime.isAfter(appointment.getEnd())) {
                    return true;
                } else if (startDateTime.isEqual(appointment.getStart()) || endDateTime.isEqual(appointment.getEnd())) {
                    return true;
                }
            }
        }
        return false;
    }

}