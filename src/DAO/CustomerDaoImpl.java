package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDaoImpl {
    public static Customer getCustomer(int cusID) throws SQLException {
        Customer customerResult = new Customer();

        JDBC.getConnection();
        String sqlStmt = "SELECT * FROM customers WHERE Customer_ID = '" + cusID + "'";
        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        if (result.next()) {
            int cusId = result.getInt("Customer_ID");
            String cusName = result.getString("Customer_Name");
            String cusAddress = result.getString("Address");
            String cusPostal = result.getString("Postal_Code");
            String cusPhoneNum = result.getString("Phone");
            int cusDivId = result.getInt("Division_ID");
            customerResult = new Customer(cusId, cusName, cusAddress, cusPostal, cusPhoneNum, cusDivId);
            return customerResult;
        }
        return customerResult;
    }


    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        JDBC.getConnection();
        String sqlStmt = "SELECT * FROM customers";
        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        while (result.next()) {
            int cusId = result.getInt("Customer_ID");
            String cusName = result.getString("Customer_Name");
            String cusAddress = result.getString("Address");
            String cusPostal = result.getString("Postal_Code");
            String cusPhoneNum = result.getString("Phone");
            int cusDivId = result.getInt("Division_ID");
            Customer customerResult = new Customer(cusId, cusName, cusAddress, cusPostal, cusPhoneNum, cusDivId);
            customerList.add(customerResult);

        }
        return customerList;
    }

    public static int generateCustomerId() throws SQLException {
        int newId = 0;
        ObservableList<Customer> allCustomers = getAllCustomers();
        ArrayList<Integer> idNumbers = new ArrayList<Integer>();

        for (Customer c : allCustomers) {
            idNumbers.add(c.getCusID());
        }
        for (int i = 1; i < (allCustomers.size() * 2) ; i++) { //checks if any ID number starting from 1 is available to be used
            if(!idNumbers.contains(i)) {
                newId = i; //assigns i to the newId number if i does not have an ID assigned to it already.
                break; //ends the for loop when newId gets assigned i
            }
        }
        return newId;
    }
}
