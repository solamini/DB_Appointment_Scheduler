package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

/** This class gets a connection with the database and uses various methods to query the database.
 * This class queries the database for data on Customers. */
public class CustomerDaoImpl {

    /**
     * Used to query the database and return a Customer based on the customer ID.
     * This connects to the database, pulls data, and creates a Customer Object.
     *
     * @param cusID
     * @return Customer object
     */
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

    /**
     * Used to query the database and return All Customers in the database.
     * This connects to the database, pulls data, creates Customer Objects, and puts them into an observable list.
     * @return List of all Customer Objects
     */
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

}
