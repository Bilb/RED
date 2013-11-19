package utbm.lo54.projet.model;

import java.util.Date;

public class ByLocationRecord {
	private int id;
	private Date start;
	private Date end;
	private String courseCode;
	private String location;
	
	
	public ByLocationRecord() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ByLocationRecord(int id, Date start, Date end, String courseCode,
			String location) {
		super();
		this.id = id;
		this.start = start;
		this.end = end;
		this.courseCode = courseCode;
		this.location = location;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
