package controller;

import DAO.CustomerDaoImpl;
import DAO.FLDivisionDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.TimeZoneHelper;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class addAppointmentController implements Initializable {


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

        try {
            AppIDLabel.setText(String.valueOf(DAO.AppointmentDaoImpl.generateAppointmentId()));
            ContactCombo.setItems(DAO.ContactDaoImpl.getAllContacts());
            CustomerCombo.setItems(DAO.CustomerDaoImpl.getAllCustomers());
            UserCombo.setItems(DAO.UserDaoImpl.getAllUsers());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void onAppointmentAdd(ActionEvent actionEvent) {
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
                Customer appCustomer = (Customer) CustomerCombo.getSelectionModel().getSelectedItem();
                User appUser = (User) UserCombo.getSelectionModel().getSelectedItem();
                Timestamp currentTimeStamp = TimeZoneHelper.LocalToUTCTimestamp();
                LocalDateTime startLDT = StartDatePicker.getValue().atTime(Integer.valueOf(StartDateHourText.getText()), Integer.valueOf(StartDateMinuteText.getText()));
                Timestamp startTimeStamp = Timestamp.valueOf(startLDT);
                LocalDateTime endLDT = StartDatePicker.getValue().atTime(Integer.valueOf(EndDateHourText.getText()), Integer.valueOf(EndDateMinuteText.getText()));
                Timestamp endTimeStamp = Timestamp.valueOf(endLDT);
                System.out.println(startTimeStamp +" "+endTimeStamp);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setContentText("Please check your inputs and try again. Make sure all fields are filled.");
        }
    }

    public void onAppointmentAddCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/appointments.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
        stage.setScene(new Scene(root, 975, 500));
        stage.show();
    }
}
