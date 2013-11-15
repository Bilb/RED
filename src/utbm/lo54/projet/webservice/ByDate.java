package utbm.lo54.projet.webservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 
 * @author Audric Ackermann
 *
 */
@Path("/byDate")
public class ByDate {


	@GET 
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getByDate(@QueryParam("date") String requestDate) {
		
		IndentObjectMapperProvider provider = new IndentObjectMapperProvider();
		ObjectMapper mapper = provider.getContext(null);
		

		List<ByDateRecord> records = new ArrayList<ByDateRecord>();
		try {
			DateFormat decodeFromQuery = new SimpleDateFormat("dd-MM-yyyy");
			Date date = (Date) decodeFromQuery.parse(requestDate);

			Connection con = null;
			try {
				Class.forName(Commons.JDBC_DRIVER).newInstance();
				String dataServerURL=new
						String(Commons.SQL_SERVER_URL);
				con=DriverManager.getConnection(dataServerURL,Commons.DATABASE_USER, Commons.DATABASE_PASSWORD);

				PreparedStatement stmt = con.prepareStatement("SELECT ID, START, END, "
						+ "COURSE_CODE, TITLE FROM COURSE_SESSION AS ses "
						+ "INNER JOIN COURSE AS crs ON ses.COURSE_CODE=crs.CODE "
						+ "WHERE START=?");

				DateFormat encodeForSql = new SimpleDateFormat("yyyy-MM-dd");
				stmt.setString(1, "" + encodeForSql.format(date));

				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					ByDateRecord record = new ByDateRecord(rs.getInt(1), rs.getString(2), 
							rs.getString(3), rs.getString(4), rs.getString(5));
					records.add(record);
				}


			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if(con !=null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (ParseException e) {
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