package utbm.lo54.projet.model;


/**
 * Enregistrement d'une localisation
 *
 */
public class Location {
	
	/**
	 * Identifiant de cette localisation
	 */
	private int id;
	
	/**
	 * Ville de cette localisation
	 */
	private String city;
	
	public Location() {
	}
	
	public Location(int id, String city) {
		super();
		this.id = id;
		this.city = city;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
}
