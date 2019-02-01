package org.usfirst.frc.team1559.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team1559.robot.Wiring;

import edu.wpi.first.wpilibj.Solenoid;

public class Stepper {
	/*
	 * Designated Programmers of the Stepper:
	 * Nicholas Merante
	 * 
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
	 * either a TalonSRX or a spark to control them. The wheels will pull the robot forward once they
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
	 */

	//Fr this code, the back of the robot will be called the front and vice versa, as the robot
	//will be backing onto the step. All names are based on this fact.

	private TalonSRX lifterMotor;
	private TalonSRX rotationalMotor;
	private TalonSRX driveMotor;
	private Solenoid leftPiston;
	private Solenoid rightPiston;

	public Stepper()
	{
		lifterMotor = new TalonSRX(Wiring.STEPPER_LIFTER_MOTOR);
		rotationalMotor = new TalonSRX(Wiring.STEPPER_ROTATIONAL_MOTOR);
		driveMotor = new TalonSRX(Wiring.STEPPER_DRIVE_MOTOR);
		leftPiston = new Solenoid(Wiring.STEPPER_LEFT_PISTON);
		rightPiston = new Solenoid(Wiring.STEPPER_RIGHT_PISTON);
	}

	public void liftBack()
	{
		leftPiston.set(true);
		rightPiston.set(true);
	}
	
	//moves lifterMotor to correct position for rotationalMotor
	public void positionLifter()
	{
		
	}

	//have back rotationalMotor spin to lift front
	public void liftFront()
	{
		
	}

	//drives the front wheels
	public void drive()
	{
		
	}
}