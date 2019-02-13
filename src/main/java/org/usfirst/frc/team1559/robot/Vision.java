package org.usfirst.frc.team1559.robot;

public class Vision {

	UDPClient client;
	
	private  VisionData VData; 
	double cameraYOffset = 11.6;
	double cameraXOffset = 2.6;
	public Vision() {
		client = new UDPClient();
		VData = new VisionData();
		VData.status = 0;
		
	}
	public void VisionInit()
	{
	//client.run();
	}
	public void update() {
		try {
			VisionData NewData = new VisionData();
			NewData.status = 2;

			String in = client.get();
			//System.out.println(in);

			if(in != null) {
				String[] parameters = in.split(" ");

				if(parameters.length >= 4){
					NewData.x = Double.parseDouble(parameters[0])-cameraXOffset;
					NewData.y = Double.parseDouble(parameters[1])-cameraYOffset;
					NewData.r = Double.parseDouble(parameters[2]);
					NewData.status = Integer.parseInt(parameters[3]);
				}	
			}
			VData = NewData;	
        } 
        catch (NumberFormatException | NullPointerException e) {
			//System.err.println(e.toString());
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
