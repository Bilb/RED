package utbm.lo54.projet.webservice;

import java.sql.Connection;
import java.sql.DriverManager;
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

import utbm.lo54.projet.model.Location;

@Path("/byLocation")
public class Locations {

	@GET 
	@Produces("text/plain")
	public String getLocations() {

		Connection connexion = null;

		try {
			Context myContext = new InitialContext();
			DataSource datasource = (DataSource) myContext.lookup("java:comp/env/jdbc/schoolFormationDataSource");
			connexion = datasource.getConnection();
//			String dataServerURL = new String("jdbc:mysql://localhost:3306/schoolFormation");
//			connexion = DriverManager.getConnection(dataServerURL,"blue","blue");
			Statement statement = connexion.createStatement();
			ResultSet resultSet = statement.executeQuery("select id, city from location");
			ArrayList<Location> locations = new ArrayList<Location>();
			
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String city = resultSet.getString("city");
				Location location = new Location(id, city);
				locations.add(location);
				System.out.println("id = " + id + "	| city = " + city);
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

		return "ByLocation";
	}
}
