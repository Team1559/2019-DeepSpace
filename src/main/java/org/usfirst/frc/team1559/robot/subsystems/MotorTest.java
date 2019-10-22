package org.usfirst.frc.team1559.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class MotorTest
{
    public static void main(String[] args)
    {
        WPI_TalonSRX motor = new WPI_TalonSRX(1);
        

    }

    public static void goUp(WPI_TalonSRX motor)
    {
        motor.set(ControlMode.PercentOutput,0.5);
    }

    public static void goDown(WPI_TalonSRX motor)
    {
        motor.set(ControlMode.PercentOutput, -0.5);
    }

    public static void stop(WPI_TalonSRX motor)
    {
        motor.stopMotor();
    }
}