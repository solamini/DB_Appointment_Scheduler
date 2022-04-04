package controller;

import DAO.Query;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import DAO.UserDaoImpl;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ResourceBundle rb = ResourceBundle.getBundle("main/Language", Locale.getDefault());

        Username.setText(rb.getString("Username"));
        Password.setText(rb.getString("Password"));
        UserLocation.setText(rb.getString("Location") + ": " + userLocation);
        UserLanguage.setText(rb.getString("Language") + ": " + userLanguage);


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
                alert.setContentText("You have logged in!");
            }
            else {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Username or Password is incorrect!");
            }
            alert.show();

        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("it did NOT work");
        }



    }
}
