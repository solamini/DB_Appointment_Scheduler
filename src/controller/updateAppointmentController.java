package controller;

import DAO.Query;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.TimeZoneHelper;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class updateAppointmentController implements Initializable {

    public TextField TitleTextField;
    public TextField DescriptionTextField;
    public TextField LocationTextField;
    public ComboBox ContactCombo;
    public TextField TypeTextField;
    public ComboBox CustomerCombo;
    public ComboBox UserCombo;
    public Label AppIDLabel;
    public TextField StartDateMinuteText;
    public TextField StartDateHourText;
    public TextField EndDateMinuteText;
    public TextField EndDateHourText;
    public DatePicker StartDatePicker;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Appointment currentAppointment = appointmentsController.getCurrentAppointment();

        AppIDLabel.setText(String.valueOf(currentAppointment.getAppID()));
        TitleTextField.setText(currentAppointment.getAppTitle());
        DescriptionTextField.setText(currentAppointment.getAppDescription());
        LocationTextField.setText(currentAppointment.getAppLocation());
        TypeTextField.setText(currentAppointment.getAppType());
        try {
            ContactCombo.setItems(DAO.ContactDaoImpl.getAllContacts());
            CustomerCombo.setItems(DAO.CustomerDaoImpl.getAllCustomers());
            UserCombo.setItems(DAO.UserDaoImpl.getAllUsers());
            for(int i =0; i<DAO.CustomerDaoImpl.getAllCustomers().size(); i++) {  //sets the customer combobox to the correct customer ID.
                if (currentAppointment.getAppCustomer().getCusID() == DAO.CustomerDaoImpl.getAllCustomers().get(i).getCusID()) {
                    CustomerCombo.getSelectionModel().clearAndSelect(i);
                }
            }
            for(int i =0; i<DAO.UserDaoImpl.getAllUsers().size(); i++) { //sets the user combobox to the correct user ID.
                if (currentAppointment.getAppUser().getUserId() == DAO.UserDaoImpl.getAllUsers().get(i).getUserId()) {
                    UserCombo.getSelectionModel().clearAndSelect(i);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContactCombo.getSelectionModel().clearAndSelect(currentAppointment.getAppContact().getContactID()-1);
        StartDateHourText.setText(main.TimeZoneHelper.HoursFromTimestamp(currentAppointment.getAppStartDate()));
        StartDateMinuteText.setText(main.TimeZoneHelper.MinutesFromTimestamp(currentAppointment.getAppStartDate()));
        EndDateHourText.setText(main.TimeZoneHelper.HoursFromTimestamp(currentAppointment.getAppEndDate()));
        EndDateMinuteText.setText(main.TimeZoneHelper.MinutesFromTimestamp(currentAppointment.getAppEndDate()));
        StartDatePicker.setValue(currentAppointment.getAppStartDate().toLocalDateTime().toLocalDate());

    }


    public void onSaveChangesUpdate(ActionEvent actionEvent) {
        Alert alert;
        try {
            if (TitleTextField.getText().isEmpty() || DescriptionTextField.getText().isEmpty() || LocationTextField.getText().isEmpty() || TypeTextField.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please make sure all fields are entered.");
                alert.show();
            } else if (ContactCombo.getSelectionModel().isEmpty() || CustomerCombo.getSelectionModel().isEmpty() || UserCombo.getSelectionModel().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select the Contact,Customer, and User.");
                alert.show();
            } else {
                int appID = Integer.valueOf(AppIDLabel.getText());
                String appTitle = TitleTextField.getText();
                String appDescription = DescriptionTextField.getText();
                String appLocation = LocationTextField.getText();
                Contact appContact = (Contact) ContactCombo.getSelectionModel().getSelectedItem();
                String appType = TypeTextField.getText();
                LocalDateTime startLDT = StartDatePicker.getValue().atTime(Integer.valueOf(StartDateHourText.getText()), Integer.valueOf(StartDateMinuteText.getText()));
                Timestamp startTimeStamp = main.TimeZoneHelper.LocalToUTCTimestamp(Timestamp.valueOf(startLDT));
                LocalDateTime endLDT = StartDatePicker.getValue().atTime(Integer.valueOf(EndDateHourText.getText()), Integer.valueOf(EndDateMinuteText.getText()));
                Timestamp endTimeStamp = main.TimeZoneHelper.LocalToUTCTimestamp(Timestamp.valueOf(endLDT));
                Timestamp currentTimeStamp = TimeZoneHelper.LocalToUTCTimestamp();
                User appUser = (User) UserCombo.getSelectionModel().getSelectedItem();
                String updatedByUser = appUser.getUserName();
                Customer appCustomer = (Customer) CustomerCombo.getSelectionModel().getSelectedItem();
                String appCustomerID = String.valueOf(appCustomer.getCusID());
                String appUserID = String.valueOf(appUser.getUserId());
                String contactID = String.valueOf(appContact.getContactID());


                String sqlStmt = "UPDATE appointments SET Title='"+ appTitle + "', Description='" + appDescription + "', Location='" + appLocation
                        + "', Type='" + appType + "',Start='" + startTimeStamp + "', End='" + endTimeStamp + "', Last_Update='" + currentTimeStamp
                        + "', Last_Updated_By='"+updatedByUser+"', Customer_ID="+appCustomerID+", User_ID="+appUserID+", Contact_ID="+contactID+" WHERE Appointment_ID="+appID;

                Query.dataManipulateQuery(sqlStmt);

                Parent root = FXMLLoader.load(getClass().getResource("/view/appointments.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Appointments");
                stage.setScene(new Scene(root, 975, 500));
                stage.show();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setContentText("Please check your inputs and try again. Make sure all fields are filled.");
        }
    }

    public void onAppointmentUpdateCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/appointments.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
        stage.setScene(new Scene(root, 975, 500));
        stage.show();
    }
}
