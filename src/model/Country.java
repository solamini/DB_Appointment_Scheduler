package model;

/** This class creates the Country object.
 * @author Aleksandr Ogilba  */
public class Country {
    private int countryID;
    private String countryName;

    /** This is an Empty Constructor */
    public Country(){
    }

    /** This is a constructor that is used to create a Country object.
     * @param countryID Country ID
     * @param countryName Country Name */
    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /** Sets the input country ID to the Country object.
     * @param countryID Country ID */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /** Returns the country ID from the Country object.
     * @return Country ID*/
    public int getCountryID() {
        return countryID;
    }

    /** Sets the input country Name to the Country object.
     * @param countryName Country Name */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /** Returns the country Name from the Country object.
     * @return Country Name*/
    public String getCountryName() {
        return countryName;
    }

    @Override
    public String toString(){
        return(countryName);
    }
}


