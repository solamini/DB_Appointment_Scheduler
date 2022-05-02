package model;

public class Customer {
    private int cusID;
    private String cusName;
    private String cusAddress;
    private String cusPostal;
    private String cusPhoneNum;
    private int cusDivID;

    public Customer(){
    }

    public Customer(int cusID, String cusName, String cusAddress, String cusPostal, String cusPhoneNum,int cusDivID){
        this.cusID = cusID;
        this.cusName = cusName;
        this.cusAddress = cusAddress;
        this.cusPostal = cusPostal;
        this.cusPhoneNum = cusPhoneNum;
        this.cusDivID = cusDivID;
    }

    public int getCusID() {
        return cusID;
    }
    public void setCusID(int cusID) {
        this.cusID = cusID;
    }

    public String getCusFullName() {
        return cusName;
    }
    public void setCusID(String cusName) {
        this.cusName = cusName;
    }

    public String getCusFirstName(){
        String[] arrayOfStr = cusName.split(" ");
        String firstName = arrayOfStr[0];
        return firstName;
    }
    public String getCusLastName(){
        String[] arrayOfStr = cusName.split(" ");
        String lastName = arrayOfStr[1];
        return lastName;
    }

    public String getCusAddress() {
        return cusAddress;
    }
    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public String getCusPostal() {
        return cusPostal;
    }
    public void setCusPostal(String cusPostal) {
        this.cusPostal = cusPostal;
    }

    public String getCusPhoneNum() {
        return cusPhoneNum;
    }
    public void setCusPhoneNum(String cusPhoneNum) {
        this.cusPhoneNum = cusPhoneNum;
    }

    public void setCusDivID(int cusDivID) {
        this.cusDivID = cusDivID;
    }
    public int getCusDivID() {
        return cusDivID;
    }
}

