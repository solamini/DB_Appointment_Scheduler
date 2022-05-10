package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AppointmentDaoImpl {
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
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Timestamp appStartDateTS = Timestamp.valueOf(appStartDateString);
            Timestamp appEndDateTS = Timestamp.valueOf(appEndDateString);
            int cusId = result.getInt("Customer_ID");
            int userId = result.getInt("User_ID");
            int contactId = result.getInt("Contact_ID");

            Customer appCustomer = DAO.CustomerDaoImpl.getCustomer(cusId);
            User appUser = DAO.UserDaoImpl.getUser(userId);
            Contact appContact = DAO.ContactDaoImpl.getContact(contactId);

            Appointment appResult = new Appointment(appId, appTitle, appDescription, appLocation, appType, appStartDateTS, appEndDateTS, appCustomer, appUser, appContact);
            appList.add(appResult);
        }
        return appList;
    }

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
