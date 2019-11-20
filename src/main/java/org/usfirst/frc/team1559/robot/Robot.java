/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.CameraServer;
import org.usfirst.frc.team1559.robot.subsystems.Grabber;
import org.usfirst.frc.team1559.robot.subsystems.pixylinevector;
import org.usfirst.frc.team1559.robot.Vision;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1559.robot.subsystems.Lifter;
import org.usfirst.frc.team1559.robot.subsystems.Stepper;
import org.usfirst.frc.team1559.robot.OperatorInterface;

import java.lang.Thread;

public class Robot extends TimedRobot
{
	public DriveTrain drive;
	public static OperatorInterface oi;

	private static Lifter lifter;
	private static Grabber grabber;
	private static Stepper stepper;
	public Compressor airCompressor;

	// Pixy and Vision Variables/Constants
	public static Pixy pixy2;
	public static Vision vision;
	public static Relay LED_Relay;
	// The distance sensors are set right and left from the perspective of the back of the robot.
	public static DistSensor distRight;
	public static DistSensor distLeft;
	private double jKx, jKy, jKr, pKx, pKy, pKr, Er, Ex, pDx, pDr;
	private double Ey, errorX, errorR, errorY;
	private double prevXerror, prevRerror;
	private int state;
	private int lastState;
	private int counter;
	private float wallGap;
	private double liftPotError = 100;
	public boolean flagHatch = false;
	private int arriveCounter = 0;
	public boolean lifterCal = true; /*initial lifter calibrsation */
	private double xDrive1;
	private boolean prevPixyXSign = true;
	private boolean Grabbinghatch = true;
	private double yTarget = 6.0;

	@Override
	public void robotInit()
	{
		
		// Sub-System Instantiations
			drive = new DriveTrain();
			oi = new OperatorInterface();
			airCompressor = new Compressor();
			grabber = new Grabber(oi);
			lifter = new Lifter(oi, grabber);
			pixy2 = new Pixy();
			LED_Relay = new Relay(0);
			vision = new Vision();
			stepper = new Stepper(oi);
			distRight = new DistSensor(new AnalogInput (2));
			distLeft = new DistSensor(new AnalogInput (0));

		// Vision/Pixy Variables and Constants
			jKx = 0.011 ;//.015
			jKr = 0.02f;//0.016
			jKy = 0.0045f;//shold be .009

			LED_Relay.set(Value.kOn);//turns on the greeen led ring for jetson autodrive]
		// Stepper
			stepper.stopDrive();
			CameraServer.getInstance().startAutomaticCapture();
		
		}

	@Override
	public void robotPeriodic()
	{

	}

	@Override
	public void autonomousInit()
	{
		
		teleopInit();
	}

	@Override
	public void autonomousPeriodic() {
		teleopPeriodic();
	}

	@Override
	public void teleopInit()
	{
		stepper.stepperWheeles = false;
		vision.VisionInit();
		LED_Relay.set(Value.kOn);
		stepper.retractPistons();


	}


