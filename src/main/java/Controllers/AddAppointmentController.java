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
import java.time.format.DateTimeParseException;
import java.util.Objects;

/** This class is the controller for the addAppointments.fxml file.
 * It handles the logic for adding an appointment to the database.
 */
public class AddAppointmentController {

    /** ComboBox for contact selection.
     *
     */
    public ComboBox<Contacts> appointmentContactComboBox;

    /** TextField for Appointment Title.
     *
     */
    public TextField appointmentTitleField;
    /** TextField for Appointment Description.
     *
     */
    public TextField appointmentDescriptionField;
    /** TextField for appointment location.
     *
     */
    public TextField appointmentLocationField;
    /** TextField for appointment type.
     *
     */
    public TextField appointmentTypeField;
    /** DatePicker for Appointment Start Date.
     *
     */
    public DatePicker appointmentStartDate;
    /** DatePicker for appointment end date.
     *
     */
    public DatePicker appointmentEndDate;
    /** ComboBox for customer selection.
     *
     */
    public ComboBox<Customers> appointmentCustIDComboBox;
    /** ComboBox for User selection.
     *
     */
    public ComboBox<Users> appointmentUserIDComboBox;
    /** BUtton to save appointments in database.
     *
     */
    public Button saveAppointmentButton;
    /** Button to cancel and return to main screen.
     *
     */
    public Button cancelButton;
    /** Label to display userID
     *
     */
    public Label userIDLabel;
    /** Label to display customer ID
     *
     */
    public Label customerIDLabel;
    /** TextField for appointment start time.
     *
     */
    public TextField startTimeField;
    /** TextField for appointment end time.
     *
     */
    public TextField endTimeField;
    /** Used to parse customer ID from combo box selection.
     *
     */
    int customerIDParse;
    /** Used to parse user ID from combo box selection.
     *
     */
    int userIDParse;
    /** Used to parse contact ID form combo box selection.
     *
     */
    int contactIDParse;
    /** Used to hold the contact ID as a string.
     *
     */
    String contactIDSelection;
    /** Used to hold the customer ID as a string.
     *
     */
    String customerIDSelection;
    /** Used to hold the user ID as a string.
     *
     */
    String userIDSelection;
    /** Used to hold the Appointment Start Date.
     *
     */
    LocalDate startDate;
    /** Used to hold the appointment end date.
     *
     */
    LocalDate endDate;
    /** Used to hold the appointment start time.
     *
     */
    LocalTime startTime;
    /** Used to hold the appointment end time.
     *
     */
    LocalTime endTime;
    /** Used to combine startDate and startTime into a LocalDateTime.
     *
     */
    LocalDateTime startDateTime;
    /** Used to combine endDate and endTime into a LocalDateTime.
     *
     */
    LocalDateTime endDateTime;
    /** Used to hold startDateTime after conversion to EST time.
     *
     */
    public LocalDateTime startDateTimeEst;
    /**Used to hold endDateTime after conversion to EST time.
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

    /** This method is used to initialize the screen.
     *  It populates the combo boxes with the appropriate data.
     */
    public void initialize() {
        appointmentContactComboBox.setItems(DAOContact.getAllContacts());
        appointmentCustIDComboBox.setItems(DAOCustomer.getAllCustomers());
        appointmentUserIDComboBox.setItems(DAOUsers.getAllUsers());

    }

