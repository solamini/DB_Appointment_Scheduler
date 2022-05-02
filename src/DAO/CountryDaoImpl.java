package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDaoImpl {
    public static Country getCountry(int countryID) throws SQLException {
        Country countryResult = new Country();

        JDBC.getConnection();
        String sqlStmt = "SELECT * FROM countries WHERE Country_ID = '" + countryID + "'";
        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        if (result.next()) {
            int countryId = result.getInt("Country_ID");
            String countryName = result.getString("Country");
            countryResult = new Country(countryId, countryName);
            return countryResult;
        }
        return countryResult;
    }

    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> countriesList = FXCollections.observableArrayList();

        JDBC.getConnection();
        String sqlStmt = "SELECT * FROM countries";
        Query.makeQuery(sqlStmt);
        ResultSet result = Query.getResult();
        while (result.next()) {
            int countryId = result.getInt("Country_ID");
            String countryName = result.getString("Country");
            Country countryResult = new Country(countryId, countryName);
            countriesList.add(countryResult);
        }
        return countriesList;
    }


}
