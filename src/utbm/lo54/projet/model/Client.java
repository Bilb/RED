package utbm.lo54.projet.model;


/**
 * Cette classe encapsule tout ce qui représente un enregistrement
 * dans la table client, c'est à dire ces infos, ainsi que l'identifiant de 
 * session auquelle est enregistré celui ci. Nous avons donc des doublons
 */
public class Client {
	/**
	 * Identifiant de ce client
	 */
	private int id;
	
	/**
	 * Prénom de cet client
	 */
	private String lastname;
	
	/**
	 * Nom de famille
	 */
	private String firstName;
	
	/**
	 * Adresse de ce client
	 */
	private String address;
	
	/**
	 * Téléphone de ce client
	 */
	private String phone;
	
	
	/**
	 * Identifiant de la session associé à cet enregistrement
	 */
	private int sessionId;
	
	public Client(int id, String lastname, String firstName, String address,
			String phone, int sessionId) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.firstName = firstName;
		this.address = address;
		this.phone = phone;
		this.sessionId = sessionId;
	}
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}	
}
