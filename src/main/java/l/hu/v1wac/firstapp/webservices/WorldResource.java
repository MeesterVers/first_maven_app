package l.hu.v1wac.firstapp.webservices;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import l.hu.v1wac.firstapp.model.Country;
import l.hu.v1wac.firstapp.model.ServiceProvider;
import l.hu.v1wac.firstapp.model.WorldService;

@Path("/countries")
public class WorldResource {

  @GET
  @Produces("application/json")
  public String getAllCountries() {
    WorldService service = ServiceProvider.getWorldService();
    JsonArrayBuilder jab = Json.createArrayBuilder();

    for (Country c : service.getAllCountries()) {
      JsonObjectBuilder job = Json.createObjectBuilder();
      job.add("id", c.getName());
      jab.add(job);
    }

    JsonArray array = jab.build();
    return array.toString();
  }
  @GET
  @Path("/country")
  @Produces("application/json")
  public String getOrderInfo(@QueryParam("code") String code) {
    WorldService service = ServiceProvider.getWorldService();
    Country country = service.getCountryByCode(code);
 
    JsonObjectBuilder job = Json.createObjectBuilder();
    job.add("naam", country.getName());

    return job.build().toString();
  }

  
  @GET
  @Path("/largestsurfaces")
  @Produces("application/json")
  public String get10LargestLargestSurfaces() {
	    WorldService service = ServiceProvider.getWorldService();
	    JsonArrayBuilder jab = Json.createArrayBuilder();

	    for (Country c : service.get10LargestSurfaces()) {
	      JsonObjectBuilder job = Json.createObjectBuilder();
	      job.add("id", c.getName());
	      jab.add(job);
	    }

	    JsonArray array = jab.build();
	    return array.toString();
	  }

  @GET
  @Path("/largestpoupulations")
  @Produces("application/json")
  public String get10LargestPopulations() {
	    WorldService service = ServiceProvider.getWorldService();
	    JsonArrayBuilder jab = Json.createArrayBuilder();

	    for (Country c : service.get10LargestPopulations()) {
	      JsonObjectBuilder job = Json.createObjectBuilder();
	      job.add("id", c.getName());
	      jab.add(job);
	    }

	    JsonArray array = jab.build();
	    return array.toString();
	  }
}