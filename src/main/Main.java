package main;

import DAO.CustomerDaoImpl;
import DAO.JDBC;
import DAO.UserDaoImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;


import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

import static DAO.UserDaoImpl.getUser;


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



        JDBC.closeConnection();

    }
}
