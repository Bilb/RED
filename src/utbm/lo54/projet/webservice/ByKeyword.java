package utbm.lo54.projet.webservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import utbm.lo54.projet.model.Course;

@Path("/ByKeyword/{keyword}")
public class ByKeyword {

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public String getFormationList(@PathParam("keyword") String keyword) {

		DataSource dataSource = null;
		Connection connexion = null;
		List<Course> courseListMatchingKeyword = new ArrayList<Course>();
		
		try {
			try {
				Context namingContext;
				namingContext = new InitialContext();
				dataSource = (DataSource)namingContext.lookup("java:comp/env/jdbc/schoolFormationDataSource");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connexion = dataSource.getConnection();
			PreparedStatement statement = connexion.prepareStatement("SELECT code, title  FROM Course WHERE title like '%?%'");
			statement.setString(1, keyword);
			ResultSet resultat = statement.executeQuery( "" );
			while ( resultat.next() ) {
				Course record = new Course(resultat.getString("code"), resultat.getString("title"));
				courseListMatchingKeyword.add(record);
			}

		} catch ( SQLException e ) {
			return "failed to connect";
		}
		finally {
			if ( connexion != null ) {
				try {
					connexion.close();
				} catch ( SQLException e ) {
					e.printStackTrace();
				}
			}
		}
		
		// Converting to json format
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(courseListMatchingKeyword);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "[ ]";
	}
}
