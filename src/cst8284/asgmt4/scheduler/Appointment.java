package cst8284.asgmt4.scheduler;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Recored appointments with specified information
 * @author Tsehay Gebremeskel 
 * @version 1 
 * CourseName: cst8284    
 * ClassName: Appointment.java   
 * Date: 27/11/2019 
 */
public class Appointment implements Serializable {
	private Calendar aptDate;
	private String firstName, lastName;
	private TelephoneNumber phone;
	private Activity activity;

	 public static final long serialVersionUID = 1L;
	/**
	 * instantiate appointment with given calendar
	 * @param cal 	appointment date
	 */
	public Appointment(Calendar cal) {
		this.aptDate = cal;
	}

	/**
	 * Constructs appointment with the information provided
	 * @param cal      appointment date
	 * @param fullName full name of the client
	 * @param phone    phone number of the client
	 * @param act      activity of the client
	 */
	public Appointment(Calendar cal, String fullName, TelephoneNumber phone, Activity act) {
		this(cal, fullName.trim().split(" ")[0], fullName.trim().split(" ")[1], phone, act);
	}

	/**
	 * Creates a new appointment with specified information, validating the input
	 * 
	 * @throws BadAppointmentDataException if first and last name exceeds maximum length, or include characters other than alphabetic characters.
	 * @param cal       appointment date
	 * @param firstName first name of the client
	 * @param lastName  last name of the client
	 * @param phone     phone number of the client
	 * @param act       activity of the client
	 */
	public Appointment(Calendar cal, String firstName, String lastName, TelephoneNumber phone, Activity act) {
		// validation of size
		if (firstName.length() + lastName.length() + 1 > 30) {
			throw new BadAppointmentDataException("Name cannot exceed 30 characters", "Name exceeds maximum length");
		}

		// validation characters 
		//https://stackoverflow.com/questions/1911902/check-string-whether-it-contains-only-latin-characters
		String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.-'"; 
		for (int i = 0; i < firstName.length(); i++) {
			if (!allowedCharacters.contains(firstName.substring(i, i + 1))) {
				throw new BadAppointmentDataException(
						"Name cannot include characters other than alphabetic characters, the dash (-), the period (.), and the apostrophe (‘)",
						"Illegal characters in name");
			}
		}
		for (int i = 0; i < lastName.length(); i++) {
			if (!allowedCharacters.contains(lastName.substring(i, i + 1))) {
				throw new BadAppointmentDataException(
						"Name cannot include characters other than alphabetic characters, the dash (-), the period (.), and the apostrophe (‘)",
						"Illegal characters in name");
			}
		}

		setFirstName(firstName.trim());
		setLastName(lastName.trim());
		setCalendar(cal);
		setPhone(phone);
		setActivity(act);
	}

	/**
	 * 
	 * @return appointment date
	 */
	public Calendar getCalendar() {
		return aptDate;
	}

	/**
	 * 
	 * @param aptDate the appointment date 
	 */
	public void setCalendar(Calendar aptDate) {
		this.aptDate = aptDate;
	}

	/**
	 * 
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * 
	 * @param firstName  name to be set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * 
	 * @return Last name
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * 
	 * @param lastName last name to be set
	 */
	public void setLastName(String lastName) {

		this.lastName = lastName;
	}
	/**
	 * 
	 * @return client's phone
	 */
	public TelephoneNumber getPhone() {
		return phone;
	}
	/**
	 * 
	 * @param phone client's phone to be set
	 */
	public void setPhone(TelephoneNumber phone) {
		this.phone = phone;
	}
	/**
	 * 
	 * @return activity of the appointment
	 */
	public Activity getActivity() {
		return activity;
	}
	/**
	 * 
	 * @param activity activity to be set to the appointment
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	/**
	 * 
	 * @return the String that represents the appointment object
	 */
	public String toString() {
		return getCalendar().getTime().toString() + "\n" + getFirstName() + " " + getLastName() + "\n"
				+ getPhone().toString() + "\n" + getActivity().toString();
	}
}
