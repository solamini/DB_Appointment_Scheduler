package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDaoImpl {

    public static Contact getContact(int contactID) throws SQLException {
        Contact contactResult = new Contact();

        JDBC.getConnection();
        String sqlStmt = "SELECT * FROM contacts WHERE Contact_ID = '" + contactID + "'";
        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        if(result.next()) {
            int contactId = result.getInt("Contact_ID");
            String contactName = result.getString("Contact_Name");
            String contactEmail = result.getString("Email");
            contactResult = new Contact(contactId, contactName, contactEmail);
            return contactResult;
        }
        return contactResult;
    }

    public static ObservableList<Contact> getAllContacts() throws SQLException{
        ObservableList<Contact> contactList = FXCollections.observableArrayList();

        JDBC.getConnection();
        String sqlStmt = "SELECT * FROM contacts";
        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        while(result.next()) {
            int contactId = result.getInt("Contact_ID");
            String contactName = result.getString("Contact_Name");
            String contactEmail = result.getString("Email");
            Contact contactResult = new Contact(contactId, contactName, contactEmail);
            contactList.add(contactResult);

        }
        return contactList;
    }
}
