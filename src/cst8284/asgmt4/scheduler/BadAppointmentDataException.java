package cst8284.asgmt4.scheduler;

import javax.swing.JOptionPane;

/**
 * Exception when the data is not right
 * @author Tsehay Gebremeskel
 * @version 1
 * CourseName: cst8284
 * ClassName: BadAppointmentDataException.java
 * Date: 27/11/2019
 */
public class BadAppointmentDataException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private String description;
	/**
	 * instantiate with defaults
	 */
	public BadAppointmentDataException() {
		this("Please try again", "Bad data Entered");
	}
	/**
	 * instantiate exception with given data
	 * @param message its the message to be set to the exception
	 * @param description its the description to be set to the exception
	 */

	public BadAppointmentDataException(String message, String description) {
		super(message);
		this.description = description;
		
		JOptionPane.showMessageDialog(null, this.toString(), description, JOptionPane.ERROR_MESSAGE);
	}
	/**
	 * 
	 * @return description of the exception
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 
	 * @param description description to be set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return a string that represents the exception
	 */
	public String toString() {
		return "BadAppointmentDataException: " + this.getMessage() + "\nDescription: " + this.getDescription();
	}
	
}
