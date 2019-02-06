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
	public static final int FRONT_LEFT_MOTOR = 0;
	public static final int FRONT_RIGHT_MOTOR = 100; //Temporary for testing Lifter.
	public static final int REAR_LEFT_MOTOR = 2;
	public static final int REAR_RIGHT_MOTOR = 3;
	
	// Grabber
	public static final int NTK_SOLENOID = -1; // This variable needs to be set 
	public static final int NTK_SPARK = -1; // This variable needs to be set 
	
	// Lifter
	public static final int LIFTER_POT = 0; //This needs to be set
	public static final int LIFTER_TALON = 1; //This needs to be set. Temporary for testing Lifter
	
	// Stepper
	public static final int STEPPER_LIFTER_MOTOR = 10;
	public static final int STEPPER_ROTATIONAL_MOTOR = 11;
	public static final int STEPPER_DRIVE_MOTOR = 12;
	public static final int STEPPER_PISTONS = 9;
	public static final int STEPPER_POT = 0; //TODO: set the value for the potentiometer port

	//DistSesnsor
	public static final int ULTRASONIC_TRIGGER_PULSE_INPUT = 0; //This needs to be set

	

}
