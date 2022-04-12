package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

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
            customerResult = new Customer(cusId, cusName, cusAddress, cusPostal, cusPhoneNum);
            System.out.println(customerResult.getCusName() + " " + customerResult.getCusPostal());
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
            Customer customerResult = new Customer(cusId, cusName, cusAddress, cusPostal, cusPhoneNum);
            customerList.add(customerResult);
            //System.out.println(customerResult.getCusName() + " " + customerResult.getCusPostal());

        }
        return customerList;
    }
}