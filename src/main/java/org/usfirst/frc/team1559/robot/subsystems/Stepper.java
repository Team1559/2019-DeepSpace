// package org.usfirst.frc.team1559.robot.subsystems;

// import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

// import org.usfirst.frc.team1559.robot.Wiring;

// import edu.wpi.first.wpilibj.Solenoid;

// public class Stepper {
// 	/*
// 	 * Designated Programmers of the Stepper:
// 	 * Nicholas Merante
// 	 * 
// 	 * ____________________________________________
// 	 * 
// 	 * Pistons:
// 	 * There are two pistons located at the front of the robot. These will be at the back when 
// 	 * the robot is climbing the step, since the robot is going up backwards. The pistons will 
// 	 * be powered by two solenoids. The pistons will be toggled on by a push of a button, and  
// 	 * there will be some sort of emergency stop.
// 	 * 
// 	 * Lifter:
// 	 * The back of the robot will have a lifter in the middle. This will aid in lifting the back 
// 	 * of the robot to elevate the back wheels to be higher than the step. The lifter will have a
// 	 * motor controlling it to lift it up and down manually.
// 	 * 
// 	 * Arms:
// 	 * Two arms will flip to face out on the lifter. These will be controlled by two servos which can
// 	 * be toggled out and in.
// 	 * 
// 	 * Wheels:
// 	 * Attached to the end of the arms are two wheels. These wheels will each have a miniCim motor and
// 	 * either a WPI_TalonSRX or a spark to control them. The wheels will pull the robot forward once they
// 	 * make contact with the step to help get the main drive wheels in contact with the step. These should
// 	 * be controlled using a similar method as the main drive system.
// 	 * 
// 	 * ____________________________________________
// 	 * 
// 	 * 
// 	 * This is the equivalent of previous year's climbers. In order to advance to platform two and three in
// 	 * the habitat, a step mechanism must be implemented.
// 	 * 
// 	 * "One small step for man. One giant leap for mankind"
// 	 * 		-Neil Armstrong
// 	 *
// 	 * For this code, the back of the robot will be reffered to as the front and vice versa, since the 
// 	 * robot will be backing onto the step. All names are based on this fact.
// 	 */

// 	private WPI_TalonSRX lifterMotor;
// 	private WPI_TalonSRX rotationalMotor;
// 	private WPI_TalonSRX driveMotor;
// 	private Solenoid pistons;

// 	//instantiates all talons and the solenoid, imports which port each is plugged into
// 	public Stepper()
// 	{
// 		lifterMotor = new WPI_TalonSRX(Wiring.STEPPER_LIFTER_MOTOR);
// 		rotationalMotor = new WPI_TalonSRX(Wiring.STEPPER_ROTATIONAL_MOTOR);
// 		driveMotor = new WPI_TalonSRX(Wiring.STEPPER_DRIVE_MOTOR);
// 		pistons = new Solenoid(Wiring.STEPPER_PISTONS);
// 	}

// 	//extends or retracts both back pistons
// 	public void extendPistons(boolean extend)
// 	{
// 		pistons.set(extend);
// 	}

// 	//moves lifterMotor to correct position for rotationalMotor
// 	public void positionLifter()
// 	{
		
// 	}

// 	//have back rotationalMotor spin to lift front
// 	public void positionRotationMotor()
// 	{
		
// 	}

// 	//drives the front wheels on a scale of -1.0 to 1.0
// 	public void setDriveSpeed(double percent)
// 	{
// 		driveMotor.set(percent);
// 	}
// }