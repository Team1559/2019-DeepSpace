/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;

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
	private DriveTrain drive;
	private OperatorInterface oi;
	public static boolean fightstick = true;
	private boolean isAxis = true;
	private static Lifter lifter;
	
	@Override
	public void robotInit() {
		drive = new DriveTrain();
		oi = new OperatorInterface();
		// DistSensor dSensor = new DistSensor();
		// dSensor.setAutomaticMode(true);
		// dSensor.stopRobot();
		lifter = new Lifter(oi);
	}

	@Override
	public void autonomousInit() {

	}

	@Override
	public void autonomousPeriodic() {
		
	}

	@Override
	public void teleopPeriodic() {
		lifter.driveLifter();
		drive.driveCartesian(oi.getPilotY(), oi.getPilotX(), oi.getPilotZ());


	}

	@Override
	public void testPeriodic() {
		
	}
}
