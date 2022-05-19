package main;

import DAO.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/** This class launches the application and takes it to the login screen.
 * @author Aleksandr Ogilba */
public class Main extends Application {

    /** This method launches the login screen.
     * @param primaryStage initial stage of login screen */
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        primaryStage.setTitle("Login Screen");
        primaryStage.setScene(new Scene(root, 450, 300));
        primaryStage.show();

        JDBC.openConnection();

    }
    /** This is the main method. */
    public static void main(String[] args) {
	// write your code here
        launch(args);



        JDBC.closeConnection();

    }
}
