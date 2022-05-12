package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import DAO.UserDaoImpl;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

/** Interface declared to use a lambda expression in the following code. */
interface PrintActivity{
    void printActivityLog(String user, String timestamp, String failOrSuccess) throws IOException;
}
    /** This is a controller for the login.fxml file. */
    public class loginController implements Initializable {

    public Label UserLocation;
    public Label UserLanguage;
    public Label Username;
    public Label Password;
    public PasswordField PasswordInput;
    public TextField UsernameInput;


    private String userLocation = String.valueOf(Locale.getDefault().getCountry());
    private String userLanguage = String.valueOf(Locale.getDefault().getLanguage());

    private String LoggedIn;
    private String NotLoggedIn;

    public static User loggedInUser;

    /** Initializes and sets resources bundles to the correct language based on the system.
     * @param url
     * @param resourceBundle */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ResourceBundle rb = ResourceBundle.getBundle("main/language", Locale.getDefault());

        Username.setText(rb.getString("Username"));
        Password.setText(rb.getString("Password"));
        UserLocation.setText(rb.getString("Location") + ": " + userLocation);
        UserLanguage.setText(rb.getString("Language") + ": " + userLanguage);

        LoggedIn = rb.getString("LoggedIn");
        NotLoggedIn = rb.getString("NotLoggedIn");

    }

    /** Uses the inputs to verify the login information entered.
     * Attempts to verify the information based on Users in the database. If successful login, opens the customers screen.
     * Also logs the login attemps in a seperate text file.
     * @param event */
    @FXML
    void onLoginPress(ActionEvent event) throws SQLException, IOException {
        Timestamp currentESTTimeStamp = main.TimeZoneHelper.LocalToESTTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        Timestamp fifteenMinutesLaterTS = main.TimeZoneHelper.TimeStampPlus15Min(currentESTTimeStamp);
        AtomicReference<Boolean> noAppointments = new AtomicReference<>(true);

        //Used lambda to easily write to the activity log text file located in root folder.
        PrintActivity printThisActivity = (user, timestamp, failOrSuccess) ->{
            String filename = "login_activity.txt";
            FileWriter fwriter = new FileWriter(filename, true);
            PrintWriter outputFile = new PrintWriter(fwriter);
            outputFile.println(user + " attempted to log-in at "+ timestamp + " UTC. The attempt "+failOrSuccess);
            outputFile.close();
             };


        try {
            Boolean login = UserDaoImpl.loginAttempt(UsernameInput.getText(), PasswordInput.getText());
            Alert alert;
            if (login) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(LoggedIn);
                Parent root = FXMLLoader.load(getClass().getResource("/view/customers.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setTitle("Customers");
                stage.setScene(new Scene(root, 785, 570));
                stage.show();
                loggedInUser = UserDaoImpl.getUser(UsernameInput.getText());
                printThisActivity.printActivityLog(UsernameInput.getText(),main.TimeZoneHelper.LocalToUTCTimestamp().toString(),"succeeded.");

                //Used Lambda expression to iterate through all appointments in Eastern time to check for appointments within 15 minutes.
                DAO.AppointmentDaoImpl.getAllAppointmentsEST().forEach( (a) -> {
                    if(a.getAppStartDate().before(fifteenMinutesLaterTS)&&a.getAppStartDate().after(currentESTTimeStamp)){
                        Alert appAlert = new Alert(Alert.AlertType.INFORMATION, "Appointment with the ID: "+a.getAppID()+" will start within 15 minutes. It is scheduled for: "+a.getAppStartDate());
                        appAlert.show();
                        noAppointments.set(false);
                    }
                });
                if(noAppointments.get()){
                    Alert noAppAlert = new Alert(Alert.AlertType.INFORMATION, "You have no upcoming appointments within 15 minutes.");
                    noAppAlert.show();
                };
            }
            else {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(NotLoggedIn);
                printThisActivity.printActivityLog(UsernameInput.getText(),main.TimeZoneHelper.LocalToUTCTimestamp().toString(),"failed.");
            }
            alert.show();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