    /** This method is used to save the appointment to the database.
     * It contains the exception handling for the add appointment functionality.
     * This method contains the logic for the add appointment functionality.
     * The lambda expression is called and the screen is changed to the main screen if the appointment is successfully added to the database.
     * @param actionEvent
     * @throws IOException
     */
    public void saveButtonAction(ActionEvent actionEvent) throws IOException {

        try {
            String title = appointmentTitleField.getText();
            String description = appointmentDescriptionField.getText();
            String location = appointmentLocationField.getText();
            String type = appointmentTypeField.getText();
            startDate = appointmentStartDate.getValue();
            endDate = appointmentEndDate.getValue();
            startTime = LocalTime.parse(startTimeField.getText());
            endTime = LocalTime.parse(endTimeField.getText());
            startDateTime = LocalDateTime.of(startDate, startTime);
            endDateTime = LocalDateTime.of(endDate, endTime);
            startDateTimeEst = startDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalDateTime();
            endDateTimeEst = endDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalDateTime();

        if(appointmentTitleField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Appointment Title is required");
            alert.setContentText("Please enter a title for the appointment");
            alert.showAndWait();
        } else if(appointmentDescriptionField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Appointment Description is required");
            alert.setContentText("Please enter a description for the appointment");
            alert.showAndWait();
        } else if(appointmentLocationField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Appointment Location is required");
            alert.setContentText("Please enter a location for the appointment");
            alert.showAndWait();
        } else if(appointmentTypeField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Appointment Type is required");
            alert.setContentText("Please enter a type for the appointment");
            alert.showAndWait();
        } else if(appointmentStartDate.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Appointment Start Date is required");
            alert.setContentText("Please enter a start date for the appointment");
            alert.showAndWait();
        } else if(appointmentEndDate.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Appointment End Date is required");
            alert.setContentText("Please enter an end date for the appointment");
            alert.showAndWait();
        } else if(startTimeField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Appointment Start Time is required");
            alert.setContentText("Please enter a valid start time for the appointment");
            alert.showAndWait();
        } else if(endTimeField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter a valid end time for the appointment");
        } else if(appointmentCustIDComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Appointment Customer ID is required");
            alert.setContentText("Please enter a customer ID for the appointment");
            alert.showAndWait();
        } else if(appointmentUserIDComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Appointment User ID is required");
            alert.setContentText("Please enter a user ID for the appointment");
            alert.showAndWait();
        } else if(appointmentContactComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Appointment Contact ID is required");
            alert.setContentText("Please enter a contact ID for the appointment");
            alert.showAndWait();
        }  else if (appointmentStartDate.getValue().isAfter(appointmentEndDate.getValue())) {
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
        }

        else {

            boolean businessHours = true;


            if (startDateTimeEst.toLocalTime().isBefore(LocalTime.of(8, 0)) || startDateTimeEst.toLocalTime().isAfter(LocalTime.of(22, 0))
                    || endDateTimeEst.toLocalTime().isBefore(LocalTime.of(8, 0)) || endDateTimeEst.toLocalTime().isAfter(LocalTime.of(22, 0))) {
                businessHours = false;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Appointment is outside of business hours");
                alert.setContentText("Appointment must be between 8:00 EST and 22:00 EST");
                alert.showAndWait();

            } else if (businessHours) {
                DAOAppointment.convertToUTC(startDateTime);
                DAOAppointment.convertToUTC(endDateTime);
                int customerId = customerIDParse;
                int userId = userIDParse;
                int contactId = contactIDParse;
                DAOAppointment.addAppointment(title, description, location, type, startDateTime, endDateTime, customerId, userId, contactId);
                //screen change to main menu where lambda is called.
               screenChange.setScreen(saveAppointmentButton, "mainView",1000, 700);
            }
        }}
        catch (NumberFormatException | NullPointerException | DateTimeParseException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Appointment Time is invalid");
            alert.setContentText("Please enter a valid time for the appointment: Ex: 00:00, 05:00, 12:15, 15:45, 23:30");
            alert.showAndWait();
        }
    }

    /** This method checks if the appointment overlaps with another appointment.
     * @return true if the appointment overlaps with another appointment and false if it does not.
     */
    private boolean appointmentOverlaps() {
        for (Appointments appointment : DAOAppointment.getAllAppointments()) {
            if (appointment.getCustomerId() == customerIDParse) {
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

    /** This method is called when the cancel button is clicked.
     * It calls the lambda expression and changes the screen to the main menu.
     * @param actionEvent
     */
    public void cancelButtonAction(ActionEvent actionEvent) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cancel");
            alert.setHeaderText("Cancel");
            alert.setContentText("Are you sure you want to cancel?");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                try {
                    //Screen change to main menu where lambda is called.
                    screenChange.setScreen(saveAppointmentButton, "mainView", 1000, 700);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    /** This method is used refresh the contact combo box when it is are clicked.
     * @param actionEvent
     */
    public void contactBoxAction(ActionEvent actionEvent) {
        contactIDSelection = appointmentContactComboBox.getValue().toString();
        contactIDParse = Integer.parseInt(contactIDSelection.substring(0, contactIDSelection.indexOf(" ")));
    }

    /** This method is used refresh the customer combo box when it is are clicked.
     * @param actionEvent
     */
    public void customerIDBoxAction(ActionEvent actionEvent) {
        customerIDSelection = appointmentCustIDComboBox.getValue().toString();
        customerIDParse = Integer.parseInt(customerIDSelection.substring(0, customerIDSelection.indexOf(" ")));
    }

    /** This method is used refresh the user combo box when it is are clicked.
     * @param actionEvent
     */
    public void userIDBoxAction(ActionEvent actionEvent) {
        userIDSelection = appointmentUserIDComboBox.getValue().toString();
        userIDParse = Integer.parseInt(userIDSelection.substring(0, userIDSelection.indexOf(" ")));
    }
}
