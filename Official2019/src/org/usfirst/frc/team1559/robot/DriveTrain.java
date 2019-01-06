package org.usfirst.frc.team1559.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain {
	/*
	 * Designated Programmers of the DriveTrain:
	 * Michael Nersinger
	 * Alek Ahrens
	 * XXX
	 * 
	 * The DriveTrain class is designed to implement functionality for the drive system. This is
	 * to simplify the Robot class by delegating declaration to another class.
	 * 
	 */
	private DifferentialDrive drive;
	private SpeedControllerGroup leftSide, rightSide;
	private WPI_TalonSRX frontLeft, rearLeft, frontRight, rearRight;
	
	public DriveTrain() {
		frontLeft = new WPI_TalonSRX(Wiring.FRONT_LEFT_MOTOR);
		rearLeft = new WPI_TalonSRX(Wiring.REAR_LEFT_MOTOR);
		frontRight = new WPI_TalonSRX(Wiring.FRONT_RIGHT_MOTOR);
		rearRight = new WPI_TalonSRX(Wiring.REAR_RIGHT_MOTOR);
		
		leftSide = new SpeedControllerGroup(frontLeft, rearLeft);
		rightSide = new SpeedControllerGroup(frontRight, rearRight);
		
		drive = new DifferentialDrive(leftSide, rightSide);
	}
	
	public void tankDrive(double leftSpeed, double rightSpeed) {
		drive.tankDrive(leftSpeed, rightSpeed);
	}
}
