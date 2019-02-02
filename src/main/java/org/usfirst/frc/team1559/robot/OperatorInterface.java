/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OperatorInterface {
    public Joystick pilot, copilot;

    public OperatorInterface() {
        pilot = new Joystick(Constants.PILOT_JOYSTICK);
        copilot = new Joystick(Constants.COPILOT_JOYSTICK);
    }

    public double getPilotY() {
        return pilot.getRawAxis(0);
    }

    public double getPilotX() {
        if((pilot.getRawAxis(1))/(Math.abs(pilot.getRawAxis(1))) == 1) {
            return (-1)*(Math.pow(pilot.getRawAxis(1), 2));
        }
            return (Math.pow(pilot.getRawAxis(1), 2));
    }

    public double getPilotZ() {
        return pilot.getRawAxis(2);
    }

    public boolean axisToButtonIsPressed(int axis) {
        return (copilot.getRawAxis(axis) == 1);
    }
}