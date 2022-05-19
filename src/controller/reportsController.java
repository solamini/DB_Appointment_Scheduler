package controller;

import DAO.AppointmentDaoImpl;
import DAO.ContactDaoImpl;
import DAO.CustomerDaoImpl;
import DAO.ReportDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.Report;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** This is a controller for the reports.fxml file.
 * @author Aleksandr Ogilba */
public class reportsController implements Initializable {

    /** Table for Contact's Appointments */
    public TableView ContactAppointmentsTable;

    /** Contact Appointment Table's Appointment ID column. */
    public TableColumn ContactAppIDCol;

    /** Contact Appointment Table's Appointment Title column. */
    public TableColumn ContactTitleCol;

    /** Contact Appointment Table's Appointment Description column. */
    public TableColumn ContactDescriptionCol;

    /** Contact Appointment Table's Appointment Location column. */
    public TableColumn ContactLocationCol;

    /** Contact Appointment Table's Appointment Contact column. */
    public TableColumn ContactCol;

    /** Contact Appointment Table's Appointment Type column. */
    public TableColumn ContactTypeCol;

    /** Contact Appointment Table's Appointment Start Date column. */
    public TableColumn ContactStartDateTime;

    /** Contact Appointment Table's Appointment End Date column. */
    public TableColumn ContactEndDateTime;

    /** Contact Appointment Table's Customer ID column. */
    public TableColumn ContactCusID;

    /** Contact Appointment Table's User ID column. */
    public TableColumn ContactUserID;

    /** Combobox to select which contact to view appointments of */
    public ComboBox ContactCombo;

    /** Label that shows what is selectable in combobox */
    public Label SelectionLabel;

    /** Table for Monthly report on Appointments type */
    public TableView MonthlyAppointmentsTable;

    /** Monthly Appointment Table's Appointment month column. */
    public TableColumn MonthCol;

    /** Monthly Appointment Table's Appointment Type column. */
    public TableColumn TypeCol;

    /** Monthly Appointment Table's Appointments Total column. */
    public TableColumn TotalCol;

    /** Table for Customer's Appointments */
    public TableView CustomerAppointmentsTable;

    /** Combobox to select which customer ID to view appointments of */
    public ComboBox CustomerCombo;

    /** Customer Appointment Table's Appointment ID column. */
    public TableColumn CustomerAppIDCol;

    /** Customer Appointment Table's Appointment Title column. */
    public TableColumn CustomerTitleCol;

    /** Customer Appointment Table's Appointment ID column. */
    public TableColumn CustomerDescriptionCol;

    /** Customer Appointment Table's Appointment Location column. */
    public TableColumn CustomerLocationCol;

    /** Customer Appointment Table's Appointment Customer column. */
    public TableColumn CustomerCol;

    /** Customer Appointment Table's Appointment Type column. */
    public TableColumn CustomerTypeCol;

    /** Customer Appointment Table's Appointment Start Date column. */
    public TableColumn CustomerStartDateTime;

    /** Customer Appointment Table's Appointment End Date column. */
    public TableColumn CustomerEndDateTime;

    /** Customer Appointment Table's Customer ID column. */
    public TableColumn CustomerCusID;

    /** Customer Appointment Table's User ID column. */
    public TableColumn CustomerUserID;

    /** Initializes and fills the Contact Appointments table with all the appointments.
     * @param url
     * @param resourceBundle */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ContactCombo.setVisible(true);
        CustomerCombo.setVisible(false);

        ContactAppIDCol.setCellValueFactory(new PropertyValueFactory<>("appID"));
        ContactTitleCol.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        ContactDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        ContactLocationCol.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        ContactCol.setCellValueFactory(new PropertyValueFactory<>("appContact"));
        ContactTypeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
        ContactStartDateTime.setCellValueFactory(new PropertyValueFactory<>("appStartDate"));
        ContactEndDateTime.setCellValueFactory(new PropertyValueFactory<>("appEndDate"));
        ContactCusID.setCellValueFactory(new PropertyValueFactory<>("appCustomer"));
        ContactUserID.setCellValueFactory(new PropertyValueFactory<>("appUser"));

