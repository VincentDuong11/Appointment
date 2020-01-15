package cst8284.asgmt4.employee;

import java.util.Scanner;
/**
 * General information of the employee
 * @author Tsehay Gebremeskel
 * @version 1
 * CourseName: cst8284 
 * ClassName: Employee.java
 * Date:  27/11/2019
 */
public abstract class Employee {
	private String fullName;
	/**
	 * constructs Employee with default values
	 */
	protected Employee() {this("unknown");}
	/**
	 * Constructs Employee with the information provided
	 * @param fullName name of the employee
	 */
	protected Employee(String fullName) {setName(fullName);}
	protected static Scanner scan = new Scanner(System.in);
	/**
	 * 
	 * @param fullName full name to set
	 */
	public void setName(String fullName) {this.fullName = fullName;}
	/**
	 * 
	 * @return the full name of this employee
	 */
	public String getName() {return fullName;}
	/**
	 * 
	 * Subclasses should prompt user with extra information specific to it
	 * @return type of the activity
	 */
	public abstract String getActivityType();
	
	/**
	 * @return employee's name
	 */
	@Override
	public String toString() {return getName();}
}