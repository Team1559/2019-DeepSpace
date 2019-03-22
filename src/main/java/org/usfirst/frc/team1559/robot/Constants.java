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
	//IR sensor
	public static final float IR_OFFSET_LEFT = 0;
	public static final float IR_OFFSET_RIGHT = 0.5f;
	// DriveTrain

	// Array positions
	public static final int FL_TALON = 0;
	public static final int RL_TALON = 1;
	public static final int FR_TALON = 2;
	public static final int RR_TALON = 3;
	// Grabber
	public static final int LOWERHATCH = 3;
	public static final int BTN_OUTTAKE = 5;
	public static final int BTN_STOP = 0;
	public static final int BTN_INTAKE = 6;
	public static final int BTN_HATCH_SLAP = 6;
	public static final int BTN_HATCH_UNSLAP = 5;
	public static final int HATCH_SNATCHER = 12;
	public static final int HATCH_SNATCHER2 = 11;
	// Lifter
	public static final int POT_BOTTOM = 0; //Lowest possible pot value (is probably zero)
	public static final int POT_TOP = 0; //Highest possible pot value
	
	// Stepper button values
	public static final int STEPPER_COPILOT_LIFT_UP = 1; //bottom left button on fightstick 1
	public static final int STEPPER_COPILOT_LIFT_DOWN = 2; //button to the right of bottom left on fightstick 2
	public static final int STEPPER_PILOT_EXTEND_PISTONS = 2; //X button on pilot controller
	public static final int STEPPER_PILOT_RETRACT_PISTONS = 4; //button to the right of touchpad on controller
	public static final int STEPPER_PILOT_DRIVE_FORWARD = 4; //right trigger on pilot
	public static final int STEPPER_PILOT_DRIVE_BACKWARD = 3; //left trigger on pilot
	//stepper potentiometer control buttons
	public static final int STEPPER_COPILOT_LIFT_UP_POT = 99; //TODO: needs to be set 1
	public static final int STEPPER_COPILOT_LIFT_DOWN_POT = 99; //TODO: needs to be set 1
	public static final int STEPPER_POT = 0;
	// Ultrasonic Stopper
	public static final int STOPPING_DISTANCE = -1; // TODO: Find this value.
	
	// Controllers
	public static final int PILOT_JOYSTICK = 0;
	public static final int COPILOT_JOYSTICK = 1;
	
	// Buttons
	public static final int BTN_FINE_DRIVE_CONTROL = 10;

	//Auto scoring
	//public static final int BTN_AUTO = 3;
	//public static final int BTN_AutoStop = 1;
	public static final int LINEASSIST = 2;
}
