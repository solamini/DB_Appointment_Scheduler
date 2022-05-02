package model;

public class FLDivision {
    private int divID;
    private String divName;
    private int countryID;

    public FLDivision(){
    }

    public FLDivision(int divID, String divName, int countryID) {
        this.divID = divID;
        this.divName = divName;
        this.countryID = countryID;
    }

    public void setDivID(int divID) {
        this.divID = divID;
    }
    public int getDivID() {
        return divID;
    }

    public void setDivName(String divName) {
        this.divName = divName;
    }
    public String getDivName() {
        return divName;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
    public int getCountryID() {
        return countryID;
    }

    @Override
    public String toString(){
        return(divName);
    }
}
