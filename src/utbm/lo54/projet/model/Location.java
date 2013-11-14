package utbm.lo54.projet.model;

public class Location {
	private int id;
	private String city;
	
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
