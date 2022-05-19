package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/** This class gets a connection with the database and uses various methods to query the database.
 * This class queries the database for data on Appointments.
 * @author Aleksandr Ogilba */
public class AppointmentDaoImpl {

    /** Used to query the database and return an Appointment based on the appointment ID.
     * This connects to the database, pulls data, and creates an Appointment Object.
     * @param appID Appointment ID
     * @return Appointment object */
    public static Appointment getAppointment(int appID) throws SQLException {
        Appointment appResult = new Appointment();

        JDBC.getConnection();
        String sqlStmt = "SELECT * FROM appointments WHERE Appointment_ID = '" + appID + "'";
        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        if (result.next()) {
            int appId = result.getInt("Appointment_ID");
            String appTitle = result.getString("Title");
            String appDescription = result.getString("Description");
            String appLocation = result.getString("Location");
            String appType = result.getString("Type");
            String appStartDateString = result.getString("Start");
            String appEndDateString = result.getString("End");

            Timestamp appStartDateTS = Timestamp.valueOf(appStartDateString);
            Timestamp appEndDateTS = Timestamp.valueOf(appEndDateString);

            int cusId = result.getInt("Customer_ID");
            int userId = result.getInt("User_ID");
            int contactId = result.getInt("Contact_ID");

            Customer appCustomer = DAO.CustomerDaoImpl.getCustomer(cusId);
            User appUser = DAO.UserDaoImpl.getUser(userId);
            Contact appContact = DAO.ContactDaoImpl.getContact(contactId);

            appResult = new Appointment(appId, appTitle, appDescription, appLocation, appType, appStartDateTS, appEndDateTS, appCustomer, appUser, appContact);
            return appResult;
        }
        return appResult;
    }

    /** Used to query the database and returns All Appointments in the database.
     * This connects to the database, pulls data, creates Appointment Objects, and puts them into an observable list.
     * @return List of all Appointment Objects */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> appList = FXCollections.observableArrayList();

