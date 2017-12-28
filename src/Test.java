import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException, SuperpositionError {
		RushHour R = new RushHour("RushHour5.txt");
		int[][] tableau = R.display();
		for (int i =0; i<tableau.length; i++){
			 	for (int j=0 ; j<tableau.length; j++){
			 		System.out.print(tableau[i][j] +" ");
			 	}
			 	System.out.println("");
			 }
		System.out.println("");
		System.out.println(RushHour.AllPossibleMoves(R));
		System.out.println(RushHour.AllPossibleMoves(R).size());
		System.out.println("R est résolu : " + R.isSolution());
		System.out.println(RushHour.BFS(R));
	}
}
