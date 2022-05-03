package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.FLDivision;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FLDivisionDaoImpl {
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
