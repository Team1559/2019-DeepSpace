/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;
import org.usfirst.frc.team1559.robot.subsystems.Grabber;
import org.usfirst.frc.team1559.robot.subsystems.pixylinevector;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1559.robot.subsystems.Lifter;
import org.usfirst.frc.team1559.robot.OperatorInterface;

public class Robot extends TimedRobot {
	/*
	 * ALL MEMBERS ARE REQUIRED TO IMPLEMENT THEIR SUBSYSTEM INTO THE ROBOT CLASS
	 * 
	 * From the seasons up to and including 2018, we used the IterativeRobot class. As per WPI's
	 * annual update, classed are added, removed, or deprecated. The IterativeRobot class was
	 * deprecated as of 2019. TimedRobot is the closest match to the IterativeRobot class.
	*/
	public static DriveTrain drive;
	private OperatorInterface oi;

	private Pixy pixy2;
	public static boolean fightstick = true;
	private boolean isCargo = true;
	private static Lifter lifter;
	private static Grabber grabber; 

	public static boolean dBounce = false;
	public static DistSensor dist;
	private float Kx;
    private float Ky;
	private float Kr;
	
	@Override
	public void robotInit() {
		drive = new DriveTrain();
		oi = new OperatorInterface();

		grabber = new Grabber(oi);

		lifter = new Lifter(oi); //Keep this in mind for future games! This type of coding could prove useful!
		pixy2 = new Pixy();
		
		Kx = 0.025f; // maximum pixy translation (1/2 frame with)
		Kr = 0.014f; // maximum pixy angle
		Ky = 0.5f;

		//dSensor = new DistSensor();
		
		//grabber = new Grabber();

		//DistSensor dSensor = new DistSensor();
		//dSensor.setAutomaticMode(true);
		//dSensor.stopRobot();
	}
		@Override
		public void robotPeriodic() {

		
		}
	


	@Override
	public void autonomousInit() {
		//pixy2.start();
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

	}

	@Override
	public void teleopPeriodic() {

		//Lifter
		lifter.driveLifter();
		
		//Camera


		pixylinevector v=pixy2.getvector();
		
		
		
		// Drive Train


		// Grabber
		grabber.drive();

		
		
		
		

		//drive.driveCartesian(oi.getPilotX(), oi.getPilotY(), oi.getPilotZ());
		if(v.status == 1)
		{
	
			SmartDashboard.putNumber("__getEx,", pixy2.getEx());
			SmartDashboard.putNumber("__getEr,", pixy2.getEr());
			SmartDashboard.putNumber("__x",Kx * pixy2.getEx());
			SmartDashboard.putNumber("__r",Kr * pixy2.getEr());
			SmartDashboard.putNumber("__Kx",Kx );
			SmartDashboard.putNumber("__Kr",Kr);
			drive.driveCartesian(Kx * pixy2.getEx(), Ky , Kr * pixy2.getEr());
			
		
	}


		else{
			drive.driveCartesian(oi.getPilotX(), oi.getPilotY(), oi.getPilotZ());
		}
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

