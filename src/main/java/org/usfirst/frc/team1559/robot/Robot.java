/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;
import org.usfirst.frc.team1559.robot.subsystems.Grabber;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWM;

//import edu.wpi.first.wpilibj.Joystick;

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

	private SerialTest pixy2;
	public static boolean fightstick = true;
	private boolean isCargo = true;
	private static Lifter lifter;
	private static Grabber grabber; 

	private Counter counterHi;
	private AnalogInput exampleAnalog;
	private Counter normalCounter;

	@Override
	public void robotInit() {
		drive = new DriveTrain();
		oi = new OperatorInterface();

		pixy2 = new SerialTest();
		//grabber = new Grabber();
		//DistSensor dSensor = new DistSensor();
		//dSensor.setAutomaticMode(true);
		//dSensor.stopRobot();
		
		//counterHi = new Counter (0);
		normalCounter = new Counter(0);
		normalCounter.setSemiPeriodMode(true);
		exampleAnalog = new AnalogInput(0);
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
	}

	private void SensorTester(){
		/* Analog Sensor Testing */ 
		int raw = exampleAnalog.getValue();
		double volts = exampleAnalog.getVoltage();
		int averageRaw = exampleAnalog.getAverageValue();
		double averageVolts = exampleAnalog.getAverageVoltage();
	//	SmartDashboard.putNumber("Analog Raw Value", raw);
		SmartDashboard.putNumber("Analog  Volts", volts);
	//	SmartDashboard.putNumber("Analog Average Raw Value", averageRaw);
		double IRdistance = 187754 * Math.pow(volts, -1.51);
		SmartDashboard.putNumber("Analog Average Volts", averageVolts);
		SmartDashboard.putNumber("Distance", IRdistance);
	
		/* Ultrasonic Sensor Testing */
		// boolean direction = normalCounter.getDirection();
		 double rate = normalCounter.getRate();
		 double period = normalCounter.getPeriod();
		// double distance = normalCounter.getDistance();
		// int count = normalCounter.get();

		// counterHi.setSemiPeriodMode(true);
		//SmartDashboard.putBoolean("direction", );
		SmartDashboard.putNumber ("rate", rate);
		SmartDashboard.putNumber ("period", period);
		//SmartDashboard.putNumber ("distance", distance);
		//SmartDashboard.putNumber ("count", count);
	}

	@Override
	public void teleopPeriodic() {
		// Camera
		// System.out.println(pixy2.read());

		SensorTester();

		// Drive Train
		oi.checkFineControl();
		drive.driveCartesian(oi.getPilotY(), oi.getPilotX(), oi.getPilotZ());

		// Grabber
		if(oi.pilot.getRawButtonPressed(Constants.BTN_INTAKE)) {
			grabber.getCargo();
		} else if(oi.pilot.getRawButtonPressed(Constants.BTN_OUTTAKE)) {
			grabber.removeCargo();
		}

		if(oi.pilot.getRawButtonPressed(Constants.BTN_HATCH_LOCK)) {
			grabber.getHatch();
		} else if(oi.pilot.getRawButtonPressed(Constants.BTN_HATCH_UNLOCK)) {
			grabber.bringHatch();

		}
		

	}

	@Override
	public void testPeriodic() {
		
	}
}
