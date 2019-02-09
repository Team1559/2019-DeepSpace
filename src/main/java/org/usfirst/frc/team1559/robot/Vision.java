package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.DriverStation;

public class Vision {

	private boolean connected;

	UDPClient client;
	private double angle;
    private double x;
    private double y; 

	private Vision() {
		client = new UDPClient();
		connected = true;
    }

	public void update() {
		try {
			String in = client.get();
			
				if (in.indexOf("f") >= 0) {
					double temp1 = Double.parseDouble(in.substring(in.indexOf("f") + 1));
					if (temp1 != -1000) {
						x = temp1;
					}
                } 
                else 
                {
					double temp = Double.parseDouble(in);
					if (temp != -1000) {
						angle = temp;
					}
				}
			
        } 
        catch (NumberFormatException | NullPointerException e) {
			System.err.println("nothing happening here");
		}
	}

	public double getAngle() {
		return angle;
	}

	public double getX() {
		return x;
    }
    
    public double getY(){
        return y;
    }


	public String getRaw() {
		return client.get();
	}

	public boolean isConnected() {
		return connected;
	}

	private static Vision instance;

	public static Vision getInstance() {
		if (instance == null) {
			instance = new Vision();
		}

		return instance;
	}
}
