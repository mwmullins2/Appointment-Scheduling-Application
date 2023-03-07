package scheduling_app.mattmullinsc195;

/** This class is used to create a new contact object.
 *
 */
public class Contacts {

    /** Int to hold contact ID.
     *
     */
    private int contactId;

    /** String to hold contact name.
     *
     */
    private String contactName;

    /** This is the constructor for the Contacts class.
     * @param contactId
     * @param contactName
     */
    public Contacts(int contactId, String contactName) {
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /** This is the Contact ID getter.
     * @return contactId
     */
    public int getContactId() {
        return contactId;
    }

    /** This is the Contact ID setter.
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /** This is the Contact Name getter.
     * @return contactName
     */
    public String getContactName() {
        return contactName;
    }

    /** This is the Contact Name setter.
     *
     */
    public void setContactName() {
        this.contactName = contactName;
    }

    /** This method overrides the toString method so that the display will be correct in Contact ComboBoxes.
     * @return contactId + " " + contactName
     */
    @Override
    public String toString() {
        return (contactId + " " + contactName);
    }
}
