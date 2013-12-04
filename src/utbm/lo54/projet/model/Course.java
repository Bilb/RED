package utbm.lo54.projet.model;


/**
 * Cette classe représente un cours. 
 */
public class Course {
	/**
	 * Code de ce cours
	 */
	private String code;
	
	/**
	 * Titre de ce cours, une description
	 */
	private String title;
	
	
	public Course() {
	}
	
	public Course(String code, String title) {
		super();
		this.code = code;
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
