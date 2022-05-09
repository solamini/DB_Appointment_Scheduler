package main;

import DAO.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.Instant;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //ResourceBundle rb = ResourceBundle.getBundle("main/Language", Locale.getDefault());

        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        primaryStage.setTitle("Login Screen");
        primaryStage.setScene(new Scene(root, 450, 300));
        primaryStage.show();

        JDBC.openConnection();

    }

    public static void main(String[] args) throws SQLException {
	// write your code here
        launch(args);

        //System.out.println(TimeZoneHelper.LocalToUTCTimestamp());
        //System.out.println(TimeZoneHelper.UTCToLocalTimestamp(TimeZoneHelper.LocalToUTCTimestamp()));


        JDBC.closeConnection();

    }
}
