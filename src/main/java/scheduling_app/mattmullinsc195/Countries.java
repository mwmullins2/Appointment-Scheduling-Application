package scheduling_app.mattmullinsc195;

/**
 * This class is used to create a countries object.
 */
public class Countries {

    /** Int to hold country ID.
     *
     */
    public int countryId;

    /** String to hold country name.
     *
     */
    public String country;

    /** This is the constructor for the countries object.
     *
     * @param countryId
     * @param country
     */
    public Countries(int countryId,String country) {
        this.countryId = countryId;
        this.country = country;
    }

    /** This is the getter for the countryId.
     *
     * @return countryId
     */
    public int getCountryId() {
        return countryId;
    }

    /** This is the setter for the countryId.
     *
     * @param countryId
     */
   public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /** This is the getter for the country.
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /** This is the setter for the country.
     *
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /** This method overrides the toString method.
     * It allows the country to be displayed in a combo box properly.
     *
     * @return countryId + " " + country
     */
    @Override
    public String toString() {
        return (countryId + " " + country);
    }

}
