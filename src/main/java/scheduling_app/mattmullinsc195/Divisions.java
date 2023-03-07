package scheduling_app.mattmullinsc195;

/** This class is used to create a new Division object.
 *
 */
public class Divisions {

    /** Int to hold division ID.
     *
     */
    public int divisionId;

    /** String to hold division name.
     *
     */
    public String division;

    /** Int to hold country ID.
     *
     */
    public int countryId;

    /** This constructor is used to create a new Division object.
     * @param divisionId
     * @param division
     * @param countryId
     */
    public Divisions(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;

    }

    /** This method is used to get the divisionId.
     * @return divisionId
     */
    public int getDivisionId() {
        return divisionId;
    }

    /** This method is used to set the divisionId.
     * @param divisionId
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /** This method is used to get the division.
     * @return division
     */
    public String getDivision() {
        return division;
    }

    /**  This method is used to set the division.
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /** This method is used to get the countryId.
     * @return countryId
     */
    public int getCountryId() {
        return countryId;
    }

    /** This method is used to set the countryId.
     * @param countryId
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /** This method is used to ovveride the toString method to display divisoin information properly in a combo box.
     * @return divisionId + " " + division
     */
    @Override
    public String toString() {
        return (divisionId + " " + division);
    }
}
