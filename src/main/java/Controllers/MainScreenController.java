package Controllers;

import DAO.DAOAppointment;
import DAO.DAOCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import scheduling_app.mattmullinsc195.Appointments;
import scheduling_app.mattmullinsc195.Customers;
import scheduling_app.mattmullinsc195.ScreenChange;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

/** This class is the controller for the Main Screen.
 * It handles the tables and buttons on the screen.
 * This class also includes LAMBDA EXPRESSION #1.
 * ----LAMBDA EXPRESSION #1----
 * This lambda expression is used to change the screen when a button is clicked.
 * This reduces the amount of code within the classes where it is used. Instead of having the code to change the screen in each
 * method where the screen changes, it is now in one place within each class. This makes the code more readable and easier to maintain.
 * All the method has to do is call the lambda expression and pass in the button, screen, width, and height.
 */
public class MainScreenController {

    /** Customers to hold selected customer.
     *
     */
    public static Customers selectedCustomer;

    /** ToggleGroup for the appointment view radio buttons.
     *
     */
    public ToggleGroup appointmentGroup;

    /** Appointments to hold the selected appointment.
     *
     */
    public static Appointments selectedAppointment;

    /** TableView to hold and display appointment information.
     *
     */
    public TableView<Appointments> appointmentTable;
    public TableColumn<Appointments, Integer> appointmentIDCol;
    public TableColumn<Appointments, String> appointmentTitleCol;
    public TableColumn<Appointments, String> appointmentTypeCol;
    public TableColumn<Appointments, String> appointmentDescriptionCol;
    public TableColumn<Appointments, String> appointmentLocationCol;
    public TableColumn<Appointments, LocalDateTime> appointmentStartCol;
    public TableColumn<Appointments, LocalDateTime> appointmentEndCol;
    public TableColumn<Appointments, String> appointmentContactCol;
    public TableColumn<Appointments, Integer> appointmentCustomerIdCol;
    public TableColumn<Appointments, Integer> appointmentUserIDCol;

    /** TableView to hold and dispaly customer information.
     *
     */
    public TableView<Customers> customerTable;
    public TableColumn<Customers, Integer> customerIDCol;
    public TableColumn<Customers, String> customerNameCol;
    public TableColumn<Customers, String> customerAddressCol;
    public TableColumn<Customers, String> customerPhoneCol;
    public TableColumn<Customers, String> customerProvinceCol;
    public TableColumn<Customers, String> customerPostalCol;

    /** Button to take the user to the add appointment screen.
     *
     */
    public Button addAppointmentButton;

    /** Button to take the user to the update appointment screen.
     *
     */
    public Button updateAppointmentButton;

    /** Button to delete an appointment.
     *
     */
    public Button deleteAppointmentsButton;

    /** Button to take the user to the add customer screen.
     *
     */
    public Button addCustomerButton;

    /** Button to take the user to the update customer screen.
     *
     */
    public Button updateCustomerButton;

    /** Button to delete a customer and their associated appointments.
     *
     */
    public Button deleteCustomerButton;

    /** RadioButton to view appointments by week.
     *
     */
    public RadioButton weekAppointmentsRadio;

    /** RadioButton to view all appointments.
     *
     */
    public RadioButton allAppointmentsRadio;

    /** RadioButton to view appointments by month.
     *
     */
    public RadioButton monthAppointmentsRadio;

    /** Button to take the user to the reports page.
     *
     */
    public Button reportsButton;
    public TableColumn<Appointments, Integer> contactIDCol;

    /** ObservableList to hold all appointments.
     *
     */
    public ObservableList<Appointments> allAppointments;

    /** LAMBDA EXPRESSION #1 - This lambda expression is used to change the screen when a button is clicked.
     * This reduces the amount of code within the classes where it is used. Instead of having the code to change the screen in each
     * method where the screen changes, it is now in one place within each class. This makes the code more readable and easier to maintain.
     * All the method has to do is call the lambda expression and pass in the button, screen, width, and height.
     */
    ScreenChange screenChange = (button, screen, width, height) -> {
        Stage stage = (Stage) button.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/scheduling_app/mattmullinsc195/" + screen + ".fxml")));
        stage.setTitle("C195 Scheduling App");
        stage.setScene(new Scene(root, width, height));
        stage.show();
    };


    /**  This is the initialize method. It is called when the screen is loaded.
     *  It populates the tables and sets the columns for the tables.
     */
    public void initialize() {
        customerTable.setItems(DAOCustomer.getAllCustomers());
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerProvinceCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        customerPostalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        appointmentTable.setItems(DAOAppointment.getAllAppointments());
        ObservableList<Appointments> allAppointments = DAOAppointment.getAllAppointments();
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        contactIDCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
    }

    /** This method changes the appointment table so the user can view appointments this week.
     * @param actionEvent
     */
    public void weeklyAppointmentRadioSelected(ActionEvent actionEvent) {
        ObservableList<Appointments> weeklyAppointments = FXCollections.observableArrayList();
        if(allAppointments == null) {
            allAppointments = DAOAppointment.getAllAppointments();
        }
        for (Appointments appointment : allAppointments) {
            if (appointment.getStart().isAfter(LocalDateTime.now()) && appointment.getStart().isBefore(LocalDateTime.now().plusWeeks(1))) {
                weeklyAppointments.add(appointment);
            }
        }
        appointmentTable.setItems(weeklyAppointments);
    }

