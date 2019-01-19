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
	private boolean isCargo = true;
	private static Lifter lifter;
	
	@Override
	public void robotInit() {
		drive = new DriveTrain();
		oi = new OperatorInterface();
		DistSensor dSensor = new DistSensor();
		dSensor.setAutomaticMode(true);
		dSensor.stopRobot();
	}

	@Override
	public void autonomousInit() {

	}

	@Override
	public void autonomousPeriodic() {
		
	}

	@Override
	public void teleopPeriodic() {
		drive.driveCartesian(oi.getPilotY(), oi.getPilotX(), oi.getPilotZ());
<<<<<<< HEAD
		
		DistSensor dSensor = new DistSensor();
		dSensor.setAutomaticMode(true);
		double x = 0;
		while(x < 50)
		{
			x = dSensor.getRange();
		} 
=======
		dSensor.stopRobot();



		//LIFTER
		if(Math.abs(oi.getCopilotButton(6)).isPressed() && isCargo == true) {
			isCargo = false;
		}
		else {
			isCargo = true;
		}
		if(!fightstick) {
			if(Math.abs(oi.getCopilotAxis(0)) >= 0.05 && !oi.getCocopilotButton(1).isDown) {
				lifter.driveManual(oi.getCopilotAxis(0));
			}
			if(oi.getCopilotButton(1).isPressed()) {
				if(isCargo)
					lifter.setPortPosition(1);
				else
					lifter.setHatchPosition(1);
			}
			else if(oi.getCopilotButton(2).isPressed()) {
				if(isCargo)
					lifter.setPortPosition(2);
				else
					lifter.setHatchPosition(2);
			}
			else if(oi.getCopilotButton(3).isPressed()) {
				if(isCargo)
					lifter.setPortPosition(3);
				else
					lifter.setHatchPosition(3);
			}
			else if(oi.getCopilotButton(0).isPressed()) {
				lifter.reset();
				System.out.println("New Pot Lower Bound: " + lifter.lowerbound);
			}
		}
>>>>>>> 6776f2b84b3373fa68afe9317a4b26db5cf115c1
	}

	@Override
	public void testPeriodic() {
		
	}
}
