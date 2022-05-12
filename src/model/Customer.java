package model;

/** This class creates the Customer object. */
public class Customer {
    private int cusID;
    private String cusName;
    private String cusAddress;
    private String cusPostal;
    private String cusPhoneNum;
    private int cusDivID;

    public Customer(){
    }

    /** This is a constructor that is used to create a Customer object.
     * @param cusID
     * @param cusName
     * @param cusAddress
     * @param cusPostal
     * @param cusPhoneNum
     * @param cusDivID */
    public Customer(int cusID, String cusName, String cusAddress, String cusPostal, String cusPhoneNum,int cusDivID){
        this.cusID = cusID;
        this.cusName = cusName;
        this.cusAddress = cusAddress;
        this.cusPostal = cusPostal;
        this.cusPhoneNum = cusPhoneNum;
        this.cusDivID = cusDivID;
    }

    /** Returns the customer ID from the Customer object.
     * @return customer ID*/
    public int getCusID() {
        return cusID;
    }

    /** Sets the input customer ID to the Customer object.
     * @param cusID*/
    public void setCusID(int cusID) {
        this.cusID = cusID;
    }

    /** Returns the customer full Name from the Customer object.
     * @return customer Name*/
    public String getCusFullName() {
        return cusName;
    }

    /** Sets the input customer full name to the Customer object.
     * @param cusName*/
    public void setCusFullName(String cusName) {
        this.cusName = cusName;
    }

    /** Sets the input customer full name to the Customer object.
     * @param firstName
     * @param lastName */
    public void setCusFullName(String firstName, String lastName) {
        this.cusName = firstName + " " + lastName;
    }

    /** Returns the customer first Name from the Customer object.
     * @return customer first Name*/
    public String getCusFirstName(){
        String[] arrayOfStr = cusName.split(" ");
        String firstName = arrayOfStr[0];
        return firstName;
    }
    /** Returns the customer last Name from the Customer object.
     * @return customer last Name*/
    public String getCusLastName(){
        String[] arrayOfStr = cusName.split(" ");
        String lastName = arrayOfStr[1];
        return lastName;
    }
    /** Returns the customer address from the Customer object.
     * @return customer address*/
    public String getCusAddress() {
        return cusAddress;
    }

    /** Sets the input customer address to the Customer object.
     * @param cusAddress*/
    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    /** Returns the customer postal code from the Customer object.
     * @return customer postal*/
    public String getCusPostal() {
        return cusPostal;
    }

    /** Sets the input customer postal code to the Customer object.
     * @param cusPostal*/
    public void setCusPostal(String cusPostal) {
        this.cusPostal = cusPostal;
    }

    /** Returns the customer phone number from the Customer object.
     * @return customer phone number*/
    public String getCusPhoneNum() {
        return cusPhoneNum;
    }

    /** Sets the input customer phone number to the Customer object.
     * @param cusPhoneNum*/
    public void setCusPhoneNum(String cusPhoneNum) {
        this.cusPhoneNum = cusPhoneNum;
    }

    /** Returns the customer division ID from the Customer object.
     * @return customer Division ID*/
    public int getCusDivID() {
        return cusDivID;
    }

    /** Sets the input customer division ID to the Customer object.
     * @param cusDivID*/
    public void setCusDivID(int cusDivID) {
        this.cusDivID = cusDivID;
    }


    @Override
    public String toString(){
        return(String.valueOf(cusID));
    }
}


