package model;

/** This class creates the FLDiviosn object.
 * @author Aleksandr Ogilba */
public class FLDivision {
    private int divID;
    private String divName;
    private int countryID;

    /** This is an Empty Constructor */
    public FLDivision(){
    }

    /** This is a constructor that is used to create an FLDivision object.
     * @param divID Division ID
     * @param divName Division Name
     * @param countryID Division's Country ID  */
    public FLDivision(int divID, String divName, int countryID) {
        this.divID = divID;
        this.divName = divName;
        this.countryID = countryID;
    }

    /** Sets the input division ID to the FLDivision object.
     * @param divID  Division ID */
    public void setDivID(int divID) {
        this.divID = divID;
    }

    /** Returns the division ID from the FLDivision object.
     * @return Division ID*/
    public int getDivID() {
        return divID;
    }

    /** Sets the input division name to the FLDivision object.
     * @param divName Division Name */
    public void setDivName(String divName) {
        this.divName = divName;
    }

    /** Returns the division name from the FLDivision object.
     * @return Division Name*/
    public String getDivName() {
        return divName;
    }

    /** Sets the input country ID to the FLDivision object.
     * @param countryID Division's Country ID */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /** Returns the country ID from the FLDivision object.
     * @return country ID*/
    public int getCountryID() {
        return countryID;
    }

    @Override
    public String toString(){
        return(divName);
    }
}
