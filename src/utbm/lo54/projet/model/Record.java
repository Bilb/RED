package utbm.lo54.projet.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * Cette classe permet de récupérer en un seul objet JAVA toutes les
 * informations nécessaires lors d'une requête sur les webservices. 
 * C'est en fait cette classe que Jackson génère pour obtenir du JSON.
 * C'est un objet obtenu à partir des données de la classe Course, CourseSession
 * et Location. Elle encapsule l'ensemble des champs qui intéresse l'utilisateur
 */
@XmlRootElement
public class Record {

	/**
	 * Identifiant de cet enregistrement
	 */
	private int id;
	
	/**
	 * Date de début de cet enregistrement
	 */
	private Date start;
	
	/**
	 * Date de fin de cet enregistrement
	 */
	private Date end;
	
	/**
	 * Code du cours associé à cet enregistrement
	 */
	private String courseCode;
	
	/**
	 * Titre, ou description de ce cours
	 */
	private String title;
	
	/**
	 * Localisation de cet enregistrement
	 */
	private String location;

	
	
	
	public Record() {
	}

	/**
	 * Constructeur prenant les dates déjà en objet Date.
	 * @param id identifiant de cet enregistrement
	 * @param start date de début de cet enregistrement 
	 * @param end date de fin de cet enregistrement
	 * @param courseCode code du cours de cet enregistrement
	 * @param title titre de cet enregistrement
	 * @param location de cet enregistrement
	 */
	public Record(int id, Date start, Date end, String courseCode,
			String title, String location) {
		super();
		this.id = id;
		this.start = start;
		this.end = end;
		this.courseCode = courseCode;
		this.title = title;
		this.location = location;
	}

	/**
	 * Constructeur prenant les dates en String. Elles doivent 
	 * respecter le format yyyy-MM-dd HH:mm:ss
	 * @param id identifiant de cet enregistrement
	 * @param start date de début de cet enregistrement 
	 * @param end date de fin de cet enregistrement
	 * @param courseCode code du cours de cet enregistrement
	 * @param title titre de cet enregistrement
	 * @param location de cet enregistrement
	 */
	public Record(int id, String start, String end, String courseCode,
			String title, String location) {
		//on appelle l'autre constructeur pour ne pas se répeter
		this(id, (Date) null,null,courseCode, title, location);
		
		// Création de notre format de date
		DateFormat encodeForSql = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			//on parse les string avec notre formatage de date
			this.start = encodeForSql.parse(start);
			this.end = encodeForSql.parse(end);
		} catch (ParseException e) {
			System.err.println("Incorrect DateFormat for start or end: must be : 'yyyy-MM-dd'");
			e.printStackTrace();
		}
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}



}
