package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FLDivision;

import java.sql.ResultSet;
import java.sql.SQLException;

/** This class gets a connection with the database and uses various methods to query the database.
 * This class queries the database for data on First-Level Divisions. */
public class FLDivisionDaoImpl {

    /** Used to query the database and return a First-Level Division based on the division ID.
     * This connects to the database, pulls data, and creates an FLDivision Object.
     * @param divID
     * @return FLDivision object */
    public static FLDivision getFLDivision(int divID) throws SQLException {
        FLDivision divisionResult = new FLDivision();

        JDBC.getConnection();
        String sqlStmt = "SELECT * FROM first_level_divisions WHERE Division_ID = '" + divID + "'";
        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        if (result.next()) {
            int divId = result.getInt("Division_ID");
            String divName = result.getString("Division");
            int countryId = result.getInt("Country_ID");
            divisionResult = new FLDivision(divId, divName, countryId);
            return divisionResult;
        }
        return divisionResult;
    }

    /** Used to query the database and return a First-Level Division based on the division name.
     * This connects to the database, pulls data, and creates an FLDivision Object.
     * @param divName
     * @return FLDivision object */
    public static FLDivision getFLDivision(String divName) throws SQLException {
        FLDivision divisionResult = new FLDivision();

        JDBC.getConnection();
        String sqlStmt = "SELECT * FROM first_level_divisions WHERE Division = '" + divName + "'";
        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        if (result.next()) {
            int divId = result.getInt("Division_ID");
            String divisionName = result.getString("Division");
            int countryId = result.getInt("Country_ID");
            divisionResult = new FLDivision(divId, divisionName, countryId);
            return divisionResult;
        }
        return divisionResult;
    }

    /** Used to query the database and return All First-Level Divisions in the database.
     * This connects to the database, pulls data, creates Appointment Objects, and puts them into an observable list.
     * @return List of all FLDivision Objects */
    public static ObservableList<FLDivision> getAllFLDivisions() throws SQLException {
        ObservableList<FLDivision> divisionsList = FXCollections.observableArrayList();

        JDBC.getConnection();
        String sqlStmt = "SELECT * FROM first_level_divisions";
        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        while (result.next()) {
            int divId = result.getInt("Division_ID");
            String divName = result.getString("Division");
            int countryId = result.getInt("Country_ID");
            FLDivision divisionResult = new FLDivision(divId, divName, countryId);
            divisionsList.add(divisionResult);
        }
        return divisionsList;
    }

    /** Used to query the database and return a all First-Level Divisions based on country ID.
     * This connects to the database, pulls data, and creates a FLDivision Object, and puts them into an observable list.
     * @param countryID
     * @return List of all FLDivision objects with the input Country ID */
    public static ObservableList<FLDivision> getAllCountryDivisions(int countryID) throws SQLException {
        ObservableList<FLDivision> divisionsList = FXCollections.observableArrayList();

        JDBC.getConnection();
        String sqlStmt = "SELECT * FROM first_level_divisions where Country_ID = '" + countryID + "'";
        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        while (result.next()) {
            int divId = result.getInt("Division_ID");
            String divName = result.getString("Division");
            int countryId = result.getInt("Country_ID");
            FLDivision divisionResult = new FLDivision(divId, divName, countryId);
            divisionsList.add(divisionResult);
        }
        return divisionsList;
    }
}
