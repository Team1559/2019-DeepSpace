package org.usfirst.frc.team1559.robot;

public class Lifter {

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
	public void rocket(int n) {
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
	 */
	public void cargoBay() {
		
	}

	/**
	 * Tele-operated upward movement
	 */
	public void up() {
		up(1);
		// for now 1, but change to whatever the smallest/incremental value would be
	}

	/**
	 * Move downwards, the specified amount in {UNITS}
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

}