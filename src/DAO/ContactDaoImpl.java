package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;

/** This class gets a connection with the database and uses various methods to query the database.
 * This class queries the database for data on Contacts.
 * @author Aleksandr Ogilba */
public class ContactDaoImpl {

    /** Used to query the database and return a Contact based on the contact ID.
     * This connects to the database, pulls data, and creates a Contact Object.
     * @param contactID ID of contact
     * @return Contact object */
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

    /** Used to query the database and return All contacts in the database.
     * This connects to the database, pulls data, creates contact Objects, and puts them into an observable list.
     * @return List of all Contact Objects */
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
