package org.usfirst.frc.team1559.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1559.robot.Wiring;

//dont touch my code without consent please ty - hannah, noah w, jason v
public class Grabber
{
    private DigitalInput limitSwitch1, limitSwitch2, limitSwitch3, limitSwitch4;
    private Solenoid solenoid;
    private WPI_TalonSRX ballIntake, hatchSlapperL, hatchSlapperR;


    public Grabber()
    {
        solenoid = new Solenoid(Wiring.NTK_SOLENOID);
        ballIntake = new WPI_TalonSRX(Wiring.NTK_SPARK_BI);
        hatchSlapperL = new WPI_TalonSRX(Wiring.NTK_SPARK_HL);
        hatchSlapperR = new WPI_TalonSRX(Wiring.NTK_SPARK_HR);
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
        ballIntake.set(speed); //sets the motor value
    }
    public void getCargo()
    {
        ballIntake.set(1);  //activates intake for cargo mechanism wheel motor guy
    }

    public void removeCargo()
    {
        ballIntake.set(0); //spits that cargo out like expired food
    }

    public double getSpark()
    {
        return ballIntake.get(); //give spark value
    }

    public void slapHatch() //Activates motors on hatch slapper that will SLAP THAT HATCH
    {
        hatchSlapperL.set(1);
        hatchSlapperR.set(1);
        if(getLimitValue(1) == true);
        {
            hatchSlapperL.set(0);
            hatchSlapperR.set(0);
        }
    }

    public void unslapHatch() //Brings the hatch slapper back into rest position (Should place the hatch on the hatch snatcher!!)
    {
        hatchSlapperL.set(0);
        hatchSlapperR.set(0);
        if(getLimitValue(1) == true);
        {
            hatchSlapperL.set(0);
            hatchSlapperR.set(0);
        }
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