    /** This method changes the appointment table so the user can view all appointments.
     * @param actionEvent
     */
    public void allAppointmentRadioSelected(ActionEvent actionEvent) {
        appointmentTable.setItems(DAOAppointment.getAllAppointments());
    }

    /** This method changes the appointment table so the user can view appointments this month.
     * @param actionEvent
     */
    public void monthlyAppointmentSelected(ActionEvent actionEvent) {
        ObservableList<Appointments> monthlyAppointments = FXCollections.observableArrayList();
        if(allAppointments == null) {
            allAppointments = DAOAppointment.getAllAppointments();
        }
        for (Appointments appointment : allAppointments) {
            if (appointment.getStart().isAfter(LocalDateTime.now()) && appointment.getStart().isBefore(LocalDateTime.now().plusMonths(1))) {
                monthlyAppointments.add(appointment);
            }
        }
        appointmentTable.setItems(monthlyAppointments);

    }

    /** This method changes the screen to the add appointment screen.
     * @param actionEvent
     * @throws IOException
     */
    public void addAppointmentButtonClick(ActionEvent actionEvent) throws IOException {
        screenChange.setScreen(addAppointmentButton, "addAppointments", 700, 716);


    }

    /** This method changes the screen to the update appointment screen for the selected appointment.
     * @param actionEvent
     */
    public void updateAppointmentButtonClick(ActionEvent actionEvent) {
        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Appointment Selected");
            alert.setContentText("Please select an appointment to update.");
            alert.showAndWait();
        } else {
            try {
                screenChange.setScreen(updateAppointmentButton, "updateAppointments", 700, 716);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** This method deletes the selected appointment. It displays an error message if no appointment is selected.
     * It also displays a confirmation message before deleting the appointment.
     * Then the appointment table is refreshed for the appropriate radio button.
     * @param actionEvent
     */
    public void deleteAppointmentButtonClick(ActionEvent actionEvent) {
        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Customer Selected");
            alert.setContentText("Please select an appointment to Delete.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Appointment");
            alert.setHeaderText("Delete Appointment");
            alert.setContentText("Are you sure you want to delete this appointment? \n Appointment ID: "
                    + selectedAppointment.getAppointmentId() + "\n Type: " + selectedAppointment.getType());
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    DAOAppointment.deleteAppointment(selectedAppointment.getAppointmentId());
                    if (weekAppointmentsRadio.isSelected() || monthAppointmentsRadio.isSelected()) {
                        allAppointments = DAOAppointment.getAllAppointments();
                        weeklyAppointmentRadioSelected(new ActionEvent());
                        monthlyAppointmentSelected(new ActionEvent());
                    } else {
                        appointmentTable.setItems(DAOAppointment.getAllAppointments());
                    }
                }
            });
        }
    }

    /**  This method changes the screen to the add customer screen.
     * @param actionEvent
     * @throws IOException
     */
    public void addCustomerButtonClick(ActionEvent actionEvent) throws IOException {
        screenChange.setScreen(addCustomerButton, "addCustomers", 700, 716);
    }

    /** This method changes the screen to the update customer screen for the selected customer.
     * @param actionEvent
     * @throws IOException
     */
    public void updateCustomerButtonClick(ActionEvent actionEvent) throws IOException {
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Customer Selected");
            alert.setContentText("Please select a customer to update.");
            alert.showAndWait();
        } else {
            screenChange.setScreen(updateCustomerButton, "updateCustomers", 700, 716);
        }
    }

    /** This method deletes the selected customer. It displays an error message if no customer is selected.
     * It also displays a confirmation message before deleting the customer.
     * If the delettion is confirmed all appointments for the customer are deleted first.
     * Then the customer is deleted and the customer table and appointment table are refreshed.
     * @param actionEvent
     */
    public void deleteCustomerButtonClick(ActionEvent actionEvent) {
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Customer Selected");
            alert.setContentText("Please select a customer to Delete.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer");
            alert.setHeaderText("Delete Customer");
            alert.setContentText("Are you sure you want to delete " + selectedCustomer.getCustomerName() + " from the customer list?" +
                    " Deleting " + selectedCustomer.getCustomerName() + " will also delete all appointments associated with them.");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    DAOCustomer.deleteCustomer(selectedCustomer.getCustomerId());
                    customerTable.setItems(DAOCustomer.getAllCustomers());
                    if(allAppointmentsRadio.isSelected()){
                    appointmentTable.setItems(DAOAppointment.getAllAppointments());
                    }else if (weekAppointmentsRadio.isSelected() || monthAppointmentsRadio.isSelected()) {
                        allAppointments = DAOAppointment.getAllAppointments();
                        weeklyAppointmentRadioSelected(new ActionEvent());
                        monthlyAppointmentSelected(new ActionEvent());
                    }
                }
            });
        }
    }

    /** This method changes the screen to the reports screen.
     * @param actionEvent
     * @throws IOException
     */
    public void reportsButtonClick(ActionEvent actionEvent) throws IOException {
        screenChange.setScreen(reportsButton, "reports", 1000, 700);
    }

    /** This method is used to pass the selected Customer between screens.
     * @return
     */
    public static Customers getSelectedCustomer() {
        return selectedCustomer;
    }

    /** This method is used to pass the selected Appointment between screens.
     * @return
     */
    public static Appointments getSelectedAppointment() {
        return selectedAppointment;
    }
}


