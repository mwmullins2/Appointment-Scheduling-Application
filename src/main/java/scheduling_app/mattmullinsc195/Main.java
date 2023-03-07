package scheduling_app.mattmullinsc195;

import Utility.JDBC;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;


/** Main class for the application. This class is responsible for loading the
 * initial screen and setting the stage.
 */
public class Main extends Application {

    /** This method loads the initial screen and sets the stage.
     * @param primaryStage
     * @throws IOException
     */
    @Override
public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loginView.fxml")));
        primaryStage.setTitle("C195 Scheduling Application");
        primaryStage.setScene(new Scene(root, 500, 350));
        primaryStage.show();
        primaryStage.setOnCloseRequest(Event::consume);
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            programExit(primaryStage);
        });
    }

    /**  This method creates a confirmation alert when the user attempts to close the application.
     *  This message is displayed in French if the user's locale is set to French.
     * @param stage
     */
    public void programExit(Stage stage) {
        if (Locale.getDefault().getLanguage().equals("en")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit Program");
            alert.setHeaderText("Exiting Program");
            alert.setContentText("Are you sure you want to exit the program?");
            alert.showAndWait();
            if (alert.getResult().getText().equals("OK")) {
                stage.close();

            }
        } else if (Locale.getDefault().getLanguage().equals("fr")){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Quitter le programme");
                alert.setHeaderText("Quitter le programme");
                alert.setContentText("Êtes-vous sûr de vouloir quitter le programme?");
                alert.showAndWait();
                if (alert.getResult().getText().equals("OK")) {
                    stage.close();
                }
        }
    }

    /** This method launches the application. It also establishes a connection to the database.
     * Once the connection is established, the application will launch.
     * Once the application is closed, the connection to the database will be closed.
     * @param args
     */
    public static void main(String[] args) {
        JDBC.startConnection();
        launch();
        JDBC.endConnection();
    }
}