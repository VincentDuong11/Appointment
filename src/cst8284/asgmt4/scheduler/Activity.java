package cst8284.asgmt4.scheduler;

import java.io.Serializable;
/**
 * The description of what will happen in appointment
 * @author Tsehay Gebremeskel
 * @version 1
 * CourseName: cst8284
 * ClassName: Activity.java
 * Date: 27/11/2019
 */
public class Activity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String descriptionOfWork;
	private String category;
	/**
	 * create activity with given information
	 * @param description description of the activity
	 * @param category category of the activity
	 */
	public Activity(String description, String category) {
		setDescription(description);
		setCategory(category);
	}
	/**
	 * 
	 * @return the work description
	 */
	public String getDescription() {return descriptionOfWork;}
	/**
	 * 
	 * @param description description to be set
	 */
	public void setDescription(String description) {this.descriptionOfWork = description;}
	/**
	 * 
	 * @return category of the activity
	 */
	public String getCategory() {return category;}
	/**
	 * 
	 * @param category category to be set
	 */
	public void setCategory(String category) {this.category = category;}
	
	/**
	 * @return string that represents the activity
	 */
	public String toString() {return getCategory() + "\n" + getDescription();}
	
}
