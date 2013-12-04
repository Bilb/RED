package utbm.lo54.projet.model;

import java.util.Date;

/**
 * Enregistrement d'une session d'un cours 
 */
public class CourseSession {
	
	/**
	 * Identifiant de cette session
	 */
	private int id;
	
	/**
	 * Date de début de cette session
	 */
	private Date start;
	
	/**
	 * Date de fin de cette session
	 */
	private Date end;

	/**
	 * Identifiant du cours associé à cette session
	 */
	private String courseCode;
	
	/**
	 * Identifiant de la localisation associé à cette session
	 */
	private int locationId;
	
	public CourseSession() {
	}
	
	public CourseSession(int id, Date start, Date end, String courseCode,
			int locationId) {
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

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
}
