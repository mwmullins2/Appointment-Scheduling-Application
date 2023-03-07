package Controllers;

import DAO.DAOCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import scheduling_app.mattmullinsc195.Countries;
import scheduling_app.mattmullinsc195.Customers;
import scheduling_app.mattmullinsc195.Divisions;
import scheduling_app.mattmullinsc195.ScreenChange;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

/** This is the controller for the Update Customer Screen.
 * It is responsible for updating the customer information in the database.
 */
public class UpdateCustomerController {


    /** Int to hold customer ID
     *
     */
    public static int customerID;

    /** String to hold the customer's country.
     *
     */
    public static String customerCountry;

    /** String to hold the customer's division.
     *
     */
    public static String customerDivision;

    /** TextField to hold customer ID.
     *
     */
    public TextField updateCustomerIDTextField;

    /** Button to cancel and return to the main screen without changes to customer.
     *
     */
    public Button updateCustCancelButton;

    /** Customers to hold selected customer from Main Screen Table.
     *
     */
    Customers selectedCustomer = MainScreenController.getSelectedCustomer();

    /** TextField to display and change customer name.
     *
     */
    public TextField updateCustomerNameTextField;

    /** TextField to display and change customer phone.
     *
     */
    public TextField updateCustomerPhoneTextField;

    /** TextField to display and change customer address.
     *
     */
    public TextField updateCustomerAddressTextField;

    /** TextField to display and change customer postal code.
     *
     */
    public TextField updateCustomerPostalTextField;

    /** ComboBox to display and change customer Division.
     *
     */
    public ComboBox<Divisions> updateCustomerDivisionComboBox;

    /** ComboBox to display and change customer country.
     *
     */
    public ComboBox<Countries> updateCustomerCountryComboBox;

    /** Button to initiate update customer process.
     *
     */
    public Button updateCustomerButton;

    /** String to hold division id.
     *
     */
    public static String divisionIDSelection;

    /** Int to hold division id.
     *
     */
    public static int divisionIDInt;

    /** String to could country id.
     *
     */
    public static String countryIDSelection;

    /** Char to hold country ID.
     *
     */
    public static char countryID;

    /** int to hold country ID.
     *
     */
    public int countryIDInt;

    /** Screen Change Lambda expression used throughout the application.
     *
     */
    ScreenChange screenChange = (button, screen, width, height) -> {
        Stage stage = (Stage) button.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/scheduling_app/mattmullinsc195/" + screen + ".fxml")));
        stage.setTitle("C195 Scheduling App");
        stage.setScene(new Scene(root, width, height));
        stage.show();
    };

    /** This is the initialize method for the Update Customer Screen.
     * It populates the fields with the selected customer's information.
     * @throws SQLException
     */
    public void initialize() throws SQLException {

        updateCustomerIDTextField.setText(String.valueOf(selectedCustomer.getCustomerId()));
        updateCustomerNameTextField.setText(selectedCustomer.getCustomerName());
        updateCustomerPhoneTextField.setText(selectedCustomer.getPhone());
        updateCustomerAddressTextField.setText(selectedCustomer.getAddress());
        updateCustomerPostalTextField.setText(selectedCustomer.getPostalCode());
        customerID = Integer.parseInt(updateCustomerIDTextField.getText());
        updateCustomerCountryComboBox.setItems(DAOCustomer.getAllCountries());
        for (Countries country : updateCustomerCountryComboBox.getItems()) {
            DAOCustomer.getCustomerCountry(customerID);
            if (country.getCountry().equals(customerCountry)) {
                updateCustomerCountryComboBox.getSelectionModel().select(country);
            }
        }
        countryIDSelection = updateCustomerCountryComboBox.getValue().toString();
        countryID = countryIDSelection.charAt(0);
        countryIDInt = Character.getNumericValue(countryID);

        updateCustomerDivisionComboBox.setItems(DAOCustomer.filterDivisions(countryIDInt));
        for (Divisions division : updateCustomerDivisionComboBox.getItems()) {
            DAOCustomer.getCustomerDivision(customerID);
            if (division.getDivision().equals(customerDivision)) {
                updateCustomerDivisionComboBox.getSelectionModel().select(division);
                divisionIDSelection = updateCustomerDivisionComboBox.getValue().toString();
                divisionIDInt = Integer.parseInt(divisionIDSelection.substring(0, divisionIDSelection.indexOf(" ")));

            }
        }
    }

