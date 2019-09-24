package org.usfirst.frc.team1559.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import com.ctre.phoenix.motorcontrol.ControlMode;
import org.usfirst.frc.team1559.robot.OperatorInterface;
import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.Wiring;
import org.usfirst.frc.team1559.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Grabber
{
    public Solenoid solenoid;
    public Solenoid relice_hatch;
    public WPI_TalonSRX ballIntake;
    public double speedBall, slowBall, speedHatch, stopHatch;
    public int cargocounter;
    public int cargotimer;
    public boolean hatchUp;
    private final int TIMEOUT = 0;
    public Grabber(OperatorInterface oi)
    
    {
        relice_hatch = new Solenoid(Wiring.Newhatchsoloniod);
        solenoid = new Solenoid(Wiring.NTK_SOLENOID);
        ballIntake = new WPI_TalonSRX(Wiring.NTK_TALONSRX_BI);
        speedBall = 0.85; //FIND A SPEED THAT WORKSs
        slowBall = 0.5;
        speedHatch = 0.5; //FIND A SPEED THAT WORKS
        stopHatch = 0.5;
        cargocounter = 0;
        cargotimer = 0;
        hatchUp = true;
        solenoid.set(true);
        ballIntake.enableCurrentLimit(true);
		ballIntake.configPeakCurrentLimit(24,TIMEOUT); // values may not be correct
    }
    public void drive() {
        if(Robot.oi.pilot.getRawButton(Constants.BTN_INTAKE)) {
            cargotimer = 1;
            getCargo();
            SmartDashboard.putNumber("__Ball", 1);
        }
        else if(Robot.oi.pilot.getRawButton(Constants.BTN_OUTTAKE)) {
            cargocounter = 1;
            cargotimer = 0;
            removeCargo(); 
        }
        if(cargotimer>=1 && cargotimer <=8){
        cargotimer = cargotimer + 1;
        }
        else{ if(cargotimer>4)
        slowBall();
        }
            if(cargocounter>=1 && cargocounter <=7){
                cargocounter = cargocounter + 1;
                
                }
               else if(cargocounter>3){
                    StopBall(); 
                    cargocounter = 0;  
                }
        
    
    }
        
    public void slowBall()
    {
        ballIntake.set(ControlMode.PercentOutput, slowBall);
    }
    public void StopBall()
    {
        ballIntake.stopMotor();
    }
    public void releaseHatch()
    {
        SmartDashboard.putNumber("realice hatch", 5.0);
        relice_hatch.set(true); //go Snatch that Hatch
    }
    public void GrabHatch()
    {
        SmartDashboard.putNumber("realice hatch", 1.0);
        relice_hatch.set(false); //bring that hatch in bb
    }
    public void releasePiston()
    {
        solenoid.set(false); //go Snatch that Hatch
    }
    public void resetPiston()
    {
        solenoid.set(true); //bring that hatch in bb
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
        //ControlMode.PercentOutput,  was first argument
    }
    public void removeCargo()
    {
        ballIntake.set(ControlMode.PercentOutput, -speedBall);
     //ControlMode.PercentOutput,  was first argument
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
