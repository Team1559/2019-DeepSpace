package org.usfirst.frc.team1559.robot;

import com.sun.net.httpserver.Authenticator.Result;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogInput;


public class DistSensor
{

    Boolean isStopped;
    private AnalogInput analogInput; 
    public DistSensor(AnalogInput ai)
    {
        analogInput = ai; 
    }
    

    public void setAutomaticMode(boolean b)
    {
    }

    public double getRange()
    {

        		/* Analog Sensor Testing */ 
		int raw = analogInput.getValue();
		double volts = analogInput.getVoltage();
		int averageRaw = analogInput.getAverageValue();
		double averageVolts = analogInput.getAverageVoltage();
	//	SmartDashboard.putNumber("Analog Raw Value", raw);
	//	SmartDashboard.putNumber("Analog  Volts", volts);
	//	SmartDashboard.putNumber("Analog Average Raw Value", averageRaw);

        double IRdistance = Math.min((24.031 * Math.pow(volts, -1.5549))-9, 48.0);

	//	SmartDashboard.putNumber("Analog Average Volts", averageVolts);
	//	SmartDashboard.putNumber("Distance", IRdistance);
        return IRdistance * 1.4215 - 1.0323;
    }
    public void drive() {}

/*
    public void stopRobot() //Tells you if the robot needs to rostop (if its true you must rostop)
    {


        if(getRange() > Constants.STOPPING_DISTANCE)
        {
            //Robot.drive.driveCartesian(0, 0, 0);
            isStopped = !isStopped;
            SmartDashboard.putNumber("Robot Stopped " + isStopped, 0.0);
        }
        boolean b  = false;
        if(getRange() < 12) //Set this to an actual value
        {
            b = true;
            SmartDashboard.putBoolean("Robot Stopped", b);
            //Robot.drive.driveCartesian(0,0,0);
        }


        
    }
    */


}
