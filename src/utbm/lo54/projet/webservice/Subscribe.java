package utbm.lo54.projet.webservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/Subscribe/{session}/{lastName}/{firstName}/{address}/{phone}/{email}")
public class Subscribe {
	@GET
	@Produces("text/plain")
	public String registerSubscription(
			@PathParam("session") 		int			sessionId	,
			@PathParam("lastName") 		String		lastName	,
			@PathParam("firstName") 	String		firstName	,
			@PathParam("address") 		String		address		,
			@PathParam("phone") 		String		phone		,
			@PathParam("email") 		String		email				
			) {

		/* loading the JDBC driver for MySQL */
		try {
			Class.forName( "com.mysql.jdbc.Driver" );
		} catch ( ClassNotFoundException e ) {
			return "failed loading driver";
		}

		Connection connexion = null;
		try {
			connexion = DriverManager.getConnection( "jdbc:mysql://localhost:3306/schoolFormation", "blue", "blue" );
			PreparedStatement statement = connexion.prepareStatement("INSERT INTO CLIENT"
					+ " ( LASTNAME, FIRSTNAME, ADDRESS, PHONE, EMAIL, SESSION_ID)"
					+ " VALUES (?, ?, ?, ?, ?, ?)"
					);
			statement.setString(1, lastName);
			System.out.println("lastname: " + lastName);
			System.out.println("firstname: " + firstName);
			System.out.println("address: " + address);
			System.out.println("phone: " + phone);
			System.out.println("email: " + email);
			System.out.println("session: " + sessionId);
			statement.setString(2, firstName);
			statement.setString(3, address);
			statement.setString(4, phone);
			statement.setString(5, email);
			statement.setInt(6, sessionId);
			statement.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
			return "failed to update";
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

		return "utilisateur " + lastName + " inscrit à la session num " + sessionId;
	}
}

