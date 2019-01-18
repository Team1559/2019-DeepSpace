package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.Wiring;
import org.usfirst.frc.team1559.robot.Constants;
import org.usfirst.frc.team1559.robot.MathUtils;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

// Lifter Programers are Jack and Nick. Please refer to them if you want to make changes.
public class Lifter {

private WPI_TalonSRX liftermotor; //Remember! This will need to be instantiated in the Lifter constructor!
private static final int TIMEOUT = 0;
private double kP = 0;// values for the PID loop
private double kI = 0;
private double kD = 0;
private double kF = 0;
private double setpoint;

private static final double[] ROCKET_PORTS_INCHES = {27.5, 55.5, 83.5};
private static final double[] ROCKET_HATCH_INCHES = {19.0, 47.0, 65.0};
private static final double ROCKET_BOT_PORT = 27.5;
private static final double ROCKET_TOP_PORT = 83.5;
private static final double ROCKET_BOT_HATCH = 19.0;
private static final double ROCKET_TOP_HATCH = 65.0;
public int lowerbound = 0; //Find the lowest point on the potentiometer to make this integer.
public int upperbound;
private static final int RANGE = 0; //Find the range that we need from the pot!
private double[] positionTicks = new double[ROCKET_PORTS_INCHES.length];
public boolean gamePiecePicker = true;

private int position = 1;

	public Lifter() {
		// motors, ids, etc
		liftermotor = new WPI_TalonSRX(Wiring.LIFTER_TALON);
		liftermotor.set(ControlMode.Position, 0);
	}

	public void debug() {
		// output things for debugging such as motor/encoder values
	}

	public void rocket(int buttonNumber) { //buttonNumber is basically just the index value of the controller button
		if(gamePiecePicker) {
			switch(buttonNumber) {
				case 1:
				setPosition(Constants.ROCKET_LOWER_PORT);// port 1
				break;
				case 2:
				setPosition(Constants.ROCKET_MIDDLE_PORT);// port 2
				break;
				case 3:
				setPosition(Constants.ROCKET_UPPER_PORT);// port 3
				break;
			}
		}
		else{
			switch(buttonNumber){
				case 1:
				setPosition(Constants.ROCKET_LOWER_HATCH);// hatch 1 
				break;
				case 2:
				setPosition(Constants.ROCKET_MIDDLE_PORT);// port 2
				break;
				case 3:
				setPosition(Constants.ROCKET_UPPER_HATCH);// hatch 3
				break;
			}
		}
	}
	/**
	 * Go to the specified rocket cargo port (1, 2 or 3)
	 *      __
	 *    /    \
	 *    | [] |    3 (6 ft, 11.5 in)
	 *    |    |
	 *    | [] |    2 (4 ft, 7.5 in)
	 *   /|    |\
	 *  / | [] | \  1 (2 ft, 3.5 in)
	 */
	// public void rocketPort(int n) {
	// 	switch ((short) n) {
	// 		case 1:
	// 			setPosition(Constants.ROCKET_LOWER_PORT);// port 1
	// 			break;
	// 		case 2:
	// 			setPosition(Constants.ROCKET_MIDDLE_PORT);// port 2
	// 			break;
	// 		case 3:
	// 			setPosition(Constants.ROCKET_UPPER_PORT);// port 3
	// 			break;
	// 		default:
	// 			// unknown
	// 		/** Have Lifter go to cargo port 1 and have grabber unfold, should be a preset position
	// 		 * Have Lifter go to cargo port 2 and have grabber unfold, should be a preset position
	// 		 * Have Lifter go to cargo port 3 and have grabber unfold, should be a preset position
	// 		 */
	// 	}
	// }

