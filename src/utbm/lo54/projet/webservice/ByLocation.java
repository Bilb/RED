package utbm.lo54.projet.webservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import utbm.lo54.projet.core.IndentObjectMapperProvider;
import utbm.lo54.projet.model.Record;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Webservice retournant la liste des enregistrements, en JSON, des sessions
 * se déroulant à la localisation passé en paramètres
 */
@Path("/byLocation/{KeyLocation}")
public class ByLocation {

	@GET 
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public String getCourse(@PathParam("KeyLocation") String KeyLocation) {
		
		
		Connection connexion = null;
		List<Record> records = new ArrayList<Record>();

		try {
			/* récupération du DataSource vers notre base de données */
			Context myContext = new InitialContext();
			DataSource datasource = (DataSource) myContext.lookup("java:comp/env/jdbc/schoolFormationDataSource");
			connexion = datasource.getConnection();
			
			
			PreparedStatement statement = connexion.prepareStatement("select "
															+ "cs.id, c.title, cs.start, cs.end, cs.course_code, l.city "
														+ "from "
															+ "COURSE_SESSION as cs "
																+ "inner join LOCATION as l "
																	+ "on (cs.location_id = l.id) "
																+ "inner join COURSE as c "
																	+ "on (cs.course_code = c.code) "
														+ "where "
															+ "l.city =  ? ");
			statement.setString(1,KeyLocation);
			ResultSet resultSet = statement.executeQuery();
			
			/* On ajoute les enregistrements trouvés à la liste */
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String start = resultSet.getString("start");
				String end = resultSet.getString("end");
				String course_code = resultSet.getString("course_code");
				String title = resultSet.getString("title");
				String location = resultSet.getString("city");

				Record record = new Record(id, start, end, course_code, title, location);
				records.add(record);
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
			return mapper.writeValueAsString(records);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return "[ ]";
	}
}
