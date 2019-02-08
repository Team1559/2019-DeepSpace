package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
		SmartDashboard.putNumber("Analog  Volts", volts);
	//	SmartDashboard.putNumber("Analog Average Raw Value", averageRaw);
		double IRdistance = 24.031 * Math.pow(volts, -1.5549);
		SmartDashboard.putNumber("Analog Average Volts", averageVolts);
		SmartDashboard.putNumber("Distance", IRdistance);
        return IRdistance;
    }
    public void drive() {}


    public void stopRobot() //Tells you if the robot needs to rostop (if its true you must rostop)
    {
        
    }


}