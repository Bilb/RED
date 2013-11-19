package utbm.lo54.projet.webservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import utbm.lo54.projet.model.Course;

@Path("/ByKeyword/{keyword}")
public class ByKeyword {

    @GET
    @Produces("application/json")
    public String getFormationList(@PathParam("keyword") String keyword) {
    	
    	StringBuilder formationContainingKeyword = new StringBuilder();
    	formationContainingKeyword.append("Keyword:" + keyword);
    	/* Chargement du driver JDBC pour MySQL */
    	try {
    	    Class.forName( "com.mysql.jdbc.Driver" );
    	} catch ( ClassNotFoundException e ) {
    		return "failed loading driver";
    	}
    	
    	Connection connexion = null;
		try {
    		    connexion = DriverManager.getConnection( "jdbc:mysql://localhost:3306/schoolFormation", "blue", "blue" );
    		    Statement statement = connexion.createStatement();
    		    
    		    /* Exécution d'une requête de lecture */
    		    ResultSet resultat = statement.executeQuery( "SELECT code, title  FROM Course WHERE title like '%"+ keyword +"%';" );
    		    
    		    //ObjectMapper mapper = new ObjectMapper();
    		    /* Récupération des données du résultat de la requête de lecture */
    		    while ( resultat.next() ) {
    		    	//Course tempCourse = new Course(resultat.getString("code"), resultat.getString("title")) ;
					//	RES = RES + mapper.writeValueAsString(tempCourse) + "\n";
    		    	formationContainingKeyword.append("\nCode  = " + resultat.getString("code") + "\t\tTitle = " + resultat.getString("title"));
    		    }
    		    
    		
    		} catch ( SQLException e ) {
    		    return "failed to connect";
    		}
    		    finally {
    		    if ( connexion != null )
    		        try {
    		            /* Fermeture de la connexion */
    		            connexion.close();
    		        } catch ( SQLException ignore ) {
    		            // Si une erreur survient lors de la fermeture, il suffit de l'ignorer
    		        }
    		}
    	return formationContainingKeyword.toString();
    }
}
