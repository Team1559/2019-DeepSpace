package org.usfirst.frc.team1559.robot;

public class Lifter {
	
	public static final int POS1 = 1;
	public static final int POS2 = 2;
	public static final int POS3 = 3;
	public static final int POS4 = 4;
	public static final int POS_HOME = 99;

	public boolean isEmpty = true;

	public Lifter() {
		// motors, ids, etc
	}

	public void debug() {
		// output things for debugging such as motor/encoder values
	}

	/**
	 * Go to the specified rocket opening (1, 2 or 3)
	 *      __
	 *    /    \
	 *    | [] |    3
	 *    |    |
	 *    | [] |    2
	 *   /|    |\
	 *  / | [] | \  1
	 */
	public void rocket(int n) {
		switch (n) {
			case 1:
				// window 1
				break;
			case 2:
				// window 2
				break;
			case 3:
				// window 3
				break;
			default:
				// unknown
		}
	}

	/**
	 * Go to the height of the cargo bay
	 */
	public void cargoBay() {
		
	}

	/**
	 * Tele-operated upward movement, from the current position
	 */
	public void up() {

	}

	/**
	 * Move downwards, the specified amount, from the current position
	 */
	public void up(int n) {

	}

	/**
	 * Tele-operated downward movement, from the current position
	 */
	public void down() {

	}

	/**
	 * Move downwards, the specified amount, from the current position
	 */
	public void down(int n) {

	}

	/**
	 * Moves to the "home" position
	 */
	public void home() {
		moveTo(POS_HOME);
	}

	public void moveTo(int positionNumber) {
		switch (positionNumber) {
			case POS1:
				// pos 1
				break;
			case POS2:
				// pos 2
				break;
			case POS3:
				// pos 3
				break;
			case POS4:
				// pos 4
				break;
			case POS_HOME:
				// pos home
				break;
			default:
				// unknown pos
		}
	}

}