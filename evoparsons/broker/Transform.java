package evoparsons.broker;

import java.util.regex.Pattern;

/**
 * This class is used to store individual transforms loaded from files, with each transform
 * stored in one instance of the class.
 * @author paul
 *
 */
public class Transform {
	public static final String TITLE = "Title";
	public static final String DESC = "Description";
	public static final String SELECT = "FragmentSelection";
	public static final String TRANSFORM = "Transform";
	
	private String fileName;
	private String title;
	private String description;
	private String transform;
	private Pattern pattern;

	public Transform(String fileName, String title, String description, String fragmentSelect, String transform) {
		this.title = title;
		this.fileName = fileName;
		this.description = description;
		this.transform = transform;
		this.pattern = Pattern.compile(fragmentSelect, Pattern.MULTILINE | Pattern.DOTALL);
	}
	
	/**
	 * Setter for the title of the transform.
	 * @param title
	 */
	public void setTitle(String title){
		this.title = title;
	}
	
	/**
	 * Getter for the title of the transform.
	 * @return String
	 */
	public String getTitle(){
		return title;
	}

	public String getFileName() {
		return fileName;
	}
	
	/**
	 * Setter for the description of the transform.
	 * @param description
	 */
	public void setDescription(String description){
		this.description = description;
	}
	
	/**
	 * Getter for the description of the transform.
	 * @return String
	 */
	public String getDescription(){
		return description;
	}	
	
	/**
	 * Getter for the fragment selection definition used.
	 * @return String
	 */
	public Pattern getPattern() {
		return pattern;
	}
	
	/**
	 * Setter for the replacement value of the transform.
	 * @param transform
	 */
	public void setTransform(String transform){
		this.transform = transform;
	}
	
	/**
	 * Getter for the replacement value of the transform.
	 * @return String
	 */
	public String getTransform(){
		return transform;
	}

}
