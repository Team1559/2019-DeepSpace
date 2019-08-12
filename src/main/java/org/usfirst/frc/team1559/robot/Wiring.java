package org.usfirst.frc.team1559.robot;

public interface Wiring {
	/*
	 * ALL MEMBERS ARE REQUIRED TO MAINTAIN THIS CLASS
	 * 
	 * The purpose of the Wiring class is to store all electrical ports and configurations in one place. These values
	 * include ints and doubles ONLY.
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
	public static final int FRONT_LEFT_MOTOR = 1;
	public static final int FRONT_RIGHT_MOTOR = 2;
	public static final int REAR_LEFT_MOTOR = 3;
	public static final int REAR_RIGHT_MOTOR = 4;

	// Grabber
	public static final int Newhatchsoloniod = 3;//new hatch piston
	public static final int NTK_SOLENOID = 0; // hatch pistion
	public static final int NTK_TALONSRX_BI = 9; // This variable needs to be set 

	// Lifter   
	public static final int LIFTER_POT = 0; //This needs to be set
	public static final int LIFTER_TALON = 5; //Normal 5. Test roboRIO is 15

	//Stepper port constants
	public static final int STEPPER_LIFTER_MOTOR = 6;
	public static final int STEPPER_DRIVE_MOTOR = 4;
	public static final int STEPPER_PISTONS = 1;

	//DistSesnsor
	public static final int ULTRASONIC_TRIGGER_PULSE_INPUT = 0; //This needs to be set

	

}
