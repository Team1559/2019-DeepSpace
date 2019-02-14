/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot.subsystems;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * Add your docs here.
 */
public class Pixyballtracking {
    public int timer;
    public double r0;
    public double r1;
    public int bStatus;
    public int Ber;
    public int Bex;

    public double berror_r(double x0, double x1){
        double brError=(r0);
        SmartDashboard.putNumber("__r1",r1);
        SmartDashboard.putNumber("__r0",r0);
        return brError;
            
            
        }

    
    
    } 
