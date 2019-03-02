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
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1559.robot.subsystems.Lifter;
import org.usfirst.frc.team1559.robot.subsystems.Stepper;
import org.usfirst.frc.team1559.robot.OperatorInterface;

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
	private float jKx, jKy, jKr, pKx, pKy, pKr, Er, Ex;
	private double Ey, errorX, errorR, errorY;
	private int close_enough;
	private int backuptimer;
	private int scoreingcomplete;
	private int grabberon;
	private int cargodrop;
	private int hatchtimer;
	private int cargodroped;
	private int state;
	private int lastState;
	private int counter;

	@Override
	public void robotInit()
	{
		// Sub-System Instantiations
			drive = new DriveTrain();
			oi = new OperatorInterface();
			airCompressor = new Compressor();
			lifter = new Lifter(oi);
			pixy2 = new Pixy();
			LED_Relay = new Relay(0);
			vision = new Vision();
			grabber = new Grabber(oi);
			stepper = new Stepper(oi);
			distRight = new DistSensor(new AnalogInput (2));
			distLeft = new DistSensor(new AnalogInput (0));

		// Vision/Pixy Variables and Constants
			jKx = 0.012f;//.015
			jKr = 0.025f;//0.016 
			jKy = 0.007f;//shold be .009
			pKx = -0.0125f;// maximum pixy translation (1/2 frame with)0.025
			pKr = 0.007f;// maximum pixy angle0.014
			pKy = 0.015f;//0.002f; // 0.0416f;//1/24 for the distance sensors max speed; 0.416
			LED_Relay.set(Value.kOn);
		
		// Stepper
			stepper.stopDrive();
	}	
		
	@Override
	public void robotPeriodic()
	{

	}

	@Override
	public void autonomousInit()
	{
		//pixy2.start();
		teleopInit();
	}

	@Override
	public void autonomousPeriodic() {
		teleopPeriodic();
	}

	@Override
	public void teleopInit()
	{
		pixy2.start();
		vision.VisionInit();
		LED_Relay.set(Value.kOn);
		stepper.retractPistons();
		lifter.recallibrateSystem();
	}


	@Override
	public void teleopPeriodic()
	{
		// Air Compressor
		airCompressor.setClosedLoopControl(true);
		if(oi.getCopilotAxis(Constants.LINEASSIST) < 0.9){//if in auto don't have maunual control
			lifter.driveLifter();
		// Grabber Functions
			grabber.drive();
			if(oi.pilot.getRawButtonPressed(Constants.HATCH_SNATCHER))
			{
				grabber.toggleHatch();
			}

			//Lifter Functions
			if(oi.getCopilotAxis(1) <= -0.9)
			{
				lifter.isAxis = true;
				lifter.goUp();
			}
			if(oi.getCopilotAxis(1) >= 0.9)
			{
				lifter.isAxis = true;
				lifter.goDown();
			}
			if((Math.abs(oi.getCopilotAxis(1)) <= 0.1)&& lifter.isAxis)
			{
				lifter.stop();
			}
			
			// Stepper Functions
			stepper.activate();
		}
		// Pixy and Vision Functions
		pixylinevector v=pixy2.getvector();
		vision.update();
		VisionData vData = vision.getData();
		vData.Print();
		Ex = pixy2.getEx();
		Er = pixy2.getEr();
		
	
		float Rightdistance = (float)distRight.getRange();
		float Leftdistance = (float)distLeft.getRange();
		Ey = Rightdistance;

		double maxPixyRange = 18.0;
		SmartDashboard.putNumber("RightIRDistance,", Rightdistance);
		SmartDashboard.putNumber("LeftIRdistance", Leftdistance);
		//case 0=Driver
		//case 1=jetson
		//case 2=lift
		//case 3=Pixy
		//case 4 = Ball
		//case 5=Retreat
		if(oi.getCopilotAxis(Constants.LINEASSIST) >= 0.9) 
		{
			if(state == 0) {
				state = 1;
				System.out.println("JETSON MODE");
			}
		}
		else {
			state = 0;
			//System.out.println("DRIVE MODE");
		}
		switch(state)
		{
			case 0: //DRIVE :(
				drive.driveCartesian(oi.getPilotX(), oi.getPilotY(), oi.getPilotZ());
				break;
			case 1: 			//JETSON
				lastState = state;		
				System.out.println("It's alive");
				pixy2.lampon();
				if(vData.status==1) {
					if(vData.y >= maxPixyRange )
					{
						errorX = vData.x;
						if ((errorX > -7.0) && (errorX < 7.0))
						{
							SmartDashboard.putNumber("__Close enough x", errorX);
							errorX = errorX/5.0;
						}

						errorR = vData.r;
						if ((errorR > -4.0) && (errorR < 4.0))
						{
							SmartDashboard.putNumber("__Close enough r", errorR);
							errorR = errorR/5.0;
						}

						double xDrive = (jKx * errorX)*24/vData.y;

						if(xDrive > 1.0)
							xDrive = 1.0;
						else if(xDrive < -1.0)
							xDrive = -1.0;

						errorY = vData.y;
						// SmartDashboard.putNumber("ex",vData.x);
						// SmartDashboard.putNumber("ey", vData.y);
						// SmartDashboard.putNumber("er",vData.r);	
					
					
						SmartDashboard.putNumber("__x",xDrive);
						SmartDashboard.putNumber("__y", jKy * errorY);
						SmartDashboard.putNumber("__r",jKr * errorR);	
						SmartDashboard.putString("Mode","jetson");
						drive.driveCartesian(xDrive, jKy * errorY , jKr * errorR);	
					}
					else{
						state = 2;
						System.out.println("LIFTER MODE");
					}
				}
				else{
					state = 0;
					System.out.println("DRIVE MODE");
				}
				break;
			case 2:  //LIFTER
				lastState = state;
				lifter.driveLifter();
				System.out.println(lifter.getPotError());
				//check to see if lifter is within a range of values
				if(Math.abs(lifter.getPotError()) < 1) {
					if(v.status == 1)
					{
					state = 3;
					}
				}
				else{
					drive.driveCartesian(0,0,0);//ALL STOP!!!!!
				}
				break;
			case 3: //PIXY
				lastState = state;
				if(v.status ==1 )
				{
					if(Math.abs(Ey)>=2)
					{
						if (pixy2.getEx() > -0.3 && pixy2.getEx() < 0.3)
						{
							SmartDashboard.putNumber("__Close enough x", Ex);
							Ex = Ex/10;
						}
						if (pixy2.getEr() > -4 && pixy2.getEr() < 4)
						{
							SmartDashboard.putNumber("__Close enough r", Er);
							Er = Er/15; 
						}
						if(Er < -3 && Er > 3)
						{
							pKy=0.416f;	
						}
						if(Ey <= 2 && Ey >= -2){
							Ey = 0;
						}

						drive.driveCartesian(pKx * Ex, pKy * Ey , pKr * Er);
						//to go right increase, to go left decrease
						Ex = Rightdistance - Leftdistance;
						SmartDashboard.putNumber("__x",pixy2.getEx());
						SmartDashboard.putNumber("__y", Rightdistance);
						SmartDashboard.putNumber("__r",pixy2.getEr());
						SmartDashboard.putString("Mode","pixy");
						System.out.println("Pixy " + pixy2.getEx() + " " + Math.min(Rightdistance,Leftdistance) + " " + pixy2.getEr());
					}
					else
					{
						state = 4;
						System.out.println("Ball MODE");
						counter = 100;
					}
				}
				else
				{
					state = 4;
					System.out.println("Ball MODE");
					counter = 10;
				}
				break;
			case 4: //BALL MODE
				lastState = state;
				
				if(oi.getCopilotAxis(3)==1 && counter > 0)  
				{
					grabber.removeCargo();
					counter-=1;
				}
				else {
					state = 5;
					System.out.println("Retreat Mode");
				}
			case 5: //RETREAT!!!
				lastState = state;
				if(Math.min(Leftdistance,Rightdistance)<= 24) {
					drive.driveCartesian(0, -0.3, 0);
				}
				else {
					drive.driveCartesian(0, 0, 0);
				}
				if(Math.min(Leftdistance,Rightdistance)>= 12){
					//grabber.toggleHatch();
					//^comment code when piston is reinstalled
					lifter.goToBottom(1);
				}
				break;
			
				
			default: 
				state = 0;
				System.out.println("GOING FROM UNKNOWN STATE TO DRIVE MODE ERROR ERROR ERROR ERROR ERROR ERROR");
				break;
		}
		SmartDashboard.putNumber("State", state);
		SmartDashboard.putNumber("Last State", lastState);
	}

	@Override
	public void disabledInit()
	{
		pixy2.lampoff();
		LED_Relay.set(Value.kOff);
	}

	@Override
	public void disabledPeriodic() 
	{
		airCompressor.stop();
	}
}