package utbm.lo54.projet.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Record {

	private int id;
	private Date start;
	private Date end;
	private String courseCode;
	private String title;
	private String location;



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

	public Record(int id, String start, String end, String courseCode,
			String title, String location) {
		super();
		this.id = id;
		DateFormat encodeForSql = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			this.start = encodeForSql.parse(start);
			this.end = encodeForSql.parse(end);
		} catch (ParseException e) {
			System.err.println("Incorrect DateFormat for start or end: must be : 'yyyy-MM-dd'");
			e.printStackTrace();
		}
		this.courseCode = courseCode;
		this.title = title;
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
