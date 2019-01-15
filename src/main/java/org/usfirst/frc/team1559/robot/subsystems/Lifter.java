package org.usfirst.frc.team1559.robot.subsystems;

import org.usfirst.frc.team1559.robot.Robot;
import org.usfirst.frc.team1559.robot.Wiring;
import org.usfirst.frc.team1559.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

// Lifter Programers are Jack and Nick. Please refer to them if you want to make changes.
public class Lifter {

private WPI_TalonSRX liftermotor; //Remember! This will need to be instantiated in the Lifter constructor!

	public Lifter() {
		// motors, ids, etc
	}

	public void debug() {
		// output things for debugging such as motor/encoder values
	}

	/**
	 * Go to the specified rocket port (1, 2 or 3)
	 *      __
	 *    /    \
	 *    | [] |    3 (6 ft, 11.5 in)
	 *    |    |
	 *    | [] |    2 (4 ft, 7.5 in)
	 *   /|    |\
	 *  / | [] | \  1 (2 ft, 3.5 in)
	 */
	public void rocketCargo(int n) {
		switch ((short) n) {
			case 1:
				// port 1
				break;
			case 2:
				// port 2
				break;
			case 3:
				// port 3
				break;
			default:
				// unknown
		}
	}

	/**
	 * Go to the cargo bay position
	 *  ________________________   ____ 4 ft _____
	 * |      |      |      |   \  
	 * |______|______|______|____| ___ 2 ft 7.5 in ___
	 * |      |      |      |    |
	 * |  []  |  []  |  []  |    |  <--- Not important, as this is where the cargo falls out
	 * |______|______|______|____|
	 */
	public void cargoBayCargo() {
		
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
	}

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
*Returns the value of the Potentiometer (I am assuming we will be needing one)
*Note: if it needs to be a double, PLEASE feel free to change it so it is correct
*/
	public double getPot(){
		return liftermotor.getSelectedSensorPosition(0);
	}
	
	public WPI_TalonSRX getTalon(){
		return liftermotor;
	}

}