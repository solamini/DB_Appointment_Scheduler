package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    public Label UserLocation;
    public Label UserLanguage;

    private String userLocation = String.valueOf(Locale.getDefault().getCountry());
    private String userLanguage = String.valueOf(Locale.getDefault().getLanguage());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserLocation.setText("Location: " + userLocation);
        UserLanguage.setText("Language: " + userLanguage);
    }

    public void onLoginPress(ActionEvent actionEvent) {

    }
}
