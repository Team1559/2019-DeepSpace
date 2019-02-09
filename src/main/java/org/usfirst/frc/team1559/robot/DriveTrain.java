package org.usfirst.frc.team1559.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team1559.robot.subsystems.DevilDrive;

import edu.wpi.first.wpilibj.drive.MecanumDrive;


public class DriveTrain 
{
	private static final int TIMEOUT = 0;
	public static final double WHEEL_RADIUS_INCHES_MECANUM = 3;
	public static final double MAX_SPEED_FPS_TRACTION = 9.67 * 1.01;
	public static final double MAX_TICKS_PER_100MS = MAX_SPEED_FPS_TRACTION * 4096.0 / (Math.PI * WHEEL_RADIUS_INCHES_MECANUM * 2.0 / 12.0) / 10.0;
	private DevilDrive drive;
	public WPI_TalonSRX FL_TALON, RL_TALON, FR_TALON, RR_TALON;
	private static final double kF = 0; //F-gain = (100% X 1023) / 7350 F-gain = 0.139183673 - (7350 is max speed)
	private static final double kP = 0; // P-gain = (.1*1023)/(155) = 0.66 - (155 is average error)


	public DriveTrain() 
	{
		FL_TALON = new WPI_TalonSRX(Wiring.FRONT_LEFT_MOTOR);
		RL_TALON = new WPI_TalonSRX(Wiring.REAR_LEFT_MOTOR);
		FR_TALON = new WPI_TalonSRX(Wiring.FRONT_RIGHT_MOTOR);
		RR_TALON = new WPI_TalonSRX(Wiring.REAR_RIGHT_MOTOR);

			
		FL_TALON.configClosedloopRamp(0.05, TIMEOUT);
		FL_TALON.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
		FL_TALON.config_kF(1, kF);
		FL_TALON.config_kP(1, kP);

		FR_TALON.set(ControlMode.Velocity, 2);
		FR_TALON.configClosedloopRamp(0.05, TIMEOUT);
		FR_TALON.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);	
		FR_TALON.config_kF(2, kF);
		FR_TALON.config_kP(2, kP);

		RL_TALON.set(ControlMode.Velocity, 2);
		RL_TALON.configClosedloopRamp(0.05, TIMEOUT);
		RL_TALON.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);	
		RL_TALON.config_kF(3, kF);
		RL_TALON.config_kP(3, kP);

		RR_TALON.set(ControlMode.Velocity, 2);
		RR_TALON.configClosedloopRamp(0.05, TIMEOUT);
		RR_TALON.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);	
		RR_TALON.config_kF(4, kF);
		RR_TALON.config_kP(4, kP);

		System.out.println(FL_TALON.getControlMode());
		System.out.println(FR_TALON.getControlMode());
		System.out.println(RL_TALON.getControlMode());
		System.out.println(RR_TALON.getControlMode());



		
		//talon.configOpenloopRamp(0.15, TIMEOUT);

		/*
		talon.configNominalOutputForward(0, TIMEOUT);
		talon.configNominalOutputReverse(0, TIMEOUT);
		talon.configPeakOutputForward(+1, TIMEOUT);
		talon.configPeakOutputReverse(-1, TIMEOUT);
		*/


		//talon.setNeutralMode(NeutralMode.Coast);
		drive = new DevilDrive(FL_TALON, RL_TALON, FR_TALON, RR_TALON);
		drive.setMaxOutput(7350); // Sets the maximum 'speed' // 7350

	}

	public void driveCartesian(double ySpeed, double xSpeed, double zRotation) 
	{

		drive.driveCartesian(ySpeed, xSpeed, zRotation);
	}
}


