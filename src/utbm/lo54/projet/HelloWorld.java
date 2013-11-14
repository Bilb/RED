package utbm.lo54.projet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/hello")
public class HelloWorld {

    @GET 
    @Produces("text/plain")
    public String getHelloWorld() {
    	return "hello World";
    }
}