        JDBC.getConnection();
        String sqlStmt = "SELECT * FROM appointments";
        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        while (result.next()) {
            int appId = result.getInt("Appointment_ID");
            String appTitle = result.getString("Title");
            String appDescription = result.getString("Description");
            String appLocation = result.getString("Location");
            String appType = result.getString("Type");
            String appStartDateString = result.getString("Start");
            String appEndDateString = result.getString("End");
            Timestamp appStartDateTSLocal = main.TimeZoneHelper.UTCToLocalTimestamp(Timestamp.valueOf(appStartDateString));
            Timestamp appEndDateTSLocal = main.TimeZoneHelper.UTCToLocalTimestamp(Timestamp.valueOf(appEndDateString));

            int cusId = result.getInt("Customer_ID");
            int userId = result.getInt("User_ID");
            int contactId = result.getInt("Contact_ID");

            Customer appCustomer = DAO.CustomerDaoImpl.getCustomer(cusId);
            User appUser = DAO.UserDaoImpl.getUser(userId);
            Contact appContact = DAO.ContactDaoImpl.getContact(contactId);

            Appointment appResult = new Appointment(appId, appTitle, appDescription, appLocation, appType, appStartDateTSLocal, appEndDateTSLocal, appCustomer, appUser, appContact);
            appList.add(appResult);
        }
        return appList;
    }

    /** Used to query the database and returns All Appointments in the database and convert the times to Eastern.
     * This connects to the database, pulls data, creates Appointment Objects, and puts them into an observable list.
     * @return List of all Appointment Objects in Eastern Time */
    public static ObservableList<Appointment> getAllAppointmentsEST() throws SQLException {
        ObservableList<Appointment> appList = FXCollections.observableArrayList();

        JDBC.getConnection();
        String sqlStmt = "SELECT * FROM appointments";
        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        while (result.next()) {
            int appId = result.getInt("Appointment_ID");
            String appTitle = result.getString("Title");
            String appDescription = result.getString("Description");
            String appLocation = result.getString("Location");
            String appType = result.getString("Type");
            String appStartDateString = result.getString("Start");
            String appEndDateString = result.getString("End");
            Timestamp appStartDateTSLocal = main.TimeZoneHelper.UTCToESTTimestamp(Timestamp.valueOf(appStartDateString));
            Timestamp appEndDateTSLocal = main.TimeZoneHelper.UTCToESTTimestamp(Timestamp.valueOf(appEndDateString));
            int cusId = result.getInt("Customer_ID");
            int userId = result.getInt("User_ID");
            int contactId = result.getInt("Contact_ID");

            Customer appCustomer = DAO.CustomerDaoImpl.getCustomer(cusId);
            User appUser = DAO.UserDaoImpl.getUser(userId);
            Contact appContact = DAO.ContactDaoImpl.getContact(contactId);

            Appointment appResult = new Appointment(appId, appTitle, appDescription, appLocation, appType, appStartDateTSLocal, appEndDateTSLocal, appCustomer, appUser, appContact);
            appList.add(appResult);
        }
        return appList;
    }

    /** Used to query the database and returns all Appointments for the next 7 days in the database.
     * This connects to the database, pulls data, creates Appointment Objects, and puts then into an observable list.
     * @return List of all Appointment Objects for the next 7 days */
    public static ObservableList<Appointment> getWeeklyAppointments() throws SQLException {
        ObservableList<Appointment> appList = FXCollections.observableArrayList();
        Timestamp currentTS = new Timestamp(System.currentTimeMillis());

        JDBC.getConnection();
        String sqlStmt = "SELECT * FROM appointments WHERE start BETWEEN '"+currentTS+"' AND '"+main.TimeZoneHelper.TimestampPlus7Days(currentTS)+"'";

        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        while (result.next()) {
            int appId = result.getInt("Appointment_ID");
            String appTitle = result.getString("Title");
            String appDescription = result.getString("Description");
            String appLocation = result.getString("Location");
            String appType = result.getString("Type");
            String appStartDateString = result.getString("Start");
            String appEndDateString = result.getString("End");
            Timestamp appStartDateTSLocal = main.TimeZoneHelper.UTCToLocalTimestamp(Timestamp.valueOf(appStartDateString));
            Timestamp appEndDateTSLocal = main.TimeZoneHelper.UTCToLocalTimestamp(Timestamp.valueOf(appEndDateString));
            int cusId = result.getInt("Customer_ID");
            int userId = result.getInt("User_ID");
            int contactId = result.getInt("Contact_ID");

            Customer appCustomer = DAO.CustomerDaoImpl.getCustomer(cusId);
            User appUser = DAO.UserDaoImpl.getUser(userId);
            Contact appContact = DAO.ContactDaoImpl.getContact(contactId);

            Appointment appResult = new Appointment(appId, appTitle, appDescription, appLocation, appType, appStartDateTSLocal, appEndDateTSLocal, appCustomer, appUser, appContact);
            appList.add(appResult);
            main.TimeZoneHelper.TimestampPlus7Days(appResult.getAppStartDate());
        }
        return appList;
    }

    /** Used to query the database and returns all Appointments in the database for the current month.
     * This connects to the database, pulls data, creates Appointment Objects, and puts them into an observable list.
     * @return List of all Appointment Objects for the current month. */
    public static ObservableList<Appointment> getMonthlyAppointments() throws SQLException {
        ObservableList<Appointment> appList = FXCollections.observableArrayList();
        Timestamp currentTS = new Timestamp(System.currentTimeMillis());

        JDBC.getConnection();
        String sqlStmt = "SELECT * FROM appointments WHERE start BETWEEN '"+currentTS+"' AND '"+main.TimeZoneHelper.TimestampPlus30Days(currentTS)+"'";

        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        while (result.next()) {
            int appId = result.getInt("Appointment_ID");
            String appTitle = result.getString("Title");
            String appDescription = result.getString("Description");
            String appLocation = result.getString("Location");
            String appType = result.getString("Type");
            String appStartDateString = result.getString("Start");
            String appEndDateString = result.getString("End");
            Timestamp appStartDateTSLocal = main.TimeZoneHelper.UTCToLocalTimestamp(Timestamp.valueOf(appStartDateString));
            Timestamp appEndDateTSLocal = main.TimeZoneHelper.UTCToLocalTimestamp(Timestamp.valueOf(appEndDateString));

            int cusId = result.getInt("Customer_ID");
            int userId = result.getInt("User_ID");
            int contactId = result.getInt("Contact_ID");

            Customer appCustomer = DAO.CustomerDaoImpl.getCustomer(cusId);
            User appUser = DAO.UserDaoImpl.getUser(userId);
            Contact appContact = DAO.ContactDaoImpl.getContact(contactId);

            Appointment appResult = new Appointment(appId, appTitle, appDescription, appLocation, appType, appStartDateTSLocal, appEndDateTSLocal, appCustomer, appUser, appContact);
            appList.add(appResult);
            main.TimeZoneHelper.TimestampPlus7Days(appResult.getAppStartDate());
        }
        return appList;
    }

    /** Used to query the database and return an Appointment list based on the contact ID associated with the appointment.
     * This connects to the database, pulls data, and creates an Appointment Object.
     * @param contactID ID of Contact to get all their appointments
     * @return Appointment object list */
    public static ObservableList<Appointment> getAllContactAppointments(int contactID) throws SQLException {
        ObservableList<Appointment> appList = FXCollections.observableArrayList();

        JDBC.getConnection();
        String sqlStmt = "SELECT * FROM appointments WHERE Contact_ID ="+contactID;
        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        while (result.next()) {
            int appId = result.getInt("Appointment_ID");
            String appTitle = result.getString("Title");
            String appDescription = result.getString("Description");
            String appLocation = result.getString("Location");
            String appType = result.getString("Type");
            String appStartDateString = result.getString("Start");
            String appEndDateString = result.getString("End");
            Timestamp appStartDateTSLocal = main.TimeZoneHelper.UTCToLocalTimestamp(Timestamp.valueOf(appStartDateString));
            Timestamp appEndDateTSLocal = main.TimeZoneHelper.UTCToLocalTimestamp(Timestamp.valueOf(appEndDateString));
            int cusId = result.getInt("Customer_ID");
            int userId = result.getInt("User_ID");
            int contactId = result.getInt("Contact_ID");

            Customer appCustomer = DAO.CustomerDaoImpl.getCustomer(cusId);
            User appUser = DAO.UserDaoImpl.getUser(userId);
            Contact appContact = DAO.ContactDaoImpl.getContact(contactId);

            Appointment appResult = new Appointment(appId, appTitle, appDescription, appLocation, appType, appStartDateTSLocal, appEndDateTSLocal, appCustomer, appUser, appContact);
            appList.add(appResult);
        }
        return appList;
    }

    /** Used to query the database and return an Appointment list based on the customer ID.
     * This connects to the database, pulls data, and creates an Appointment Object.
     * @param cusID ID of customer to get all their appointments
     * @return Appointment object list */
    public static ObservableList<Appointment> getAllCustomerAppointments(int cusID) throws SQLException {
        ObservableList<Appointment> appList = FXCollections.observableArrayList();

        JDBC.getConnection();
        String sqlStmt = "SELECT * FROM appointments WHERE Customer_ID ="+cusID;
        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        while (result.next()) {
            int appId = result.getInt("Appointment_ID");
            String appTitle = result.getString("Title");
            String appDescription = result.getString("Description");
            String appLocation = result.getString("Location");
            String appType = result.getString("Type");
            String appStartDateString = result.getString("Start");
            String appEndDateString = result.getString("End");
            Timestamp appStartDateTSLocal = main.TimeZoneHelper.UTCToLocalTimestamp(Timestamp.valueOf(appStartDateString));
            Timestamp appEndDateTSLocal = main.TimeZoneHelper.UTCToLocalTimestamp(Timestamp.valueOf(appEndDateString));
            int cusId = result.getInt("Customer_ID");
            int userId = result.getInt("User_ID");
            int contactId = result.getInt("Contact_ID");

            Customer appCustomer = DAO.CustomerDaoImpl.getCustomer(cusId);
            User appUser = DAO.UserDaoImpl.getUser(userId);
            Contact appContact = DAO.ContactDaoImpl.getContact(contactId);

            Appointment appResult = new Appointment(appId, appTitle, appDescription, appLocation, appType, appStartDateTSLocal, appEndDateTSLocal, appCustomer, appUser, appContact);
            appList.add(appResult);
        }
        return appList;
    }
    /** Used to query the database and return an Appointment list with Eastern timestamps based on the customer ID.
     * This connects to the database, pulls data, and creates an Appointment Object.
     * @param cusID ID of customer to get all their appointments
     * @return Appointment object list */
    public static ObservableList<Appointment> getAllCustomerAppointmentsEST(int cusID) throws SQLException {
        ObservableList<Appointment> appList = FXCollections.observableArrayList();

        JDBC.getConnection();
        String sqlStmt = "SELECT * FROM appointments WHERE Customer_ID ="+cusID;
        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        while (result.next()) {
            int appId = result.getInt("Appointment_ID");
            String appTitle = result.getString("Title");
            String appDescription = result.getString("Description");
            String appLocation = result.getString("Location");
            String appType = result.getString("Type");
            String appStartDateString = result.getString("Start");
            String appEndDateString = result.getString("End");
            Timestamp appStartDateTSLocal = main.TimeZoneHelper.UTCToESTTimestamp(Timestamp.valueOf(appStartDateString));
            Timestamp appEndDateTSLocal = main.TimeZoneHelper.UTCToESTTimestamp(Timestamp.valueOf(appEndDateString));
            int cusId = result.getInt("Customer_ID");
            int userId = result.getInt("User_ID");
            int contactId = result.getInt("Contact_ID");

            Customer appCustomer = DAO.CustomerDaoImpl.getCustomer(cusId);
            User appUser = DAO.UserDaoImpl.getUser(userId);
            Contact appContact = DAO.ContactDaoImpl.getContact(contactId);

            Appointment appResult = new Appointment(appId, appTitle, appDescription, appLocation, appType, appStartDateTSLocal, appEndDateTSLocal, appCustomer, appUser, appContact);
            appList.add(appResult);
        }
        return appList;
    }



    /** Generates an Appointment ID that is not currently being used by any appointment.
     * Goes through the list of appointment IDs and returns the first ID number available to be used.
     * @return An integer that is free to be used as an appointment ID. */
    public static int generateAppointmentId() throws SQLException {
        int newId = 0;
        ObservableList<Appointment> allAppointments = getAllAppointments();
        ArrayList<Integer> idNumbers = new ArrayList<Integer>();

        for (Appointment a : allAppointments) {
            idNumbers.add(a.getAppID());
        }
        for (int i = 1; i < (allAppointments.size() * 2) ; i++) { //checks if any ID number starting from 1 is available to be used
            if(!idNumbers.contains(i)) {
                newId = i; //assigns i to the newId number if i does not have an ID assigned to it already.
                break; //ends the for loop when newId gets assigned i
            }
        }
        return newId;
    }
}
