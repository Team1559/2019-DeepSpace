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



import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


import org.usfirst.frc.team1559.robot.subsystems.Lifter;
import org.usfirst.frc.team1559.robot.subsystems.Stepper;
import org.usfirst.frc.team1559.robot.OperatorInterface;

public class Robot extends TimedRobot {
	/*
	 * ALL MEMBERS ARE REQUIRED TO IMPLEMENT THEIR SUBSYSTEM INTO THE ROBOT CLASS
	 * 
	 * From the seasons up to and including 2018, we used the IterativeRobot class. As per WPI's
	 * annual update, classed are added, removed, or deprecated. The IterativeRobot class was
	 * deprecated as of 2019. TimedRobot is the closest match to the IterativeRobot class.
	*/
	public DriveTrain drive;
	private OperatorInterface oi;

	private Pixy pixy2;
	private Relay LED_Relay;
	public static boolean fightstick = true;
	private boolean isCargo = true;
	//private static Lifter lifter;
	private static Grabber grabber; 
	private static Stepper stepper;
	public static boolean dBounce = false;


	public static DistSensor dist;
	private static DistSensor ds;
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
	private double errorX;
	private double errorR;
	private double errorY;
	@Override
	public void robotInit() {
		drive = new DriveTrain();


		oi = new OperatorInterface();
		//lifter = new Lifter(oi); //Keep this in mind for future games! This type of coding could prove useful!
		pixy2 = new Pixy();
		LED_Relay = new Relay(0);
		vision = new Vision();
		

		jKx = -0.015f;
		jKr = 0.014f;//0.014 
		jKy = 0.004f;//shold be .009
		pKx = 0.0125f;// maximum pixy translation (1/2 frame with)0.025
		pKr = 0.007f;// maximum pixy angle0.014
		pKy = 0.015f;//0.002f; // 0.0416f;//1/24 for the distance sensors max speed; 0.416
		
		pixy2 = new Pixy();
		ai = new AnalogInput(0); 

		ds = new DistSensor(ai);
	}	

		//dSensor = new DistSensor();

		
		//grabber = new Grabber();
		//dist = new DistSensor( new AnalogInput(0));
		//dSensor.setAutomaticMode(true);
		//dSensor.stopRobot();
	
		@Override
		public void robotPeriodic() {

		
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
		vision.VisionInit();
	}

