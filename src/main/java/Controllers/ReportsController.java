package Controllers;

import DAO.DAOAppointment;
import DAO.DAOContact;
import DAO.DAOReports;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import scheduling_app.mattmullinsc195.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/** This class is the controller for the Reports screen.
 * It handles the logic for the reports screen.
 * It also contains LAMBDA EXPRESSION #2
 *
 * ----LAMBDA EXPRESSION #2----
 * A lambda expression is used to set the cell values in a few columns.
 */
public class ReportsController {

    /** TableView to hold and display number of Customers by type and month report.
     *
     */
    public TableView<NumCustomersReport> numberCustomersReportTable;
    public TableColumn<Reports, Integer> numbCustomersCol;
    public TableColumn<Reports,String> numCustTypeCol;
    public TableColumn<Reports, String> numbCustMonthCol;

    /** TableView to hold and display the previous appointments report.
     *
     */
    public TableView<PreviousAppointmentsReport> customReportTable;

    /** TableView to hold and display contact schedule reports.
     *
     */
    public TableView<Appointments> contactScheduleReportTable;
    public TableColumn<Appointments, Integer> scheduleAppIDCol;
    public TableColumn<Appointments, String> scheduleTitleCol;
    public TableColumn<Appointments, String > scheduleTypeCol;
    public TableColumn<Appointments, String> scheduleDescriptionCol;
    public TableColumn<Appointments, LocalDateTime> scheduleStartCol;
    public TableColumn<Appointments, LocalDateTime> scheduleEndCol;
    public TableColumn<Appointments, Integer> scheduleCustIDCol;

    /** ComboBox used to select which contact schedule to view.
     *
     */
    public ComboBox<Contacts> contactComboBox;

    /** Button to return to the main screen.
     *
     */
    public Button exitButton;
    public TableColumn<PreviousAppointmentsReport, Integer> previousAppointmentIDCol;
    public TableColumn<PreviousAppointmentsReport, Integer> previousCustomerIDCol;
    public TableColumn<PreviousAppointmentsReport, LocalDate> previousAppointmentEndCol;

    /** Int to hold the contact ID.
     *
     */
    public int selectedContactID;

    /** This Lambda expression is used to change screens.
     *
     */
    ScreenChange screenChange = (button, screen, width, height) -> {
        Stage stage = (Stage) button.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/scheduling_app/mattmullinsc195/" + screen + ".fxml")));
        stage.setTitle("C195 Scheduling App");
        stage.setScene(new Scene(root, width, height));
        stage.show();
    };

    /** This method initializes the reports screen.
     * It populates the tables and combo box.
     * It also sets the cell value factories for the tables.
     * ----LAMBDA EXPRESSION #2----
     * A lambda expression is used to set the cell values in a few columns.
     * The lambda expression was used to set the cell value for the column because
     * there was an error while setting it with a PropertyValueFactory.
     * The lambda expression fixed the issue and the cell value was set with the correct data.
     */
    public void initialize() {
        ObservableList<NumCustomersReport> customersByTypeAndMonth = DAOReports.getCustomersByTypeAndMonth();
        numberCustomersReportTable.setItems(customersByTypeAndMonth);
        numbCustomersCol.setCellValueFactory(new PropertyValueFactory<>("numberOfCustomers"));
        numCustTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        numbCustMonthCol.setCellValueFactory(new PropertyValueFactory<>("month"));

        ObservableList<PreviousAppointmentsReport> customReport = DAOReports.getPreviousAppointments();
        customReportTable.setItems(customReport);
        previousAppointmentIDCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAppointmentId()).asObject());
        previousCustomerIDCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCustomerId()).asObject());
        previousAppointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));

        contactComboBox.setItems(DAOContact.getAllContacts());
    }

    /** This method is used to select and populate the contact schedule report table.
     * Lambda expression #2 is used to set some cell values in the table that were not populating correctly with PropertyValueFactory.
     * @param actionEvent
     */
    public void contactComboBoxAction(ActionEvent actionEvent) {
        selectedContactID = contactComboBox.getSelectionModel().getSelectedItem().getContactId();
        ObservableList<Appointments> contactSchedule = DAOAppointment.appointmentByContactID(selectedContactID);
        contactScheduleReportTable.setItems(contactSchedule);
        for (Appointments appointment : contactSchedule)
            if(appointment.getContactId() == selectedContactID) {
                scheduleAppIDCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAppointmentId()).asObject());
                scheduleTitleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));
                scheduleTypeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getType()));
                scheduleDescriptionCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));
                scheduleStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
                scheduleEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
                scheduleCustIDCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCustomerId()).asObject());
            }
    }

    /** This method is used to exit the reports screen.
     * @param actionEvent
     */
    public void exitButtonAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Return to Main Menu");
        alert.setHeaderText("Are you sure you want to return to the main menu?");
        alert.setContentText("Click OK to return to the main menu.");
        alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                try {
                    screenChange.setScreen(exitButton, "mainView", 1000, 700);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

}
