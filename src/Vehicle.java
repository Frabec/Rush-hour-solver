/* Intermediary class that allows to store the vehicles of the game efficiently */
public class Vehicle {

	/* State of a vehicle, as described in the subject */
	public int name;
	private char orientation;
	private int length;
	private int absissa;
	private int ordinate;

	Vehicle(String S) {
		int i = 0;
		char c = S.charAt(0);
		while (c != ' ') {
			this.name = 10 * this.name + (int) c;
			i += 1;
			c = S.charAt(i);
		}

		i += 1;
		c = S.charAt(i);
		this.orientation = c;

		i += 2;
		c = S.charAt(i);
		this.length = (int) c;
		// We consider, like the real game, that length<10.

		i += 2;
		c = S.charAt(i);
		while (c != ' ') {
			this.absissa = 10 * this.absissa + (int) c;
			i += 1;
			c = S.charAt(i);
		}

		i += 1;
		c = S.charAt(i);
		while (i <= S.length() && S.charAt(i) != ' ') {
			this.ordinate = 10 * this.ordinate + (int) S.charAt(i);
			i += 1;
		}
	}

	Vehicle(int name, String orientation, int length, int absissa, int ordinate) {
		this.name = name;
		this.orientation = orientation.charAt(0);
		this.length = length;
		this.absissa = absissa;
		this.ordinate = ordinate;
	}

	public char getOrientation() {
		return this.orientation;
	}

	public int getLength() {
		return this.length;
	}

	public int getAbsissa() {
		return this.absissa;
	}

	public int getOrdinate() {
		return this.ordinate;
	}

	@Override
	public boolean equals(Object o) {
		Vehicle that = (Vehicle) o;
		if (this.absissa == that.absissa && this.ordinate == that.ordinate && this.length == that.length
				&& this.orientation == that.orientation) {
			return true;
		} else
			return false;
	}

	///// The next method moves the car without checking if the move is allowed
	///// ////
	public void move(int n) {
		if (this.orientation == 'h') {
			this.absissa = this.absissa + n; // n=1 the car is going one case
												// right
		} else {
			this.ordinate = this.ordinate + n; // n=1 the car is going one case
												// down
		}
	}
}
