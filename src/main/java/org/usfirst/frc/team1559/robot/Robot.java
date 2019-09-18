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
			jKx = 0.022f;//.015
			jKr = 0.04f;//0.016
			jKy = 0.009f;//shold be .009
			pKx = -0.012f;// WAS -0.014 on 3/19 maximum pixy translation (1/2 frame with)0.0250.007
			pKr = 0.018f;//was .007 at 3/19was .015 on beginning of 3/19// maximum pixy angle0.005//0.007
			pKy = 0.0f;//0.07//0.042f//0.002f; // 0.0416f;//1/24 for the distance sensors max speed; 0.416  (0.0015)  //0.1
			pDx = 0*-12.0 * pKx;
			pDr = 0*-12.0 * pKr;

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
			//pixy2.lampon();

			SmartDashboard.putNumber("__y", jKy * errorY);
			SmartDashboard.putNumber("__r",jKr * errorR);
			//System.out.println(pixy2.getEx());
			//System.out.println(pixy2.getEr());
			SmartDashboard.putNumber("Pixyy", Ey);
			SmartDashboard.putNumber("Pixyr",pixy2.getEr());
			SmartDashboard.putString("Mode","pixy");
			if(state == 0) {
				state = 2;//change back to state 1//we need to understand why jetson is not driving has good values
			}
		}
		else {
			state = 0;																							
			pixy2.lampoff();
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
				pixy2.lampon();

				if(vData.status==1) {
					if(vData.y <=16)
					{
						pixy2.lampon();
					}
					if(vData.y <= 16)
					{
						vData.y = Math.min( Rightdistance, Leftdistance );
						pixy2.lampon();
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


						drive.driveCartesian(xDrive, yDrive , jKr * errorR);
					}
					// else{
					// 	pixy2.lampon();
					// 	liftPotError = 100;
					// 	drive.driveCartesian(0, 0, 0);
					// 	state = 2;//should be 2
					// }
				}
				else{
					state = 0;
				}
				break;
			case 2:  //LIFTER
				lastState = state;
				lifter.driveLifter();
				SmartDashboard.putNumber("pot error", liftPotError);
				//check to see if lifter is within a range of values
				Ey = Math.min(Rightdistance,Leftdistance)-yTarget;
				pixy2.lampon();
				if(v.status == 1)
				{
					counter = 20;
					
					if((Er >= -2.0) && (Er <= 2.0)) {
						Er = Er * 2.0;
					}
					if((Ex >= -2.0) && (Ex <= 2.0)) {
						Ex = Ex * 2.0;
					}
					double PXDrive = pKx * Ex + pDx * (Ex - prevXerror);
					double PRDrive = pKr * Er + pDr * (Er - prevRerror);
					prevXerror = Ex;
					prevRerror = Er;

					if(Math.abs(PXDrive) < Math.abs(PRDrive))
					{
						PXDrive = 0;
				}//hello my name is Rlan and i am no smart. see me grammer for detailk
					else
					{
						PRDrive = 0;
					}
					double PXMinValue = 0.09;
					if(PXDrive <=PXMinValue && PXDrive > 0 )
					{
						PXDrive = PXMinValue;
					}
					else if(PXDrive >= -PXMinValue && PXDrive < 0 )
					{
						PXDrive = -PXMinValue;
					}
				
					drive.driveCartesian(PXDrive, (Ey *pKy) *0, PRDrive); /* do we want a nominally small y? *///pKy * Ey
				//	System.out.println("Pixy " + pixy2.getEx() + "EY " + Ey + "ER " + pixy2.getEr());
				}
				else{
					if(vData.status==1)
					{
					drive.driveCartesian((vData.x*jKx)*0.0, (Ey * pKy) *0 , (vData.r*jKr)*0.0);
					//System.out.println("Using Jetson ");
					}
					else{
						state = 0;
					}
				}
				liftPotError = Math.abs(lifter.getPotError());
				//System.out.println(liftPotError + "" + v.status);
				//if(liftPotError < 1.0)
				if(true) {//go to above
					//System.out.println("lifter pos is correct");
					if(v.status == 1)
					{
						if((pixy2.getEr() >= -6.0) && (pixy2.getEr() <= 6.0) && (pixy2.getEx() >= -7.0) && (pixy2.getEx() <= 7.0))
						{
						//	System.out.println("You Enter into charge State");
							drive.driveCartesian(0, 0, 0);
							state = 0; //3 for the actual auto
							counter = 1000;
							drive.driveCartesian(0.0, 0.0, 0.0);
						}
					}
				}
				break;
			case 3: //CHARGE
				if(Grabbinghatch == true){
					grabber.releaseHatch();
				}

				lastState = state;
				counter = 100;
				if((Ey)>=1.0) /*|| Math.abs(Er)>=4)*/
				{
					arriveCounter = 0;
					if (pixy2.getEx() > -0.3 && pixy2.getEx() < 0.3)
						Ex = Ex/6; //change to 8 and test

					if(v.status == 1 && (pixy2.getEr() >= -6.0) && (pixy2.getEr() <= 6.0) && (pixy2.getEx() >= -5.0) && (pixy2.getEx() <= 5.0)) {
						drive.driveCartesian((pKx * Ex)*0.2, (pKy * Ey)*0.7 , (pKr * Er)*0.2);
					}
						else{
						//drive.driveCartesian(0, (pKy * Ey)/2 , 0);
							drive.driveCartesian(0, 0, 0);
							state = 5;
						}
				}
				else
				{
					arriveCounter++;
					drive.driveCartesian(0, 0.0, 0);
					if(arriveCounter > 8)
					{
					state = 4;
					//System.out.println("Ball MODE-Arrived");
					counter = 100;
					}
				}
				break;
			case 4: //BALL MODE
				lastState = state;
				if(Grabbinghatch == true){
					grabber.GrabHatch();
					lifter.Exithatchstation();
				}
				else{
					grabber.releaseHatch();
				}

				if(oi.getCopilotAxis(3)==1 && counter > 0)
				{
					grabber.removeCargo();
					grabber.cargocounter = 1;
           			grabber.cargotimer = 0;
					counter-=1;
				}
				else {
					flagHatch = true;
					state = 5;
				//	System.out.println("Retreat Mode");
				}
				break;
			case 5: //RETREAT!!!
				lastState = state;
				if(vData.y<= 30) {
					drive.driveCartesian(0, -0.5, 0);
				}
				else  {
					drive.driveCartesian(0, 0, 0);
				}
				if(Math.min(Leftdistance,Rightdistance)>= 12){
					lifter.goToBottom(1);
					if(flagHatch == true){
						grabber.toggleHatch();
						flagHatch = false;
					}
				}
				break;


			default:
				state = 0;
				//System.out.println("GOING FROM UNKNOWN STATE TO DRIVE MODE ERROR ERROR ERROR ERROR ERROR ERROR");
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
