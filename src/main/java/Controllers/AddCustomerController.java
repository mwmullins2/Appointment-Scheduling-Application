package Controllers;

import DAO.DAOCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import scheduling_app.mattmullinsc195.Countries;
import scheduling_app.mattmullinsc195.Divisions;
import scheduling_app.mattmullinsc195.ScreenChange;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

/** This class is the controller for the addCustomers.fxml file.
 * It is used to add a new customer to the database.
 */
public class AddCustomerController {

    /** ComboBox used to hold Division Information.
     *
     */
    public ComboBox<Divisions> addCustomerDivisionComboBox;

    /** ComboBox used to hold Country Information.
     *
     */
    public ComboBox<Countries> addCustomerCountryComboBox;

    /** TextField used to enter customer name.
     *
     */
    public TextField addCustomerNameTextField;

    /** TextField used to enter customer phone.
     *
     */
    public TextField addCustomerPhoneTextField;

    /** TextField used to enter customer address.
     *
     */
    public TextField addCustomerAddressTextField;

    /** TextField used to enter customer postal code.
     *
     */
    public TextField addCustomerPostalTextField;

    /** String used to hold the countryID.
     *
     */
    public static String countryIDSelection;

    /** Char to hold the country ID
     *
     */
    public static char countryID;

    /** Int to hold the country ID
     *
     */
    public int countryIDInt;

    /** String to hold the country name.
     *
     */
    public static String countryName;

    /** String to hold the selected division.
     *
     */
    public static String divisionIDSelection;

    /** Int to hold the division ID
     *
     */
    public static int divisionIDInt;

    /** Button to activate the logic and exception handling to save a customer to the database.
     *
     */
    public Button saveCustomerButton;

    /** Button to cancel and return to the main screen without any data being saved.
     *
     */
    public Button addCustCancelButton;

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

    /** This method is used to initialize the Add Customer screen.
     *  It populates the country and division combo boxes.
     * @throws SQLException
     */
    public void initialize() throws SQLException {
        ComboBox<Countries> countryList = new ComboBox<>();
        countryList.setItems(DAOCustomer.getAllCountries());
        addCustomerCountryComboBox.setItems(countryList.getItems());
        addCustomerCountryComboBox.setPromptText("Select Country");
        addCustomerDivisionComboBox.setPromptText("Select Country First");
        if( addCustomerCountryComboBox.getSelectionModel().getSelectedItem() != null){
        ComboBox<Divisions> divisionList = new ComboBox<>();
        divisionList.setItems(DAOCustomer.getAllDivisions());
        addCustomerDivisionComboBox.setItems(divisionList.getItems());
        }
    }

    /** This method contains the logic for the Country Combo Box.
     * It populates the division combo box based on the country selected.
     * @param actionEvent
     * @throws SQLException
     */
    public void countryComboAction(ActionEvent actionEvent) throws SQLException {
        countryIDSelection = addCustomerCountryComboBox.getValue().toString();
        countryID = countryIDSelection.charAt(0);
        countryIDInt = Character.getNumericValue(countryID);
        countryName = countryIDSelection.substring(2);
        System.out.println(countryName);
        addCustomerDivisionComboBox.setItems(DAOCustomer.filterDivisions(countryIDInt));
        addCustomerDivisionComboBox.setPromptText("Select State/Province");
    }

    /** This method contains the logic for the Division Combo Box. It sets the divisionID based on the division selected.
     * @param actionEvent
     */
    public void divisionComboAction(ActionEvent actionEvent) {
        if(addCustomerDivisionComboBox.getValue() != null) {
            divisionIDSelection = addCustomerDivisionComboBox.getValue().toString();
            divisionIDInt = Integer.parseInt(divisionIDSelection.substring(0, divisionIDSelection.indexOf(" ")));
            System.out.println(divisionIDInt);
        }
    }

    /** This method contains the logic for the Save Customer Button. It saves the new customer to the database using the information
     *  entered in the text fields. The screen change lambda expression is used to change the screen to the main screen.
     * @param actionEvent
     * @throws IOException
     */
    public void saveCustomerAction(ActionEvent actionEvent) throws IOException {
        String customerName = addCustomerNameTextField.getText();
        String address = addCustomerAddressTextField.getText();
        String postalCode = addCustomerPostalTextField.getText();
        String phone = addCustomerPhoneTextField.getText();
        int divisionID = divisionIDInt;
        if(customerName.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phone.isEmpty() || addCustomerCountryComboBox.getValue() == null || addCustomerDivisionComboBox.getValue() == null) {
         addCustomerExceptionHandling();
        } else {DAOCustomer.addCustomer(customerName, address, postalCode, phone, divisionID);
            screenChange.setScreen(saveCustomerButton, "mainView", 1000, 700);
        }



    }

    /** This method is used to cancel adding a new customer. The screen change lambda expression is used to change the screen to the main screen.
     * No data is saved to the database.
     * @param actionEvent
     * @throws IOException
     */
    public void addCustCancelAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Cancel");
        alert.setContentText("Are you sure you want to cancel?");
        alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                try {
                    screenChange.setScreen(addCustCancelButton, "mainView", 1000, 700);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    /** This method is used to handle exceptions when the user does not enter data in all the fields.
     *
     */
    public void addCustomerExceptionHandling() {
        if(addCustomerNameTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Customer Name is required!");
            alert.setContentText("Please enter a customer name!");
            alert.showAndWait();
        }
        if(addCustomerAddressTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Address is required!");
            alert.setContentText("Please enter an address!");
            alert.showAndWait();
        }
        if(addCustomerPostalTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Postal Code is required!");
            alert.setContentText("Please enter a postal code!");
            alert.showAndWait();
        }
        if(addCustomerPhoneTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Phone Number is required!");
            alert.setContentText("Please enter a phone number!");
            alert.showAndWait();
        }
        if(addCustomerCountryComboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Country is required!");
            alert.setContentText("Please select a country!");
            alert.showAndWait();
        }
        if(addCustomerDivisionComboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("State/Province is required!");
            alert.setContentText("Please select a state/province!");
            alert.showAndWait();
        }
    }
}

