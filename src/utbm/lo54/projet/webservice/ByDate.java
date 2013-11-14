package utbm.lo54.projet.webservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;



@Path("/byDate")
public class ByDate {


	@GET 
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ByDateRecord getByDate(@QueryParam("date") String requestDate) {

		//List<ByDateRecord> records = new ArrayList<ByDateRecord>();
		try {
			DateFormat decodeFromQuery = new SimpleDateFormat("dd-MM-yyyy");
			Date date = (Date) decodeFromQuery.parse(requestDate);

			Connection con = null;
			try {
				Class.forName("org.gjt.mm.mysql.Driver").newInstance();
				String dataServerURL=new
						String("jdbc:mysql://localhost:3306/schoolFormation");
				con=DriverManager.getConnection(dataServerURL,"blue","blue");

				PreparedStatement stmt = con.prepareStatement("SELECT ID, START, END, "
						+ "COURSE_CODE, TITLE FROM COURSE_SESSION AS ses "
						+ "INNER JOIN COURSE AS crs ON ses.COURSE_CODE=crs.CODE "
						+ "WHERE START=?");

				DateFormat encodeForSql = new SimpleDateFormat("yyyy-MM-dd");
				stmt.setString(1, "" + encodeForSql.format(date));

				ResultSet rs = stmt.executeQuery();

				while(rs.next()) {
					ByDateRecord record = new ByDateRecord(rs.getInt(1), rs.getString(2), rs.getString(3), 
							rs.getString(4), rs.getString(5));
					//records.add(record);
					return record;
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

		return null;

	}
}