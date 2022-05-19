package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/** This class gets a connection with the database and uses various methods to query the database.
 * This class queries the database for data on appointments to create the Report object.
 * @author Aleksandr Ogilba */
public class ReportDaoImpl {

    /** Used to query the database and return information about appointment month, type and amount of appointments.
     * This connects to the database, pulls data, and creates a Report Object.
     * @return Report object list */
    public static ObservableList<Report> getMonthTypeReport() throws SQLException {
        ObservableList<Report> reportList = FXCollections.observableArrayList();

        JDBC.getConnection();
        String sqlStmt = "SELECT MonthName(Start) AS Month , Type , COUNT(*) AS Amount FROM appointments GROUP BY MonthName(Start), Type";
        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        while (result.next()) {
            String repMonth = result.getString("Month");
            String repType = result.getString("Type");
            String repAmount = result.getString("Amount");

            Report newReport = new Report(repMonth, repType, repAmount);
            reportList.add(newReport);
        }
        return reportList;
    }
}
