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

/** This is a controller for the appointment.fxml file. */
public class appointmentsController implements Initializable {


    public TableView<Appointment> AllAppointmentsTable;

    public TableView WeeklyAppointmentsTable;
    public TableColumn WeeklyAppIDCol;
    public TableColumn WeeklyTitleCol;
    public TableColumn WeeklyDescriptionCol;
    public TableColumn WeeklyLocationCol;
    public TableColumn WeeklyContactCol;
    public TableColumn WeeklyTypeCol;
    public TableColumn WeeklyStartDateTime;
    public TableColumn WeeklyEndDateTime;
    public TableColumn WeeklyCusID;
    public TableColumn WeeklyUserID;

    public TableView MonthlyAppointmentsTable;
    public TableColumn MonthlyAppIDCol;
    public TableColumn MonthlyTitleCol;
    public TableColumn MonthlyDescriptionCol;
    public TableColumn MonthlyLocationCol;
    public TableColumn MonthlyContactCol;
    public TableColumn MonthlyTypeCol;
    public TableColumn MonthlyStartDateTime;
    public TableColumn MonthlyEndDateTime;
    public TableColumn MonthlyCusID;
    public TableColumn MonthlyUserID;

    public Tab AllAppTab;
    public Tab WeeklyTab;
    public Tab MonthlyTab;
    public Button AddAppointment;
    public Button DeleteAppointment;
    public Button UpdateAppointment;
    public Button CustomersButton;

    ObservableList<Appointment> appointmentsTableList = FXCollections.observableArrayList();

    public TableColumn AllAppIDCol;
    public TableColumn AllTitleCol;
    public TableColumn AllDescriptionCol;
    public TableColumn AllLocationCol;
    public TableColumn AllContactCol;
    public TableColumn AllTypeCol;
    public TableColumn AllStartDateTime;
    public TableColumn AllEndDateTime;
    public TableColumn AllCusID;
    public TableColumn AllUserID;

    private static Appointment currentAppointment = null; //the Appointment object to be passed to other screens when needed.
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
     * @param actionEvent */
    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addAppointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Create Appointment");
        stage.setScene(new Scene(root, 425, 425));
        stage.show();
    }

    /** Deletes selected appointment after confirming first with an alert.
     * @param actionEvent */
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
     * @param actionEvent */
    public void onCustomersClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/customers.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customers");
        stage.setScene(new Scene(root, 785, 570));
        stage.show();
    }

    /** When the Update Appointment button is clicked, takes user to the Update Appointments screen.
     * @param actionEvent */
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
     * @param event */
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
     * @param event */
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
     * @param event */
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
     * @param actionEvent */
    public void onReportsClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/reports.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Reports");
        stage.setScene(new Scene(root, 975, 500));
        stage.show();
    }
}
