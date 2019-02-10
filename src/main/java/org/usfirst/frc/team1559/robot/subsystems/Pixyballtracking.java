/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot.subsystems;

/**
 * Add your docs here.
 */
public class Pixyballtracking {
    public int timer;
    public double x0;
    public double x1;
    public int bStatus;
    public int Ber;
    public int Bex;

    public double berror_x(double x0, double x1){
        double bxError=(40-x0);
            return bxError;
        }
}