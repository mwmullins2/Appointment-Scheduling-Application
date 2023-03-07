package scheduling_app.mattmullinsc195;

import javafx.scene.control.Button;

import java.io.IOException;

/** This is a functional interface that is used to change screens.
 * Lambda expressions are used to implement this interface.
 */
public interface ScreenChange {

    /** This method is used to change screens.
     * @param button
     * @param screen
     * @param screenWidth
     * @param screenHeight
     * @throws IOException
     */
    void setScreen(Button button, String screen, double screenWidth, double screenHeight) throws IOException;

}
