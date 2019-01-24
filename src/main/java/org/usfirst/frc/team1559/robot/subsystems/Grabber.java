package org.usfirst.frc.team1559.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1559.robot.Wiring;

//dont touch my code without consent please ty - hannah, noah w, jason v
public class Grabber
{
    private DigitalInput limitSwitch1, limitSwitch2, limitSwitch3, limitSwitch4;
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

    public double getSpark()
    {
        return spark.get(); //give spark value
    }

    public boolean getLimitValue(int x) //x will be 1 or 2 (1 is for checking if the arms are on the robot, 2 is for checking if the arms are deployed)
    {
        boolean b = false;
        if(x == 1)
        {
            if(limitSwitch1.get() == true && limitSwitch2.get() == true)
            {
                b = true;
            }
            else
            {
                b = false;
            }
        }
        if(x == 2)
        {
            if(limitSwitch3.get() == true && limitSwitch4.get() == true)
            {
                b = true;
            }
            else
            {
                b = false;
            }
        }
        return b;
        /*if it returns true then the switches are activated.*/
    }

}