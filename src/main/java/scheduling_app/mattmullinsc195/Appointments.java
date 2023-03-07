package scheduling_app.mattmullinsc195;

import java.time.LocalDateTime;

/** This class is used to create an Appointment object.
 *
 */
public class Appointments {

    /** Int to hold appointment ID.
     *
     */
    private int appointmentId;

    /** String to hold appointment title.
     *
     */
    private String title;

    /** String to hold appointment description.
     *
     */
    private String description;

    /** String to hold appointment location.
     *
     */
    private String location;

    /** String to hold appointment contact info.
     *
     */
    private String contact;

    /** String to hold appointment type.
     *
     */
    private String type;

    /** LocalDateTime to hold appointment start.
     *
     */
    private LocalDateTime start;

    /** LocalDateTime to hold appointment end.
     *
     */
    private LocalDateTime end;

    /** Int to hold customer id.
     *
     */
    private int customerId;

    /** Int to hold user ID.
     *
     */
    private int userId;

    /** Int to hold contact ID.
     *
     */
    private int contactId;


    /** This is the Appointment class constructor.
     * @param appointmentId
     * @param title
     * @param description
     * @param location
     * @param contact
     * @param type
     * @param start
     * @param end
     * @param customerId
     * @param userId
     * @param contactId
     */
    public Appointments( int appointmentId, String title, String description, String location, String contact, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /** This is the Appointment ID Getter
     * @return appointmentId
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /** This is the Appointment ID Setter
     * @param appointmentId
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /** This is the Appointment Title Getter
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /** This is the Appointment Title Setter
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** This is the Appointment Description Getter
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /** This is the Appointment Description Setter
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /** This is the Appointment Location Getter
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /** This is the Appointment Location Setter
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /** This is the Appointment Contact Getter
     * @return contact
     */
    public String getContact() {
        return contact;
    }

    /** This is the Appointment Contact Setter
     * @param contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /** This is the Appointment Type Getter
     * @return type
     */
    public String getType() {
        return type;
    }

    /** This is the Appointment Type Setter
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /** This is the Appointment Start Getter
     * @return start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /** This is the Appointment Start Setter
     * @param start
     */
    public void setStart(String start) {
        this.start = LocalDateTime.parse(start);
    }

    /** This is the Appointment End Getter
     *
     * @return end
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /** This is the Appointment End Setter
     * @param end
     */
    public void setEnd(String end) {
        this.end = LocalDateTime.parse(end);
    }

    /** This is the Appointment Customer ID Getter
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /** This is the Appointment Customer ID Setter
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /** This is the Appointment User ID Getter
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    /** This is the Appointment User ID Setter
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** This is the Appointment Contact ID Getter
     * @return contactId
     */
    public int getContactId() {
        return contactId;
    }

    /** This is the Appointment Contact ID Setter
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

}
