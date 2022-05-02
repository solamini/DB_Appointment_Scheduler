package model;

public class Country {
    private int countryID;
    private String countryName;

    public Country(){
    }

    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
    public int getCountryID() {
        return countryID;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public String getCountryName() {
        return countryName;
    }

    @Override
    public String toString(){
        return(countryName);
    }
}


