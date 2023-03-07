package Utility;
import java.sql.Connection;
import java.sql.DriverManager;

/** This class is used to connect to the database.
 *
 */
public class JDBC {

    /** String to hold protocol information.
     *
     */
    private static final String protocol = "jdbc";

    /** String to hold vendor information.
     *
     */
    private static final String vendor = ":mysql:";

    /** String to hold server location.
     *
     */
    private static final String location = "//localhost/";

    /** String to hold database name.
     *
     */
    private static final String databaseName = "client_schedule";

    /** String to hold url for the database connection.
     *
     */
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";

    /** String to hold the driver information.
     *
     */
    private static final String driver = "com.mysql.cj.jdbc.Driver";

    /** String to hold the database username.
     *
     */
    private static final String userName = "sqlUser";

    /** String to hold the database password.
     *
     */
    private static final String password = "Passw0rd!";

    /** Connection to establish and end connections.
     *
     */
    public static Connection connection;

    /** This method starts the connection to the database.
     *  It prints a message to the console if the connection is successful or if there is an error.
     */
    public static void startConnection()
    {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Successfully Connected!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /** This method ends the connection to the database.
     *  It prints a message to the console if the connection is closed or if there is an error.
     */
    public static void endConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

}
