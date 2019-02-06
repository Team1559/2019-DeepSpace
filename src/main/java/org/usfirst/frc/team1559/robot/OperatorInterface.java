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
    private DTButton[] driverButtons, copilotButtons, cocopilotButtons;

    public OperatorInterface() {
        pilot = new Joystick(Constants.PILOT_JOYSTICK);
        copilot = new Joystick(Constants.COPILOT_JOYSTICK);
        copilotButtons = new DTButton[20];
        for(int i = 0; i < copilotButtons.length; i++) {
            copilotButtons[i] = new DTButton(copilot, i + 1);
        }
    }

    public double getPilotX() {
        return (-1)*pilot.getRawAxis(0);
    }

    public double getPilotY() {
        if((pilot.getRawAxis(1))/(Math.abs(pilot.getRawAxis(1))) == 1) {
            return (-1)*(Math.pow(pilot.getRawAxis(1), 2));
        }
            return (Math.pow(pilot.getRawAxis(1), 2));
    }

    public double getPilotZ() {
        return pilot.getRawAxis(2);
    }

    public DTButton getCopilotButton(int num) {
        //Enter important stuff here Mike!
        return copilotButtons[num];
    }

    public double getCopilotAxis(int num) {
        //Enter important stuff here Mike!
        return copilot.getRawAxis(num);
    }

    public DTButton getCocopilotButton(int num) {
        //Enter important stuff here Mike!!!!
        return cocopilotButtons[num];
    }

    public boolean axisToButtonIsPressed(int axis) {
        return (copilot.getRawAxis(axis) == 1);
    }
}