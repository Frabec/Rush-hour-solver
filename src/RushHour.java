
/* Class containing all the programs asked in the PI project */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RushHour {

	//////////// Constructors ///////////////////

	int size;
	Vehicle[] vehicules;
	int[][] grid;

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

	RushHour(int size, Vehicle[] vehicules) throws SuperpositionError  {
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
	public LinkedList<RushHour> AllPossibleMoves() throws SuperpositionError {
		LinkedList<RushHour> L = new LinkedList<RushHour>();
		Vehicle [] newConfiguration= new Vehicle[this.vehicules.length];
		//copy the array to avoid side effect
		for (int i=0; i<this.vehicules.length;i++){
			newConfiguration[i]=new Vehicle(this.vehicules[i].name,this.vehicules[i].getOrientation()+"",this.vehicules[i].getLength(),this.vehicules[i].getAbsissa(), this.vehicules[i].getOrdinate());
		}
		for (int j=0; j<this.vehicules.length;j++) {
			if (this.vehicules[j].getOrientation() == 'h') {
				int i = -1;
				while (this.vehicules[j].getAbsissa() - 1 + i >= 0 && this.grid[this.vehicules[j].getOrdinate() - 1][this.vehicules[j].getAbsissa() - 1 + i] == 0) {
					newConfiguration[j].move(i);
					L.add(new RushHour(size, newConfiguration));
					newConfiguration[j].move(-i);
					i--;
				}
				i = 1;
				while (this.vehicules[j].getAbsissa() - 1 + this.vehicules[j].getLength() - 1 + i < this.size
						&& this.grid[this.vehicules[j].getOrdinate() - 1][this.vehicules[j].getAbsissa() - 1 + this.vehicules[j].getLength() - 1 + i] == 0) {
					newConfiguration[j].move(i);
					L.add(new RushHour(size, newConfiguration));
					newConfiguration[j].move(-i);
					i++;
				}
			} else {
				int i = -1;
				while (this.vehicules[j].getOrdinate() - 1 + i >= 0 && this.grid[this.vehicules[j].getOrdinate() - 1 + i][this.vehicules[j].getAbsissa() - 1] == 0) {
					this.show();
					System.out.println(this.vehicules[j].getOrdinate());
					newConfiguration[j].move(i);
					System.out.println(this.vehicules[j].getOrdinate());
					L.add(new RushHour(size, newConfiguration));
					newConfiguration[j].move(-i);
					System.out.println(this.vehicules[j].getOrdinate());
					i--;
				}
				i = 1;
				while (this.vehicules[j].getOrdinate() - 1 + this.vehicules[j].getLength() - 1 + i < this.size
						&& this.grid[this.vehicules[j].getOrdinate() - 1 + this.vehicules[j].getLength() - 1 + i][this.vehicules[j].getAbsissa() - 1] == 0) {
					newConfiguration[j].move(i);
					L.add(new RushHour(size, newConfiguration));
					newConfiguration[j].move(-i);
					i++;
				}
			}
		}
		return L;
	}

	///// Defining what is a solution /////

	public boolean isSolution() throws SuperpositionError {
		int i = this.vehicules[0].getOrdinate() - 1; // Assuming the first car
														// is the red one that
														// needs to "escape"
		int j = this.vehicules[0].getAbsissa() - 1 + this.vehicules[0].getLength();
		while (j < this.grid[0].length) {
			if (this.grid[i][j] != 0) {
				return false; // The current state is a solution if the way to
								// the right is open for the red car
			}
			j++;
		}
		return true;
	}

	///// BFS solver : a first brute solution /////

	public static int BFS(RushHour R) throws SuperpositionError {

		/* Initialization as learned in INF411 */
		HashMap<RushHour, Integer> Visited = new HashMap<RushHour, Integer>(); // Visit
																				// each
																				// possibility
																				// only
																				// once
		Queue<RushHour> Q = new LinkedList<RushHour>(); // Contains the next
														// neighbors that will
														// be visit
		Visited.put(R, 0);
		Q.add(R);

		while (!Q.isEmpty()) {
			RushHour current = Q.poll();
			if (current.isSolution())
				return (Visited.get(current));
			for (RushHour r : current.AllPossibleMoves()) {
				if (!Visited.containsKey(r)) {
					Q.add(r);
					Visited.put(r, Visited.get(current) + 1);
				}
			}
		}
		return 0; // Means that there are no solution
	}

	public int hashcode() {
		int h = 0;
		for (Vehicle v : this.vehicules) {
			h += v.getAbsissa() * 12 + v.getOrdinate() * 17;
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
