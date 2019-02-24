
package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.Wiring;
import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.MathUtils;
import org.usfirst.frc.team1559.robot.OperatorInterface;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

// Lifter Programers are Jack and Nick. Please defer to them if you want to make changes.

//Important images:
/**
	 * Go to the specified rocket cargo port (1, 2 or 3)
	 *      __
	 *    /    \
	 *    | [] |    port 3 (6 ft, 11.5 in)
	 *    |    |
	 *    | [] |    port 2 (4 ft, 7.5 in)
	 *   /|    |\
	 *  / | [] | \  port 1 (2 ft, 3.5 in)
	 *
	 * 
	 * Go to the specified rocket hatch holes (1, 2 or 3)
	 *      __
	 *    /    \
	 *    | [] |    hatch 3 (6 ft, 3 in)
	 *    |    |
	 *    | [] |    hatch 2 (3 ft, 11 in)
	 *   /|    |\
	 *  / | [] | \  hatch 1 (1 ft, 7 in)
	 *
	 * 
	 * Go to the cargo bay positions
	 *  ________________________   ____ 4 ft _____
	 * |      |      |      |   \  <--- (Drop off point) 3ft 3.75 in
	 * |______|______|______|____| ___ 2 ft 7.5 in ___
	 * |      |      |      |    |
	 * |  []  |  []  |  []  |    |  <--- 1ft, 7in
	 * |______|______|______|____|
	 */
public class Lifter {
private WPI_TalonSRX lifterMotor;
private OperatorInterface oi;

private double[] portPositions = new double[3]; //Holds heights of the port positions
private double[] hatchPositions = new double[3]; //Holds heights of the hatch positions

private final double ticksPerInch = 7.2;
private final double homeInches = 12;
									//27.5
private final double ticksToPort1 = (47-homeInches) * ticksPerInch; //The ticks to port 1
private final double ticksToPort2 = (75-homeInches) * ticksPerInch; //The ticks to port 2
private final double ticksToPort3 = (89-homeInches) * ticksPerInch; //The ticks to port 3

private final double ticksToHatch1 = (12-homeInches) * ticksPerInch; //The ticks to hatch 1
private final double ticksToHatch2 = (35-homeInches) * ticksPerInch; //The ticks to hatch 2
private final double ticksToHatch3 = (63-homeInches) * ticksPerInch; //The ticks to hatch 3


private int potUseableBottom = 95; //Bottom of the pot that we want
private int potUseableTop = 731; //Top of the pot that we want
private int potRange = 546; //Range of the pot that we will be using

private final int potMax = 1023; //This is the farthest the pot can rotate without breaking
private final int potMin = 5; //This is the lowest the pot can possibly go.

private double kP = 17; //Our P value for the PID loop
private double kI = 0; //Our I value for the PID loop
private double kD = 10*kP; //Our D value for the PID loop
private double kF = 0; //Our F value for the PID loop
private final int TIMEOUT = 0; //Our TIMEOUT value

public boolean isAxis = true; //Code uses this to determine if it is in manual control or copilot button mode

	public Lifter(OperatorInterface oiInput) { //This is the constructor for the Lifter
		lifterMotor = new WPI_TalonSRX(Wiring.LIFTER_TALON); //Creates the Lifter TalonSRX
		oi = oiInput; //Sets up the oi for the Lifter
		goToBottom(1); //Goes to the bottom at the start of the match

		// potUseableBottom = getPot();
		potUseableTop = potUseableBottom + potRange; //Double checks the top value

		lifterMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TIMEOUT);
		lifterMotor.enableCurrentLimit(true);
		lifterMotor.configPeakCurrentLimit(75,TIMEOUT);
		lifterMotor.configContinuousCurrentLimit(40, TIMEOUT);
		lifterMotor.configPeakCurrentDuration(1800,TIMEOUT);

		lifterMotor.configNominalOutputForward(0.05, TIMEOUT);
		lifterMotor.configNominalOutputReverse(-0.1, TIMEOUT);
		lifterMotor.configPeakOutputForward(1, TIMEOUT);
		lifterMotor.configPeakOutputReverse(-0.65, TIMEOUT);
		lifterMotor.config_kP(0, kP, TIMEOUT);
		lifterMotor.config_kI(0, kI, TIMEOUT);
		lifterMotor.config_kD(0, kD, TIMEOUT);
		lifterMotor.config_kF(0, kF, TIMEOUT);

		if(potUseableTop > potMax) { //Just a warning system to make sure we don't break our pot
			for(int i = 0; i < 20; i++){
				System.out.println("WARNING!!!! The current value for the top of the pot is higher than the pot can actually go!");
				//Do we want the motor to stop at the pot max?
			}
		}
		
