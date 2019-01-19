/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OperatorInterface {
    //drive.driveCartesian((pilot.getRawAxis(0)), -1*pilot.getRawAxis(1), pilot.getRawAxis(2));
    Joystick pilot, copilot;
    private DTButton[] driverButtons, copilotButtons, cocopilotButtons;

    public OperatorInterface() {
        pilot = new Joystick(Constants.PILOT_JOYSTICK);
        copilot = new Joystick(Constants.COPILOT_JOYSTICK);
    }

    public double getPilotY() {
        if(pilot.getRawAxis(0) <= 0.1 || -0.1 <= pilot.getRawAxis(0)) {
            return 0;
        }
        return pilot.getRawAxis(0);
    }

    public double getPilotX() {
        if((-1)*(pilot.getRawAxis(1)) <= 0.1 || -0.1 <= (-1)*(pilot.getRawAxis(1))) {
            return 0;
        }
        return (-1)*(pilot.getRawAxis(1));
    }

    public double getPilotZ() {
        if(pilot.getRawAxis(2) <= 0.1 || -0.1 <= pilot.getRawAxis(2)) {
            return 0;
        }
        return pilot.getRawAxis(2);
    }

    public DTButton getCopilotButton(int num) {
        //Enter important stuff here Mike!
        return copilotButtons[num];
    }

    public double getCopilotAxis(int num) {
        //Enter important stuff here Mike!
        return 0.0;
    }

    public DTButton getCocopilotButton(int num) {
        //Enter important stuff here Mike!!!!
        return cocopilotButtons[num];
    }

}