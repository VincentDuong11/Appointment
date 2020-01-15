package cst8284.asgmt4.scheduler;

import java.io.Serializable;

/**
 * class for formating phone number
 * @author Tsehay Gebremeskel
 * @version 1 
 * CourseName: cst8284    
 * ClassName:  TelephoneNumber.java   
 * Date: 27/11/2019 
 */

public class TelephoneNumber implements Serializable {
	private static final long serialVersionUID = 1L;
	private int areaCode, lineNumber, prefix;
	/**
	 * creates phone number using given string
	 * @param phoneNumber string to be converted to phone
	 */
	public TelephoneNumber(String phoneNumber) { //https://stackoverflow.com/questions/1911902/check-string-whether-it-contains-only-latin-characters
		// validate only numbers and dash
		String allowedCharacters = "1234567890-";
		for (int i = 0; i < phoneNumber.length(); i++) {
			if (!allowedCharacters.contains(phoneNumber.substring(i, i + 1))) {
				throw new BadAppointmentDataException("Telephone Number can only contain numbers and the character '-'",
						"Bad character (s) input string");
			}
		}

		// validate dash
		if (phoneNumber.split("-").length < 3) {
			throw new BadAppointmentDataException(
					"Missing digit(s); correct format is AAA-PPP-NNNN, where AAA is the area code and PPP-NNNN is the local number",
					"Incorrect format");
		}

		String areaCode = phoneNumber.split("-")[0].trim();
		String prefix = phoneNumber.split("-")[1].trim();
		String lineNumber = phoneNumber.split("-")[2].trim();

		// validate prefix
		if (prefix.length() < 3) {
			throw new BadAppointmentDataException(
					"Missing digit(s); correct format is AAA-PPP-NNNN, where AAA is the area code and PPP-NNNN is the local number",
					"Incorrect format");
		}

		// validate area code //https://www.tutorialgateway.org/java-startswith-method/
		if (areaCode.startsWith("0") || areaCode.startsWith("1")) {
			throw new BadAppointmentDataException("Area Code can't Start with 0 or 1", "Invalid Number");
		}

		setAreaCode(Integer.parseInt(areaCode));
		setPrefix(Integer.parseInt(prefix));
		setLineNumber(Integer.parseInt(lineNumber));
	}
	/**
	 * 
	 * @return the area code of the phone 
	 */
	public int getAreaCode() {
		return areaCode;
	}
	/**
	 * 
	 * @param areaCode area code to be set
	 */
	public void setAreaCode(int areaCode) {
		this.areaCode = areaCode;
	}
	/**
	 * 
	 * @return the prefix of the phone
	 */
	public int getPrefix() {
		return prefix;
	}
	/**
	 * 
	 * @param prefix prefix to be set
	 */
	public void setPrefix(int prefix) {
		this.prefix = prefix;
	}
	/**
	 * 
	 * @return the line number of the phone
	 */
	public int getLineNumber() {
		return lineNumber;
	}
	/**
	 * 
	 * @param lineNumber line number to be set
	 */
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	/**
	 * @return a string that represents the phone
	 */
	public String toString() {
		return "(" + getAreaCode() + ") " + getPrefix() + "-" + getLineNumber();
	}
}
