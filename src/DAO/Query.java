package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/** Used to query the database. */
public class Query {
    private static String query;
    private static PreparedStatement ps;
    public static ResultSet result;

    /** Takes a string and executes a query with that string.
     * @param q -String used to query the database */
    public static void makeQuery(String q) {
        query = q;
        try {
            ps = JDBC.getConnection().prepareStatement(q);
            result = ps.executeQuery();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }

    /** Takes a string and executes a command with that string.
     * @param q -String used to execute command in the database */
    public static void dataManipulateQuery(String q) {
        query = q;
        try {
            ps = JDBC.getConnection().prepareStatement(q);
            ps.execute();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }
    /** Returns the result set from a previous query of the database.
     * @return result set */
    public static ResultSet getResult() {
        return result;
    }

}
