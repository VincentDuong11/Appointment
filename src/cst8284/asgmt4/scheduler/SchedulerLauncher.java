package cst8284.asgmt4.scheduler;

import cst8284.asgmt4.employee.Dentist;

/**
 * Application launcher
 * @author Tsehay Gebremeskel
 * @version 1 
 * CourseName: cst8284    
 * ClassName:  SchedulerLauncher.java   
 * Date: 27/11/2019 
 */

public class SchedulerLauncher {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				(new Scheduler(new Dentist("Dr. Andrews"))).launch();				
			}
		});
	}
}
