package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl {

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



    public static Boolean loginAttempt(String inputUsername, String inputPassword) throws SQLException {
        User newUser = getUser(inputUsername);
        String userPassword = newUser.getPassword();
        //System.out.println("This is input password: " + inputPassword);
        //System.out.println("This is database password: " + userPassword);
        if(userPassword == null) {
            userPassword = "";
        }
        if (userPassword.equals(inputPassword) & !userPassword.equals("")) {
            return true;
        }
        //else { return false;}
        return false;
    }



}
