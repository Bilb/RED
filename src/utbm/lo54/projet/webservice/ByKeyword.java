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
import utbm.lo54.projet.model.Record;

@Path("/byKeyword/{keyword}")
public class ByKeyword {

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public String getFormationList(@PathParam("keyword") String keyword) {

		Connection connexion = null;
		List<Record> courseListMatchingKeyword = new ArrayList<Record>();

		try {

			DataSource dataSource = null;
			Context namingContext;
			namingContext = new InitialContext();
			dataSource = (DataSource)namingContext.lookup("java:comp/env/jdbc/schoolFormationDataSource");
			connexion = dataSource.getConnection();
			PreparedStatement statement = connexion.prepareStatement("select "
															+ "cs.id,c.code, c.title,  l.city, cs.start, cs.end "
														+ "from "
															+ "COURSE as c inner join "
															+ "COURSE_SESSION as cs "
															+ "	on (c.code = cs.course_code)"
																+ "inner join LOCATION as l "
																	+ "on (cs.location_id = l.id) "
														+ "where "
															+ "c.title like ? ");
			keyword = "%"+keyword+"%";
			statement.setString(1, keyword);
			ResultSet resultat = statement.executeQuery();
			while ( resultat.next() ) {
				Record record = new Record(resultat.getInt(1), resultat.getString(5), resultat.getString(6),
											resultat.getString(2), resultat.getString(3), resultat.getString(4));
				courseListMatchingKeyword.add(record);
			}

		} catch (NamingException e) {
			e.printStackTrace();

		} catch ( SQLException e ) {
			e.printStackTrace();
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