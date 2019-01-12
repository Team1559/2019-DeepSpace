package org.usfirst.frc.team1559.robot.subsystems;

import src.org.usfirst.frc.team1559.robot.Robot;
import src.org.usfirst.frc.team1559.robot.Wiring;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; 

public class Grabber()
{
    private Solenoid solenoid;
    private Spark spark;


    public Grabber()
    {
        solenoid = new Solenoid(Wiring.NTK_SOLENOID);
        spark = new Spark(Wiring.NTK_SPARK);
        spark.enableDeadbandElimination(true);
    }

    public void getHatch()
    {
        solenoid.set(true); //go grab that hatch
    }

    public void bringHatch()
    {
        solenoid.set(false); //bring that hatch in bb
    }

    public double setSpark(double speed)
    {
        spark.set(speed);
    }
    public void getCargo()
    {
        spark.set(1);
    }

    public void removeCargo()
    {
        spark.set(0);
    }

    public Spark getSpark()
    {
        return spark;
    }
}