import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException, SuperpositionError {
<<<<<<< HEAD
		RushHour R = new RushHour("RushHour7.txt");
		R.show();
		
		//for (RushHour v : RushHour.AllPossibleMoves(R)){
			//v.show();
		//}
		//System.out.println(RushHour.AllPossibleMoves(R).size());
		//System.out.println("R est résolu : " + R.isSolution());
		System.out.println("Number of movements " + RushHour.BFS(R));
=======
		RushHour R = new RushHour("RushHour5.txt");
		R.show();
		
		long startTime = System.currentTimeMillis();
		
		System.out.println(RushHour.BFS(R));   // Return 7 for RushHour1, in 55ms, that is correct !
		
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("Temps d'exécution : " + elapsedTime + " ms");
	    
		System.out.println("h(R) = " + RushHour.h(R));
	    
		startTime = System.currentTimeMillis();
		
		System.out.println(RushHour.Heuristics(R));   // Return 7 for RushHour1, in 55ms, that is correct !
		
		stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		System.out.println("Temps d'exécution avec heuristique : " + elapsedTime + " ms");
		
		System.out.println("h1(R) = " + RushHour.h1(R));
	    
		startTime = System.currentTimeMillis();
		
		System.out.println(RushHour.Heuristics1(R));   
		
		stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		System.out.println("Temps d'exécution avec seconde heuristique : " + elapsedTime + " ms");
	    
>>>>>>> 73cec5178424c72a76361a6052c67f4df1f8430c
	}
	
	public static void printConfig(Vehicle [] vehicules){
		for (int i=0; i<vehicules.length;i++){
			System.out.println(vehicules [i]);
		}
		System.out.println("");
	}
}

