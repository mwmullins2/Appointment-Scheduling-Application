package scheduling_app.mattmullinsc195;

/** This is the Users class.
 *
 */
public class Users {
    private int userId;
    private String userName;
    private String password;

    /** This is the Users constructor.
     * @param userId
     * @param userName
     * @param password
     */
    public Users(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /** This is the getter for the userId.
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    /** This is the setter for the userId.
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** This is the getter for the userName.
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /** This is the setter for the userName.
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** This is the getter for the password.
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /** This is the setter for the password.
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /** This method overrides the toString method. This allows the users to be displayed properly in ComboBoxes.
     * @return userId + " " + userName
     */
    @Override
    public String toString() {
        return (userId + " " + userName);
    }
}
