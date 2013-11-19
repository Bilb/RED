package utbm.lo54.projet.webservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
import utbm.lo54.projet.model.ByLocationRecord;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Path("/byLocation/{KeyLocation}")
public class ByLocation {

	@GET 
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public String getCourse(@PathParam("KeyLocation") String KeyLocation) {
		
		IndentObjectMapperProvider provider = new IndentObjectMapperProvider();
		ObjectMapper mapper = provider.getContext(null);

		Connection connexion = null;
		StringBuilder string = new StringBuilder();
		List<ByLocationRecord> records = new ArrayList<ByLocationRecord>();

		try {
			Context myContext = new InitialContext();
			DataSource datasource = (DataSource) myContext.lookup("java:comp/env/jdbc/schoolFormationDataSource");
			connexion = datasource.getConnection();
			
			
			PreparedStatement statement = connexion.prepareStatement("select "
															+ "cs.id, cs.start, cs.end, cs.course_code, l.city "
														+ "from "
															+ "course_session as cs "
																+ "inner join location as l "
																	+ "on (cs.location_id = l.id) "
														+ "where "
															+ "l.city =  ? ");
			statement.setString(1,KeyLocation);
			ResultSet resultSet = statement.executeQuery();
			
			
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				Date start = resultSet.getDate("start");
				Date end = resultSet.getDate("end");
				String course_code = resultSet.getString("course_code");
				String location = resultSet.getString("city");
				ByLocationRecord record = new ByLocationRecord(id,start,end,course_code,location);
				records.add(record);
				string.append("id = " + id + "	| start = " + start + "	| end = " + end + "	| course_code = " + course_code + "	| location_id = " + location + "\n");
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
			return mapper.writeValueAsString(records);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return string.toString();
	}
}
