package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Query {
    private static String query;
    private static PreparedStatement ps;
    public static ResultSet result;

    public static void makeQuery(String q) {
        query = q;
        try {
            ps = JDBC.getConnection().prepareStatement(q);
            result = ps.executeQuery();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }

    public static void dataManipulateQuery(String q) {
        query = q;
        try {
            ps = JDBC.getConnection().prepareStatement(q);
            ps.execute();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }
    public static ResultSet getResult() {
        return result;
    }

}
