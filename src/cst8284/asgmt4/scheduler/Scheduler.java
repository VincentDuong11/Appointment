package cst8284.asgmt4.scheduler;

import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cst8284.asgmt4.employee.Employee;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

/**
 * Main program. Records,deletes,and displays appointment information.
 * 
 * @author Tsehay Gebremeskel
 * @version 1 CourseName: cst8284 ClassName: Scheduler.java Date: 27/11/2019
 */
// assignment 3 based on Assignment 2 solution @authored by Professor Dave
// Houtman

public class Scheduler {

	private static Scanner scan = new Scanner(System.in);
	private ArrayList<Appointment> appointments = new ArrayList<>();
	private Employee employee;
	private JTextArea scheduleOutputTextArea;
	private JFrame menuFrame;

	private static final int SAVE_APPOINTMENT = 1, DELETE_APPOINTMENT = 2, CHANGE_APPOINTMENT = 3,
			DISPLAY_APPOINTMENT = 4, DISPLAY_SCHEDULE = 5, SAVE_APPOINTMENTS_TO_FILE = 6,
			LOAD_APPOINTMENTS_FROM_FILE = 7, EXIT = 0;

	/**
	 * Constructs a scheduler containing the emp of the specified employee
	 * 
	 * @param emp the employee to be added to this scheduler
	 */
	public Scheduler(Employee emp) {

		setEmployee(emp);
	}

	/**
	 * @param emp the employee to be set
	 */
	private void setEmployee(Employee emp) {
		this.employee = emp;
	}

	/**
	 * @return the employee of this scheduler
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * runs application
	 */
	public void launch() {
		displayMenu();
	}

	/**
	 * Displays all Appointment in the current Scheduler
	 * 
	 * @return user's choice
	 */
	private void displayMenu() {
		menuFrame = new JFrame("Scheduling appointments for " + this.employee);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int X = (int) screenSize.getWidth() / 2;
		int Y = (int) screenSize.getHeight() / 2;
		menuFrame.setPreferredSize(new Dimension(X, Y));
		menuFrame.pack();

		menuFrame.setLayout(new BorderLayout(10, 10));

		JPanel btnPanel = new JPanel(new GridLayout(8, 1));
		btnPanel.add(newMenuButton("Save Appointment", SAVE_APPOINTMENT));
		btnPanel.add(newMenuButton("Display appointment", DISPLAY_APPOINTMENT));
		btnPanel.add(newMenuButton("Display schedule", DISPLAY_SCHEDULE));
		btnPanel.add(newMenuButton("Save Appointment To File", SAVE_APPOINTMENTS_TO_FILE));
		btnPanel.add(newMenuButton("Load Appointment From File", LOAD_APPOINTMENTS_FROM_FILE));
		btnPanel.add(newMenuButton("Exit", EXIT));

		menuFrame.add(btnPanel, BorderLayout.WEST);

		scheduleOutputTextArea = new JTextArea();
		scheduleOutputTextArea.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		scheduleOutputTextArea.setBackground(Color.WHITE);
		scheduleOutputTextArea.setEditable(false);

		menuFrame.add(scheduleOutputTextArea, BorderLayout.CENTER);

		menuFrame.setVisible(true);
	}

