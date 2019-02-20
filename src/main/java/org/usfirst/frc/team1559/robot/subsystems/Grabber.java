package org.usfirst.frc.team1559.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team1559.robot.OperatorInterface;
import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.Wiring;
import org.usfirst.frc.team1559.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//dont touch my code without consent please ty - hannah, noah w, jason v
public class Grabber
{
    private DigitalInput limitSwitch1, limitSwitch2, limitSwitch3, limitSwitch4;
    private Solenoid solenoid;
    private WPI_TalonSRX hatchSlapperL, hatchSlapperR;
    private Talon ballIntake;
    private double speedBall, slowBall, speedHatch, stopHatch;
    private int Cargocounter;
    private int Cargotimer;
    private boolean hatchUp;
    public Grabber(OperatorInterface oi)

    {
        solenoid = new Solenoid(Wiring.NTK_SOLENOID);
        ballIntake = new Talon(Wiring.NTK_TALONSRX_BI);
        hatchSlapperL = new WPI_TalonSRX(Wiring.NTK_TALONSRX_HL);
        hatchSlapperR = new WPI_TalonSRX(Wiring.NTK_TALONSRX_HR);
        speedBall = 0.8; //FIND A SPEED THAT WORKSs
        slowBall = 0.4;
        speedHatch = 0.5; //FIND A SPEED THAT WORKS
        stopHatch = 0.5;
        Cargocounter = 0;
        Cargotimer = 0;
        hatchUp = true;
    }

    public void drive() {

        if(Robot.oi.pilot.getRawButton(Constants.BTN_INTAKE)) {
            Cargotimer = 1;
            getCargo();
            SmartDashboard.putNumber("__Ball", 1);
        }
        else if(Robot.oi.pilot.getRawButton(Constants.BTN_OUTTAKE)) {
            Cargocounter = 1;
            Cargotimer = 0;
            removeCargo();
            
        }
       
        if(Cargotimer>=1 && Cargotimer <=8){
        Cargotimer = Cargotimer + 1;
        }
        
        else{ if(Cargotimer>4)
        slowBall();
        }
            if( Cargocounter>=1 && Cargocounter <=7){
                Cargocounter = Cargocounter + 1;
                
                }
               else if(Cargocounter>3){
                    StopBall(); 
                    Cargocounter = 0;  
               }
    }

    public void slowBall()
    {
        ballIntake.set(slowBall);
    }

    public void StopBall()
    {
        ballIntake.stopMotor();
    }

    public void releasePiston()
    {
        solenoid.set(true); //go Snatch that Hatch
    }

    public void resetPiston()
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
        ballIntake.set(speedBall);
        //ControlMode.PercentOutput,  was first argument
    }

    public void removeCargo()
    {
        ballIntake.set(-speedBall);
     //ControlMode.PercentOutput,  was first argument
    }

    public void slapHatch() //Activates motors on hatch slapper that will SLAP THAT HATCH
    {
        hatchSlapperL.set(ControlMode.PercentOutput, speedHatch);
        hatchSlapperR.set(ControlMode.PercentOutput, speedHatch);
        {
            if(getLimitValue(2) == true)
            {
                stopHatch = 0;
                hatchSlapperL.set(ControlMode.PercentOutput, stopHatch);
                hatchSlapperR.set(ControlMode.PercentOutput, stopHatch);
            }
        }
        while(stopHatch != 0); 
    }

    public void unslapHatch() //Brings the hatch slapper back into rest position (Should place the hatch on the hatch snatcher!!)
    {
        hatchSlapperL.set(ControlMode.PercentOutput, -speedHatch);
        hatchSlapperR.set(ControlMode.PercentOutput, -speedHatch);
        {
            if(getLimitValue(1) == true)
            {
                stopHatch = 0;
                hatchSlapperL.set(ControlMode.PercentOutput, stopHatch);
                hatchSlapperR.set(ControlMode.PercentOutput, stopHatch);
            }
        } 
        while(stopHatch != 0);
    }

    //Limit Switches positions: 
    //Upper Left (1) Upper Right (2)
    //Lower Left (3) Lower Right (4)
    public boolean getLimitValue(int x) 
    {
        boolean b = false;
        if(x == 1) //Stop at upper pos
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
        else if(x == 2) //Stop at floor
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
        //if it returns true then the switches are activated.
    }

    public void toggleHatch()
    {
        if(hatchUp)
        {
            hatchUp = false;
            releasePiston();
            //this brings the hatch piston down
        }
        else if(!hatchUp)
        {
            hatchUp = true;
            resetPiston();
            //this brings the hatch piston up
        }
    }
}