package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DistSensor
{
    Ultrasonic ultra = new Ultrasonic(1,1);
    DriveTrain drive =  new DriveTrain();

    public void setAutomaticMode(boolean b)
    {
        ultra.setAutomaticMode(b);
    }

    public double getRange()
    {
        double range = ultra.getRangeInches();
        SmartDashboard.putNumber("Robot inches", range);
        return range;
    }

    public void stopRobot() //Tells you if the robot needs to rostop (if its true you must rostop)
    {
        boolean b  = false;
        if(getRange() < 12) //Set this to an actual value
        {
            b = true;
            SmartDashboard.putBoolean("Robot Stopped", b);
            drive.driveCartesian(0,0,0);
        }
        
    }

}