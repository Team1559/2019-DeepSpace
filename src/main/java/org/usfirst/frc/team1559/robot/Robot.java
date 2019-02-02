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
		// drive = new DriveTrain();
		oi = new OperatorInterface();
		// DistSensor dSensor = new DistSensor();
		// dSensor.setAutomaticMode(true);
		// dSensor.stopRobot();
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
		// drive.driveCartesian(oi.getPilotY(), oi.getPilotX(), oi.getPilotZ());



		//LIFTER
		/**
		 * IMPORTANT!!!! Please note that the changed button orientation has occured on multiple occasions.
		 * It seems that if the Driver's Station says Controller (MAYFLASH Arcade Fightstick F300), it will
		 * have the current button configuration listed in the code. However, if it just says
		 * MAYFLASH Arcade Fightstick F300, then it will use the commented button positions.
		 * It seems that if the computer chooses to configure the Fightstick, it will change the button configuration.
		 * KEEP AN EYE ON THIS AS IT WILL AFFECT THE ROBOT'S FUNCTIONALITY!!!!
		*/
		if(oi.getCopilotButton(0).isPressed()) { //button 1 if Fightstick changes button orientation, normal is button 0.
			lifter.goToPortPos(1);
			isAxis = false;
		}
		else if(oi.getCopilotButton(1).isPressed()) { //button 2 if Fightstick changes button orientation, normal is button 1.
			lifter.goToPortPos(2);
			isAxis = false;
		}
		else if(oi.getCopilotAxis(3) == 1) { //button 7 if Fightstick changes button orientation, normal is Axis 3.
			lifter.goToPortPos(3);
			isAxis = false;
		}
		else if(oi.getCopilotButton(2).isPressed()) { //button 0 if Fightstick changes button orientation, normal is button 2.
			lifter.goToCargoShipHatch();
			isAxis = false;
		}
		else if(oi.getCopilotButton(3).isPressed()) { //button 3 if Fightstick changes button orientation, normal is button 3.
			lifter.goToCargoShipCargoDrop();
			isAxis = false;
		}
		else if(oi.getCopilotButton(5).isPressed()) { //button 5 for either button orientation
			lifter.goToHatchPos(3);
			isAxis = false;
		}
		// else if(oi.getCopilotButton(4).isPressed()) {
		// 	lifter.goToBottom();
		// }
		else if(oi.getCopilotButton(6).isPressed()) { //button 8 if Fightstick changes button orientation, normal is button 6.
			lifter.recallibrateSystem();
			isAxis = false;
		}
		else if(oi.getCopilotAxis(1) == -1.0) {
			lifter.goUp();
			System.out.println(oi.getCopilotAxis(1));
			isAxis = true;
		}
		else if(oi.getCopilotAxis(1) == 1) {
			lifter.goDown();
			System.out.println(oi.getCopilotAxis(1));
			isAxis = true;
		}
		else if(oi.getCopilotAxis(1) < 0.1 && oi.getCopilotAxis(1) > -0.1 && isAxis) {
			lifter.stop();
			System.out.println(oi.getCopilotAxis(1));
		}
		// int pos = 0;
		// if(oi.getCopilotButton(2).isPressed()) {
		// 	pos++;
		// }
		// if(oi.getCopilotButton(0).isPressed()) {
		// 	pos--;
		// }
		// switch(pos) {
		// 	case 0:
		// 	lifter.goToBottom();
		// 	break;
		// 	case 1:
		// 	lifter.goToCargoShipHatch(); //Same as hatch position 1
		// 	break;
		// 	case 2:
		// 	lifter.goToPortPos(1);
		// 	break;
		// 	case 3:
		// 	lifter.goToCargoShipCargoDrop(); //Same as hatch position 2
		// 	break;
		// 	case 4:
		// 	lifter.goToPortPos(2);
		// 	break;
		// 	case 5:
		// 	lifter.goToHatchPos(3);
		// 	break;
		// 	case 6:
		// 	lifter.goToPortPos(3);
		// }
		// if(oi.getCopilotButton(7).isPressed()) {
		// 	lifter.recallibrateSystem();
		// }

	}

	@Override
	public void testPeriodic() {
		
	}
}
