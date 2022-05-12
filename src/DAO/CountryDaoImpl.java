package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.ResultSet;
import java.sql.SQLException;

/** This class gets a connection with the database and uses various methods to query the database.
 * This class queries the database for data on Countries. */
public class CountryDaoImpl {

    /** Used to query the database and return a Country based on the country ID.
     * This connects to the database, pulls data, and creates a Country Object.
     * @param countryID
     * @return Country object */
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

    /** Used to query the database and return All Countries in the database.
     * This connects to the database, pulls data, creates Country Objects, and puts them into an observable list.
     * @return List of all Country Objects */
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
