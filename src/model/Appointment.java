package model;

import java.sql.Timestamp;


public class Appointment {
    private int appID;
    private String appTitle;
    private String appDescription;
    private String appLocation;
    private String appType;
    private Timestamp appStartDate;
    private Timestamp appEndDate;
    private Customer appCustomer;
    private User appUser;
    private Contact appContact;


    public Appointment() {
    }

    public Appointment(int appID, String appTitle, String appDescription, String appLocation, String appType, Timestamp appStartDate, Timestamp appEndDate, Customer appCustomer, User appUser, Contact appContact){
        this.appID = appID;
        this.appTitle = appTitle;
        this.appDescription = appDescription;
        this.appLocation = appLocation;
        this.appType = appType;
        Timestamp localAppStartDate = main.TimeZoneHelper.UTCToLocalTimestamp(appStartDate);
        this.appStartDate = localAppStartDate;
        Timestamp localAppEndDate = main.TimeZoneHelper.UTCToLocalTimestamp(appEndDate);
        this.appEndDate = localAppEndDate;
        this.appCustomer = appCustomer;
        this.appUser = appUser;
        this.appContact = appContact;
    }

    public int getAppID() {
        return appID;
    }
    public void setAppID(int appID) {
        this.appID = appID;
    }

    public String getAppTitle() {
        return appTitle;
    }
    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public String getAppDescription() {
        return appDescription;
    }
    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    public String getAppLocation() {
        return appLocation;
    }
    public void setAppLocation(String appLocation) {
        this.appLocation = appLocation;
    }

    public String getAppType() {
        return appType;
    }
    public void setAppType(String appType) {
        this.appType = appType;
    }

    public Timestamp getAppStartDate() {
        return appStartDate;
    }
    public void setAppStartDate(Timestamp appStartDate) {
        this.appStartDate = appStartDate;
    }

    public Timestamp getAppEndDate() {
        return appEndDate;
    }
    public void setAppEndDate(Timestamp appEndDate) {
        this.appEndDate = appEndDate;
    }

    public Customer getAppCustomer() {
        return appCustomer;
    }
    public void setAppCustomer(Customer appCustomer) {
        this.appCustomer = appCustomer;
    }

    public User getAppUser() {
        return appUser;
    }
    public void setAppUser(User appUser) {
        this.appUser = appUser;
    }

    public Contact getAppContact() {
        return appContact;
    }
    public void setAppContact(Contact appContact) {
        this.appContact = appContact;
    }
}
