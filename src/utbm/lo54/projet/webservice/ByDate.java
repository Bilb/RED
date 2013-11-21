package utbm.lo54.projet.webservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import utbm.lo54.projet.core.IndentObjectMapperProvider;
import utbm.lo54.projet.model.Record;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 
 * @author Audric Ackermann
 *
 */
@Path("/byDate/{KeyDate}")
public class ByDate {

	@GET 
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public String getByDate(@PathParam("KeyDate") String requestDate) {

		IndentObjectMapperProvider provider = new IndentObjectMapperProvider();
		ObjectMapper mapper = provider.getContext(null);


		List<Record> records = new ArrayList<Record>();
		DateFormat decodeFromQuery = new SimpleDateFormat("dd-MM-yyyy");
		

		try {
			Date date = (Date) decodeFromQuery.parse(requestDate);
			Context namingContext = new InitialContext();

			DataSource dataSource = (DataSource)namingContext.lookup("java:comp/env/jdbc/schoolFormationDataSource");
			Connection con = dataSource.getConnection();

			PreparedStatement stmt = con.prepareStatement("SELECT ses.ID, START, END, "
					+ "COURSE_CODE, TITLE, CITY FROM COURSE_SESSION AS ses "
					+ "INNER JOIN COURSE AS crs ON ses.COURSE_CODE=crs.CODE "
					+ "INNER JOIN LOCATION AS loc ON loc.ID=ses.LOCATION_ID "
					+ "WHERE START LIKE ?");

			DateFormat encodeForSql = new SimpleDateFormat("yyyy-MM-dd");
			stmt.setString(1, "" + encodeForSql.format(date)+"%");
			

			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				System.out.println("id:" + rs.getString(6));
				Record record = new Record(rs.getInt(1), rs.getString(2), 
						rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
				records.add(record);
			}

		} catch (NamingException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ParseException e) {
			e.printStackTrace();
		}


		try {
			return mapper.writeValueAsString(records);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "[ ]";

	}





}