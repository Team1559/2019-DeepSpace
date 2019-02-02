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
	public static final int FRONT_RIGHT_MOTOR = 1;
	public static final int REAR_LEFT_MOTOR = 2;
	public static final int REAR_RIGHT_MOTOR = 3;
	
	// Grabber
	public static final int NTK_SOLENOID = -1; // This variable needs to be set 
	public static final int NTK_TALONSRX_BI = 1; // This variable needs to be set 
	public static final int NTK_TALONSRX_HR = 1; // This variable needs to be set 
	public static final int NTK_TALONSRX_HL = 2; // This variable needs to be set 
	public static final int NTK_DIGITALINPUT_LS1 = 0; // This variable needs to be set 
	public static final int NTK_DIGITALINPUT_LS2 = 1; // This variable needs to be set  
	public static final int NTK_DIGITALINPUT_LS3 = 2; // This variable needs to be set 
	public static final int NTK_DIGITALINPUT_LS4 = 3; // This variable needs to be set 


	// Lifter
	public static final int LIFTER_POT = 0; //This needs to be set
	public static final int LIFTER_TALON = 4; //This needs to be set
	// Stepper

	//DistSesnsor
	public static final int ULTRASONIC_TRIGGER_PULSE_INPUT = 0; //This needs to be set

}