	public JButton newMenuButton(String text, int choice) {
		JButton button = new JButton(text);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					executeMenuItem(choice);
				} catch (Exception ex) {

				}
			}
		});

		return button;
	}

	/**
	 * executeMenuItem, prompt user for choice
	 * 
	 * @param choice users choice
	 */
	private void executeMenuItem(int choice) {
		switch (choice) {
		case SAVE_APPOINTMENT:
			makeAppointmentFromUserInput();
			break;
		case DELETE_APPOINTMENT:
			deleteAppointment(makeCalendarFromUserInput(false));
			break;
		case CHANGE_APPOINTMENT:
			changeAppointment(makeCalendarFromUserInput(false));
			break;
		case DISPLAY_APPOINTMENT:
			displayAppointment(makeCalendarFromUserInput(false));
			break;
		case DISPLAY_SCHEDULE:
			displayDaySchedule(makeCalendarFromUserInput(true));
			break;
		case SAVE_APPOINTMENTS_TO_FILE:
			saveAppointmentsToFile(getAppointments(), "CurrentAppointments.apts");
			break;
		case LOAD_APPOINTMENTS_FROM_FILE:
			loadAppointmentsFromFile("CurrentAppointments.apts", getAppointments());
			break;
		case EXIT:
			displayMessage("Exiting Scheduler");
			menuFrame.dispose();
			break;
		default:
			displayMessage("Invalid choice: try again. (Select " + EXIT + " to exit.)");
		}

	}

	/**
	 * prompts the user with the string s and user's answer
	 * 
	 * @param s the string to be printed
	 * @return users input
	 */
	private static String getResponseTo(String s) {
		return (JOptionPane.showInputDialog(s));
	}

	/**
	 * prompts the user for date, fullName, phoneNumber and activity and creates
	 * appointment
	 * 
	 * @throws BadAppointmentDataException if the fullName, phoneNumber or activity
	 *                                     are null or empty
	 * @return appointment from scheduler
	 */
	private void makeAppointmentFromUserInput() {
		AppointmentDialog.showAppointmentDialog(this);
	}

	/**
	 * it prompts the user for the date and create a calendar from it
	 * 
	 * @throws BadAppointmentDataException if the date is null, empty or wrong
	 *                                     format.
	 * @param suppressHour whether the hours should be shown
	 * @return the calendar with or without hours
	 */
	private static Calendar makeCalendarFromUserInput(boolean suppressHour) {
		JTextField dateField = new JTextField(8);
		JTextField timeField = new JTextField(5);

		JPanel datePanel = new JPanel();
		datePanel.setLayout(new GridLayout(4, 1));
		datePanel.add(new JLabel("Appointment Date (entered as DDMMYYYY): "));
		datePanel.add(dateField);

		if (!suppressHour) {
			datePanel.add(new JLabel("Appointment Time: "));
			datePanel.add(timeField);
		}

		JOptionPane.showConfirmDialog(null, datePanel, "Please enter the date", JOptionPane.OK_CANCEL_OPTION);

		String date = dateField.getText();
		String time = timeField.getText();

		return makeCalendar(date, time);
	}

	public static Calendar makeCalendar(String date, String time) {
		Calendar cal = Calendar.getInstance();
		int hour = 0;

		cal.clear();
		// https://www.programiz.com/java-programming/examples/string-empty-null
		if (date == null || date.isEmpty()) {
			throw new BadAppointmentDataException("Must enter a value", "Empty or null value entered");
		}

		// validate format (DDMMYYYY)
		if (date.length() != 8) {
			throw new BadAppointmentDataException("Bad calendar date entered; format is DDMMYYYY ",
					"Bad calendar format");
		}

		// https://stackoverflow.com/questions/1911902/check-string-whether-it-contains-only-latin-characters
		String allowedCharacters = "1234567890";
		for (int i = 0; i < date.length(); i++) {
			if (!allowedCharacters.contains(date.substring(i, i + 1))) {
				throw new BadAppointmentDataException("Bad calendar date entered; format is DDMMYYYY ",
						"Bad calendar format");
			}
		}

		int day = Integer.parseInt(date.substring(0, 2));
		if (day > 31 || day == 0) {
			throw new BadAppointmentDataException("Bad calendar date entered; format is DDMMYYYY ",
					"Bad calendar format");
		}

		int month = Integer.parseInt(date.substring(2, 4)) - 1; // offset by one to account for zero-based month in
		if (month > 11) {
			throw new BadAppointmentDataException("Bad calendar date entered; format is DDMMYYYY ",
					"Bad calendar format");
		}

		int year = Integer.parseInt(date.substring(4, 8));
		if (year < 2019) {
			throw new BadAppointmentDataException("Bad calendar date entered; format is DDMMYYYY ",
					"Bad calendar format");
		}

		if (!time.isEmpty()) {
			hour = processTimeString(time);
		}

		cal.set(year, month, day, hour, 0);

		return (cal);
	}

	/**
	 * convert string into time bettween 8am and 8pm
	 * 
	 * @param t time string
	 * @return hour between 8am and 8pm
	 */
	private static int processTimeString(String t) {
		int hour = 0;
		t = t.trim();
		if (t.contains(":"))
			hour = Integer.parseInt(t.split(":")[0]);
		else if (t.contains(" "))
			hour = Integer.parseInt(t.split(" ")[0]);
		else
			hour = Integer.parseInt(t);
		return ((hour < 8) ? hour + 12 : hour);
	}

	/**
	 * find the appointment with the cal specified in this scheduler
	 * 
	 * @param cal the calendar of the appointment to find in this scheduler
	 * @return the appointment found with cal
	 */
	public Appointment findAppointment(Calendar cal) {
		SortAppointmentByCalendar sort = new SortAppointmentByCalendar();

		ArrayList<Appointment> appointments = getAppointments();

		Collections.sort(appointments, sort);

		int index = Collections.binarySearch(appointments, new Appointment(cal), sort);

		if (index >= 0) {
			return appointments.get(index);
		}

		return null;
	}

	/**
	 * Appends Appointment to the scheduler with specified apt(appointment to be
	 * saved)
	 * 
	 * @param apt Appointment to be saved
	 * @return true if Appointment saved
	 * @return false if Appointment can not be saved
	 */
	public boolean saveAppointment(Appointment apt) {
		Calendar cal = apt.getCalendar(); // Check that the appointment does not already exist
		if (findAppointment(cal) == null) { // Time slot available, okay to add appointment
			getAppointments().add(apt);
			this.appointments.sort(new SortAppointmentByCalendar());
			displayMessage("Appointment saved.");
			return true;
		} // else time slot taken, need to make another choice
		displayMessage("Cannot save; an appointment at that time already exists");
		return false;
	}

	/**
	 * Prompt user which appointment to remove from appointment cal(appointments)
	 * 
	 * @param cal display existing appointment
	 * @return true if appointment deleted
	 * @return false if appointment didn't exist at the date/time specified
	 */
	public boolean deleteAppointment(Calendar cal) {
		if (displayAppointment(cal)) { // display existing appointment on this date/time
			String okToChange = getResponseTo("Enter 'Yes' to delete this appointment");
			if (okToChange.trim().equals("Yes")) { // okay to proceed with change/deletion?
				getAppointments().remove(findAppointment(cal));
				displayMessage("Appointment deleted");
				return true;
			} else
				displayMessage("Request cancelled");
		}
		return false; // Appointment didn't exist at the date/time specified
	}

	/**
	 * change appointment with the cal specified in this scheduler
	 * 
	 * @param cal the calendar of the appointment date to be changed in this
	 *            scheduler
	 * @return true if appointment changed
	 * @return false Appointment didn't exist at the date/time specified
	 */
	public boolean changeAppointment(Calendar cal) {
		try {
			if (displayAppointment(cal)) { // display existing appointment on this date/time
				String okToChange = getResponseTo("Enter 'Yes' to change the date and time of this appointment ");
				if (okToChange.trim().equals("Yes")) {
					Calendar newCal = makeCalendarFromUserInput(false); // get new date/time
					if (findAppointment(newCal) == null) { // appointment time not already taken
						findAppointment(cal).setCalendar(newCal); // set new date/time in appointment
						displayMessage("Appointment re-booked");
						return true; // new appointment time set
					} else
						displayMessage("That time is already booked for an appointment");
				} else
					displayMessage("Request cancelled");
			}
		} catch (Exception e) {
		}
		return false; // Appointment does not exist, was unavailable, or cancelled
	}

	/**
	 * 
	 * @param cal appointment date
	 * @return true Appointment is exist
	 * @return false Appointment does not exist, was unavailable, or cancelled
	 */
	public boolean displayAppointment(Calendar cal) {
		Appointment apt = findAppointment(cal);
		int hr = cal.get(Calendar.HOUR_OF_DAY);
		displayMessage((apt != null) ? apt.toString() + "\n" : // Output the appointment as a string to the
																// console, otherwise...
				"No appointment scheduled between " + hr + ":00 and " + (hr + 1) + ":00\n");
		return (apt != null);
	}

	/**
	 * 
	 * @param cal Day's date
	 */
	private void displayDaySchedule(Calendar cal) {
		scheduleOutputTextArea.setText("");
		for (int hrCtr = 8; hrCtr < 17; hrCtr++) {
			cal.set(Calendar.HOUR_OF_DAY, hrCtr);
			Appointment apt = findAppointment(cal);
			int hr = cal.get(Calendar.HOUR_OF_DAY);
			scheduleOutputTextArea.append((apt != null) ? "\n" + apt.toString() + "\n\n" : // Output the appointment as
																							// a string to the
			// console, otherwise...
					"No appointment scheduled between " + hr + ":00 and " + (hr + 1) + ":00\n");
		}
	}

	public static void displayMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	/**
	 * Saves appointment to the file CurrentAppointments.apts
	 * 
	 * @param apts     the appointment list
	 * @param saveFile saves file to the saved file
	 * @return whether its saved or not
	 */
	private static boolean saveAppointmentsToFile(ArrayList<Appointment> apts, String saveFile) {
		try (FileOutputStream fos = new FileOutputStream(saveFile);
				ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			for (Appointment apt : apts)
				oos.writeObject(apt);
			displayMessage("Appointment data saved to " + saveFile);
			return true;
		} catch (IOException e) {
			displayMessage("Failed to load appointments from " + saveFile);
			return false;
		}
	}

	/**
	 * loads appointment from the file CurrentAppointments.apts
	 * 
	 * @param sourceFile this is where the file will be loaded
	 * @param apts       this is the list of the appointments
	 * @return true if it loads the appointment
	 * @return false if it can not find the appointment
	 */
	private static boolean loadAppointmentsFromFile(String sourceFile, ArrayList<Appointment> apts) {
		apts.clear(); // remove all existing appointments from the ArrayList before loading from file
		try (FileInputStream fis = new FileInputStream(sourceFile);
				ObjectInputStream ois = new ObjectInputStream(fis);) {
			while (true)
				apts.add((Appointment) ois.readObject());
		} catch (EOFException ex) {
			displayMessage("Appointments successfully loaded from " + sourceFile);
			return true;
		} catch (IOException | ClassNotFoundException e) {
			return false;
		}
	}

	/**
	 * 
	 * @return appointments in this scheduler
	 */
	private ArrayList<Appointment> getAppointments() {
		return appointments;
	}

	/**
	 * 
	 * @author Tsehay Gebremeskel
	 *
	 *         class used to sort the appointments
	 */
	public class SortAppointmentByCalendar implements Comparator<Appointment> {
		@Override
		public int compare(Appointment o1, Appointment o2) {
			return o1.getCalendar().compareTo(o2.getCalendar());
		}
	}
}
