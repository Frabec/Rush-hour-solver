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

		int i = R.vehicules[0].getOrdinate() - 1;
		int[] Check = new int[R.vehicules.length + 1];

		for (int j = R.vehicules[0].getAbsissa() - 1 + R.vehicules[0].getLength(); j < R.size; j++) {
			int k = R.grid[i][j];
			if (k != 0) {
				h++; // No need to check if we already counted the car, as they
						// are necessarily verticals

				// But we have to check if we don't count twice the cars that
				// block the cars "k".
				// That's what we do with the Check array.

				if (R.vehicules[k - 1].getLength() < i) { // If car k is not too
															// tall to be sent
															// up, to unblock
															// the red one
					int h1 = 0;
					for (int a = 0; a < R.vehicules[k - 1].getOrdinate() - 1; a++) {
						if (R.grid[a][j] != 0 && R.grid[a][j] != k && Check[R.grid[a][j]] == 0) {
							h1++;
							Check[R.grid[a][j]] = 1;
						}
					}
					if (R.vehicules[k - 1].getLength() > R.size - 1 - i) { // Check
																			// down
																			// too
						h = h + h1;
					} else {
						int h2 = 0;
						for (int a = i + 1; a < i + R.vehicules[k - 1].getLength() + 1; a++) {
							if (R.grid[a][j] != 0 && R.grid[a][j] != k && Check[R.grid[a][j]] == 0) {
								h2++;
								Check[R.grid[a][j]] = 1;
							}
						}
						h = h + Math.min(h1, h2);
					}
				} else {
					int h2 = 0;
					for (int a = i + 1; a < R.size; a++) {
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
