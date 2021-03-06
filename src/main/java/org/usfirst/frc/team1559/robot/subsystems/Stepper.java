 package org.usfirst.frc.team1559.robot.subsystems;


import javax.lang.model.util.ElementScanner6;
import javax.swing.SpringLayout.Constraints;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Relay.Value;
import org.usfirst.frc.team1559.robot.OperatorInterface;
import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.Wiring;
import org.usfirst.frc.team1559.robot.Pixy;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import org.usfirst.frc.team1559.robot.Constants;

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
	 * 
	 * All values that need to be changed are created as variables; you should
	 * not need to alter any other code values to change speeds, positions, etc.
	 */

	//creates all objects
	private WPI_TalonSRX lifterMotor;
	private Talon driveMotor;
	private Solenoid pistons;
	
	//speed of motors (-1.0 to 1.0)

	private double liftSpeed = 1; //speed of lifterMotor

	//controls on and off of drive wheels
	private boolean driving;

 	//instantiates all talons and the solenoid, imports which port each is plugged into
 	public Stepper(OperatorInterface oi)
 	{
 		lifterMotor = new WPI_TalonSRX(Wiring.STEPPER_LIFTER_MOTOR);
 		driveMotor = new Talon(Wiring.STEPPER_DRIVE_MOTOR);
		pistons = new Solenoid(Wiring.STEPPER_PISTONS);
		driving = false;
		
		lifterMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog);

	}

 	//extends both back pistons
 	public void extendPistons()
 	{
		 pistons.set(true);
		 System.out.println("Extending pistons");
	}
	 
	//retracts back pistons
	public void retractPistons()
	{
		pistons.set(false);
		System.out.println("Retract Pistons");
	}

	//drives the front wheels forward
	public void driveForward(double speed)
	{
		driveMotor.set(speed);
	}

	//drives the front wheels backward
	public void driveBackward(double speed)
	{
		driveMotor.set(-speed);
	}

	//stops drive wheels
	public void stopDrive()
	{
		driveMotor.stopMotor();
	}

	//lifts the lifterMotor to its maximum height
	public void liftStepper()
	{
		lifterMotor.set(-liftSpeed);
		System.out.println("Lift Up");
	}

	//brings lifter back to lowest position; lifts the front of the robot
	public void lowerStepper()
	{
		lifterMotor.set(liftSpeed);

		System.out.println("Lift Down");
	}

	//stops the stepper motor
	public void stopStepper()
	{
		lifterMotor.stopMotor();
		System.out.println("Stepper Stopped Lifting");
	}

	public void activate()
	{
		//Stepper button controls

		//extends pistons
		
		if(Robot.oi.pilot.getRawButtonPressed(Constants.STEPPER_PILOT_EXTEND_PISTONS))
		{
			extendPistons();
		}
		//retracts pistons

		if(Robot.oi.pilot.getRawButtonPressed(Constants.STEPPER_PILOT_RETRACT_PISTONS))

		{
			retractPistons();
		}

		//drive controls for front wheels
		if(Robot.oi.pilot.getRawAxis(Constants.STEPPER_PILOT_DRIVE_FORWARD) >= 0.15)
		{
			driveForward(Robot.oi.pilot.getRawAxis(Constants.STEPPER_PILOT_DRIVE_FORWARD));
		}
		else if(Robot.oi.pilot.getRawAxis(Constants.STEPPER_PILOT_DRIVE_BACKWARD) >= 0.15)
		{
			driveBackward(Robot.oi.pilot.getRawAxis(Constants.STEPPER_PILOT_DRIVE_BACKWARD));
		}
		else
		{
			stopDrive();
		}
		
		//manually moves lifter
		if(Robot.oi.copilot.getRawButton(Constants.STEPPER_COPILOT_LIFT_UP))
		{
			Robot.pixy2.lampoff();
			Robot.LED_Relay.set(Value.kOff);
			liftStepper();
		}
		else if(Robot.oi.copilot.getRawButton(Constants.STEPPER_COPILOT_LIFT_DOWN))
		{
			Robot.pixy2.lampoff();
			Robot.LED_Relay.set(Value.kOff);
			lowerStepper();
		}
		else
		{
			stopStepper();
		}
	}
}
