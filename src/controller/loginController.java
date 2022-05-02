package controller;

import DAO.Query;
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
import DAO.JDBC;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.ResourceBundle;
import java.sql.Connection;



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

    @FXML
    void onLoginPress(ActionEvent event) throws SQLException {
        //System.out.println("its tracking obviously");

        try {
            Boolean login = UserDaoImpl.loginAttempt(UsernameInput.getText(), PasswordInput.getText());
            System.out.println(login);
            Alert alert;
            if (login) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(LoggedIn);
                Parent root = FXMLLoader.load(getClass().getResource("/view/customers.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setTitle("Customers");
                stage.setScene(new Scene(root, 785, 560));
                stage.show();
            }
            else {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(NotLoggedIn);
            }
            alert.show();

        }
        catch(Exception e) {
            e.printStackTrace();
        }



    }
}
