package Utility;


import java.io.FileWriter;
import java.io.IOException;

/** This is the file handling class.
 * It is used to write to a log file.
 */
public class FileHandling {

    /** String to hold fileName.
     *
     */
    static String fileName = "login_activity.txt";

    /** FileWriter to create a file writer.
     *
     */
    static FileWriter fileWriter;
    static {
        try {
            fileWriter = new FileWriter(fileName, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /** This method writes a string to the log file.
     * @throws IOException
     */
    public static void writeToFile(String string) throws IOException {
    fileWriter.write(string);
    fileWriter.write(System.getProperty("line.separator"));
    fileWriter.flush();

    }
}
