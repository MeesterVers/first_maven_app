package l.hu.v1wac.firstapp.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import l.hu.v1wac.firstapp.model.Country;

public class CountryPostgresDaoImpl extends PostgresBaseDao implements CountryDao {

	public CountryPostgresDaoImpl() {
		getConnection();
	}

    @Override
    public boolean save(Country country) {
    	System.out.println("save");
    	boolean saved = false;
        try (Connection conn = super.getConnection()){
            String query = "INSERT INTO country(name, code, capital, governmentform, region, surfacearea, population) VALUES(?, ?, ?, ?, ?, ? ,?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, country.getName());
            statement.setString(2, country.getCode());
            statement.setString(3, country.getCapital());
            statement.setString(4, country.getGovernment());
            statement.setString(5, country.getRegion());
            statement.setDouble(6, country.getSurface());
            statement.setInt(7, country.getPopulation());
            saved = statement.executeUpdate() == 1;
            statement.close();
            return saved;
        } catch (SQLException se){
            se.printStackTrace();
        }
        return true;
    }

	@Override
	public List<Country> findAll() {
		ArrayList<Country> countries = new ArrayList<>();
		try (Connection conn = super.getConnection()){
			String query = "SELECT * FROM country";
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				Country country = new Country(result.getString("code"), result.getString("iso3"),
						result.getString("name"), result.getString("capital"), result.getString("continent"),
						result.getString("region"), result.getDouble("surfacearea"), result.getInt("population"),
						result.getString("governmentform"), result.getDouble("latitude"),
						result.getDouble("longitude"));
				countries.add(country);
			}
			result.close();
			statement.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return countries;
	}

	@Override
	public Country findByCode(String code) {
		Country country = null;
		try {
			String query = "SELECT * FROM country WHERE code = ?";
			PreparedStatement statement = super.getConnection().prepareStatement(query);
			statement.setString(1, code);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				country = new Country(result.getString("code"), result.getString("iso3"), result.getString("name"),
						result.getString("capital"), result.getString("continent"), result.getString("region"),
						result.getDouble("surfacearea"), result.getInt("population"),
						result.getString("governmentform"), result.getDouble("latitude"),
						result.getDouble("longitude"));
			}
			result.close();
			statement.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return country;
	}

	@Override
	public List<Country> find10LargestPopulations() {
		List<Country> list = findAll();
		Collections.sort(list, new Comparator<Country>() {
			@Override
			public int compare(Country arg0, Country arg1) {
				return arg1.getPopulation() - arg0.getPopulation();
			}
		});
		return list.subList(0, 10);
	}

	@Override
	public List<Country> find10LargestSurfaces() {
		List<Country> list = findAll();
		Collections.sort(list, new Comparator<Country>() {
			@Override
			public int compare(Country arg0, Country arg1) {
				return (int) (arg1.getSurface() - arg0.getSurface());
			}
		});
		return list.subList(0, 10);
	}

	@Override
	public boolean update(Country country){
		boolean updated = false;
		try {
			String query = "UPDATE country SET name = ?, capital = ?, region = ?, surfacearea = ?, population = ? WHERE code = ?";
			PreparedStatement statement = super.getConnection().prepareStatement(query);
			statement.setString(1, country.getName());
			statement.setString(2, country.getCapital());
			statement.setString(3, country.getRegion());
			statement.setDouble(4, country.getSurface());
			statement.setInt(5, country.getPopulation());
			statement.setString(6, country.getCode());
			updated = statement.executeUpdate() == 1;
			statement.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return updated;
	}

	@Override
	public boolean delete(Country country){
		try {
			String query = "DELETE FROM country WHERE code = ?";
			PreparedStatement statement = super.getConnection().prepareStatement(query);
			statement.setString(1, country.getCode());
			statement.executeUpdate();
			statement.close();
			return true;
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return false;
	}
	
    private boolean contains(Country country){
        for (Country c : findAll()){
            if (c.equals(country))
                return true;
        }
        return false;
    }
}
