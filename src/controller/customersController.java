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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/** Interface declared to use a lambda expression in the following code. */
interface GetCustomerID {
    int getUnusedCusID() throws SQLException;
}

/** This is a controller for the customers.fxml file. */
public class customersController implements Initializable {

    public TableColumn CustomerID;
    public TableColumn CustomerName;
    public TableColumn CustomerAddress;
    public TableColumn CustomerZip;
    public TableColumn CustomerPhoneNum;
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

    ObservableList<Customer> customersTableList = FXCollections.observableArrayList();

    /** Initializes and sets the Customer table to fill with all customers.
     * @param url
     * @param resourceBundle */
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

    /** Clears all fields in the customer screen. */
    private void clearAllFields () {
        CusIDLabel.setText("");
        FirstNameText.clear();
        LastNameText.clear();
        AddressText.clear();
        PostalText.clear();
        PhoneNumberText.clear();
        CountryCombo.getSelectionModel().clearSelection();
        StateCombo.getSelectionModel().clearSelection();
        StateCombo.setItems(null);
    }

    /** Takes all inputs and converts them to a Customer in the database.
     * Takes all of the user input and checks for any blanks. If no blanks, and all info is valid, it adds the customer into the database.
     * @param actionEvent */
    public void onAddClick(ActionEvent actionEvent) {
        //Used Lambda to create unused customer ID when adding the customer to the database. Goes through all the current customer ID's and returns the next available number.
        GetCustomerID number = () -> {
            int newId = 0;
            ObservableList<Customer> allCustomers = DAO.CustomerDaoImpl.getAllCustomers();
            ArrayList<Integer> idNumbers = new ArrayList<Integer>();
            for (Customer c : allCustomers) {
                idNumbers.add(c.getCusID()); }

            for (int i = 1; i < (allCustomers.size() * 2) ; i++) {
                if(!idNumbers.contains(i)) {
                    newId = i;
                    break; } }

            return newId;
        };

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
                int cusID = number.getUnusedCusID();
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

                clearAllFields();
            }

        }
        catch (Exception e) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setContentText("Please check your inputs and try again. Make sure all fields are filled.");
        }
    }

    /** Takes all inputs and updates them to that Customer in the database.
     * Takes all of the user input and checks for any blanks. If no blanks, and all info is valid, it updates the customer in the database.
     * @param actionEvent */
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

    /** Takes selected customer and deletes it.
     * If a customer has no appointments and after the user confirms the prompt that asks if they wish to delete, it deletes the customer.
     * @param actionEvent */
    public void onDeleteClick(ActionEvent actionEvent) {
        JDBC.getConnection();
        try {
            String cusID = CusIDLabel.getText();
            if (!cusID.isEmpty()) {
                String sqlStmt = "DELETE FROM customers WHERE Customer_ID = '" + cusID + "'";
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected item?");
                alert.setTitle("Customer");
                alert.setHeaderText("Delete");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    int cusIDAppointments = DAO.AppointmentDaoImpl.getAllCustomerAppointments(currentCustomer.getCusID()).size();
                    if(cusIDAppointments == 0) {
                        Query.dataManipulateQuery(sqlStmt);

                        customersTableList = CustomerDaoImpl.getAllCustomers();
                        CustomerTable.setItems(customersTableList);

                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "You have deleted the customer.");
                        alert2.show();
                    } else {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR, "You must first delete any associated appointments with this customer.");
                        alert2.show();
                    }
                }
            }
            if(cusID.isEmpty()){
                Alert alert3 = new Alert(Alert.AlertType.ERROR, "Please select the customer to delete.");
                alert3.show();
            }
        }
        catch (Exception e){}

    }

    /** Clears all fields if user clicks on the 'Clear' button. */
    public void onClearClick(ActionEvent actionEvent) {
        clearAllFields();
    }

    /** When the Appointments button is clicked, takes user to the appointments screen.
     * @param actionEvent */
    public void onAppointmentsClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/appointments.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
        stage.setScene(new Scene(root, 975, 500));
        stage.show();
    }

    /** Sets all inputs to the values of the customer that user clicks on in the table.
     * @param mouseEvent */
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

    /** Sets the first-level division when user selects a country from the Country Combo box.
     * @param actionEvent */
    public void onCountryComboAction(ActionEvent actionEvent) {
        try{
            int countryID = ((Country) CountryCombo.getSelectionModel().getSelectedItem()).getCountryID();
            StateCombo.setItems(FLDivisionDaoImpl.getAllCountryDivisions(countryID));
        }
        catch (Exception e) {
        }
    }
}
