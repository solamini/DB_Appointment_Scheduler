package model;

import java.sql.Timestamp;

/** This class creates the Appointment object. */
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

    /** This is a constructor that is used to create an Appointment object.
     * @param appID
     * @param appTitle
     * @param appDescription
     * @param appLocation
     * @param appType
     * @param appStartDate
     * @param appEndDate
     * @param appCustomer
     * @param appUser
     * @param appContact */
    public Appointment(int appID, String appTitle, String appDescription, String appLocation, String appType, Timestamp appStartDate, Timestamp appEndDate, Customer appCustomer, User appUser, Contact appContact){
        this.appID = appID;
        this.appTitle = appTitle;
        this.appDescription = appDescription;
        this.appLocation = appLocation;
        this.appType = appType;
        this.appStartDate = appStartDate;
        this.appEndDate = appEndDate;
        this.appCustomer = appCustomer;
        this.appUser = appUser;
        this.appContact = appContact;
    }

    /** Returns the appointment ID from the Appointment object.
     * @return appointment ID*/
    public int getAppID() {
        return appID;
    }
    /** Sets the input appID to the Appointment object.
     * @param appID*/
    public void setAppID(int appID) {
        this.appID = appID;
    }

    /** Returns the appointment title from the Appointment object.
     * @return appointment title*/
    public String getAppTitle() {
        return appTitle;
    }
    /** Sets the input appTitle to the Appointment object.
     * @param appTitle */
    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    /** Returns the appointment description from the Appointment object.
     * @return appointment description*/
    public String getAppDescription() {
        return appDescription;
    }
    /** Sets the input appDescription to the Appointment object.
     * @param appDescription */
    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    /** Returns the appointment location from the Appointment object.
     * @return appointment location*/
    public String getAppLocation() {
        return appLocation;
    }
    /** Sets the input appLocation to the Appointment object.
     * @param appLocation */
    public void setAppLocation(String appLocation) {
        this.appLocation = appLocation;
    }

    /** Returns the appointment type from the Appointment object.
     * @return appointment type*/
    public String getAppType() {
        return appType;
    }
    /** Sets the input appType to the Appointment object.
     * @param appType */
    public void setAppType(String appType) {
        this.appType = appType;
    }

    /** Returns the appointment start date as a timestamp from the Appointment object.
     * @return appointment start date as timestamp*/
    public Timestamp getAppStartDate() {
        return appStartDate;
    }
    /** Sets the input timestamp start date to the Appointment object.
     * @param appStartDate */
    public void setAppStartDate(Timestamp appStartDate) {
        this.appStartDate = appStartDate;
    }
    /** Returns the appointment end date as a timestamp from the Appointment object.
     * @return appointment end date as timestamp*/
    public Timestamp getAppEndDate() {
        return appEndDate;
    }
    /** Sets the input timestamp end date to the Appointment object.
     * @param appEndDate */
    public void setAppEndDate(Timestamp appEndDate) {
        this.appEndDate = appEndDate;
    }

    /** Returns the appointment customer associated with the Appointment object.
     * @return appointment customer*/
    public Customer getAppCustomer() {
        return appCustomer;
    }
    /** Sets the input appCustomer to the Appointment object.
     * @param appCustomer */
    public void setAppCustomer(Customer appCustomer) {
        this.appCustomer = appCustomer;
    }

    /** Returns the appointment user associated with the Appointment object.
     * @return appointment user*/
    public User getAppUser() {
        return appUser;
    }
    /** Sets the input appUser to the Appointment object.
     * @param appUser */
    public void setAppUser(User appUser) {
        this.appUser = appUser;
    }

    /** Returns the appointment contact associated with the Appointment object.
     * @return appointment contact */
    public Contact getAppContact() {
        return appContact;
    }
    /** Sets the input appContact to the Appointment object.
     * @param appContact */
    public void setAppContact(Contact appContact) {
        this.appContact = appContact;
    }
}
