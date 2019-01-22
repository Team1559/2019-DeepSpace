/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OperatorInterface {
    Joystick pilot, copilot;
    boolean fineControl = false;

    public OperatorInterface() {
        pilot = new Joystick(Constants.PILOT_JOYSTICK);
        copilot = new Joystick(Constants.COPILOT_JOYSTICK);
    }

    public double getPilotY() {
        return pilot.getRawAxis(0);
    }

    public double getPilotX() {
        if(fineControl) {
            if(Math.abs(pilot.getRawAxis(1)) <= 0.2 ) {
                return 0;
            }
        }
        return (-1)*(pilot.getRawAxis(1));
    }

    public double getPilotZ() {
        return pilot.getRawAxis(2);
    }

    public void checkFineControl() {
        if(pilot.getRawButton(Constants.BTN_FINE_DRIVE_CONTROL)) {
            fineControl = !fineControl;
        }
    }

}