		setupPortPos(); //Sets up the array of port positions
		setupHatchPos(); //Sets up the array of hatch positions

	}

	public int getPot() { //Returns the pot value for use by the code
		return lifterMotor.getSelectedSensorPosition(Wiring.LIFTER_POT);
	}

	public void setupPortPos() { //Sets up the array of port positions
		portPositions[0] = potUseableBottom + ticksToPort1;
		portPositions[1] = potUseableBottom + ticksToPort2;
		portPositions[2] = potUseableBottom + ticksToPort3;
	}

	public void setupHatchPos() { //Sets up the array of hatch positions
		hatchPositions[0] = potUseableBottom + ticksToHatch1;
		hatchPositions[1] = potUseableBottom + ticksToHatch2;
		hatchPositions[2] = potUseableBottom + ticksToHatch3;
	}

	public void goToPortPos(int pos) { //Goes to the specified port position
		pos -= 1;
		lifterMotor.set(ControlMode.Position, portPositions[pos]);
		SmartDashboard.putNumber("Pot going to", portPositions[pos]);
	}

	public void goToHatchPos(int pos) { //Goes to the specified hatch position
		pos -= 1;
		lifterMotor.set(ControlMode.Position, hatchPositions[pos]);
		SmartDashboard.putNumber("Pot going to", hatchPositions[pos]);
	}

	public void goToCargoShipHatch() { //Goes to the cargo ship hatch position
		goToHatchPos(1);
	}

	public void goToCargoShipCargoDrop() { //Goes to the cargo ship cargo dropoff position
		goToHatchPos(2);
	}

	public void goToBottom(int whichHome) { //Goes to a home position. 1 is standard home, 2 is if we have cargo in the cargo grabber
		if(whichHome == 1) {
		lifterMotor.set(ControlMode.Position, potUseableBottom);
		SmartDashboard.putNumber("Pot going to", potUseableBottom);
		}
		else if(whichHome == 2) {
		lifterMotor.set(ControlMode.Position, potUseableBottom + (3*ticksPerInch));
		SmartDashboard.putNumber("Pot going to", potUseableBottom + (3*ticksPerInch));
		}
	}

	public void recallibrateSystem() { //This method is in case the pot slips and we need to reset the other pot values based on it.
		potUseableBottom = getPot();
		potUseableTop = potUseableBottom + potRange;
		if(potUseableTop > potMax) {
			for(int i = 0; i < 20; i++){
				System.out.println("WARNING!!!! The current value for the top of the pot is higher than the pot can actually go!");
			}
		}
		setupPortPos();
		setupHatchPos();
	}

	public void goUp() { //Drives up the motor
		lifterMotor.set(ControlMode.PercentOutput,0.5);
		System.out.println("Going up!!!!");
	}

	public void goDown() { //Drives down the motor
		lifterMotor.set(ControlMode.PercentOutput,-0.3);
		System.out.println("Going down!!!!");
	}

	public void stop() { //Stops the motor
		lifterMotor.stopMotor();
	}

	/**
	 * IMPORTANT!!!! VERY BIG DRIVE LIFTER METHOD!!!!
	 * This method will do everything originally put into the robot.java class.
	 * @author SonicDRJ
	 */
	public void driveLifter() {
		/**
		 * IMPORTANT!!!! 
		 * Use the Fightstick in the XInput option to make sure it has the correct buttons!
		 * Otherwise it won't work!!!!
		*/
		// System.out.println(getPot()); //For testing purposes
		SmartDashboard.putNumber("Pot", getPot());
		SmartDashboard.putNumber("Range", potRange);
		SmartDashboard.putNumber("Pot Top", potUseableTop);
		SmartDashboard.putNumber("Pot Bottom", potUseableBottom);
		SmartDashboard.putNumber("Pot Max", potMax);
		SmartDashboard.putNumber("Pot Min", potMin);
		//maxOverride();
		/**
		 * The following methods all serve the same basic purpose:
		 * To check if their corresponding button is pressed and if the cargo button is pressed as well
		 * RawButton 4 corresponds to position 1
		 * RawButton 6 corresponds to position 2
		 * RawButton 5 corresponds to position 3
		 * CopilotAxis 3 corresponds to the cargo button, which changes how the above three button function
		 */
		if(oi.copilot.getRawButton(4) && oi.getCopilotAxis(3) == 1) { 
			isAxis = false;
			goToPortPos(1);
		}

		else if(oi.copilot.getRawButton(6) && oi.getCopilotAxis(3) == 1) { 
			isAxis = false;
			goToPortPos(2);
			
		}
		else if(oi.copilot.getRawButton(5) && oi.getCopilotAxis(3) == 1) { 
			isAxis = false;
			goToPortPos(3);
		}
		else if(oi.copilot.getRawButton(4) && oi.getCopilotAxis(3) != 1) { 
			isAxis = false;
			goToCargoShipHatch();
			
		}
		else if(oi.copilot.getRawButton(6) && oi.getCopilotAxis(3) != 1) { 
			isAxis = false;
			goToCargoShipCargoDrop();
			
		}
		else if(oi.copilot.getRawButton(5) && oi.getCopilotAxis(3) != 1) { 
			isAxis = false;
			goToHatchPos(3);
			
		}
		else if(oi.copilot.getRawButton(3) && oi.getCopilotAxis(3) != 1) {
			isAxis = false; 
			goToBottom(1);
		}
		else if(oi.copilot.getRawButton(3) && oi.getCopilotAxis(3) == 1) {
			isAxis = false;
			goToBottom(2);
		}
		else if(oi.copilot.getRawButton(8)) { 
			isAxis = false;
			recallibrateSystem();
		
		}
	}
}
//It's over!!!!