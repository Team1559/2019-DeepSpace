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
    public Solenoid solenoid;
    public Solenoid relice_hatch;
    private WPI_TalonSRX hatchSlapperL, hatchSlapperR;
    public Talon ballIntake;
    public double speedBall, slowBall, speedHatch, stopHatch;
    public int cargocounter;
    public int cargotimer;
    public boolean hatchUp;
    public Grabber(OperatorInterface oi)
    {
        relice_hatch = new Solenoid(Wiring.Newhatchsoloniod);
        solenoid = new Solenoid(Wiring.NTK_SOLENOID);
        ballIntake = new Talon(Wiring.NTK_TALONSRX_BI);
        hatchSlapperL = new WPI_TalonSRX(Wiring.NTK_TALONSRX_HL);
        hatchSlapperR = new WPI_TalonSRX(Wiring.NTK_TALONSRX_HR);
        speedBall = 0.85; //FIND A SPEED THAT WORKSs
        slowBall = 0.4;
        speedHatch = 0.5; //FIND A SPEED THAT WORKS
        stopHatch = 0.5;
        cargocounter = 0;
        cargotimer = 0;
        hatchUp = true;
        solenoid.set(true);
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
        
        // if(Robot.oi.pilot.getRawButtonPressed(Constants.HATCH_SNATCHER))
		// 	{
		// 		toggleHatch();
        //     }
            if(Robot.oi.pilot.getRawButtonPressed(Constants.LOWERHATCH)){
                releaseHatch();
            }
            else{
                GrabHatch();
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
    public void releaseHatch()
    {
        relice_hatch.set(true); //go Snatch that Hatch
    }
    public void GrabHatch()
    {
        relice_hatch.set(false); //bring that hatch in bb
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
        ballIntake.set(-1);
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
