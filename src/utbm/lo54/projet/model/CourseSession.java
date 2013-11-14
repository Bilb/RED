package utbm.lo54.projet.model;

import java.sql.Date;

public class CourseSession {
	private int id;
	private Date start;
	private Date end;
	private String courseCode;
	private int locationId;
	
	public CourseSession(int id, Date start, Date end, String courseCode,
			int locationId) {
		super();
		this.id = id;
		this.start = start;
		this.end = end;
		this.courseCode = courseCode;
		this.locationId = locationId;
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
