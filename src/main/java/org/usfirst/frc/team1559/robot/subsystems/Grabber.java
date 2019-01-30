package org.usfirst.frc.team1559.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team1559.robot.Wiring;

//dont touch my code without consent please ty - hannah, noah w, jason v
public class Grabber
{
    private DigitalInput limitSwitch1, limitSwitch2, limitSwitch3, limitSwitch4;
    private Solenoid solenoid;
    private WPI_TalonSRX ballIntake, hatchSlapperL, hatchSlapperR;
    private double speedBall, speedHatch;

    public Grabber()
    {
        //solenoid = new Solenoid(Wiring.NTK_SOLENOID);
        ballIntake = new WPI_TalonSRX(Wiring.NTK_SPARK_BI);
        //hatchSlapperL = new WPI_TalonSRX(Wiring.NTK_SPARK_HL);
        //hatchSlapperR = new WPI_TalonSRX(Wiring.NTK_SPARK_HR);
        speedBall = .5; //FIND A SPEED THAT WORKS
        //speedHatch = .5; //FIND A SPEED THAT WORKS
    }

    public void getHatch()
    {
        solenoid.set(true); //go Snatch that Hatch
    }

    public void bringHatch()
    {
        solenoid.set(false); //bring that hatch in bb
    }

    public void setSpeedBall(double speed)
    {
        speedBall = speed; //sets the motor value
    }
    public void setSpeedHatch(double speed)
    {
        speedHatch = speed; //sets the motor value
    }

    public void getCargo()
    {
        ballIntake.set(ControlMode.PercentOutput, speedBall);
    }

    public void removeCargo()
    {
        ballIntake.set(ControlMode.PercentOutput, -speedBall);
    }

    public double getSpark()
    {
        return ballIntake.get(); //give spark value
    }

    public void slapHatch() //Activates motors on hatch slapper that will SLAP THAT HATCH
    {
        hatchSlapperL.set(ControlMode.PercentOutput, speedHatch);
        hatchSlapperR.set(ControlMode.PercentOutput, speedHatch);
        
        do
        {
            if(getLimitValue(2) == true)
            {
                hatchSlapperL.set(ControlMode.PercentOutput, 0);
                hatchSlapperR.set(ControlMode.PercentOutput, 0);
            }
        }
        while(speedHatch != 0);
    }

    public void unslapHatch() //Brings the hatch slapper back into rest position (Should place the hatch on the hatch snatcher!!)
    {
        hatchSlapperL.set(ControlMode.PercentOutput, -speedHatch);
        hatchSlapperR.set(ControlMode.PercentOutput, -speedHatch);
        do
        {
            if(getLimitValue(1) == true)
            {
                hatchSlapperL.set(ControlMode.PercentOutput, 0);
                hatchSlapperR.set(ControlMode.PercentOutput, 0);
            }
        }
        while(speedHatch != 0);
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