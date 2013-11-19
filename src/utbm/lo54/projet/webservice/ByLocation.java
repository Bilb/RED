package utbm.lo54.projet.webservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import utbm.lo54.projet.core.Commons;
import utbm.lo54.projet.model.CourseSession;

@Path("/byLocation/{KeyLocation}")
public class ByLocation {

	@GET 
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public String getCourse(@PathParam("KeyLocation") String KeyLocation) {

		Connection connexion = null;
		StringBuilder string = new StringBuilder();

		try {
			Class.forName(Commons.JDBC_DRIVER).newInstance();
			String dataServerURL = new String(Commons.SQL_SERVER_URL);
			connexion = DriverManager.getConnection(dataServerURL,Commons.DATABASE_USER,Commons.DATABASE_PASSWORD);
			Statement statement = connexion.createStatement();
			ResultSet resultSet = statement.executeQuery("select "
															+ "cs.id, cs.start, cs.end, cs.course_code, cs.location_id "
														+ "from "
															+ "course_session as cs "
																+ "inner join location as l "
																	+ "on (cs.location_id = l.id) "
														+ "where "
															+ "l.city = '"+KeyLocation+"'");
			
			ArrayList<CourseSession> courseSessions = new ArrayList<CourseSession>();
			
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				Date start = resultSet.getDate("start");
				Date end = resultSet.getDate("end");
				String course_code = resultSet.getString("course_code");
				int location_id = resultSet.getInt("location_id");
				CourseSession courseSession = new CourseSession(id, start, end, course_code, location_id);
				courseSessions.add(courseSession);
				string.append("id = " + id + "	| start = " + start + "	| end = " + end + "	| course_code = " + course_code + "	| location_id = " + location_id + "\n");
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

		return string.toString();
	}
}