	@Override
	public void teleopPeriodic() {


		// Camera
		// System.out.println(pixy2.read());
		//double sensor = ds.getRange();


		//Lifter
		//lifter.driveLifter();
		
		//Camera

		pixylinevector v=pixy2.getvector();
		 vision.update();
		 VisionData vData = vision.getData();
		 vData.Print();
		 Ex = pixy2.getEx();
		 Er = pixy2.getEr();
	     
		
		
		
		// Drive Train
		//System.out.println(drive.talons[0].getMotorOutputPercent

		//drive.driveCartesian(oi.getPilotX(), oi.getPilotY(), oi.getPilotZ());
		
		//if(v.status == 1)

		double distance = ds.getRange();
		Ey = distance - 5;
		double maxPixyRange = 18.0;
		SmartDashboard.putNumber("IRDistance,", distance);
		if(oi.copilot.getRawAxis(Constants.LINEASSIST) == 1) {
		pixy2.lampon();
		LED_Relay.set(Value.kOn);
		if(vData.status==1){
			if(vData.y >= maxPixyRange){
					errorX = vData.x;
					if ((errorX > -7.0) && (errorX < 7.0)){
						SmartDashboard.putNumber("__Close enough x", errorX);
						errorX = errorX/10.0;
					}

					errorR = vData.r;
					if ((errorR>-1.0)&&(errorR<1.0))
						errorR = 0.01;
					if ((errorR > -5.0) && (errorR < 5.0)){
						SmartDashboard.putNumber("__Close enough r", errorR);
						errorR = errorR/10.0;
					}
					double xDrive = jKx * errorX;
					if(xDrive > 1.0)
						xDrive = 1.0;
					else if(xDrive < -1.0)
						xDrive = -1.0;

						errorY = vData.y;

					SmartDashboard.putNumber("__error_r",vData.r);	
					SmartDashboard.putNumber("__error_x",vData.x);
					SmartDashboard.putNumber("__error_y",vData.y);
					SmartDashboard.putNumber("__x",xDrive);
					SmartDashboard.putNumber("__y", jKy * errorY);
					SmartDashboard.putNumber("__r",jKr * errorR);	
					SmartDashboard.putString("Mode","jetson");
					drive.driveCartesian(xDrive, jKy * errorY , jKr * errorR);
					
				}
			else if(v.status ==1 ){
				/*SmartDashboard.putNumber("__x",pixy2.getEx());
				SmartDashboard.putNumber("__y", distance);
				SmartDashboard.putNumber("__r",pixy2.getEr());
				SmartDashboard.putString("Mode","pixy");*/
				System.out.println("Pixy " + pixy2.getEx() + " " + distance + " " + pixy2.getEr());

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
				//drive.driveCartesian(pKx * Ex, pKy * Ey , pKr * Er );	
					}
			else{
				SmartDashboard.putString("Mode","driver");
				SmartDashboard.putNumber("__x",oi.getPilotX());
				SmartDashboard.putNumber("__y",oi.getPilotY());
				SmartDashboard.putNumber("__r",oi.getPilotZ());
				drive.driveCartesian(oi.getPilotX(), oi.getPilotY(), oi.getPilotZ());
			}
		}
		else{
			LED_Relay.set(Value.kOff);
		}
	}


		
	
		//Stepper button controls
		
		//drive wheel button control
		// if(oi.pilot.getRawButtonPressed(Constants.STEPPER_PILOT_DRIVE_FORWARD))
		// {
		// 	stepper.driveForward();
		// }
		// else if(oi.pilot.getRawButtonPressed(Constants.STEPPER_PILOT_DRIVE_BACKWARD))
		// {
		// 	stepper.driveBackward();
		// }
		// else
		// {
		// 	stepper.stopDrive();
		// }

		//retracts pistons
		// if(oi.pilot.getRawButtonPressed(Constants.STEPPER_PILOT_PULL_PISTONS))
		// {

	
			// SmartDashboard.putNumber("__getEx,", pixy2.getEx());
			// SmartDashboard.putNumber("__getEr,", pixy2.getEr());
			// SmartDashboard.putNumber("__x",Kx * pixy2.getEx());
			// SmartDashboard.putNumber("__r",Kr * pixy2.getEr());
			// SmartDashboard.putNumber("__Kx",Kx );
			// SmartDashboard.putNumber("__Kr",Kr);
			//drive.driveCartesian(Kx * pixy2.getEx(), Ky * ds.getRange(), Kr * pixy2.getEr());
			
		//Kx * pixy2.getEx()
		//Kr * pixy2.getEr()
	

		//	stepper.retractPistons();
	//	}


		//lifter to top position
	// 	if(oi.copilot.getRawButtonPressed(Constants.STEPPER_COPILOT_LIFT_UP))
	// 	{
	// 		stepper.liftStepper();
	// 	}

	// 	//lifter to lowest position
	// 	if(oi.copilot.getRawButtonPressed(Constants.STEPPER_COPILOT_LIFT_DOWN))
	// 	{
	// 		stepper.lowerStepper();
	// 	}
	// }
//}
	
	// Grabber
		// if(oi.pilot.getRawButtonPressed(Constants.BTN_INTAKE)) {
		// 	grabber.getCargo();
		// } else if(oi.pilot.getRawButtonPressed(Constants.BTN_OUTTAKE)) {
		// 	grabber.removeCargo();
		// }

		// if(oi.pilot.getRawButtonPressed(Constants.BTN_HATCH_LOCK)) {
		// 	grabber.getHatch();
		// } else if(oi.pilot.getRawButtonPressed(Constants.BTN_HATCH_UNLOCK)) {
		// 	grabber.bringHatch();

		// }

		//  if(oi.pilot.getRawButtonPressed(Constants.BTN_AUTO) || dBounce == true){
		//  	dBounce = true;
			
			
		// 	drive.driveCartesian(.5, .5, 0); //replace with Jetson data
		// 	if(ds.getRange() == 18)

		//  }
// 			/*if(oi.pilot.getRawButtonPressed(Constants.BTN_AUTO))
// 			{

// 				dBounce = false;}
// 		 }
// 	}

		
// 	@Override
// 	public void testInit() {
		
// 	}




// 	@Override
// 	public void testPeriodic() {
	
}


@Override
public void disabledInit() {
	pixy2.lampoff();
}

@Override
public void disabledPeriodic() {

}


}
