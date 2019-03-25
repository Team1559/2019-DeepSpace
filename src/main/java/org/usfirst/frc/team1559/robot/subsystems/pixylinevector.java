/*-- --------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1559.robot.subsystems;
import java.lang.Math;
public class pixylinevector {
    public int status;
    public int x0;
    public int y0;
    public int x1;
    public int y1;
    public int index;
    public int flags;
    public double Ex; 
    public double Er;
    private double Pv;
    private double Pt;
    private double cx;
    private double vp;
    public int timer;

    public double error_x(double x0, double x1){
        double xError=(40-x0);
            return xError;
    }
    public double error_r(double y0, double y1, double x0, double x1){
       double rError;
        double error_tan;
       if (x1==x0){
            rError=0;
        }
        else{       
            // error_tan=Math.atan((x1-40)/(Math.abs(y1-y0)));
            Pv = Math.atan((x1-x0)/(y1-y0));
            Pt = Math.atan((cx - x0)/(vp - y0));
            error_tan = Pt-Pv;
            rError=Math.toDegrees(error_tan);
        }  
        return rError;
    }
}