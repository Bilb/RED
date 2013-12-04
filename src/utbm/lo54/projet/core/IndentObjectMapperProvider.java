package utbm.lo54.projet.core;

import java.text.SimpleDateFormat;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


/**
 * Cette classe permet de récupérer un mapper custom 
 * 	afin d'obtenir du JSON grâce à Jackson.
 */
@Provider
public class IndentObjectMapperProvider implements ContextResolver<ObjectMapper> {
	/**
	 * le mapper JSON de ce provider
	 */
	final ObjectMapper defaultObjectMapper;

	public IndentObjectMapperProvider() {
		defaultObjectMapper = createCustomMapper();
	}

	/**
	 * Retourne un mapper générant du JSON avec indentation et 
	 * formatage de date en dd-MM-yyyy HH:mm:ss
	 */
	@Override
	public ObjectMapper getContext(Class<?> type) {
		return defaultObjectMapper;
	}

	/**
	 * Permet de créer un mapper avec les options qui nous intéressent : 
	 * 	indentation du json et formatage de la date en
	 *  dd-MM-yyyy HH:mm:ss
	 * 
	 * @return 
	 */
	private static ObjectMapper createCustomMapper() {
		ObjectMapper result = new ObjectMapper();
		result.enable(SerializationFeature.INDENT_OUTPUT);
		result.setDateFormat(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"));
		
		return result;
	}

}

