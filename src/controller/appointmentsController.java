package controller;

import DAO.AppointmentDaoImpl;
import DAO.CountryDaoImpl;
import DAO.CustomerDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class appointmentsController implements Initializable {


    public TableView<Appointment> AllAppointmentsTable;
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

    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addAppointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Create Appointment");
        stage.setScene(new Scene(root, 425, 425));
        stage.show();
    }

    public void onDeleteAppointment(ActionEvent actionEvent) {

    }

    public void onCustomersClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/customers.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customers");
        stage.setScene(new Scene(root, 785, 570));
        stage.show();
    }
}
