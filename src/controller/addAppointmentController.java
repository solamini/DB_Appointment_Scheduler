package controller;

import DAO.JDBC;
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
import java.time.LocalTime;
import java.util.ResourceBundle;

/** This is a controller for the addAppointment.fxml file.
 * @author Aleksandr Ogilba */
public class addAppointmentController implements Initializable {

    /** Textfield that shows title of Appointment */
    public TextField TitleTextField;

    /** Textfield that shows description of Appointment */
    public TextField DescriptionTextField;

    /** Textfield that shows location of Appointment */
    public TextField LocationTextField;

    /** Combobox that allows user to select Contact */
    public ComboBox ContactCombo;

    /** Textfield that shows type of Appointment */
    public TextField TypeTextField;

    /** Combobox that allows user to select Customer */
    public ComboBox CustomerCombo;

    /** Combobox that allows user to select User */
    public ComboBox UserCombo;

    /** Textfield that shows the ID of Appointment */
    public Label AppIDLabel;

    /** Textfield that shows the Start Date Minutes of Appointment */
    public TextField StartDateMinuteText;

    /** Textfield that shows the Start Date Hours of Appointment */
    public TextField StartDateHourText;

    /** Textfield that shows the End Date Minutes of Appointment */
    public TextField EndDateMinuteText;

    /** Textfield that shows the End Date Hours of Appointment */
    public TextField EndDateHourText;

    /** StartDatePicker that allows user to select date */
    public DatePicker StartDatePicker;


    /** Initializes and sets the AppID label and the contact,customer, and user Comboboxes with items.
     * @param url
     * @param resourceBundle */
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

    /** Takes all inputs and converts them to an Appointment in the database.
     * Takes all of the user input and checks for any blanks. If no blanks, it checks if the appointment time is within business hours,
     * and that it doesnt overlap with other appointments. If all info is valid, it adds the appointment into the database.
     * @param actionEvent Clicking button */
    public void onAppointmentAdd(ActionEvent actionEvent) {
        Boolean goodAppointmentTime = true;
        Alert alert;
        try {
            int startHour = Integer.parseInt(StartDateHourText.getText());
            int startMinute = Integer.parseInt(StartDateMinuteText.getText());
            int endHour = Integer.parseInt(EndDateHourText.getText());
            int endMinute = Integer.parseInt(EndDateMinuteText.getText());
            if(startHour<0 || startHour>23 || startMinute<0 || startMinute>59 || endHour<0 || endHour>23 || endMinute<0 || endMinute>59) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please Enter a valid appointment time (0-23 Hours & 0-59 Minutes)");
                alert.show();
            } else if(endHour<startHour || endHour==startHour && endMinute<startMinute){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Your appointment start time must be before the end time.");
                alert.show();
                goodAppointmentTime = false;
            }
        } catch (Exception e){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Enter a valid appointment time (0-23 Hours & 0-59 Minutes)");
            alert.show();
        }
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
                Timestamp ESTStartTimeStamp = main.TimeZoneHelper.LocalToESTTimestamp(Timestamp.valueOf(startLDT));
                Timestamp ESTEndTimeStamp = main.TimeZoneHelper.LocalToESTTimestamp(Timestamp.valueOf(endLDT));
                Timestamp currentTimeStamp = TimeZoneHelper.LocalToUTCTimestamp();
                User appUser = (User) UserCombo.getSelectionModel().getSelectedItem();
                String createdByUser = appUser.getUserName();
                Customer appCustomer = (Customer) CustomerCombo.getSelectionModel().getSelectedItem();
                String appCustomerID = String.valueOf(appCustomer.getCusID());
                String appUserID = String.valueOf(appUser.getUserId());
                String contactID = String.valueOf(appContact.getContactID());

                LocalTime openLocalTime = LocalTime.of(8,0);
                LocalTime closeLocalTime = LocalTime.of(22, 0);
                LocalTime ESTAppStartTime = ESTStartTimeStamp.toLocalDateTime().toLocalTime();
                LocalTime ESTAppEndTime = ESTEndTimeStamp.toLocalDateTime().toLocalTime();

                if(ESTAppStartTime.isBefore(openLocalTime) || ESTAppEndTime.isAfter(closeLocalTime)){
                    goodAppointmentTime = false;
                    alert = new Alert(Alert.AlertType.WARNING, "Please choose an appointment time that is within business hours.");
                    alert.show();
                } else { //if the appointment time is within business hours, it continues to check for overlaps.

                    for (Appointment a : DAO.AppointmentDaoImpl.getAllCustomerAppointmentsEST(appCustomer.getCusID())) {
                        if ((ESTStartTimeStamp.after(a.getAppStartDate()) && ESTStartTimeStamp.before(a.getAppEndDate()))
                                || (ESTEndTimeStamp.after(a.getAppStartDate()) && ESTEndTimeStamp.before(a.getAppEndDate()))
                                || (ESTStartTimeStamp.before(a.getAppStartDate()) && ESTEndTimeStamp.after(a.getAppEndDate()))
                                || ESTStartTimeStamp.equals(a.getAppStartDate()) || ESTEndTimeStamp.equals(a.getAppEndDate())) {
                            goodAppointmentTime = false;
                            alert = new Alert(Alert.AlertType.WARNING, "Your appointment time overlaps with another appointment!");
                            alert.show();
                            break; //once an overlap is found, exit the for loop.
                        }
                    }
                }

                if (goodAppointmentTime) {
                    JDBC.getConnection();
                    String sqlStmt = "INSERT INTO appointments VALUES(" + appID + ",'" + appTitle + "','" + appDescription + "','" + appLocation + "','"
                            + appType + "','" + startTimeStamp + "','" + endTimeStamp + "','" + currentTimeStamp + "','" + createdByUser + "','" + currentTimeStamp + "','"
                            + createdByUser + "'," + appCustomerID + "," + appUserID + "," + contactID + ")";

                    Query.dataManipulateQuery(sqlStmt);

                    Parent root = FXMLLoader.load(getClass().getResource("/view/appointments.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    stage.setTitle("Appointments");
                    stage.setScene(new Scene(root, 975, 500));
                    stage.show();
                }
            }
        }
        catch (Exception e) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setContentText("Please check your inputs and try again. Make sure all fields are filled.");
        }
    }

    /** When the Cancel button is clicked, takes user back to the appointments screen.
     * @param actionEvent Clicking button */
    public void onAppointmentAddCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/appointments.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
        stage.setScene(new Scene(root, 975, 500));
        stage.show();
    }
}
