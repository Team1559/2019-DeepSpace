package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Ultrasonic;

public class distSensor()
{
    Ultrasonic ultra = new Ultrasonic(1,1);

    public double getRange()
    {
        double range = ultra.getRangeInches();
    }

}