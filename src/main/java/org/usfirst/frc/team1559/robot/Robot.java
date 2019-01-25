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
	// private boolean isCargo = true;
	private static Lifter lifter;
	
	@Override
	public void robotInit() {
		drive = new DriveTrain();
		oi = new OperatorInterface();
		DistSensor dSensor = new DistSensor();
		dSensor.setAutomaticMode(true);
		dSensor.stopRobot();
		lifter = new Lifter();
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



		//LIFTER
		
		// if(oi.getCopilotButton(0).isPressed()) {
		// 	lifter.goToPortPos(1);
		// }
		// else if(oi.getCopilotButton(1).isPressed()) {
		// 	lifter.goToPortPos(2);
		// }
		// else if(oi.getCopilotButton(2).isPressed()) {
		// 	lifter.goToPortPos(3);
		// }
		// else if(oi.getCopilotButton(3).isPressed()) {
		// 	lifter.goToHatchPos(1);
		// }
		// else if(oi.getCopilotButton(4).isPressed()) {
		// 	lifter.goToHatchPos(2);
		// }
		// else if(oi.getCopilotButton(5).isPressed()) {
		// 	lifter.goToHatchPos(3);
		// }
		// else if(oi.getCopilotButton(6).isPressed()) {
		// 	lifter.goToCargoShipCargoDrop();
		// }
		// else if(oi.getCopilotButton(7).isPressed()) {
		// 	lifter.goToCargoShipHatch();
		// }
		// else if(oi.getCopilotButton(8).isPressed()) {
		// 	lifter.goToBottom();
		// }
		int pos = 0;
		if(oi.getCopilotButton(2).isPressed()) {
			pos++;
		}
		if(oi.getCopilotButton(0).isPressed()) {
			pos--;
		}
		switch(pos) {
			case 0:
			lifter.goToBottom();
			break;
			case 1:
			lifter.goToCargoShipHatch(); //Same as hatch position 1
			break;
			case 2:
			lifter.goToPortPos(1);
			break;
			case 3:
			lifter.goToCargoShipCargoDrop(); //Same as hatch position 2
			break;
			case 4:
			lifter.goToPortPos(2);
			break;
			case 5:
			lifter.goToHatchPos(3);
			break;
			case 6:
			lifter.goToPortPos(3);
		}
		if(oi.getCopilotButton(7).isPressed()) {
			lifter.recallibrateSystem();
		}

	}

	@Override
	public void testPeriodic() {
		
	}
}
