//Class representing a state of the game
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class RushHour {

	//////////// Constructors ///////////////////

	final int size;
	final Vehicle[] vehicules;
	final int[][] grid;

	RushHour(String fileName) throws FileNotFoundException, SuperpositionError {
		File fichier = new File(fileName);
		java.util.Scanner lecteur = new Scanner(fichier);
		this.size = lecteur.nextInt();
		this.vehicules = new Vehicle[lecteur.nextInt()];
		for (int i = 0; i < vehicules.length; i++) {
			vehicules[i] = new Vehicle(lecteur.nextInt(), lecteur.next(), lecteur.nextInt(), lecteur.nextInt(),
					lecteur.nextInt());
		}
		lecteur.close();

		int[][] tableau = new int[this.size][this.size];
		for (Vehicle v : this.vehicules) {
			if (v.getOrientation() == 'h') {
				for (int i = 0; i < v.getLength(); i++) {
					if (tableau[v.getOrdinate() - 1][v.getAbsissa() + i - 1] != 0)
						throw new SuperpositionError();
					tableau[v.getOrdinate() - 1][v.getAbsissa() + i - 1] = v.name;
				}
			} else {
				for (int i = 0; i < v.getLength(); i++) {
					if (tableau[v.getOrdinate() + i - 1][v.getAbsissa() - 1] != 0)
						throw new SuperpositionError();
					tableau[v.getOrdinate() + i - 1][v.getAbsissa() - 1] = v.name;
				}
			}
		}
		this.grid = tableau;
	}

	RushHour(int size, Vehicle[] vehicules) throws SuperpositionError {
		this.size = size;
		this.vehicules = vehicules;
		int[][] tableau = new int[this.size][this.size];
		for (Vehicle v : this.vehicules) {
			if (v.getOrientation() == 'h') {
				for (int i = 0; i < v.getLength(); i++) {
					if (tableau[v.getOrdinate() - 1][v.getAbsissa() + i - 1] != 0)
						throw new SuperpositionError();
					tableau[v.getOrdinate() - 1][v.getAbsissa() + i - 1] = v.name;
				}
			} else {
				for (int i = 0; i < v.getLength(); i++) {
					if (tableau[v.getOrdinate() + i - 1][v.getAbsissa() - 1] != 0)
						throw new SuperpositionError();
					tableau[v.getOrdinate() + i - 1][v.getAbsissa() - 1] = v.name;
				}
			}
		}
		this.grid = tableau;

	}

	// Constructor used to move vehicle, returns a new object where vehicle j is
	// moved by i cases (vers le bas si vertical, ou vers la droite si horizontal)
	public RushHour(RushHour R, int j, int i) throws SuperpositionError {
		Vehicle vehicules[] = new Vehicle[R.vehicules.length];
		this.size = R.size;
		for (int l = 0; l < R.vehicules.length; l++) {
			if (l == j) {
				if (R.vehicules[l].getOrientation() == 'h') {
					vehicules[l] = new Vehicle(R.vehicules[l].name, R.vehicules[l].getOrientation() + "",
							R.vehicules[l].getLength(), R.vehicules[l].getAbsissa() + i, R.vehicules[l].getOrdinate());
				} else {
					vehicules[l] = new Vehicle(R.vehicules[l].name, R.vehicules[l].getOrientation() + "",
							R.vehicules[l].getLength(), R.vehicules[l].getAbsissa(), R.vehicules[l].getOrdinate() + i);
				}
			}
			else {
				vehicules[l]=R.vehicules[l];
			}
		}
		this.vehicules=vehicules;
		int[][] tableau = new int[this.size][this.size];
		for (Vehicle v : this.vehicules) {
			if (v.getOrientation() == 'h') {
				for (int l= 0; l< v.getLength(); l++) {
					if (tableau[v.getOrdinate() - 1][v.getAbsissa() + l- 1] != 0)
						throw new SuperpositionError();
					tableau[v.getOrdinate() - 1][v.getAbsissa() + l- 1] = v.name;
				}
			} else {
				for (int l= 0; l< v.getLength(); l++) {
					if (tableau[v.getOrdinate() + l- 1][v.getAbsissa() - 1] != 0)
						throw new SuperpositionError();
					tableau[v.getOrdinate() + l- 1][v.getAbsissa() - 1] = v.name;
				}
			}
		}
		this.grid = tableau;

	}
		
	

	/// Method used to display the grid.
	public void show() throws SuperpositionError {
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid.length; j++) {
				System.out.print(this.grid[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("");
	}

	/// Find all the neighbors from a given state in a complexity
	/// O(numbervehicles*gamesize). They are all stored in a LinkedList.
	public static LinkedList<RushHour> AllPossibleMoves(RushHour R) throws SuperpositionError {
		LinkedList<RushHour> L = new LinkedList<RushHour>();
		for (int j = 0; j < R.vehicules.length; j++) {
			if (R.vehicules[j].getOrientation() == 'h') {
				int i = -1;
				while (R.vehicules[j].getAbsissa() - 1 + i >= 0
						&& R.grid[R.vehicules[j].getOrdinate() - 1][R.vehicules[j].getAbsissa() - 1 + i] == 0) {
					
					L.add(new RushHour(R, j, i));
					i--;
				}
				i = 1;
				while (R.vehicules[j].getAbsissa() - 1 + R.vehicules[j].getLength() - 1 + i < R.size
						&& R.grid[R.vehicules[j].getOrdinate() - 1][R.vehicules[j].getAbsissa() - 1
								+ R.vehicules[j].getLength() - 1 + i] == 0) {
					
					L.add(new RushHour(R,j,i));
					i++;
				}
			} else {
				int i = -1;
				while (R.vehicules[j].getOrdinate() - 1 + i >= 0
						&& R.grid[R.vehicules[j].getOrdinate() - 1 + i][R.vehicules[j].getAbsissa() - 1] == 0) {
					L.add(new RushHour(R,j,i));
					i--;
				}
				i = 1;
				while (R.vehicules[j].getOrdinate() - 1 + R.vehicules[j].getLength() - 1 + i < R.size
						&& R.grid[R.vehicules[j].getOrdinate() - 1 + R.vehicules[j].getLength() - 1 + i][R.vehicules[j]
								.getAbsissa() - 1] == 0) {
					L.add(new RushHour(R,j,i));
					i++;
				}
			}
		}
		return L;
	}

	///// Defining what is a solution /////

	public boolean isSolution() throws SuperpositionError {
		
		int j = this.vehicules[0].getAbsissa() - 1 + this.vehicules[0].getLength();
		if (j==this.size){
			return true;
		}
		return false;
	}

	public int hashCode() {
		int h = 0;
		for (Vehicle v : this.vehicules) {
			h += v.getAbsissa() * 12 + v.getOrdinate()^2 * 173;
		}
		return h;
	}

	@Override
	public boolean equals(Object o) {
		RushHour that = (RushHour) o;
		if (this.size != that.size || this.vehicules.length != that.vehicules.length)
			return false;
		for (int i = 0; i < this.vehicules.length; i++) {
			if (!this.vehicules[i].equals(that.vehicules[i])) {
				return false;
			}
		}
		return true;
	}
}