	@Override
	public void teleopPeriodic()
	{
	pixy2.lampon();
	System.out.println(oi.pilot.getRawButton(2));
	System.out.println(oi.pilot.getRawButton(9));
	//	pixy2.lampon();//Temporary
		SmartDashboard.putNumber("Pixy Boi x",Ex);
			SmartDashboard.putNumber("Pixy Boi y", Ey);
			SmartDashboard.putNumber("Pixy Boi R",Er);
		VisionData vDataTemp = vision.getData();
		vDataTemp.Print();
		//drive.driveCartesian(0.12, 0.0, 0.0);

		// if(lifterCal == true){
		// 	lifter.initCalLifter();
		// }
		// else{
		// 	lifter.driveLifter();
		// }
		// Air Compressor
		airCompressor.setClosedLoopControl(true);
		if(oi.getCopilotAxis(Constants.LINEASSIST) < 0.9){//if in auto don't have maunual control

		// Grabber Functions
		//Lifter Functions
			//
		//	pixy2.lampoff();
			// Stepper Functions
			stepper.activate();
		}
		stepper.getstepperpot();
		//SmartDashboard.putNumber("stepper_pot", stepper.getstepperpot());
		//SmartDashboard.putNumber("Lifter Pot", lifter.getPot());
		// Pixy and Vision Functions
		pixylinevector v=pixy2.getvector();
		pixy2.getEr();
		vision.update();
		VisionData vData = vision.getData();
		vData.Print();
		Ex = pixy2.getEx();
		Er = pixy2.getEr();
		float Rightdistance = (float)distRight.getRange()-Constants.IR_OFFSET_RIGHT;
		float Leftdistance = (float)distLeft.getRange()-Constants.IR_OFFSET_LEFT;

			if(oi.copilot.getRawButton(4) || oi.copilot.getRawButton(5) || oi.copilot.getRawButton(6))
					{
						//wallGap = 3;
						Ey = Math.min(Rightdistance,60) - 3;//think we are 3 in closer-go to bumper
						Grabbinghatch = false;
					}
					else
					{
						//wallGap = 1;
						Ey =Math.min( Rightdistance, 60) - 3;//think we are .5 in further
						//Ey = Math.min(Rightdistance,Leftdistance) - 1;
						Grabbinghatch = true;
					}


		//Ey = Math.min(Rightdistance,Leftdistance) + wallGap;
		//Ey = Rightdistance;
		double maxPixyRange = 8.0;
		SmartDashboard.putNumber("RightIRDistance,", Rightdistance);
		SmartDashboard.putNumber("LeftIRdistance", Leftdistance);
		SmartDashboard.putNumber("RiGhtIRDistance,", Rightdistance);
		SmartDashboard.putNumber("LeFtIRdistance", Leftdistance);

		//case 0=Driver
		//case 1=jetson
		//case 2=lift
		//case 3=Pixy
		//case 4 = Ball
		//case 5=Retreat
																				//0
		
		if(oi.getCopilotAxis(Constants.LINEASSIST) >= 0.9)
		{

			SmartDashboard.putNumber("__y", jKy * errorY);
			SmartDashboard.putNumber("__r",jKr * errorR);
			SmartDashboard.putNumber("Pixyy", Ey);
			SmartDashboard.putNumber("Pixyr",pixy2.getEr());
			SmartDashboard.putString("Mode","pixy");
			if(state == 0) {
				state = 1;//change back to state 1//we need to understand why jetson is not driving has good values
			}
		}
		else {
			state = 0;																							
			//System.out.println("DRIVE MODE");
		}

		switch(state)
		{
			case 0: //DRIVE :(
				if(oi.pilot.getRawButton(8)){
					grabber.releaseHatch();
				}
				else{
					grabber.GrabHatch();
				}
				lifter.driveLifter();
				grabber.drive();
				drive.driveCartesian(oi.getPilotX(), oi.getPilotY(), oi.getPilotZ());
				break;
			case 1: 			//JETSON
				lastState = state;
				arriveCounter = 0;
				//System.out.println("It's alive");

				if(vData.status==1) {
					if(vData.y <=16)
					{
					}
					if(vData.y <= 16)
					{
						vData.y = Math.min( Rightdistance, Leftdistance );
						
					}
					if(v.status==1)
					{
						prevRerror = Er;
						prevXerror = Ex;
					}
					if(vData.y >= maxPixyRange || v.status != 1)
					{
						errorX = vData.x;

						if ((errorX > -7.0) && (errorX < 7.0))
						{
							SmartDashboard.putNumber("__Close enough x", errorX);
							//errorX = errorX/5.0;
						}
						errorR = vData.r;
						if ((errorR > -4.0) && (errorR < 4.0))
						{
							SmartDashboard.putNumber("__Close enough r", errorR);
							//errorR = errorR/5.0;
						}

						double xDrive = (jKx * errorX)*36/vData.y;
						xDrive1 = xDrive;
						double xbound = .28;
						if(xDrive > xbound)
							xDrive = xbound;
						else if(xDrive < -xbound)
							xDrive = -xbound;
						errorY = vData.y-yTarget;//jetson trying to drive to 10in	
						double yDrive = jKy * errorY;
						double ymax = 0.85;

						if(v.status == 1){
						pixy2.lampon();
						liftPotError = 100;
						drive.driveCartesian(0, 0, 0);
						state = 2;//should be 2
					}

					else if(yDrive > .85)
					yDrive = .85;
						// if(yDrive > .85)
						// 	yDrive = .85;
						else if(yDrive < -.85)
							yDrive = -.85;


						drive.driveCartesian(xDrive, yDrive + 100 , jKr * errorR);
					}
				}
				else{
					state = 0;
				
		}
	}
				
	}

	@Override
	public void disabledInit()
	{
		pixy2.lampoff();
		LED_Relay.set(Value.kOff);
		grabber.releasePiston();
	}

	@Override
	public void disabledPeriodic()
	{
		grabber.releasePiston();
		grabber.GrabHatch();
		airCompressor.stop();
	}
}
