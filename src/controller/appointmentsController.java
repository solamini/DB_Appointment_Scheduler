package controller;

import DAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is a controller for the appointment.fxml file.
 * @author Aleksandr Ogilba */
public class appointmentsController implements Initializable {

    /** Table for Weekly Appointments */
    public TableView WeeklyAppointmentsTable;

    /** Weekly Table's Appointment ID column. */
    public TableColumn WeeklyAppIDCol;

    /** Weekly Table's Appointment Title column. */
    public TableColumn WeeklyTitleCol;

    /** Weekly Table's Appointment Description column. */
    public TableColumn WeeklyDescriptionCol;

    /** Weekly Table's Appointment Location column. */
    public TableColumn WeeklyLocationCol;

    /** Weekly Table's Appointment Contact column. */
    public TableColumn WeeklyContactCol;

    /** Weekly Table's Appointment Type column. */
    public TableColumn WeeklyTypeCol;

    /** Weekly Table's Appointment Start Date column. */
    public TableColumn WeeklyStartDateTime;

    /** Weekly Table's Appointment End Date column. */
    public TableColumn WeeklyEndDateTime;

    /** Weekly Table's Customer ID column. */
    public TableColumn WeeklyCusID;

    /** Weekly Table's User ID column. */
    public TableColumn WeeklyUserID;

    /** Table for Monthly Appointments */
    public TableView MonthlyAppointmentsTable;

    /** Monthly Table's Appointment ID column. */
    public TableColumn MonthlyAppIDCol;

    /** Monthly Table's Appointment Title column. */
    public TableColumn MonthlyTitleCol;

    /** Monthly Table's Appointment Description column. */
    public TableColumn MonthlyDescriptionCol;

    /** Monthly Table's Appointment Location column. */
    public TableColumn MonthlyLocationCol;

    /** Monthly Table's Appointment Contact column. */
    public TableColumn MonthlyContactCol;

    /** Monthly Table's Appointment Type column. */
    public TableColumn MonthlyTypeCol;

    /** Monthly Table's Appointment Start Date column. */
    public TableColumn MonthlyStartDateTime;

    /** Monthly Table's Appointment End Date column. */
    public TableColumn MonthlyEndDateTime;

    /** Monthly Table's Customer ID column. */
    public TableColumn MonthlyCusID;

    /** Monthly Table's User ID column. */
    public TableColumn MonthlyUserID;

    /** Tab that shows the All Appointments Table */
    public Tab AllAppTab;

    /** Tab that shows the Weekly Appointments Table */
    public Tab WeeklyTab;

    /** Tab that shows the Monthly Appointments Table */
    public Tab MonthlyTab;

    /** Button that takes you to the screen to add Appointments */
    public Button AddAppointment;

    /** Button that deletes Appointments */
    public Button DeleteAppointment;

    /** Button that takes you to the screen to update Appointments */
    public Button UpdateAppointment;

    /** Button that takes you to the Customers screen */
    public Button CustomersButton;

    /** Observable List used to populate table */
    ObservableList<Appointment> appointmentsTableList = FXCollections.observableArrayList();

    /** Table for All Appointments */
    public TableView<Appointment> AllAppointmentsTable;

    /** All Appointment Table's Appointment ID column. */
    public TableColumn AllAppIDCol;

    /** All Appointment Table's Appointment Title column. */
    public TableColumn AllTitleCol;

    /** All Appointment Table's Appointment Description column. */
    public TableColumn AllDescriptionCol;

    /** All Appointment Table's Appointment Location column. */
    public TableColumn AllLocationCol;

    /** All Appointment Table's Appointment Contact column. */
    public TableColumn AllContactCol;

    /** All Appointment Table's Appointment Type column. */
    public TableColumn AllTypeCol;

    /** All Appointment Table's Appointment Start Date column. */
    public TableColumn AllStartDateTime;

    /** All Appointment Table's Appointment End Date column. */
    public TableColumn AllEndDateTime;

    /** All Appointment Table's Customer ID column. */
    public TableColumn AllCusID;

    /** All Appointment Table's User ID column. */
    public TableColumn AllUserID;

    /** Appointment object of the currently selected appointment */
    private static Appointment currentAppointment = null; //the Appointment object to be passed to other screens when needed.

    /** Gets the currentAppointment object
     * @return Appointment object */
    public static Appointment getCurrentAppointment(){
        return currentAppointment;
    }


    /** Initializes and sets All Appointments table to fill with all appointments in the database.
     * @param url
     * @param resourceBundle */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AllAppIDCol.setCellValueFactory(new PropertyValueFactory<>("appID"));
        AllTitleCol.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        AllDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        AllLocationCol.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        AllContactCol.setCellValueFactory(new PropertyValueFactory<>("appContact"));
        AllTypeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
        AllStartDateTime.setCellValueFactory(new PropertyValueFactory<>("appStartDate"));
        AllEndDateTime.setCellValueFactory(new PropertyValueFactory<>("appEndDate"));
        AllCusID.setCellValueFactory(new PropertyValueFactory<>("appCustomer"));
        AllUserID.setCellValueFactory(new PropertyValueFactory<>("appUser"));