    /** This method is responsible for updating the customer information in the database.
     * It checks to make sure all fields are filled and calls the exception handling method if not.
     * @param actionEvent
     * @throws IOException
     */
    public void updateCustomerAction(ActionEvent actionEvent) throws IOException {
        String customerName = updateCustomerNameTextField.getText();
        String address = updateCustomerAddressTextField.getText();
        String postalCode = updateCustomerPostalTextField.getText();
        String phone = updateCustomerPhoneTextField.getText();
        int divisionID = divisionIDInt;

        if(customerName.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phone.isEmpty() || updateCustomerCountryComboBox.getSelectionModel().isEmpty()
                || updateCustomerDivisionComboBox.getSelectionModel().isEmpty()) {
            updateCustomerExceptionHandling();
        } else {
            DAOCustomer.updateCustomer(customerName, address, postalCode, phone, divisionID);
            screenChange.setScreen(updateCustomerButton, "mainView", 1000, 700);
        }


    }

    /** This method refreshes the division combo box when it is clicked.
     * @param actionEvent
     */
    public void divisionComboAction(ActionEvent actionEvent) {
        if (updateCustomerDivisionComboBox.getValue() != null) {
            divisionIDSelection = updateCustomerDivisionComboBox.getValue().toString();
            divisionIDInt = Integer.parseInt(divisionIDSelection.substring(0, divisionIDSelection.indexOf(" ")));
            System.out.println(divisionIDInt);
        }
    }

    /**  This method refreshes the country combo box and filters the division combo box when it is clicked.
     * @param actionEvent
     * @throws SQLException
     */
    public void countryComboAction(ActionEvent actionEvent) throws SQLException {
        countryIDSelection = updateCustomerCountryComboBox.getValue().toString();
        countryID = countryIDSelection.charAt(0);
        countryIDInt = Character.getNumericValue(countryID);
        customerCountry = countryIDSelection.substring(2);
        System.out.println(customerCountry);
        updateCustomerDivisionComboBox.setItems(DAOCustomer.filterDivisions(countryIDInt));
    }

    /** This method is responsible for canceling the update and returning to the main screen.
     * @param actionEvent
     * @throws IOException
     */
    public void cancelAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Update");
        alert.setHeaderText("Cancel Update");
        alert.setContentText("Are you sure you want to cancel the update?");
        alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                try {
                    screenChange.setScreen(updateCustCancelButton, "mainView", 1000, 700);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }


    /** This method is responsible for exception handling for the update customer screen.
     *
     */
    public void updateCustomerExceptionHandling(){
        if(updateCustomerNameTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Customer Name is required!");
            alert.setContentText("Please enter a customer name!");
            alert.showAndWait();
        }
        if(updateCustomerAddressTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Address is required!");
            alert.setContentText("Please enter an address!");
            alert.showAndWait();
        }
        if(updateCustomerPostalTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Postal Code is required!");
            alert.setContentText("Please enter a postal code!");
            alert.showAndWait();
        }
        if(updateCustomerPhoneTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Phone Number is required!");
            alert.setContentText("Please enter a phone number!");
            alert.showAndWait();
        }
        if(updateCustomerCountryComboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Country is required!");
            alert.setContentText("Please select a country!");
            alert.showAndWait();
        }
        if(updateCustomerDivisionComboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("State/Province is required!");
            alert.setContentText("Please select a state/province!");
            alert.showAndWait();
        }
    }
}
