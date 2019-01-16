package org.usfirst.frc.team1559.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1559.robot.Wiring;

//dont touch my code without consent please ty - hannah, noah w, jason v
public class Grabber
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
        solenoid.set(true); //go Snatch that Hatch
    }

    public void bringHatch()
    {
        solenoid.set(false); //bring that hatch in bb
    }

    public void setSpark(double speed)
    {
        spark.set(speed); //sets the motor value
    }
    public void getCargo()
    {
        spark.set(1);  //activates intake for cargo mechanism wheel motor guy
    }

    public void removeCargo()
    {
        spark.set(0); //spits that cargo out like expired food
    }

    public Spark getSpark()
    {
        return spark; //give spark value
    }

    public void getValues()
    {
        //to do: make this class useful once i know for sure what goes here

    }
}