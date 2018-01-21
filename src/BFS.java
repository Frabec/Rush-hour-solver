import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {
	///// BFS solver : a first brute solution /////

	public static int bfs(RushHour R, boolean showPath) throws SuperpositionError { //if showPath we show the path 

		/* Initialization as learned in INF411 */
		HashMap<RushHour, Integer> Visited = new HashMap<RushHour, Integer>(); // Visit
																				// each
																				// possibility
																				// once
		HashMap<RushHour, RushHour> path = new HashMap<RushHour, RushHour>(); // Store
																				// the
																				// path,
																				// the
																				// value
																				// is
																				// the
																				// predecessor
																				// of
																				// the
																				// key
																				// //
																				// each
		Queue<RushHour> Q = new LinkedList<RushHour>(); // Contains the next
														// neighbors that will
														// be visited
		Visited.put(R, 0);
		path.put(R, null);
		Q.add(R);
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
					Q.add(r);
					Visited.put(r, Visited.get(current) + 1);
					path.put(r, current);
				}
			}
		}
		return 0; // Means that there are no solution
	}
}
