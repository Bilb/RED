package utbm.lo54.projet.webservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;



@Path("/byDate")
public class ByDate {


	@GET 
	@Produces("text/plain")
	public String getHelloWorld() {

		Connection con = null;

		try {
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			String dataServerURL=new
					String("jdbc:mysql://localhost:3306/schoolFormation");
			con=DriverManager.getConnection(dataServerURL,"blue","blue");

			// do what to do
		} catch (Exception e) {
			System.out.println("Failed");
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

		
		return "byDate";
	}
}