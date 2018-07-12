package l.hu.v1wac.firstapp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import l.hu.v1wac.firstapp.persistence.CountryPostgresDaoImpl;

public class WorldService {

	private CountryPostgresDaoImpl dao = new CountryPostgresDaoImpl();
	
	public WorldService() {
	}
	
	public List<Country> getAllCountries() {
		return dao.findAll();
	}
	
	public List<Country> get10LargestPopulations() {
		return dao.find10LargestPopulations();
	}

	public List<Country> get10LargestSurfaces() {
		return dao.find10LargestSurfaces();
	}
	
	public Country getCountryByCode(String code) {
		return dao.findByCode(code);
	}

	public boolean deleteCountry(String code) {
		return dao.delete(getCountryByCode(code));
	}
	
	public Country addCountry(String code, String land, String hoofdstad, String regio, double oppervlakte, int inwoners) {
		System.out.println("addcountry");
		for (Country c :  getAllCountries()) {
			if (!(c.getCode().equals(code) && c.getCapital().equals(hoofdstad))) {
				System.out.println("save country");
				Country country = new Country(code, land, hoofdstad, regio, oppervlakte, inwoners);
				if (!dao.save(country)) {
					return null;
				} else {
					dao.save(country);
					return country;
				}
			}
		}
		return null;
	}

	public Country updateCountry(String code, String land, String hoofdstad, String regio, double oppervlakte, int inwoners) {
		System.out.println(code);
		for (Country c :  getAllCountries()) {
			System.out.println(c.getCode());
			if (c.getCode().equals(code)) {
				c.setName(land);
				c.setCapital(hoofdstad);
				c.setRegion(regio);
				c.setSurface(oppervlakte);
				c.setPopulation(inwoners);
				dao.update(c);
				return c;
			}
		}
		return null;
	}
}