        try {
            ObservableList<Appointment> appointmentsTableList = AppointmentDaoImpl.getAllAppointments();
            ContactAppointmentsTable.setItems(appointmentsTableList);
            ContactCombo.setItems(ContactDaoImpl.getAllContacts());

        } catch (SQLException e) {
        }

    }

    /** When the monthly tab is selected, sets the table settings and fills it with a list of the Report.
     * @param event Monthly tab selected */
    public void onMonthlyTotalsTabSelected(Event event) {

        MonthCol.setCellValueFactory(new PropertyValueFactory<>("repMonth"));
        TypeCol.setCellValueFactory(new PropertyValueFactory<>("repType"));
        TotalCol.setCellValueFactory(new PropertyValueFactory<>("repAmount"));

        try {
            ContactCombo.setVisible(false);
            CustomerCombo.setVisible(false);
            SelectionLabel.setVisible(false);
            ObservableList<Report> reportsTableList = FXCollections.observableArrayList();
            reportsTableList = ReportDaoImpl.getMonthTypeReport();
            MonthlyAppointmentsTable.setItems(reportsTableList);

        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    /** When the customer tab is selected, sets the table settings for the Customer's appointments.
     * @param event Customer tab selected */
    public void onCustomerTabSelected(Event event) {

        CustomerAppIDCol.setCellValueFactory(new PropertyValueFactory<>("appID"));
        CustomerTitleCol.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        CustomerDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        CustomerLocationCol.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        CustomerCol.setCellValueFactory(new PropertyValueFactory<>("appContact"));
        CustomerTypeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
        CustomerStartDateTime.setCellValueFactory(new PropertyValueFactory<>("appStartDate"));
        CustomerEndDateTime.setCellValueFactory(new PropertyValueFactory<>("appEndDate"));
        CustomerCusID.setCellValueFactory(new PropertyValueFactory<>("appCustomer"));
        CustomerUserID.setCellValueFactory(new PropertyValueFactory<>("appUser"));


        try {
            ContactCombo.setVisible(false);
            CustomerCombo.setVisible(true);
            SelectionLabel.setVisible(true);
            SelectionLabel.setText("Customer ID");
            ObservableList<Appointment> appointmentsTableList = AppointmentDaoImpl.getAllAppointments();
            CustomerCombo.setItems(CustomerDaoImpl.getAllCustomers());
            CustomerAppointmentsTable.setItems(appointmentsTableList);
        } catch (Exception e) {
        }


    }

    /** When the Appointments button is clicked, takes user back to the appointments screen.
     * @param actionEvent Button clicked */
    public void AppointmentsClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/appointments.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
        stage.setScene(new Scene(root, 975, 500));
        stage.show();
    }

    /** When a contact is selected from the Contact combo box, it fills the table with appointments related to the contact.
     * @param actionEvent Contact selected in combobox */
    public void onContactCombo(ActionEvent actionEvent) {

        try {
            Contact selectedContact = (Contact)ContactCombo.getSelectionModel().getSelectedItem();
            int contactID = selectedContact.getContactID();
            ObservableList<Appointment> appointmentsTableList = AppointmentDaoImpl.getAllContactAppointments(contactID);
            ContactAppointmentsTable.setItems(appointmentsTableList);
        }
        catch (Exception e) {
        }

    }

    /** When the contact tab is selected, sets the settings to show the contact items.
     * @param event Contact tab selected */
    public void onContactScheduleSelected(Event event) {
        try {
            CustomerCombo.setVisible(false);
            ContactCombo.setVisible(true);
            SelectionLabel.setVisible(true);
            SelectionLabel.setText("Select Contact");
            ContactCombo.setItems(ContactDaoImpl.getAllContacts());
        }
        catch(Exception e){
        }
    }
    /** When a customer is selected from the Customer combo box, it fills the table with appointments related to the customer.
     * @param actionEvent Customer selected from combobox */
    public void onCustomerCombo(ActionEvent actionEvent) {

        try {
            Customer selectedCustomer = (Customer)CustomerCombo.getSelectionModel().getSelectedItem();
            int customerID = selectedCustomer.getCusID();
            ObservableList<Appointment> appointmentsTableList = AppointmentDaoImpl.getAllCustomerAppointments(customerID);
            CustomerAppointmentsTable.setItems(appointmentsTableList);

        } catch (Exception e) {
        }
    }
}