        try {
            appointmentsTableList = AppointmentDaoImpl.getAllAppointments();
            AllAppointmentsTable.setItems(appointmentsTableList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /** When the Add Appointment button is clicked, takes user to the add Appointments screen.
     * @param actionEvent Clicking button */
    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addAppointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Create Appointment");
        stage.setScene(new Scene(root, 425, 425));
        stage.show();
    }

    /** Deletes selected appointment after confirming first with an alert.
     * @param actionEvent  Clicking button */
    public void onDeleteAppointment(ActionEvent actionEvent) {
        JDBC.getConnection();
        try {
            currentAppointment = AllAppointmentsTable.getSelectionModel().getSelectedItem();
            String appID = String.valueOf(currentAppointment.getAppID());
            String sqlStmt = "DELETE FROM appointments WHERE Appointment_ID = '"+appID+"'";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete the "+currentAppointment.getAppType()+" appointment, with the ID of "+currentAppointment.getAppID());
            alert.setTitle("Appointment");
            alert.setHeaderText("Delete");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {

                Query.dataManipulateQuery(sqlStmt);

                appointmentsTableList = AppointmentDaoImpl.getAllAppointments();
                AllAppointmentsTable.setItems(appointmentsTableList);

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "You have cancelled the "+currentAppointment.getAppType()+" appointment, with the ID of "+currentAppointment.getAppID());
                alert2.show();
            }
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select the appointment you want to cancel.");
            alert.show();
        }
    }

    /** When the Customers button is clicked, takes user to the Customers screen.
     * @param actionEvent Clicking button */
    public void onCustomersClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/customers.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customers");
        stage.setScene(new Scene(root, 900, 570));
        stage.show();
    }

    /** When the Update Appointment button is clicked, takes user to the Update Appointments screen.
     * @param actionEvent Clicking button */
    public void onUpdateAppointment(ActionEvent actionEvent) throws IOException {
        try {
            currentAppointment = AllAppointmentsTable.getSelectionModel().getSelectedItem();

            Parent root = FXMLLoader.load(getClass().getResource("/view/updateAppointment.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Update Appointment");
            stage.setScene(new Scene(root, 425, 425));
            stage.show();
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an appointment to update.");
            alert.show();
        }
    }

    /** Sets the table to hold all the appointments in the next seven days and fills the table.
     * @param event Selecting Weekly Tab */
    public void onWeeklyTabSelected(Event event) {
        if(!AllAppTab.isSelected()){
            AddAppointment.setVisible(false);
            DeleteAppointment.setVisible(false);
            UpdateAppointment.setVisible(false);
        }


        WeeklyAppIDCol.setCellValueFactory(new PropertyValueFactory<>("appID"));
        WeeklyTitleCol.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        WeeklyDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        WeeklyLocationCol.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        WeeklyContactCol.setCellValueFactory(new PropertyValueFactory<>("appContact"));
        WeeklyTypeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
        WeeklyStartDateTime.setCellValueFactory(new PropertyValueFactory<>("appStartDate"));
        WeeklyEndDateTime.setCellValueFactory(new PropertyValueFactory<>("appEndDate"));
        WeeklyCusID.setCellValueFactory(new PropertyValueFactory<>("appCustomer"));
        WeeklyUserID.setCellValueFactory(new PropertyValueFactory<>("appUser"));

        try {
            appointmentsTableList = AppointmentDaoImpl.getWeeklyAppointments();
            WeeklyAppointmentsTable.setItems(appointmentsTableList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Sets the table to hold all the appointments in the current month and fills the table.
     * @param event Selecting Monthly Tab */
    public void onMonthlyTabSelected(Event event) {
        if(!AllAppTab.isSelected()){
            AddAppointment.setVisible(false);
            DeleteAppointment.setVisible(false);
            UpdateAppointment.setVisible(false);
        }

        MonthlyAppIDCol.setCellValueFactory(new PropertyValueFactory<>("appID"));
        MonthlyTitleCol.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        MonthlyDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        MonthlyLocationCol.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        MonthlyContactCol.setCellValueFactory(new PropertyValueFactory<>("appContact"));
        MonthlyTypeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
        MonthlyStartDateTime.setCellValueFactory(new PropertyValueFactory<>("appStartDate"));
        MonthlyEndDateTime.setCellValueFactory(new PropertyValueFactory<>("appEndDate"));
        MonthlyCusID.setCellValueFactory(new PropertyValueFactory<>("appCustomer"));
        MonthlyUserID.setCellValueFactory(new PropertyValueFactory<>("appUser"));

        try {
            appointmentsTableList = AppointmentDaoImpl.getMonthlyAppointments();
            MonthlyAppointmentsTable.setItems(appointmentsTableList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    /** Sets buttons to be visible when the All Appointments tab is selected.
     * @param event Selecting the All Appointments tab */
    public void onAllAppTabSelected(Event event) {
        try {
            if (AllAppTab.isSelected()) {
                AddAppointment.setVisible(true);
                DeleteAppointment.setVisible(true);
                UpdateAppointment.setVisible(true);
            }
        }
        catch(Exception e){}
    }

    /** When the Reports button is clicked, takes user to the Reports screen.
     * @param actionEvent Clicking button */
    public void onReportsClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/reports.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Reports");
        stage.setScene(new Scene(root, 975, 500));
        stage.show();
    }
}
