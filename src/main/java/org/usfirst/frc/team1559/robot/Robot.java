/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;
import org.usfirst.frc.team1559.robot.subsystems.Grabber;
import org.usfirst.frc.team1559.robot.subsystems.pixylinevector;
import org.usfirst.frc.team1559.robot.Vision;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.AnalogInput;



import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1559.robot.subsystems.Lifter;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team1559.robot.OperatorInterface;

public class Robot extends TimedRobot {
	/*
	 * ALL MEMBERS ARE REQUIRED TO IMPLEMENT THEIR SUBSYSTEM INTO THE ROBOT CLASS
	 * 
	 * From the seasons up to and including 2018, we used the IterativeRobot class. As per WPI's
	 * annual update, classed are added, removed, or deprecated. The IterativeRobot class was
	 * deprecated as of 2019. TimedRobot is the closest match to the IterativeRobot class.
	*/
	public DriveTrain drive;
	private OperatorInterface oi;

	private Pixy pixy2;
	public static boolean fightstick = true;
	private boolean isCargo = true;
	//private static Lifter lifter;
	private static Grabber grabber; 
	public static boolean dBounce = false;

	public static DistSensor dist;
	public static Vision vision;

	private float Kx;
    private float Ky;
	private float Kr;
	
	@Override
	public void robotInit() {
		drive = new DriveTrain();


		oi = new OperatorInterface();
		//lifter = new Lifter(oi); //Keep this in mind for future games! This type of coding could prove useful!
		pixy2 = new Pixy();
		vision = new Vision();
		Kx = 0.025f; // maximum pixy translation (1/2 frame with)
		Kr = 0.014f; // maximum pixy angle
		Ky = 0.5f;

		
		//grabber = new Grabber();
		dist = new DistSensor( new AnalogInput(0));
		//dSensor.setAutomaticMode(true);
		//dSensor.stopRobot();
	}
		@Override
		public void robotPeriodic() {

		
		}
	


	@Override
	public void autonomousInit() {
		pixy2.start();
		// No autonomous neccesary
	}

	@Override
	public void autonomousPeriodic() {
		// No autonomous neccesary
	}

	@Override
	public void teleopInit() {
		pixy2.start();
		pixy2.lampon();
		vision.VisionInit();
	}

	@Override
	public void teleopPeriodic() {
		
		System.out.println(drive.FL_TALON.getControlMode());
		System.out.println(drive.FR_TALON.getControlMode());
		System.out.println(drive.RL_TALON.getControlMode());
		System.out.println(drive.RR_TALON.getControlMode());
		//Lifter
		//lifter.driveLifter();
		
		//Camera

		pixylinevector v=pixy2.getvector();
		vision.update();
		VisionData vData = vision.getData();
		vData.Print();

		
		
		
		// Drive Train
		//System.out.println(drive.talons[0].getMotorOutputPercent());
		
		
		
		
		double distance = dist.getRange();
		double maxPixyRange = 24.0;
		SmartDashboard.putNumber("IRDistance,", distance);
		if(v.status == 1 && distance <= maxPixyRange)

		//System.out.println("Y: " + oi.getPilotY() + " X: " + oi.getPilotX() + " Z: " + oi.getPilotZ());
		drive.driveCartesian(oi.getPilotX(), oi.getPilotY(), oi.getPilotZ());
		//drive.driveCartesian(0.0, 0.1, 0.0);
		/*
		if(v.status == 1)
		{
			SmartDashboard.putNumber("__x",pixy2.getEx());
			SmartDashboard.putNumber("__y", distance);
			SmartDashboard.putNumber("__r",pixy2.getEr());
			SmartDashboard.putString("Mode","pixy");
			drive.driveCartesian(Kx * pixy2.getEx(), 0 , Kr * pixy2.getEr());
		}
		else if (vData.status == 1) {
			SmartDashboard.putNumber("__x",vData.x);
			SmartDashboard.putNumber("__y",vData.y);
			SmartDashboard.putNumber("__r",vData.r);	
			SmartDashboard.putString("Mode","jetson");
			drive.driveCartesian(Kx * vData.x, Ky * vData.y , Kr * vData.r);
		}
		else{
			SmartDashboard.putString("Mode","driver");
			SmartDashboard.putNumber("__x",oi.getPilotX());
			SmartDashboard.putNumber("__y",oi.getPilotY());
			SmartDashboard.putNumber("__r",oi.getPilotZ());
			drive.driveCartesian(oi.getPilotX(), oi.getPilotY(), oi.getPilotZ());
		}
		*/
	}
	// Grabber
		// if(oi.pilot.getRawButtonPressed(Constants.BTN_INTAKE)) {
		// 	grabber.getCargo();
		// } else if(oi.pilot.getRawButtonPressed(Constants.BTN_OUTTAKE)) {
		// 	grabber.removeCargo();
		// }

		// if(oi.pilot.getRawButtonPressed(Constants.BTN_HATCH_LOCK)) {
		// 	grabber.getHatch();
		// } else if(oi.pilot.getRawButtonPressed(Constants.BTN_HATCH_UNLOCK)) {
		// 	grabber.bringHatch();

		// }

		// if(oi.pilot.getRawButtonPressed(Constants.BTN_AUTO) || dBounce == true){
		// 	dBounce = true;
		// 	Auto.pixydrive();
			
			//drive.driveCartesian(.5, .5, 0); //replace with Jetson data
			/*if(dist.getRange() == 18)
			{
			}*/
			/*if(oi.pilot.getRawButtonPressed(Constants.BTN_AUTO))
			{
				dBounce = false;
			}*/
		
	@Override
	public void testInit() {
		
	}




	@Override
	public void testPeriodic() {
	
	}


@Override
public void disabledInit() {
	pixy2.lampoff();
}

@Override
public void disabledPeriodic() {

}


}
