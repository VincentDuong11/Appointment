package cst8284.asgmt4.employee;

import javax.swing.JOptionPane;

/**
 * Dentist class is an employee that will be used in scheduler
 * @author Tsehay Gebremeskel
 * @version 1
 * CourseName: cst8284 
 * ClassName: Dentist.java
 * Date: 27/11/2019
 */
public class Dentist extends Employee {
	private static String[] workDescription = { "Assessment", "Filling", "Crown", "Cosmetic Repair" };

	/**
	 * creates the dentist using the name
	 * @param fullName full name of the employee
	 */
	public Dentist(String fullName) {
		super(fullName);
	}
	
	public static String[] getWorkDescription() {
		return workDescription;
	}

	/**
	 * prompts the user for a selection of work description
	 * @return the chosen work description
	 */
	public String getActivityType() {
		System.out.println("Enter a selection from the following menu:");
		int i = 1;
		for (String description : workDescription)
			System.out.println(i++ + "." + description);
		int ch = scan.nextInt();
		scan.nextLine(); // 'eat' the next line in the buffer
		System.out.println(); // add a space
		return workDescription[ch - 1];
	}

}