	/**
	 * Go to the specified rocket hatch holes (1, 2 or 3)
	 *      __
	 *    /    \
	 *    | [] |    hatch 3 (6 ft, 3 in)
	 *    |    |
	 *    | [] |    hatch 2 (3 ft, 11 in)
	 *   /|    |\
	 *  / | [] | \  hatch 1 (1 ft, 7 in)
	 */
	// public void rocketHatches(int n) {
	// 	switch ((short) n) {
	// 		case 1:
	// 			setPosition(Constants.ROCKET_LOWER_HATCH);// hatch 1 
	// 			break;
	// 		case 2:
	// 			setPosition(Constants.ROCKET_MIDDLE_HATCH);// hatch 2
	// 			break;
	// 		case 3:
	// 			setPosition(Constants.ROCKET_UPPER_HATCH);// hatch 3
	// 			break;
	// 		default:
	// 			// unknown
	// 		/** Have Lifter go to hatch hole 1 then have grabber unfold, should be a preset position
	// 		 * Have Lifter go to hatch hole 2 then have grabber unfold, should be a preset position
	// 		 * Have Lifter go to hatch hole 3 then have grabber unfold, should be a preset position
	// 		 */
	// 	}
	// }

	/**
	 * Go to the cargo bay position
	 *  ________________________   ____ 4 ft _____
	 * |      |      |      |   \  
	 * |______|______|______|____| ___ 2 ft 7.5 in ___
	 * |      |      |      |    |
	 * |  []  |  []  |  []  |    |  <--- 1ft, 7in
	 * |______|______|______|____|
	 */
	public void cargoBayCargo() {
	/** Have Lifter go to cargo bay port then have grabber unfold, should be a preset position*/
		setPosition(Constants.CARGOSHIP_CARGO_DROPOFF);
	}

	public void cargoBayHatches() {
	/** Have Lifter go to hatch hole 1 then have grabber unfold, should be a preset position*/
		setPosition(Constants.CARGOSHIP_HATCH);
	}

	/**
	 * Tele-operated upward movement
	 */
	public void up() {
		up(1);
		// for now 1, but change to whatever the smallest/incremental value would be
	}

	/**
	 * Move upwards, the specified amount in {UNITS}
	 */
	public void up(int n) {

	}

	/**
	 * Tele-operated downward movement
	 */
	public void down() {
		down(1);
		// for now 1, but change to whatever the smallest/incremental value would be
	}

	/**
	 * Move downwards, the specified amount in {UNITS}
	 */
	public void down(int n) {

	}

	/**
	 * Moves to the bottom position
	 */
	public void home() {
		// change to ground() if that would make more sense
		moveTo(Constants.LIFTER_BOTTOM);
	}

	public void setPosition(int value) {
		// motor values or whatever is
		liftermotor.setSelectedSensorPosition(value, 0, TIMEOUT);
		
	}

	//Will probably be removed due to other methods having its basic function. Keep just in case
	public void moveTo(int positionNumber) {
		// maybe instead of hardcoding each constant, just have their values as motor/position units for setPosition(int)
		switch ((short) positionNumber) {
			case Constants.LIFTER_POS1:
				// pos 1
				break;
			case Constants.LIFTER_POS2:
				// pos 2
				break;
			case Constants.LIFTER_POS3:
				// pos 3
				break;
			case Constants.LIFTER_POS4:
				// pos 4
				break;
			case Constants.LIFTER_TOP:
				// pos top (highest possible)
				break;
			case Constants.LIFTER_BOTTOM:
				// pos bottom (lowest possible)
				break;
			default:
				// unknown
		}
	}
/**
*Returns the value of the Potentiometer
*/
	public double getPot(){
		return liftermotor.getSelectedSensorPosition(Wiring.LIFTER_POT);
	}
	
	public WPI_TalonSRX getTalon(){
		return liftermotor;
	}

	private void calculatePortPositions() {
		upperbound = lowerbound + RANGE;
		int n = ROCKET_PORTS_INCHES.length;
		for (int i = 0; i < n; i++) {
			positionTicks[i] = MathUtils.mapRange(ROCKET_PORTS_INCHES[i], ROCKET_BOT_PORT, ROCKET_TOP_HATCH, lowerbound, upperbound);

		}

	}

	public void holdPosition() {
		setpoint = getPot();
	}

}