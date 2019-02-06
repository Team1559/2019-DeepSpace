 package org.usfirst.frc.team1559.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


import org.usfirst.frc.team1559.robot.Wiring;


import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class Stepper {
	/*
	 * Designated Programmers of the Stepper:
	 * Nicholas Merante
	 * Noah Hartman
	 * ____________________________________________
	 * 
	 * Pistons:
	 * There are two pistons located at the front of the robot. These will be at the back when 
	 * the robot is climbing the step, since the robot is going up backwards. The pistons will 
	 * be powered by two solenoids. The pistons will be toggled on by a push of a button, and  
	 * there will be some sort of emergency stop.
	 * 
	 * Lifter:
	 * The back of the robot will have a lifter in the middle. This will aid in lifting the back 
	 * of the robot to elevate the back wheels to be higher than the step. The lifter will have a
	 * motor controlling it to lift it up and down manually.
	 * 
	 * Arms:
	 * Two arms will flip to face out on the lifter. These will be controlled by two servos which can
	 * be toggled out and in.
	 * 
	 * Wheels:
	 * Attached to the end of the arms are two wheels. These wheels will each have a miniCim motor and
	 * either a WPI_TalonSRX or a spark to control them. The wheels will pull the robot forward once they
	 * make contact with the step to help get the main drive wheels in contact with the step. These should
	 * be controlled using a similar method as the main drive system.
	 * 
	 * ____________________________________________
	 * 
	 * 
	 * This is the equivalent of previous year's climbers. In order to advance to platform two and three in
	 * the habitat, a step mechanism must be implemented.
	 * 
	 * "One small step for man. One giant leap for mankind"
	 * 		-Neil Armstrong
	 *
	 * For this code, the back of the robot will be reffered to as the front and vice versa, since the 
	 * robot will be backing onto the step. All names are based on this fact.
	 */

	private WPI_TalonSRX lifterMotor;
	private WPI_TalonSRX rotationalMotor;
	private WPI_TalonSRX driveMotor;
	private Solenoid pistons;
	private double motorPositionValue = 0; //the encoder value that the lifterMotor should stop at
	private double rotationPositionValue = 0; //the encoder value for the rotationalMotor; should be 180 degrees
	private int rotationCounter = 0;


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


	//gets potentiometer position
	public int getPot()
	{
		return lifterMotor.getSelectedSensorPosition(Wiring.STEPPER_POT);
	}

	//moves lifterMotor to correct position for rotationalMotor
	public void positionLifter()
	{
		lifterMotor.set(ControlMode.Position, motorPositionValue);
	}

	//have back rotationalMotor spin 180 degrees to prepare for lifting
	public void positionRotationMotor()
	{
		while(rotationCounter < 100) //change the value depending on how long motor needs to run for
		{
			rotationalMotor.set(0.8);
		}
		rotationalMotor.set(0);
	}

	//drives the front wheels on a scale of -1.0 to 1.0
	public void setDriveSpeed(double percent)
	{
		driveMotor.set(percent);
	}

	//resets the rotationCounter value
	public void resetStepper()
	{
		rotationCounter = 0;
	}
}
