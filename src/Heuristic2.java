import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Heuristic2 {
	///////// Using a second heuristic ////////

	public static int h(RushHour R) {

		// This time, the heuristic h counts the vehicles v between the red one
		// and the exit, but adds the minimal
		// number of cars that block v from being removed of the red card path.

		// For the subject example, the big blue car can only be moved down, and
		// only if the big green car is moved too.
		// Same for the yellow one, thus h1(R) = h(R) + 2.
		// And a solution where the yellow big car can be move without
		// constraint will be considered first.

		int h = 0;
		int h1 = 0;
		int h2 = 0;
		int i = R.vehicules[0].getOrdinate() - 1;
		boolean[] checkTop = new boolean[R.vehicules.length + 1];
		boolean[] checkBottom = new boolean[R.vehicules.length + 1];
		for (int j = R.vehicules[0].getAbsissa() - 1 + R.vehicules[0].getLength(); j < R.size; j++) {
			int k = R.grid[i][j];
			if (k != 0) {
				h++; // No need to check if we already counted the car, as they
						// are necessarily verticals
				checkTop[k] = true;
				checkBottom[k] = true;
				// But we have to check if we don't count twice the cars that
				// block the cars "k".
				// That's what we do with the Checks array.*

				// First we want to go up as many times as possible
				if (i-R.vehicules[k-1].getLength() >=0) { // If
																							// we
																							// can
																							// go
																							// up
																							// we
																							// go
																							// up
					for (int a = i-R.vehicules[k-1].getLength(); a < R.vehicules[k - 1].getOrdinate() ; a++) {
						if (R.grid[a][j] != 0 && !checkTop[R.grid[a][j]]) {
							h1++;
							checkTop[R.grid[a][j]] = true;
						}
					}
				} else {
					for (int a = i + 1; a < i + R.vehicules[k - 1].getLength() + 1; a++) { // else
																							// we
																							// go
																							// down
						if (R.grid[a][j] != 0 && !checkTop[R.grid[a][j]]) {
							h1++;
							checkTop[R.grid[a][j]] = true;
						}
					}
				}
				// If we can go down, we go down.
				if (i+R.vehicules[k-1].getLength()<R.size) {
					for (int a = i + 1; a < i + R.vehicules[k - 1].getLength() + 1; a++) {
						if (R.grid[a][j] != 0 && !checkBottom[R.grid[a][j]]) {
							h2++;
							checkBottom[R.grid[a][j]] = true;
						}
					}
				}
				// If we can't we go up
				else {
					for (int a = i-R.vehicules[k-1].getLength(); a < R.vehicules[k - 1].getOrdinate(); a++) {
						if (R.grid[a][j] != 0 && !checkBottom[R.grid[a][j]]) {
							h2++;
							checkBottom[R.grid[a][j]] = true;
						}

					}
				}
			}
		}
		return (h+Math.min(h1, h2));

	}

	public static int heuristics(RushHour R, boolean showPath) throws SuperpositionError {

		/* Initialization as learned in INF411 */
		HashMap<RushHour, Integer> Visited = new HashMap<RushHour, Integer>(); // Visit
																				// each
																				// possibility
																				// only
																				// once
		HashMap<RushHour, RushHour> path = new HashMap<RushHour, RushHour>();

		Comparator<RushHour> Comp = new Comparator<RushHour>() {
			public int compare(RushHour R, RushHour S) {
				if (R.equals(S)) {
					return 0;
				} else if (h(R) + Visited.get(R) < h(S) + Visited.get(S)) {
					return -1;
				} else
					return 1;
			}
		};

		PriorityQueue<RushHour> Q = new PriorityQueue<RushHour>(1, Comp); // Contains
																			// the
																			// next
																			// neighbors
																			// that
																			// will
																			// be
																			// visit
																			// sorted
																			// via
																			// h+d
		Visited.put(R, 0);
		Q.add(R);
		path.put(R, null);

		while (!Q.isEmpty()) {
			RushHour current = Q.poll();
			if (current.isSolution()) {
				RushHour c = current;
				LinkedList<RushHour> solution = new LinkedList<RushHour>();
				while (c != null) { // we revert the path who goes from solution
									// to start
					solution.addFirst(c);
					c = path.get(c);
				}
				if (showPath == true) {
					for (RushHour a : solution) {
						a.show();
					}
				}
				return (Visited.get(current));
			}

			LinkedList<RushHour> toMove = RushHour.AllPossibleMoves(current);
			for (RushHour r : toMove) {
				if (!Visited.containsKey(r)) {
					Visited.put(r, Visited.get(current) + 1);
					path.put(r, current);
					Q.add(r);

				}
			}
		}
		return 0; // Means that there are no solution
	}
}
