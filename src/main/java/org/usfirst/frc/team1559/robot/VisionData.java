package org.usfirst.frc.team1559.robot;

public class VisionData {
    
    public double x;
    public double y;
    public double r;
    public Integer status;

    public boolean IsValid(){
        if(status!=1){
            return false;
        }
        else
            return true;
    }
}