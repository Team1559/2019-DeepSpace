package org.usfirst.frc.team1559.robot;

public interface Constants {
	/*
	 * ALL MEMBERS ARE REQUIRED TO MAINTAIN THIS CLASS
	 * 
	 * This class is meant to store all non-electrical/port values so that they can easily be
	 * modified and viewed here. (ints, doubles, booleans ONLY)
	 * 
	 * example:
	 * 
	 * public static final int VAL_OF_NUM = 0;
	 * 
	 * where
	 * 
	 * public = Accessible to all classes
	 * static = Non-abstract reference
	 * final = value can not be modified other than where it is declared
	 */
	
	// DriveTrain

	// Grabber
	public static final int BTN_OUTTAKE = 3;
	public static final int BTN_INTAKE = 1;
	public static final int BTN_HATCH_UNLOCK = 6;
	public static final int BTN_HATCH_LOCK = 7;

	// Lifter
	public static final short LIFTER_POS1 = 1;
	public static final short LIFTER_POS2 = 2;
	public static final short LIFTER_POS3 = 3;
	public static final short LIFTER_POS4 = 4;
	public static final short LIFTER_TOP = 97; //Highest point on the lifter
	public static final short LIFTER_BOTTOM = 98; //Lowest point on the lifter

	// Stepper

	// Ultrasonic Stopper
	public static final int STOPPING_DISTANCE = -1; // TODO: Find this value.
	
	// Controllers
	public static final int PILOT_JOYSTICK = 0;
	public static final int COPILOT_JOYSTICK = 1;
	
	// Buttons
	public static final int BTN_FINE_DRIVE_CONTROL = 10;

}
