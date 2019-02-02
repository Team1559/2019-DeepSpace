package org.usfirst.frc.team1559.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;

public class DriveTrain 
{
	/*
	 * Designated Programmers of the DriveTrain:
	 * 
	 * Michael Nersinger
	 * 
	 * The DriveTrain class is designed to implement functionality for the drive system. This is
	 * to simplify the Robot class by delegating declaration to another class.
	 * 
	 */
	private MecanumDrive drive;
	private WPI_TalonSRX frontLeft, rearLeft, frontRight, rearRight;

	public DriveTrain() 
	{
		frontLeft = new WPI_TalonSRX(Wiring.FRONT_LEFT_MOTOR);
		rearLeft = new WPI_TalonSRX(Wiring.REAR_LEFT_MOTOR);
		frontRight = new WPI_TalonSRX(Wiring.FRONT_RIGHT_MOTOR);
		rearRight = new WPI_TalonSRX(Wiring.REAR_RIGHT_MOTOR);

	    frontLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		rearLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		frontRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		rearRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

		frontLeft.set(ControlMode.Velocity, 0);
		rearLeft.set(ControlMode.Velocity, 0);
		frontRight.set(ControlMode.Velocity, 0);
		rearRight.set(ControlMode.Velocity, 0);

		drive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);
	}

	public void driveCartesian(double ySpeed, double xSpeed, double zRotation) 
	{
		
		drive.driveCartesian(ySpeed, xSpeed, zRotation);
	}
}

