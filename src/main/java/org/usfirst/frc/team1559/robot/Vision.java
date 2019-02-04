package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.DriverStation;

public class Vision {

	UDPClient client;
	
	private  VisionData VData; 


	private Vision() {
		client = new UDPClient();
		VData = new VisionData();
		VData.status = 0;
	}

	public void update() {
		try {
			String in = client.get();
			String[] parameters = in.split(" ");
			if(parameters.length<4){
				VData.status = 2;
			}
			
			VisionData NewData = new VisionData();
			NewData.x = Double.parseDouble(parameters[0]);
			NewData.y = Double.parseDouble(parameters[1]);
			NewData.r = Double.parseDouble(parameters[2]);
			NewData.status = Integer.parseInt(parameters[3]);
			VData = NewData;		
			
        } 
        catch (NumberFormatException | NullPointerException e) {
			System.err.println("nothing happening here");
		}
	}

	public VisionData getData() {
		return VData;
	}



	private static Vision instance;

	public static Vision getInstance() {
		if (instance == null) {
			instance = new Vision();
		}

		return instance;
	}
}
