package utbm.lo54.projet.webservice;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import utbm.lo54.projet.core.IndentObjectMapperProvider;
import utbm.lo54.projet.model.Location;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/byLocation")
public class Locations {

	@GET 
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public String getLocations() {
		
		IndentObjectMapperProvider provider = new IndentObjectMapperProvider();
		ObjectMapper mapper = provider.getContext(null);
		Connection connexion = null;
		ArrayList<Location> locations = new ArrayList<Location>();
		
		try {
			Context myContext = new InitialContext();
			DataSource datasource = (DataSource) myContext.lookup("java:comp/env/jdbc/schoolFormationDataSource");
			connexion = datasource.getConnection();
			Statement statement = connexion.createStatement();
			ResultSet resultSet = statement.executeQuery("select id, city from LOCATION");
			
			
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String city = resultSet.getString("city");
				Location location = new Location(id, city);
				locations.add(location);
				System.out.println("id = " + id + "	| city = " + city);
			}
		}
		
		catch (Exception e) {
			System.out.println("Failed");
			e.printStackTrace();

		} 
		
		finally {
			if(connexion !=null) {
				try {
					connexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		try {
			return mapper.writeValueAsString(locations);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return "ByLocation";
	}
}
