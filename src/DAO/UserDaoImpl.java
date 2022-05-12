package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class gets a connection with the database and uses various methods to query the database.
 * This class queries the database for data on Users. */
public class UserDaoImpl {

    /** Used to query the database and return a User based on the username.
     * This connects to the database, pulls data, and creates an User Object.
     * @param userName
     * @return User object */
    public static User getUser(String userName) throws SQLException{
        User userResult = new User();

        JDBC.getConnection();
        String sqlStmt = "SELECT * FROM users WHERE User_Name = '" + userName + "'";
        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        if(result.next()) {
            int userId = result.getInt("User_ID");
            String userNameA = result.getString("User_Name");
            String password = result.getString("Password");
            userResult = new User(userId, userName, password);
            return userResult;
        }
        return userResult;
    }

    /** Used to query the database and return a User based on the user ID.
     * This connects to the database, pulls data, and creates an User Object.
     * @param userID
     * @return User object */
    public static User getUser(int userID) throws SQLException{
        User userResult = new User();

        JDBC.getConnection();
        String sqlStmt = "SELECT * FROM users WHERE User_ID = '" + userID + "'";
        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        if(result.next()) {
            int userId = result.getInt("User_ID");
            String userNameA = result.getString("User_Name");
            String password = result.getString("Password");
            userResult = new User(userId, userNameA, password);
            return userResult;
        }
        return userResult;
    }

    /** Used to query the database and return All Users in the database.
     * This connects to the database, pulls data, creates User Objects, and puts them into an observable list.
     * @return List of all User Objects */
    public static ObservableList<User> getAllUsers() throws SQLException{
        ObservableList<User> userList = FXCollections.observableArrayList();

        JDBC.getConnection();
        String sqlStmt = "SELECT * FROM users";
        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        while(result.next()) {
            int userId = result.getInt("User_ID");
            String userNameA = result.getString("User_Name");
            String password = result.getString("Password");
            User userResult = new User(userId, userNameA, password);
            userList.add(userResult);

        }
        return userList;
    }

    /** Checks to see if the login attempt was successful or not.
     * Takes in two strings and checks them against the users in the database to see if they match username and password.
     * @param inputUsername
     * @param inputPassword
     * @return true or false depending if the login attempt was successful. */
    public static Boolean loginAttempt(String inputUsername, String inputPassword) throws SQLException {
        User newUser = getUser(inputUsername);
        String userPassword = newUser.getPassword();

        if(userPassword == null) {
            userPassword = "";
        }
        if (userPassword.equals(inputPassword) & !userPassword.equals("")) {
            return true;
        }

        return false;
    }

}
