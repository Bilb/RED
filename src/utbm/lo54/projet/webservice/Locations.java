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

/**
 * Webservice retournant la liste de l'ensemble des localisations, en JSON.
 */
@Path("/byLocation")
public class Locations {

	@GET 
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public String getLocations() {
		
		ArrayList<Location> locations = new ArrayList<Location>();
		Connection connexion = null;
		try {
			/* récupération du DataSource vers notre base de données */
			Context myContext = new InitialContext();
			DataSource datasource = (DataSource) myContext.lookup("java:comp/env/jdbc/schoolFormationDataSource");
			connexion = datasource.getConnection();
			
			Statement statement = connexion.createStatement();
			ResultSet resultSet = statement.executeQuery("select id, city from LOCATION");
			
			/* On ajoute les locations trouvés à la liste */
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String city = resultSet.getString("city");
				Location location = new Location(id, city);
				locations.add(location);
			}
		}
		
		catch (Exception e) {
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
		
		/*On récupère notre mapper JSON custom */
		IndentObjectMapperProvider provider = new IndentObjectMapperProvider();
		ObjectMapper mapper = provider.getContext(null);
		
		/* Génère le JSON associée à la liste d'enregistrements */
		try {
			return mapper.writeValueAsString(locations);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return "ByLocation";
	}
}
