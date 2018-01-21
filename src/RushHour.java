/* Class containing all the programs asked in the PI project */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
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

	///// BFS solver : a first brute solution /////

	public static int BFS(RushHour R) throws SuperpositionError {

		/* Initialization as learned in INF411 */
		HashMap<RushHour, Integer> Visited = new HashMap<RushHour, Integer>(); // Visit each possibility once 
		HashMap<RushHour,RushHour> path = new HashMap<RushHour, RushHour>();	// Store the path, the value is the predecessor of the key															// each
																				
		Queue<RushHour> Q = new LinkedList<RushHour>(); // Contains the next
														// neighbors that will
														// be visited
		Visited.put(R, 0);
		path.put(R,null);
		Q.add(R);
		double time1=System.currentTimeMillis();
		while (!Q.isEmpty()) {
			RushHour current = Q.poll();
			if (current.isSolution()){
				double time2=System.currentTimeMillis();
				System.out.println("Execution time = "+ (time2-time1) + " ms");
				RushHour c=current;
				LinkedList <RushHour> solution = new LinkedList<RushHour>();
				while (c!=null){ //we revert the path who goes from solution to start
					solution.addFirst(c);
					c=path.get(c);
				}
				for (RushHour a: solution){
					a.show();
				}
				return (Visited.get(current));
			}
			LinkedList<RushHour> toMove = AllPossibleMoves(current);
			for (RushHour r : toMove) {
				if (!Visited.containsKey(r)) {
					Q.add(r);
					Visited.put(r, Visited.get(current) + 1);
					path.put(r, current);
				}
			}
		}
		return 0; // Means that there are no solution
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
	
/////// Approach based on heuristics //////////
	
	public static int h(RushHour R) {
		int h = 0;
		
		int i = R.vehicules[0].getOrdinate() - 1;
		for (int j = R.vehicules[0].getAbsissa() - 1 + R.vehicules[0].getLength(); j<R.size;j++){
			if (R.grid[i][j] != 0) {
				h++;  // Pas besoin de vérifier si on n'a pas déjà comptabilisé la voiture, puisqu'elles sont
					  //  forcément verticales
			}
		}
		
		return h;
	}
	
	public static int Heuristics(RushHour R) throws SuperpositionError {

		/* Initialization as learned in INF411 */
		HashMap<RushHour, Integer> Visited = new HashMap<RushHour, Integer>(); // Visit
																				// each
																				// possibility
																				// only
																				// once
		
		Comparator<RushHour> Comp = new Comparator<RushHour>(){
			public int compare(RushHour R, RushHour S) {
				if (R.equals(S)) {
					return 0;
				}
				else if (RushHour.h(R) + Visited.get(R) < RushHour.h(S) + Visited.get(S)) {
					return -1;
				}
				else return 1;
			}}; 
		PriorityQueue<RushHour> Q = new PriorityQueue<RushHour>(1, Comp); // Contains the next
																		  // neighbors that will
																		  // be visit sorted via h+d
		Visited.put(R, 0);
		Q.add(R);

		while (!Q.isEmpty()) {
			RushHour current = Q.poll();
			if (current.isSolution()){
				return (Visited.get(current));
			}
			LinkedList<RushHour> toMove = AllPossibleMoves(current);
			for (RushHour r : toMove) {
				if (!Visited.containsKey(r)) {
					Visited.put(r, Visited.get(current) + 1);
					Q.add(r);
				}
			}
		}
		return 0; // Means that there are no solution
	}

	///////// Using a second heuristic ////////
	
	public static int h1(RushHour R) {
		
		// This time, the heuristic h1 counts the vehicles v between the red one and the exit, but adds the minimal 
		// number of cars that block v from being removed of the red card path.
		
		// For the subject example, the big blue car can only be moved down, and only if the big green car is moved too. 
		// Same for the yellow one, thus h1(R) = h(R) + 2. 
		// And a solution where the yellow big car can be move without constraint will be considered first.
		
		int h = 0;
		
		int i = R.vehicules[0].getOrdinate() - 1;
		int[] Check = new int[R.vehicules.length+1];
		
		for (int j = R.vehicules[0].getAbsissa() - 1 + R.vehicules[0].getLength(); j<R.size;j++){
			int k = R.grid[i][j];
			if (k != 0) {
				h++;  // No need to check if we already counted the car, as they are necessarily verticals
				
				// But we have to check if we don't count twice the cars that block the cars "k".
				// That's what we do with the Check array.
				
				if (R.vehicules[k-1].getLength() < i) {   //If car k is not too tall to be sent up, to unblock the red one
					int h1 = 0;		
					for (int a=0; a<R.vehicules[k-1].getOrdinate()-1; a++) {
						if (R.grid[a][j] != 0 && R.grid[a][j] != k && Check[R.grid[a][j]] == 0) { 
							h1++;
							Check[R.grid[a][j]] = 1;
						}
					}
					if (R.vehicules[k-1].getLength() > R.size-1-i) {  //Check down too
						h = h+h1;
					}
					else {
						int h2 = 0;
						for (int a=i+1; a<i+R.vehicules[k-1].getLength()+1; a++) {
							if (R.grid[a][j] != 0 && R.grid[a][j] != k && Check[R.grid[a][j]] == 0) { 
								h2++;
								Check[R.grid[a][j]] = 1;
							}
						}
						h = h + Math.min(h1,h2);
					}
				}
				else {
					int h2 = 0;
					for (int a=i+1; a<R.size; a++) {
						if (R.grid[a][j] != 0 && R.grid[a][j] != k && Check[R.grid[a][j]] == 0) { 
							h2++;
							Check[R.grid[a][j]] = 1;
						}
					}
					h = h + h2;
				}
			}
		}
		
		return h;     
	}
	
	public static int Heuristics1(RushHour R) throws SuperpositionError {

		/* Initialization as learned in INF411 */
		HashMap<RushHour, Integer> Visited = new HashMap<RushHour, Integer>(); // Visit
																				// each
																				// possibility
																				// only
																				// once
		
		Comparator<RushHour> Comp = new Comparator<RushHour>(){
			public int compare(RushHour R, RushHour S) {
				if (R.equals(S)) {
					return 0;
				}
				else if (RushHour.h1(R) + Visited.get(R) < RushHour.h1(S) + Visited.get(S)) {
					return -1;
				}
				else return 1;
			}}; 
		PriorityQueue<RushHour> Q = new PriorityQueue<RushHour>(1, Comp); // Contains the next
																		  // neighbors that will
																		  // be visit sorted via h+d
		Visited.put(R, 0);
		Q.add(R);

		while (!Q.isEmpty()) {
			RushHour current = Q.poll();
			if (current.isSolution()){
				return (Visited.get(current));
			}
			LinkedList<RushHour> toMove = AllPossibleMoves(current);
			for (RushHour r : toMove) {
				if (!Visited.containsKey(r)) {
					Visited.put(r, Visited.get(current) + 1);
					Q.add(r);
				}
			}
		}
		return 0; // Means that there are no solution
	}
	
}
