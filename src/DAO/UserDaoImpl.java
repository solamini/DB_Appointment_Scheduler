package DAO;

import model.User;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl {

    public static User getUser(String userName) throws SQLException {
        User userResult;

        JDBC.getConnection();
        String sqlStmt = "SELECT * FROM users WHERE User_Name = '" + userName+ "'";
        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        while(result.next()) {
            int userId = result.getInt("User_ID");
            String userNameA = result.getString("User_Name");
            String password = result.getString("Password");
            userResult = new User(userId, userName, password);
            //System.out.println(userResult.getUserName() + " " + userResult.getPassword());
            return userResult;
        }
        JDBC.closeConnection();
        return null;
    }
}
