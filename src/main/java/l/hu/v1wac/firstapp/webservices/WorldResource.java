package l.hu.v1wac.firstapp.webservices;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import l.hu.v1wac.firstapp.model.Country;
import l.hu.v1wac.firstapp.model.ServiceProvider;
import l.hu.v1wac.firstapp.model.WorldService;

@Path("/countries")
public class WorldResource {

	@DELETE
	@Path("{code}")
	@Produces("application/json")
	@RolesAllowed("user")
	public Response deleteCountry(@PathParam("code") String code) {
		if (!ServiceProvider.getWorldService().deleteCountry(code)) {
			return Response.status(404).build();
		}
		return Response.ok().build();
	}
		
	@POST
	@Produces("application/json")
	@RolesAllowed("user")
	public Response addCountry(
			@FormParam("code2") String code, 
			@FormParam("name2") String name,
			@FormParam("capital2") String capital,
			@FormParam("region2") String region,
			@FormParam("surface2") double surface,
			@FormParam("population2") int population) {
		Country country = ServiceProvider.getWorldService().addCountry(code, name, capital, region, surface, population);
		if (country == null) {
			Map<String, String> messages = new HashMap<>();
			messages.put("error", "Country does not exist!");
			return Response.status(409).entity(messages).build();
		}
		return Response.ok(country).build();
	}

	@PUT
	@Path("{code}")
	@Produces("application/json")
	@RolesAllowed("user")
	public Response updateCountry(
			@PathParam("code") String code, 
			@FormParam("name") String name,
			@FormParam("capital") String capital,
			@FormParam("region") String region,
			@FormParam("surface") double surface,
			@FormParam("population") int population) {
		Country country = ServiceProvider.getWorldService().updateCountry(code, name, capital, region, surface, population);
		if (country == null) {
			Map<String, String> messages = new HashMap<>();
			messages.put("error", "Country does not exist!");
			return Response.status(409).entity(messages).build();
		}
		return Response.ok(country).build();
	}

	@GET
	  @Produces("application/json")
	  public String getCountries() {
		  WorldService service = ServiceProvider.getWorldService();
		    JsonArrayBuilder jab = Json.createArrayBuilder();
		    for (Country country : service.getAllCountries()) {
		      JsonObjectBuilder job = Json.createObjectBuilder();
				job.add("code", country.getCode());
				job.add("iso3", country.getIso3());
				job.add("name", country.getName());
				job.add("capital", country.getCapital());
				job.add("continent", country.getContinent());
				job.add("region", country.getRegion());
				job.add("surface", country.getSurface());
				job.add("population", country.getPopulation());
				job.add("government", country.getGovernment());
				job.add("latitude", country.getLatitude());
				job.add("longitude", country.getLongitude());
		      jab.add(job);
		    }
		  return jab.build().toString();
	  }

	@GET
	@Path("{code}")
	@Produces("application/json")
	public String getCountryInfo(@PathParam("code") String code) {
		WorldService service = ServiceProvider.getWorldService();
		Country country = service.getCountryByCode(code);
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("code", country.getCode());
		job.add("iso3", country.getIso3());
		job.add("name", country.getName());
		job.add("capital", country.getCapital());
		job.add("continent", country.getContinent());
		job.add("region", country.getRegion());
		job.add("surface", country.getSurface());
		job.add("population", country.getPopulation());
		job.add("government", country.getGovernment());
		job.add("latitude", country.getLatitude());
		job.add("longitude", country.getLongitude());
		return job.build().toString();
	}
	
	@GET
	@Path("largestsurfaces")
	@Produces("application/json")
	public String getCountriesLargestSurfaces() {
		  WorldService service = ServiceProvider.getWorldService();
		    JsonArrayBuilder jab = Json.createArrayBuilder();
		    for (Country country : service.get10LargestSurfaces()) {
		      JsonObjectBuilder job = Json.createObjectBuilder();
				job.add("code", country.getCode());
				job.add("iso3", country.getIso3());
				job.add("name", country.getName());
				job.add("capital", country.getCapital());
				job.add("continent", country.getContinent());
				job.add("region", country.getRegion());
				job.add("surface", country.getSurface());
				job.add("population", country.getPopulation());
				job.add("government", country.getGovernment());
				job.add("latitude", country.getLatitude());
				job.add("longitude", country.getLongitude());
		      jab.add(job);
		    }
		return jab.build().toString();
	}
	
	@GET
	@Path("largestpopulations")
	@Produces("application/json")
	public String getCountriesLargestLargestPopulations() {
		  WorldService service = ServiceProvider.getWorldService();
		    JsonArrayBuilder jab = Json.createArrayBuilder();
		    for (Country country : service.get10LargestPopulations()) {
		      JsonObjectBuilder job = Json.createObjectBuilder();
				job.add("code", country.getCode());
				job.add("iso3", country.getIso3());
				job.add("name", country.getName());
				job.add("capital", country.getCapital());
				job.add("continent", country.getContinent());
				job.add("region", country.getRegion());
				job.add("surface", country.getSurface());
				job.add("population", country.getPopulation());
				job.add("government", country.getGovernment());
				job.add("latitude", country.getLatitude());
				job.add("longitude", country.getLongitude());
		      jab.add(job);
		    }
		return jab.build().toString();
	}

}
