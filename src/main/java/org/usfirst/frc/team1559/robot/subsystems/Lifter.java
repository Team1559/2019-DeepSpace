
package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.Wiring;
import org.usfirst.frc.team1559.robot.Constants;

import org.usfirst.frc.team1559.robot.MathUtils;
import org.usfirst.frc.team1559.robot.OperatorInterface;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
// import com.sun.tools.javac.util.Convert;

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

private double[] portPositions = new double[3];
private double[] hatchPositions = new double[3];

private final int ticksToPort1 = 20; //Placeholder value
private final int ticksToPort2 = 40; //Placeholder value
private final int ticksToPort3 = 60; //Placeholder value

private final int ticksToHatch1 = 10; //Placeholder value
private final int ticksToHatch2 = 30; //Placeholder value
private final int ticksToHatch3 = 50; //Placeholder value

private int potUseableBottom; //Code will auto adjust values based on this one.
private int potUseableTop; //Placeholder
private int potRange = 150; //This is just a placeholder value. Make sure we find the actual range that we want.
private final int potMax = 1023; // This is a placeholder. This is the farthest the pot can rotate.

private double kP = 6; //Just for testing purposes
private double kI = 0;
private double kD = 10*kP; //Just for testing purposes
private double kF = 0;
private final int TIMEOUT = 0;

private boolean isAxis = true;

	public Lifter(OperatorInterface oiInput) {
		lifterMotor = new WPI_TalonSRX(Wiring.LIFTER_TALON);
		oi = oiInput;

		potUseableBottom = getPot();
		potUseableTop = potUseableBottom + potRange;

		lifterMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TIMEOUT);
		lifterMotor.enableCurrentLimit(true);
		lifterMotor.configPeakCurrentLimit(0,TIMEOUT);
		lifterMotor.configContinuousCurrentLimit(40, TIMEOUT);
		lifterMotor.configPeakCurrentDuration(1800,TIMEOUT);

		lifterMotor.configNominalOutputForward(+.05, TIMEOUT);
		lifterMotor.configNominalOutputReverse(-0.1, TIMEOUT);
		lifterMotor.configPeakOutputForward(+1, TIMEOUT);
		lifterMotor.configPeakOutputReverse(-0.55, TIMEOUT);

		lifterMotor.config_kP(0, kP, TIMEOUT);
		lifterMotor.config_kI(0, kI, TIMEOUT);
		lifterMotor.config_kD(0, kD, TIMEOUT);
		lifterMotor.config_kF(0, kF, TIMEOUT);

		potUseableTop = potUseableBottom + potRange;
		if(potUseableTop > potMax) {
			for(int i = 0; i < 20; i++){
				System.out.println("WARNING!!!! The current value for the top of the pot is higher than the pot can actually go!");
				//Do we want the motor to stop at the pot max?
			}
		}
		setupPortPos();
		setupHatchPos();

	}

	public int getPot() {
		return lifterMotor.getSelectedSensorPosition(Wiring.LIFTER_POT);
	}

	public void setupPortPos() {
		portPositions[0] = potUseableBottom + ticksToPort1;
		portPositions[1] = potUseableBottom + ticksToPort2;
		portPositions[2] = potUseableBottom + ticksToPort3;
	}

	public void setupHatchPos() {
		hatchPositions[0] = potUseableBottom + ticksToHatch1;
		hatchPositions[1] = potUseableBottom + ticksToHatch2;
		hatchPositions[2] = potUseableBottom + ticksToHatch3;
	}

	public void goToPortPos(int pos) {
		pos -= 1;
		lifterMotor.set(ControlMode.Position, portPositions[pos]);
		// System.out.println(getPot()); //For testing purposes
	}

	public void goToHatchPos(int pos) {
		pos -= 1;
		lifterMotor.set(ControlMode.Position, hatchPositions[pos]);
		// System.out.println(getPot()); //For testing purposes
	}

	public void goToCargoShipHatch() {
		goToHatchPos(1);
	}

	public void goToCargoShipCargoDrop() {
		goToHatchPos(2);
	}

	public void goToBottom() {
		lifterMotor.set(ControlMode.Position, potUseableBottom);
		// System.out.println(getPot()); //For testing purposes
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

	public void goUp() {
		lifterMotor.set(0.3);
	}

	public void goDown() {
		lifterMotor.set(-0.3);
	}

	public void stop() {
		// lifterMotor.set(0.0);
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
		if(oi.copilot.getRawButton(4) && oi.getCopilotAxis(3) == 1) { 
			isAxis = false;
			// System.out.println(oi.copilot.getRawButton(1));
			// System.out.println(1);
			goToPortPos(1);
			
		}
		// else if(oi.copilot.getRawButton(1) == true)
		// {
		// 	isAxis = false;
		// 	goToPortPos(1);
		// }
		else if(oi.copilot.getRawButton(6) && oi.getCopilotAxis(3) == 1) { 
			isAxis = false;
			// System.out.println(oi.copilot.getRawButton(2));
			// System.out.println(2);
			goToPortPos(2);
			
		}
		else if(oi.copilot.getRawButton(5) && oi.getCopilotAxis(3) == 1) { 
			isAxis = false;
			goToPortPos(3);
		}
		else if(oi.copilot.getRawButton(4)) { 
			isAxis = false;
			// System.out.println(oi.copilot.getRawButton(3));
			// System.out.println(3);
			goToCargoShipHatch();
			
		}
		else if(oi.copilot.getRawButton(6)) { 
			isAxis = false;
			// System.out.println(oi.copilot.getRawButton(4));
			// System.out.println(4);
			goToCargoShipCargoDrop();
			
		}
		else if(oi.copilot.getRawButton(5)) { 
			isAxis = false;
			// System.out.println(oi.copilot.getRawButton(6));
			// System.out.println(5);
			goToHatchPos(3);
			
		}
		else if(oi.copilot.getRawButton(3)) {
			 goToBottom();
		}
		else if(oi.copilot.getRawButton(8)) { 
			isAxis = false;
			// System.out.println(oi.copilot.getRawButton(7));
			// System.out.println(6);
			recallibrateSystem();
			
		}
		else if(oi.getCopilotAxis(1) == -1.0) {
			isAxis = true;
			goUp();
			// System.out.println(oi.getCopilotAxis(1));
			
		}
		else if(oi.getCopilotAxis(1) == 1) {
			isAxis = true;
			goDown();
			// System.out.println(oi.getCopilotAxis(1));
			
		}
		else if((int)(oi.getCopilotAxis(1)) == 0 && isAxis) {
			stop();
			// System.out.println((int)(oi.getCopilotAxis(1)));
		}
		// if(oi.copilot.getRawButton(1))
		// 	System.out.println(1);
		// else if(oi.copilot.getRawButton(2))
		// 	System.out.println(2);
		// else if(oi.copilot.getRawButton(3))
		// 	System.out.println(3);
		// else if(oi.copilot.getRawButton(4))
		// 	System.out.println(4);
		// else if(oi.copilot.getRawButton(5))
		// 	System.out.println(5);
		// else if(oi.copilot.getRawButton(6))
		// 	System.out.println(6);
		// else if(oi.copilot.getRawButton(7))
		// 	System.out.println(7);
		// else if(oi.copilot.getRawButton(8))
		// 	System.out.println(8);
		// else if(oi.copilot.getRawButton(9))
		// 	System.out.println(9);
		// else if(oi.copilot.getRawButton(10))
		// 	System.out.println(10);
	}


}
//It's over!!!!