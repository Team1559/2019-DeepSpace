package org.usfirst.frc.team1559.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.Joystick;

public class DriveTrain 
{
	private static final int TIMEOUT = 0;
	public static final double WHEEL_RADIUS_INCHES_MECANUM = 3;
	public static final double MAX_SPEED_FPS_TRACTION = 9.67 * 1.01;
	public static final double MAX_TICKS_PER_100MS = MAX_SPEED_FPS_TRACTION * 4096.0 / (Math.PI * WHEEL_RADIUS_INCHES_MECANUM * 2.0 / 12.0) / 10.0;
	private MecanumDrive drive;
	public WPI_TalonSRX[] talons;

	public DriveTrain() 
	{
		talons = new WPI_TalonSRX[4];
		talons[Constants.FL_TALON] = new WPI_TalonSRX(Wiring.FRONT_LEFT_MOTOR);
		talons[Constants.RL_TALON] = new WPI_TalonSRX(Wiring.REAR_LEFT_MOTOR);
		talons[Constants.FR_TALON] = new WPI_TalonSRX(Wiring.FRONT_RIGHT_MOTOR);
		talons[Constants.RR_TALON] = new WPI_TalonSRX(Wiring.REAR_RIGHT_MOTOR);

		for(int i = 0; i < talons.length; i++) {
			configTalons(talons[i]);
		}

		drive = new MecanumDrive(talons[Constants.FL_TALON], talons[Constants.RL_TALON], talons[Constants.FR_TALON], talons[Constants.RR_TALON]);
		drive.setMaxOutput(7350); // Sets the maximum 

	}

	public void driveCartesian(double ySpeed, double xSpeed, double zRotation) 
	{
		drive.driveCartesian(ySpeed, xSpeed, zRotation);
	}

	/**
	* @param talon
	* Takes in any talon and configures it to be a driveTrain talon.
	* 
	**/
	public void configTalons(WPI_TalonSRX talon) {
		talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

		talon.set(ControlMode.Velocity, 0);

		//talon.configClosedloopRamp(0.0, TIMEOUT);
		//talon.configOpenloopRamp(0.15, TIMEOUT);

		talon.configNominalOutputForward(0, TIMEOUT);
		talon.configNominalOutputReverse(0, TIMEOUT);
		talon.configPeakOutputForward(+1, TIMEOUT);
		talon.configPeakOutputReverse(-1, TIMEOUT);


		//F-gain = (100% X 1023) / 9326 F-gain = 0.1097
		
		talon.config_kP(0, 3.96, TIMEOUT);
		/*
		talon.config_kI(slotIdx, value, timeoutMs);
		talon.config_kD(slotIdx, value, timeoutMs);
		*/
		talon.config_kF(0, 0.139183673, TIMEOUT);
		// 613.8/155
		


		//talon.setNeutralMode(NeutralMode.Coast);
		talon.configNeutralDeadband(0.01);

	}
}


