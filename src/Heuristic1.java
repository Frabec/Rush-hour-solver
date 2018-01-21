import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Heuristic1 {
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
	
	public static int heuristics(RushHour R, boolean showPath) throws SuperpositionError { //if showPath == true we show the path

		/* Initialization as learned in INF411 */
		HashMap<RushHour, Integer> Visited = new HashMap<RushHour, Integer>(); // Visit
																				// each
																				// possibility
																				// only
																				// once
		HashMap<RushHour, RushHour> path = new HashMap<RushHour, RushHour>();
		
		Comparator<RushHour> Comp = new Comparator<RushHour>(){
			public int compare(RushHour R, RushHour S) {
				if (R.equals(S)) {
					return 0;
				}
				else if (h(R) + Visited.get(R) < h(S) + Visited.get(S)) {
					return -1;
				}
				else return 1;
			}}; 
		PriorityQueue<RushHour> Q = new PriorityQueue<RushHour>(1, Comp); // Contains the next
																		  // neighbors that will
																		  // be visit sorted via h+d
		Visited.put(R, 0);
		path.put(R, null);
		Q.add(R);

		while (!Q.isEmpty()) {
			RushHour current = Q.poll();
			if (current.isSolution()){
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
