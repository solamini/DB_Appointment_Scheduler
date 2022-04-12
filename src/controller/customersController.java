package controller;

import DAO.CustomerDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class customersController implements Initializable {

    @FXML
    public TableColumn CustomerID;

    @FXML
    public TableColumn CustomerName;

    @FXML
    public TableColumn CustomerAddress;

    @FXML
    public TableColumn CustomerZip;

    @FXML
    public TableColumn CustomerPhoneNum;

    @FXML
    public TableView<Customer> CustomerTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        CustomerID.setCellValueFactory(new PropertyValueFactory<>("cusID"));
        CustomerName.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        CustomerAddress.setCellValueFactory(new PropertyValueFactory<>("cusAddress"));
        CustomerZip.setCellValueFactory(new PropertyValueFactory<>("cusPostal"));
        CustomerPhoneNum.setCellValueFactory(new PropertyValueFactory<>("cusPhoneNum"));

        try {
            CustomerTable.setItems(CustomerDaoImpl.getAllCustomers());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onAddClick(ActionEvent actionEvent) {
    }

    public void onUpdateClick(ActionEvent actionEvent) {
    }

    public void onDeleteClick(ActionEvent actionEvent) {
    }

    public void onCancelClick(ActionEvent actionEvent) {
    }

    public void onAppointmentsClick(ActionEvent actionEvent) {
    }
}
