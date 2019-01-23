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
	 *    | [] |    3 (6 ft, 11.5 in)
	 *    |    |
	 *    | [] |    2 (4 ft, 7.5 in)
	 *   /|    |\
	 *  / | [] | \  1 (2 ft, 3.5 in)
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

private WPI_TalonSRX liftermotor; //Remember! This will need to be instantiated in the Lifter constructor!
private static final int TIMEOUT = 0;
private double kP = 0;// values for the PID loop (Jack here, I can't make a PID loop, so plz fix once things explode)
private double kI = 0;
private double kD = 0;
private double kF = 0;
private double setpoint;

private static final double[] ROCKET_PORTS_INCHES = {27.5, 55.5, 83.5};
private static final double[] ROCKET_HATCH_INCHES = {19.0, 47.0, 65.0};
private static final double CARGO_SHIP_HATCH_INCHES = 19.0;
private static final double CARGO_SHIP_DROPOFF_INCHES = 39.75;
private static final double ROCKET_BOT_PORT = 27.5; //inches
private static final double ROCKET_TOP_PORT = 83.5; //inches
private static final double ROCKET_BOT_HATCH = 19.0; //inches
private static final double ROCKET_TOP_HATCH = 65.0; //inches
public int lowerbound = 0; //Find the lowest point on the potentiometer to make this integer.
public int upperbound;
private static final int RANGE = 0; //Find the range that we need from the pot!
private double[] portPositionTicks = new double[ROCKET_PORTS_INCHES.length];
private double[] hatchPositionTicks = new double[ROCKET_HATCH_INCHES.length];
private double cargoShipHatchPositionTicks;
private double cargoShipCargoBayPositionTicks;

private int position = 1;

	public Lifter() {
		// motors, ids, etc
		liftermotor = new WPI_TalonSRX(Wiring.LIFTER_TALON);
		liftermotor.set(ControlMode.Position, 0);
		calculatePortPositions();
		calculateHatchPositions();
	}
/**
*Returns the value of the Potentiometer
*/
	public double getPot(){
		return liftermotor.getSelectedSensorPosition(Wiring.LIFTER_POT);
	}

	public void driveManual(double val) {
		if (!Robot.fightstick) {
			if(val >= 0) {
				setpoint -= 3*val;
			}
			else if(val <= 0) {
				setpoint -= 5*val;
			}
		}
		else {
			setpoint -= 5*val;
		}
	}

	public void update() {
		liftermotor.set(ControlMode.Position, setpoint);
	}

	public void setPortPosition(int pos) {
		pos -= 1;
		setpoint = portPositionTicks[position-1];
		position = pos;
		update();
	}

	public void setHatchPosition(int pos) {
		pos -= 1;
		setpoint = hatchPositionTicks[position-1];
		position = pos;
		update();
	}

	public boolean isAtPortPosition(int position) {
		return setpoint == portPositionTicks[position-1];
	}

	public boolean isAtHatchPosition(int position) {
		return setpoint == hatchPositionTicks[position-1];
	}

	public void setMotor(double value) {
		liftermotor.set(ControlMode.PercentOutput, value);
	}

	public void holdPosition() {
		setpoint = getPot();
	}

	public void reset() {
		lowerbound = (int) getPot();
		calculatePortPositions();
		calculateHatchPositions();
	}
	public void debug() {
		// output things for debugging such as motor/encoder values
	}

	// public void home() {
	// 	// change to ground() if that would make more sense
	// 	moveTo(Constants.LIFTER_BOTTOM);
	// }


	//Will probably be removed due to other methods having its basic function. Keep just in case
	// public void moveTo(int positionNumber) {
	// 	// maybe instead of hardcoding each constant, just have their values as motor/position units for setPosition(int)
	// 	switch ((short) positionNumber) {
	// 		case Constants.LIFTER_POS1:
	// 			// pos 1
	// 			break;
	// 		case Constants.LIFTER_POS2:
	// 			// pos 2
	// 			break;
	// 		case Constants.LIFTER_POS3:
	// 			// pos 3
	// 			break;
	// 		case Constants.LIFTER_POS4:
	// 			// pos 4
	// 			break;
	// 		case Constants.LIFTER_TOP:
	// 			// pos top (highest possible)
	// 			break;
	// 		case Constants.LIFTER_BOTTOM:
	// 			// pos bottom (lowest possible)
	// 			break;
	// 		default:
	// 			// unknown
	// 	}
	// }

	
	
	public WPI_TalonSRX getTalon(){
		return liftermotor;
	}

	private void calculatePortPositions() {
		upperbound = lowerbound + RANGE;
		if(upperbound > Constants.POT_TOP)
		{
			System.out.println("WARNING!!!! upperbound exceeds the highest possible value of the potentiometer!");
			System.out.println("Failure to fix this may result in the pot breaking!");
		}
		int n = ROCKET_PORTS_INCHES.length;
		for (int i = 0; i < n; i++) {
			portPositionTicks[i] = MathUtils.mapRange(ROCKET_PORTS_INCHES[i], ROCKET_BOT_PORT, ROCKET_TOP_PORT, lowerbound, upperbound);

		}
		cargoShipCargoBayPositionTicks = MathUtils.mapRange(CARGO_SHIP_DROPOFF_INCHES, CARGO_SHIP_DROPOFF_INCHES, CARGO_SHIP_DROPOFF_INCHES, lowerbound, upperbound);
	}

	private void calculateHatchPositions() {
		upperbound = lowerbound + RANGE;
		int n = ROCKET_HATCH_INCHES.length;
		for(int i = 0; i < n; i++){
			hatchPositionTicks[i] = MathUtils.mapRange(ROCKET_HATCH_INCHES[i], ROCKET_BOT_HATCH, ROCKET_TOP_HATCH, lowerbound, upperbound);
		}
		cargoShipHatchPositionTicks = MathUtils.mapRange(CARGO_SHIP_HATCH_INCHES, CARGO_SHIP_HATCH_INCHES, CARGO_SHIP_HATCH_INCHES, lowerbound, upperbound);
	}

}
//It's over!!!!