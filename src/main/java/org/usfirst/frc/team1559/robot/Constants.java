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

		// Array positions
		public static final int FL_TALON = 0;
		public static final int RL_TALON = 1;
		public static final int FR_TALON = 2;
		public static final int RR_TALON = 3;
	// Grabber
	public static final int BTN_OUTTAKE = 3;
	public static final int BTN_STOP = 2;
	public static final int BTN_INTAKE = 1;
	public static final int BTN_HATCH_SLAP = 6;
	public static final int BTN_HATCH_UNSLAP = 5;

	// Lifter
	// public static final short LIFTER_POS1 = 1;
	// public static final short LIFTER_POS2 = 2;
	// public static final short LIFTER_POS3 = 3;
	// public static final short LIFTER_POS4 = 4;
	// public static final short LIFTER_TOP = 97; //Highest point on the lifter
	// public static final short LIFTER_BOTTOM = 98; //Lowest point on the lifter
	public static final int POT_BOTTOM = 0; //Lowest possible pot value (is probably zero)
	public static final int POT_TOP = 0; //Highest possible pot value
	
	// Stepper button values
	public static final int STEPPER_COPILOT_LIFT_UP = 0; //bottom left button on fightstick
	public static final int STEPPER_COPILOT_LIFT_DOWN = 1; //button to the right of bottom left on fightstick
	public static final int STEPPER_PILOT_DRIVE_FORWARD = 0; //Square on controller
	public static final int STEPPER_PILOT_DRIVE_BACKWARD = 2; //Circle on controller
	public static final int STEPPER_PILOT_PULL_PISTONS = 9; //button to the right of touchpad on controller

	// Ultrasonic Stopper
	public static final int STOPPING_DISTANCE = -1; // TODO: Find this value.
	
	// Controllers
	public static final int PILOT_JOYSTICK = 0;
	public static final int COPILOT_JOYSTICK = 1;
	
	// Buttons
	public static final int BTN_FINE_DRIVE_CONTROL = 10;

	//Auto scoring
	public static final int BTN_AUTO = 3;
	public static final int BTN_AutoStop = 1;
	public static final int LINEASSIST = 2;
}
