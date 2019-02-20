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
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Solenoid;
import org.usfirst.frc.team1559.robot.subsystems.Lifter;
import org.usfirst.frc.team1559.robot.subsystems.Stepper;
import javax.lang.model.util.ElementScanner6;
import org.usfirst.frc.team1559.robot.OperatorInterface;

public class Robot extends TimedRobot {
	public DriveTrain drive;
	public static OperatorInterface oi;
	private Pixy pixy2;
	private Relay LED_Relay;
	public static boolean fightstick = true;
	private boolean isCargo = true;
	private static Lifter lifter;
	private static Grabber grabber; 
	private static Stepper stepper;
	public static boolean dBounce = false;
	public Compressor c;
	public static DistSensor distRight; //right and left from Robots perspective (looking from the back of the robot)
	public static DistSensor distLeft;
	private static AnalogInput ai;
	public static Vision vision;

	private float jKx;
    private float jKy;
	private float jKr;
	private float pKx;
    private float pKy;
	private float pKr;
	private float Er;
	private float Ex;
	private double Ey;
	private boolean Led;
	private boolean isGrabberSolenoidFired;
	private double errorX;
	private double errorR;
	private double errorY;

	@Override
	public void robotInit() {

		drive = new DriveTrain();
		oi = new OperatorInterface();
		c = new Compressor(7);
		lifter = new Lifter(oi); //Keep this in mind for future games! This type of coding could prove useful!
		pixy2 = new Pixy();
		LED_Relay = new Relay(0);
		vision = new Vision();
		grabber = new Grabber(oi);
		stepper = new Stepper(oi);
		distRight = new DistSensor(new AnalogInput (0));
		distLeft = new DistSensor(new AnalogInput (2));

		jKx = -0.015f;
		jKr = 0.016f;//0.014 
		jKy = 0.007f;//shold be .009
		pKx = 0.0125f;// maximum pixy translation (1/2 frame with)0.025
		pKr = 0.007f;// maximum pixy angle0.014
		pKy = 0.015f;//0.002f; // 0.0416f;//1/24 for the distance sensors max speed; 0.416

		LED_Relay.set(Value.kOn);
		isGrabberSolenoidFired = false;
		stepper.stopDrive();
	}	
		
	@Override
	public void robotPeriodic() {
	}

	@Override
	public void autonomousInit() {

		teleopInit();
		//pixy2.start();
	}

	@Override
	public void autonomousPeriodic() {
		teleopPeriodic();
	}

	@Override
	public void teleopInit() {

		pixy2.start();
		vision.VisionInit();
		LED_Relay.set(Value.kOn);
	}


	@Override
	public void teleopPeriodic() {
		c.setClosedLoopControl(true);

		if(oi.pilot.getRawButtonPressed(Constants.HATCH_SNATCHER)){

			grabber.toggleHatch();

		}

		//Lifter
		lifter.driveLifter();

		if(oi.getCopilotAxis(1) <= -0.9) {
			lifter.isAxis = true;
			lifter.goUp();
		}
		 if(oi.getCopilotAxis(1) >= 0.9) {
			lifter.isAxis = true;
			lifter.goDown();
		}
		 if((Math.abs(oi.getCopilotAxis(1)) <= 0.1)&& lifter.isAxis)  {
			lifter.stop();
		}

		pixylinevector v=pixy2.getvector();
		 vision.update();
		 VisionData vData = vision.getData();
		 vData.Print();
		 Ex = pixy2.getEx();
		 Er = pixy2.getEr();
	     
		
		float Rightdistance = (float)distRight.getRange();
		float Leftdistance = (float)distLeft.getRange();
		Ey = Rightdistance - 5;

		double maxPixyRange = 18.0;
		SmartDashboard.putNumber("RightIRDistance,", Rightdistance);
		SmartDashboard.putNumber("LeftIRdistance", Leftdistance);

		if(oi.getCopilotAxis(Constants.LINEASSIST) >= 0.9) {
			System.out.println("It's alive");
			pixy2.lampon();
			if(vData.status==1){
				if(vData.y >= maxPixyRange){
					errorX = vData.x;

					if ((errorX > -7.0) && (errorX < 7.0)){
						SmartDashboard.putNumber("__Close enough x", errorX);
						errorX = errorX/5.0;
					}

					errorR = vData.r;
					if ((errorR > -4.0) && (errorR < 4.0)){
						SmartDashboard.putNumber("__Close enough r", errorR);
						errorR = errorR/5.0;
					}

					double xDrive = jKx * errorX;

					if(xDrive > 1.0)
						xDrive = 1.0;
					else if(xDrive < -1.0)
						xDrive = -1.0;

					errorY = vData.y;
					SmartDashboard.putNumber("ex",vData.x);
					SmartDashboard.putNumber("ey", vData.y);
					SmartDashboard.putNumber("er",vData.r);	
					
					
					SmartDashboard.putNumber("__x",xDrive);
					SmartDashboard.putNumber("__y", jKy * errorY);
					SmartDashboard.putNumber("__r",jKr * errorR);	
					SmartDashboard.putString("Mode","jetson");
					drive.driveCartesian(xDrive, jKy * errorY , jKr * errorR);	
				}
				else if(v.status ==1 ){
					
					if (pixy2.getEx() > -3.5 && pixy2.getEx() < 3.5){
						SmartDashboard.putNumber("__Close enough x", Ex);
						Ex = Ex/10;
					}
					if (pixy2.getEr() > -5.5 && pixy2.getEr() < 5.5){
						SmartDashboard.putNumber("__Close enough r", Er);
						Er = Er/15; 
					}
					if(Er < -3 && Er > 3){
						pKy=0.416f;	
					}

				drive.driveCartesian(pKx * Ex, pKy * Ey , pKr * Er );	
				//to go right increase, to go left decrease
				Ex = Rightdistance - Leftdistance;
				SmartDashboard.putNumber("__x",pixy2.getEx());
				SmartDashboard.putNumber("__y", Rightdistance);
				SmartDashboard.putNumber("__r",pixy2.getEr());
				SmartDashboard.putString("Mode","pixy");
				System.out.println("Pixy " + pixy2.getEx() + " " + Rightdistance + " " + pixy2.getEr());
				}
				
			}
			else{
				SmartDashboard.putString("Mode","driver-2");
				SmartDashboard.putNumber("__x",oi.getPilotX());
				SmartDashboard.putNumber("__y",oi.getPilotY());
				SmartDashboard.putNumber("__r",oi.getPilotZ());
				drive.driveCartesian(oi.getPilotX(), oi.getPilotY(), oi.getPilotZ());
			}
		}
		else{
			pixy2.lampoff();
			SmartDashboard.putString("Mode","driver");
			SmartDashboard.putNumber("__x",oi.getPilotX());
			SmartDashboard.putNumber("__y",oi.getPilotY());
			SmartDashboard.putNumber("__r",oi.getPilotZ());
			drive.driveCartesian(oi.getPilotX(), oi.getPilotY(), oi.getPilotZ());
		}	
		grabber.drive();
		stepper.activate();
	}

@Override
public void disabledInit() {
	pixy2.lampoff();
	LED_Relay.set(Value.kOff);
}

@Override
public void disabledPeriodic() {
}

}