package controller;

import DAO.CountryDaoImpl;
import DAO.CustomerDaoImpl;
import DAO.FLDivisionDaoImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Customer;
import model.FLDivision;

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

    public Label CusIDLabel;
    public TextField FirstNameText;
    public TextField LastNameText;
    public TextField AddressText;
    public TextField PostalText;
    public TextField PhoneNumberText;

    public ComboBox CountryCombo;
    public ComboBox StateCombo;


    private static Customer currentCustomer = null;


    public static Customer getCurrentCustomer() {
        return currentCustomer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        CustomerID.setCellValueFactory(new PropertyValueFactory<>("cusID"));
        CustomerName.setCellValueFactory(new PropertyValueFactory<>("cusFullName"));
        CustomerAddress.setCellValueFactory(new PropertyValueFactory<>("cusAddress"));
        CustomerZip.setCellValueFactory(new PropertyValueFactory<>("cusPostal"));
        CustomerPhoneNum.setCellValueFactory(new PropertyValueFactory<>("cusPhoneNum"));



        try {
            CustomerTable.setItems(CustomerDaoImpl.getAllCustomers());
            CountryCombo.setItems(CountryDaoImpl.getAllCountries());
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
        CusIDLabel.setText("");
        FirstNameText.clear();
        LastNameText.clear();
        AddressText.clear();
        PostalText.clear();
        PhoneNumberText.clear();
        CountryCombo.getSelectionModel().clearSelection();
        StateCombo.getSelectionModel().clearSelection();
    }

    public void onAppointmentsClick(ActionEvent actionEvent) {
    }

    public void onMouseClicked(MouseEvent mouseEvent) {
        try {
            currentCustomer = CustomerTable.getSelectionModel().getSelectedItem();

            CusIDLabel.setText(String.valueOf(currentCustomer.getCusID()));
            FirstNameText.setText(currentCustomer.getCusFirstName());
            LastNameText.setText((currentCustomer.getCusLastName()));
            AddressText.setText(currentCustomer.getCusAddress());
            PostalText.setText(currentCustomer.getCusPostal());
            PhoneNumberText.setText(currentCustomer.getCusPhoneNum());
            int cusDivID = currentCustomer.getCusDivID();
            int cusCountryID = FLDivisionDaoImpl.getFLDivision(cusDivID).getCountryID();
            ObservableList<FLDivision> divisionsList = FLDivisionDaoImpl.getAllCountryDivisions(cusCountryID);

            CountryCombo.getSelectionModel().clearAndSelect(cusCountryID-1);
            StateCombo.setItems(divisionsList);
            for(int i = 0; i < divisionsList.size(); i++) {
                if (cusDivID == divisionsList.get(i).getDivID()) {
                    StateCombo.getSelectionModel().clearAndSelect(i);
                }
            }

        }
        catch (Exception e) {
        }
    }
}
