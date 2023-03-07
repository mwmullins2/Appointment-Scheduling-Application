module scheduling_app.mattmullinsc195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens scheduling_app.mattmullinsc195 to javafx.fxml;
    exports scheduling_app.mattmullinsc195;
    exports Controllers;
    opens Controllers to javafx.fxml;
}