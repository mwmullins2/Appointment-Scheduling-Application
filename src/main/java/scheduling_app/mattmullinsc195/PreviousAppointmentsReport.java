package scheduling_app.mattmullinsc195;


import java.time.LocalDate;

/** This class extends the Reports class and is used to create a report of all appointments that have already occurred.
 *
 */
public class PreviousAppointmentsReport extends Reports {


    /** Int to hold appointment ID.
     *
     */
    private int appointmentId;

    /** LocalDate to hold appointment End Date.
     *
     */
    private LocalDate end;

    /** Int to hold customer ID.
     *
     */
    private int customerId;

    /** This constructor is used to create a new PreviousAppointmentsReport object.
     * @param numberOfCustomers
     * @param type
     * @param month
     * @param appointmentId
     * @param end
     * @param customerId
     */
    public PreviousAppointmentsReport(int numberOfCustomers, String type, String month, int appointmentId, LocalDate end, int customerId) {
        super(numberOfCustomers, type, month);

        this.appointmentId = appointmentId;
        this.end = end;
        this.customerId = customerId;

    }

    /** This is the getter for the appointmentId variable.
     * @return appointmentId
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /** This is the setter for the appointmentId variable.
     * @param appointmentId
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /** This is the getter for the end variable.
     * @return end
     */
    public LocalDate getEnd() {
        return end;
    }

    /** This is the setter for the end variable
     * @param end
     */
    public void setEnd(LocalDate end) {
        this.end = end;
    }

    /** This is the getter for the customerId variable.
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /** This is the setter for the customerId variable.
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
