package cst8284.asgmt4.scheduler;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import cst8284.asgmt4.employee.Dentist;

/* Adapted, with considerable modification, from 
 * http://www.java2s.com/Code/Java/Swing-JFC/TextAcceleratorExample.htm,
 * which is sloppy code and should not be emulated.
 */

public class AppointmentDialog {

	private static final GridBagConstraints textConstants = new GridBagConstraints(0, GridBagConstraints.RELATIVE, 1, 1,
			1, 1, // gridx, gridy, gridwidth, gridheight, weightx, weighty
			GridBagConstraints.EAST, 0, new Insets(2, 2, 2, 2), 1, 1); // anchor, fill, insets, ipadx, ipady
	private static final GridBagConstraints labelConstants = new GridBagConstraints(1, GridBagConstraints.RELATIVE, 1,
			1, 1.0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0);

	private static Container cp;
	private static final int labelWidth = 35;
	private static final Font defaultFont = new Font("SansSerif", Font.PLAIN, 16);

	private static Scheduler scheduler;
	private static JFrame f;

	public static void showAppointmentDialog(Scheduler scheduler) {
		AppointmentDialog.scheduler = scheduler;
		f = new JFrame("Get, set, change or delete an appointment");
		cp = f.getContentPane();
		cp.setLayout(new GridBagLayout());

		JTextField textName = setRow("Enter Client Name (as FirstName LastName):", 'n');
		JTextField textPhone = setRow("Phone Number (e.g. 613-555-1212):", 'p');
		JTextField textAppointmentDate = setRow("Appointment Date (entered as DDMMYYYY):", 'd');
		JTextField textAppointmentTime = setRow("Appointment Time:", 't');
		JTextField textDescription = setRow("Activity Description", 'a');
		JComboBox<String> comboType = createTypeCombobox();

		f.pack();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			}
		});

		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String action = e.getActionCommand();

				try {
					executeAction(action, textName, textPhone, textAppointmentDate, textAppointmentTime, textDescription, comboType);					
				}catch(Exception ex) {}
			}
		};

		JPanel btnPanel = new JPanel(new GridLayout(1, 4));

		JButton saveButton = new JButton("Save");
		JButton changeButton = new JButton("Change");
		JButton findButton = new JButton("Find");
		JButton deleteButton = new JButton("Delete");

		saveButton.addActionListener(actionListener);
		changeButton.addActionListener(actionListener);
		findButton.addActionListener(actionListener);
		deleteButton.addActionListener(actionListener);

		btnPanel.add(saveButton);
		btnPanel.add(changeButton);
		btnPanel.add(findButton);
		btnPanel.add(deleteButton);

		cp.add(btnPanel, labelConstants);
		f.setVisible(true);
	}

	private static JTextField setRow(String label, char keyboardShortcut) {
		JLabel l;
		JTextField t;
		cp.add(l = new JLabel(label, SwingConstants.RIGHT), textConstants);
		l.setFont(defaultFont);
		l.setDisplayedMnemonic(keyboardShortcut);
		cp.add(t = new JTextField(labelWidth), labelConstants);
		t.setFocusAccelerator(keyboardShortcut);
		return t;
	}
	
	private static JComboBox<String> createTypeCombobox() {
		JLabel l;
		String[] options = Dentist.getWorkDescription();
		JComboBox<String> combo;
		
		cp.add(l = new JLabel("Activity Type", SwingConstants.RIGHT), textConstants);
		l.setFont(defaultFont);
		l.setDisplayedMnemonic('c');
		cp.add(combo = new JComboBox<String>(options), labelConstants);
		return combo;
	}

	private static void executeAction(String action, JTextField textName, JTextField textPhone,
			JTextField textAppointmentDate, JTextField textAppointmentTime, JTextField textDescription, JComboBox<String> comboType) {

		String date = textAppointmentDate.getText();
		String time = textAppointmentTime.getText();
		if(time.equals("")) {
			throw new BadAppointmentDataException("Must enter a value", "Empty or null value entered");
		}
		Calendar cal = Scheduler.makeCalendar(date, time);

		switch (action) {
			case "Delete":
				scheduler.deleteAppointment(cal);
				break;
			case "Find":
				scheduler.displayAppointment(cal);
				break;
			case "Change":
				scheduler.changeAppointment(cal);
				break;
			case "Save":
				String fullName = textName.getText();
				if (fullName == null || fullName.isEmpty()) { // https://www.programiz.com/java-programming/examples/string-empty-null
					throw new BadAppointmentDataException("Must enter a value", "Empty or null value entered");
				}
	
				String phoneNumber = textPhone.getText();
				if (action == "Save" && (phoneNumber == null || phoneNumber.isEmpty())) {
					throw new BadAppointmentDataException("Must enter a value", "Empty or null value entered");
				}
				TelephoneNumber phone = new TelephoneNumber(phoneNumber);
				String activity = textDescription.getText();
				if (activity == null || activity.isEmpty()) {
					throw new BadAppointmentDataException("Must enter a value", "Empty or null value entered");
				}
				Activity act = new Activity(activity, comboType.getSelectedItem().toString());
				scheduler.saveAppointment(new Appointment(cal, fullName, phone, act));
				break;
		}

		f.dispose();
	}

}
