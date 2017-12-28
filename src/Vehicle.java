/* Intermediary class that allows to stock the vehicles of the game efficiently */
public class Vehicle {
	
	/* State of a vehicle, as described in the subject */
	public int name;
	private char orientation;
	private int length;
	private int absissa;
	private int ordinate;
	
	Vehicle(String S) {
		int i=0;
		char c = S.charAt(0);
		while (c!=' '){
			this.name = 10*this.name + (int) c;
			i+=1;
			c = S.charAt(i);
		}
		
		i+=1;
		c = S.charAt(i);
		this.orientation = c;
		
		i+=2;
		c = S.charAt(i);
		this.length = (int) c;
// We consider, like the real game, that length<10.
		
		i+=2;
		c = S.charAt(i);
		while (c!=' '){
			this.absissa = 10*this.absissa + (int) c ;
			i+=1;
			c = S.charAt(i);
		}
		
		i+=1;
		c = S.charAt(i);
		while (c!=' '){
			this.ordinate = 10*this.ordinate + (int) c ;
			i+=1;
			c = S.charAt(i);
		}
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
}
