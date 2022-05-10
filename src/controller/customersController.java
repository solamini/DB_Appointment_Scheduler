package controller;

import DAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.TimeZoneHelper;
import model.Country;
import model.Customer;
import model.FLDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
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

    ObservableList<Customer> customersTableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        CustomerID.setCellValueFactory(new PropertyValueFactory<>("cusID"));
        CustomerName.setCellValueFactory(new PropertyValueFactory<>("cusFullName"));
        CustomerAddress.setCellValueFactory(new PropertyValueFactory<>("cusAddress"));
        CustomerZip.setCellValueFactory(new PropertyValueFactory<>("cusPostal"));
        CustomerPhoneNum.setCellValueFactory(new PropertyValueFactory<>("cusPhoneNum"));



        try {
            customersTableList = CustomerDaoImpl.getAllCustomers();
            CustomerTable.setItems(customersTableList);
            CountryCombo.setItems(CountryDaoImpl.getAllCountries());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void clearAllFields () {
        CusIDLabel.setText("");
        FirstNameText.clear();
        LastNameText.clear();
        AddressText.clear();
        PostalText.clear();
        PhoneNumberText.clear();
        CountryCombo.getSelectionModel().clearSelection();
        StateCombo.getSelectionModel().clearSelection();
    }

    public void onAddClick(ActionEvent actionEvent) {
        Alert alert;
        try {
            if(FirstNameText.getText().isEmpty() || LastNameText.getText().isEmpty() || AddressText.getText().isEmpty() || PostalText.getText().isEmpty()
                    || PhoneNumberText.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please make sure all fields are entered.");
                alert.show();
            }
            else if(CountryCombo.getSelectionModel().isEmpty() || StateCombo.getSelectionModel().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select the Country and Region.");
                alert.show();
            }
            else {
                int cusID = CustomerDaoImpl.generateCustomerId();
                String cusName = FirstNameText.getText() + " " + LastNameText.getText();
                String cusAddress = AddressText.getText();
                String cusPostal = PostalText.getText();
                String cusPhoneNum = PhoneNumberText.getText();
                int cusDivision = FLDivisionDaoImpl.getFLDivision(String.valueOf(StateCombo.getValue())).getDivID();
                Timestamp createdTimeStamp = TimeZoneHelper.LocalToUTCTimestamp();
                String userName = loginController.loggedInUser.getUserName();

                Customer newCustomer = new Customer(cusID, cusName, cusAddress, cusPostal, cusPhoneNum, cusDivision);
                customersTableList.add(newCustomer);
                CustomerTable.refresh();

                JDBC.getConnection();
                String sqlStmt = "INSERT INTO customers VALUES("+cusID+",'"+cusName+"','"+cusAddress+"','"+cusPostal+"','"+cusPhoneNum+"','"
                        +createdTimeStamp+"','"+userName+"','"+createdTimeStamp+"','"+userName+"',"+cusDivision+")";

                Query.dataManipulateQuery(sqlStmt);

                //System.out.println("INSERT INTO customers VALUES("+cusID+",'"+cusName+"','"+cusAddress+"','"+cusPostal+"','"+cusPhoneNum+"','"+createdTimeStamp+"','"+"Alex Created"+"','"+createdTimeStamp+"','"+"Alex Updated"+"',"+cusDivision+")");
                clearAllFields();
            }

        }
        catch (Exception e) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setContentText("Please check your inputs and try again. Make sure all fields are filled.");
        }
    }

    public void onUpdateClick(ActionEvent actionEvent) {
        Alert alert;
        try {
            if (FirstNameText.getText().isEmpty() || LastNameText.getText().isEmpty() || AddressText.getText().isEmpty() || PostalText.getText().isEmpty()
                    || PhoneNumberText.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please make sure all fields are entered.");
                alert.show();
            } else if (CountryCombo.getSelectionModel().isEmpty() || StateCombo.getSelectionModel().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select the Country and Region.");
                alert.show();
            } else {
                Customer updateCustomer = CustomerTable.getSelectionModel().getSelectedItem();

                String cusID = CusIDLabel.getText();
                String cusName = FirstNameText.getText() + " " + LastNameText.getText();
                String cusAddress = AddressText.getText();
                String cusPostal = PostalText.getText();
                String cusPhoneNum = PhoneNumberText.getText();
                int cusDivision = FLDivisionDaoImpl.getFLDivision(String.valueOf(StateCombo.getValue())).getDivID();
                Timestamp createdTimeStamp = TimeZoneHelper.LocalToUTCTimestamp();
                String userName = loginController.loggedInUser.getUserName();

                updateCustomer.setCusFullName(cusName);
                updateCustomer.setCusAddress(cusAddress);
                updateCustomer.setCusPostal(cusPostal);
                updateCustomer.setCusPhoneNum(cusPhoneNum);
                updateCustomer.setCusDivID(cusDivision);

                CustomerTable.refresh();

                JDBC.getConnection();
                String sqlStmt = "UPDATE customers SET Customer_Name='"+ cusName + "', Address='" + cusAddress + "', Postal_Code='" + cusPostal + "', Phone='" + cusPhoneNum + "',Last_Update='"
                        + createdTimeStamp + "', Last_Updated_By='" + userName + "', Division_ID='" + cusDivision + "' WHERE Customer_ID="+cusID;

                Query.dataManipulateQuery(sqlStmt);
            }
        }
        catch (Exception e) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setContentText("Please check your inputs and try again. Make sure all fields are filled.");
        }
    }

    public void onDeleteClick(ActionEvent actionEvent) {
        JDBC.getConnection();
        try {
            String cusID = CusIDLabel.getText();
            String sqlStmt = "DELETE FROM customers WHERE Customer_ID = '"+cusID+"'";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete the selected item?");
            alert.setTitle("Customer");
            alert.setHeaderText("Delete");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {

                Query.dataManipulateQuery(sqlStmt);

                customersTableList = CustomerDaoImpl.getAllCustomers();
                CustomerTable.setItems(customersTableList);

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "You have deleted the customer.");
                alert2.show();
            }
        }
        catch (Exception e){}

    }

    public void onClearClick(ActionEvent actionEvent) {
        clearAllFields();
    }

    public void onAppointmentsClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/appointments.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
        stage.setScene(new Scene(root, 975, 500));
        stage.show();
    }

    public void onMouseClickedOnTable(MouseEvent mouseEvent) {
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

    public void onCountryComboAction(ActionEvent actionEvent) {
        try{
            int countryID = ((Country) CountryCombo.getSelectionModel().getSelectedItem()).getCountryID();
            StateCombo.setItems(FLDivisionDaoImpl.getAllCountryDivisions(countryID));
        }
        catch (Exception e) {
        }
    }
}
