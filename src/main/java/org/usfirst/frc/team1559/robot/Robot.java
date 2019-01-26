/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;

import java.util.Arrays;

import org.usfirst.frc.team1559.robot.subsystems.pixylinevector;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


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
	private SerialTest pixy2;
	
	@Override
	public void robotInit() {
		drive = new DriveTrain();
		oi = new OperatorInterface();
		pixy2 = new SerialTest();
		//dSensor = new DistSensor();
		pixy2.lampon();
		//dSensor.setAutomaticMode(true);
	}

	@Override
	public void autonomousInit() {

	}

	@Override
	public void autonomousPeriodic() {
		
	}

	@Override
	public void teleopInit() {
		pixy2.start();
	}

	@Override
	public void teleopPeriodic() {
		pixylinevector v=pixy2.getvector();
		drive.driveCartesian(oi.getPilotY(), oi.getPilotX(), oi.getPilotZ());
		//dSensor.stopRobot();
	}

	@Override
	public void testPeriodic() {
		
	}
}
