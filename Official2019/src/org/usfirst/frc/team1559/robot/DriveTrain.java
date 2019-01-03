package org.usfirst.frc.team1559.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain {
	private DifferentialDrive drive;
	private SpeedControllerGroup leftSide, rightSide;
	private WPI_TalonSRX frontLeft, rearLeft, frontRight, rearRight;
	
	public DriveTrain() {
		frontLeft = new WPI_TalonSRX(Wiring.Front_Left_Drive);
		rearLeft = new WPI_TalonSRX(Wiring.Rear_Left_Drive);
		frontRight = new WPI_TalonSRX(Wiring.Front_Right_Drive);
		rearRight = new WPI_TalonSRX(Wiring.Rear_Right_Drive);
		
		leftSide = new SpeedControllerGroup(frontLeft, rearLeft);
		rightSide = new SpeedControllerGroup(frontRight, rearRight);
		
		drive = new DifferentialDrive(leftSide, rightSide);
	}
	
	public void tankDrive(double leftSpeed, double rightSpeed) {
		drive.tankDrive(leftSpeed, rightSpeed);
	}
}
