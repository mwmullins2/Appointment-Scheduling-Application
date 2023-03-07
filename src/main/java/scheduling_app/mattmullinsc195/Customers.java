package scheduling_app.mattmullinsc195;

/** This class is used to create a customer object.
 *
 */
 public class Customers {


    /** Int to hold customer ID.
     *
     */
    private int  customerId;

    /** String to hold customer name.
     *
     */
    private String customerName;

    /** String to hold customer address.
     *
     */
    private String address;

    /** String to hold customer postal code.
     *
     */
    private String postalCode;

    /** String to hold customer phone number.
     *
     */
    private String phone;

    /** String to hold customer division.
     *
     */
    private String division;

    /** This is the constructor for the customer object.
     * @param customerId
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param division
     */
        public Customers(int customerId, String customerName, String address, String postalCode, String phone, String division) {
            this.customerId = customerId;
            this.customerName = customerName;
            this.address = address;
            this.postalCode = postalCode;
            this.phone = phone;
            this.division = division;

        }

    /** This is the getter for the customer name.
     * @return customerName
     */
        public String getCustomerName() {
            return customerName;
        }

    /** This is the setter for the customer name.
     * @param customerName
     */
        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

    /** This is the getter for the customer address.
     * @return address
     */
        public String getAddress() {
            return address;
        }

    /** This is the setter for the customer address.
     * @param address
     */
        public void setAddress(String address) {
            this.address = address;
        }

    /** This is the getter for the customer postal code.
     * @return postalCode
     */
        public String getPostalCode() {
            return postalCode;
        }

    /** This is the setter for the customer postal code.
     *
     */
        public void setPostalCode() {
            this.postalCode = postalCode;
        }

    /** This is the getter for the customer phone number.
     * @return phone
     */
        public String getPhone() {
            return phone;
        }

    /** This is the setter for the customer phone number.
     *
     */
        public void setPhone() {
            this.phone = phone;
        }

    /** This is the getter for the customer division.
     * @return division
     */
        public String getDivision() {
            return division;
        }

    /** This is the setter for the customer division.
     *
     */
        public void setDivision() {
            this.division = division;
        }

    /** This is the getter for the customer ID.
     * @return customerId
     */
        public int getCustomerId() {
            return customerId;
        }

    /** This is the setter for the customer ID.
     *
     */
        public void setCustomerId() {
            this.customerId = customerId;
        }

    /** This overrides the toString method to display the customer information correctly in a combo box.
     * @return customerId + " " + customerName
     */
     @Override
     public String toString() {
         return (customerId + " " + customerName);
     }
 }
