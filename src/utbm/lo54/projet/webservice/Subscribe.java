package utbm.lo54.projet.webservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/subscribe/{session}/{lastName}/{firstName}/{address}/{phone}/{email}")
public class Subscribe {
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public String registerSubscription(
			@PathParam("session") 		int			sessionId	,
			@PathParam("lastName") 		String		lastName	,
			@PathParam("firstName") 	String		firstName	,
			@PathParam("address") 		String		address		,
			@PathParam("phone") 		String		phone		,
			@PathParam("email") 		String		email				
			) {

		Connection connexion = null;
		DataSource dataSource = null;
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
			PreparedStatement statement = connexion.prepareStatement("INSERT INTO CLIENT"
					+ " ( LASTNAME, FIRSTNAME, ADDRESS, PHONE, EMAIL, SESSION_ID)"
					+ " VALUES (?, ?, ?, ?, ?, ?)"
					);
			statement.setString(1, lastName);
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

		return "utilisateur " + lastName + " inscrit � la session num " + sessionId;
	}
}

