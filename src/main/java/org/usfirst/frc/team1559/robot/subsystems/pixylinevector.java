/*----------------------------------------------------------------------------*/
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
    public int Ex; 
    public int Er;
    public int timer;
    
    
    public double error_x(int x0, int x1){
        double xError=(x1+x0)/2-40;
            return xError;
    }

    public double error_r(int y0, int y1, int x0, int x1){
       double rError;
        double error_tan;
       if (x1==x0){
            rError=0;
        }
        else{       
         error_tan=Math.atan((y1-y0)/(x1-x0));
            rError=Math.toDegrees(error_tan);
       
            if (rError<0){
                rError=90+rError;
            }
            else if(rError>0){
                rError=rError-90;
            }

        }  
        return rError;
    }
        
        
    

}