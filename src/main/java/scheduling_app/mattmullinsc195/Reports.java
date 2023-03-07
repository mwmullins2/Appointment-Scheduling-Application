package scheduling_app.mattmullinsc195;

/** This is an abstract class.
 *  It is used to create the reports for the application.
 */
public abstract class Reports {

    /** Int to hold number of customers.
     *
     */
    private int numberOfCustomers;

    /** String to hold appointment type.
     *
     */
    private String type;

    /** String to hold appointment month.
     *
     */
    private String month;

    /** This is the constructor for the Reports class.
     * @param numberOfCustomers
     * @param type
     * @param month
     */
    public Reports(int numberOfCustomers, String type, String month) {
        this.numberOfCustomers = numberOfCustomers;
        this.type = type;
        this.month = month;

    }

    /** This is the getter for the numberOfCustomers variable.
     * @return numberOfCustomers
     */
    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    /** This is the setter for the numberOfCustomers variable.
     * @param numberOfCustomers
     */
    public void setNumberOfCustomers(int numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }

    /** This is the getter for the type variable.
     * @return type
     */
    public String getType() {
        return type;
    }

    /** This is the setter for the type variable.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /** This is the getter for the month variable.
     * @return month
     */
    public String getMonth() {
        return month;
    }

    /** This is the setter for the month variable.
     * @param month
     */
    public void setMonth(String month) {
        this.month = month;
    }
}
