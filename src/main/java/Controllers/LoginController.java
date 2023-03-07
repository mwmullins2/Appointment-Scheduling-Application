package Controllers;

import DAO.DAOAppointment;
import DAO.DAOUsers;
import Utility.TimeConversion;
import Utility.FileHandling;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import scheduling_app.mattmullinsc195.Appointments;
import scheduling_app.mattmullinsc195.ScreenChange;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/** This class is the controller for the login screen.
 * It handles the login process.
 */
public class LoginController {

    /** TextField to enter the username.
     *
     */
    public TextField userNameTextField;

    /** Button to activate the login process.
     *
     */
    public Button loginButton;

    /** PasswordField to enter the user's password.
     *
     */
    public PasswordField passwordField;

    /** Label to display a message when there is a failed login attempt.
     *
     */
    public Label loginFailLabel;

    /** Label to display the Zone.
     *
     */
    public Label zoneIDLabel;

    /** AnchorPane that holds the login page.
     *
     */
    public AnchorPane loginForm;

    /** String to hold login attempt information.
     *
     */
    public static String loginAttempt;

    /** Label to display the language currently being used.
     *
     */
    public Label languageLabel;

    /** Boolean that holds if a login attempt was successful.
     *
     */
    public boolean successfulLogin = false;

    /** String that holds the username.
     *
     */
    public static String userName;

    /** Label that displays the current zone.
     *
     */
    public Label zoneLabel;

    /** Label that displays where to enter the username.
     *
     */
    public Label usernameLabel;

    /** Label that display where to enter password.
     *
     */
    public Label passwordLabel;

    /** Label that displays the currently selected language.
     *
     */
    public Label languageDisplayLabel;

    /** Boolean that holds if there is an upcoming appointment.
     *
     */
    boolean upcomingAppointment = false;

    /** LocalDateTime that gets the current time.
     *
     */
    LocalDateTime now = LocalDateTime.now();

    /** ObservableList that holds all appointment information.
     *
     */
    ObservableList<Appointments> appointments = DAOAppointment.getAllAppointments();

    /** ResourceBundle that loads the appropriate language bundle.
     *
     */
    ResourceBundle rb = ResourceBundle.getBundle("Language/schedulingAppLanguage", Locale.getDefault());

    /** This is the screen change lambda expression used throughout the application.
     *
     */
    ScreenChange screenChange = (button, screen, width, height) -> {
        Stage stage = (Stage) button.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/scheduling_app/mattmullinsc195/" + screen + ".fxml")));
        stage.setTitle("C195 Scheduling App");
        stage.setScene(new Scene(root, width, height));
        stage.show();
    };

    /** This is the initialize method for the login screen.
     * It sets the text for the labels.
     */
    public void initialize() {
        zoneIDLabel.setText(String.valueOf(java.time.ZoneId.systemDefault()));
        usernameLabel.setText(rb.getString("Username"));
        passwordLabel.setText(rb.getString("Password"));
        loginButton.setText(rb.getString("Login"));
        languageLabel.setText(rb.getString("Language"));
        zoneLabel.setText(rb.getString("Zone"));
        languageDisplayLabel.setText(rb.getString("languageDisplayLabel"));


    }

    /** This method resets the login fail label when a key is pressed in the password text field.
     * @param keyEvent
     */
    public void passwordFieldKeyStroke(KeyEvent keyEvent) {
        loginFailLabel.setText("");
    }

    /** This method resets the login fail label when a key is pressed in the username text field.
     * @param keyEvent
     */
    public void userFieldKeyStroke(KeyEvent keyEvent) {
        loginFailLabel.setText("");
    }

    /** This method is used to return the userName.
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /** This method contains the logic for the login process.
     * It checks the username and password against the database.
     * If the login is successful, it will check for any upcoming appointments within 15 minutes of the current time.
     * If there are any, it will display a warning message. If the login is successful, it will change the screen to the
     * main screen.
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void loginButtonAction(ActionEvent actionEvent) throws IOException, SQLException {


        DAOUsers daoUsers = new DAOUsers();
        if (daoUsers.checkUserCredentials(userNameTextField.getText(), passwordField.getText())) {
            System.out.println("Login Successful");
            loginAttempt = userNameTextField.getText() + " " + passwordField.getText() + " " + TimeConversion.getServerTime() + " " + "Success";
            successfulLogin = true;
            userName = userNameTextField.getText();
        } else {
            loginFailLabel.setText(rb.getString("loginFailLabel"));
            System.out.println("Login Failed!");
            loginAttempt = userNameTextField.getText() + " " + passwordField.getText() + " " + TimeConversion.getServerTime() + " " + "Failed";
        }
        FileHandling.writeToFile(loginAttempt);
        if (successfulLogin) {
            screenChange.setScreen(loginButton, "mainView", 1000, 700);
            for (Appointments appointment : appointments) {
                if (appointment.getStart().isBefore(now.plusMinutes(15)) && appointment.getStart().isAfter(now)) {
                    upcomingAppointment = true;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(rb.getString("upcomingAppointmentTitle"));
                    alert.setHeaderText(rb.getString("upcomingAppointmentHeader"));
                    alert.setContentText(rb.getString("upcomingAppointmentContent") + appointment.getAppointmentId() + " " + rb.getString("upcomingAppointmentStart") + " " + appointment.getStart());
                    alert.showAndWait();
                }
            }
            if (!upcomingAppointment) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(rb.getString("noUpcomingTitle"));
                alert.setHeaderText(rb.getString("noUpcomingHeader"));
                alert.setContentText(rb.getString("noUpcomingContent"));
                alert.showAndWait();
            }

        }
    }


}