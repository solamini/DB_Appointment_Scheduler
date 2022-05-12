package model;

/** This class creates the Country object. */
public class Country {
    private int countryID;
    private String countryName;

    public Country(){
    }

    /** This is a constructor that is used to create a Country object.
     * @param countryID
     * @param countryName */
    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /** Sets the input country ID to the Country object.
     * @param countryID*/
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /** Returns the country ID from the Country object.
     * @return Country ID*/
    public int getCountryID() {
        return countryID;
    }

    /** Sets the input country Name to the Country object.
     * @param countryName*/
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


