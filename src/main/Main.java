package main;

import DAO.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //ResourceBundle rb = ResourceBundle.getBundle("main/Language", Locale.getDefault());

        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 450, 300));
        primaryStage.show();


    }

    public static void main(String[] args)  {
	// write your code here
        launch(args);
        JDBC.openConnection();


        JDBC.closeConnection();

    }
}
