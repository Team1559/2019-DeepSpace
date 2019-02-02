package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.Wiring;
import org.usfirst.frc.team1559.robot.Constants;

import org.usfirst.frc.team1559.robot.MathUtils;

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

private double[] portPositions = new double[3];
private double[] hatchPositions = new double[3];

private final int ticksToPort1 = 20; //Placeholder value
private final int ticksToPort2 = 40; //Placeholder value
private final int ticksToPort3 = 60; //Placeholder value

private final int ticksToHatch1 = 10; //Placeholder value
private final int ticksToHatch2 = 30; //Placeholder value
private final int ticksToHatch3 = 50; //Placeholder value

private int potUseableBottom = 0; //Placeholder value Code will auto adjust values based on this one.
private int potUseableTop = 150; //Placeholder
private int potRange = 150; //This is just a placeholder value. Make sure we find the actual range that we want.
private final int potMax = 300; // This is a placeholder. This is the farthest the pot can rotate.

	public Lifter() {
		lifterMotor = new WPI_TalonSRX(Wiring.LIFTER_TALON);
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
	}

	public void goToHatchPos(int pos) {
		pos -= 1;
		lifterMotor.set(ControlMode.Position, hatchPositions[pos]);
	}

	public void goToCargoShipHatch() {
		goToHatchPos(1);
	}

	public void goToCargoShipCargoDrop() {
		goToHatchPos(2);
	}

	public void goToBottom() {
		lifterMotor.set(ControlMode.Position, potUseableBottom);
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
		lifterMotor.set(ControlMode.Velocity, 0.01);
	}

	public void goDown() {
		lifterMotor.set(ControlMode.Velocity, -0.01);
	}


}
//It's over!!!!