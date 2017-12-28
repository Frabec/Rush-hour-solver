import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException, SuperpositionError {
		RushHour R = new RushHour("RushHour1.txt");
		int[][] tableau = R.display();
		for (int i =0; i<tableau.length; i++){
			 	for (int j=0 ; j<tableau.length; j++){
			 		System.out.print(tableau[i][j] +" ");
			 	}
			 	System.out.println("");
			 }
		System.out.println("");
		R.vehicules[6].move(1);   //// Check that the car 7 is moving the right way.
		R.vehicules[1].move(2);
		tableau = R.display();
		for (int i =0; i<tableau.length; i++){
		 	for (int j=0 ; j<tableau.length; j++){
		 		System.out.print(tableau[i][j] +" ");
		 	}
		 	System.out.println("");
		 }
	}
}
