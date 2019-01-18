package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class distSensor()
{
    Ultrasonic ultra = new Ultrasonic(1,1);
    DriveTrain drive =  new DriveTrain();

    public double getRange()
    {
        double range = ultra.getRangeInches();
    }

    public void stopRobot() //Tells you if the robot needs to rostop (if its true you must rostop)
    {
        boolean b  = false;
        if(ultra.getRange() > 12) //Set this to an actual value
        {
            b = true;
            drive.driveCartesian(0,0,0);
            SmartDashboard.putNumber("Robot Stopped", b);
        }
        
    }

}