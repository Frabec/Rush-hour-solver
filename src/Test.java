import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException, SuperpositionError {

		RushHour R = new RushHour("RushHourHtest8.txt");
		System.out.println("Start state : "+"\n");
		R.show();
		
		long startTime = System.currentTimeMillis();
		System.out.println("-------------------------------Brute-force--------------------------");
		System.out.println("Number of moves :"+ BFS.bfs(R,true));   
		
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("Execution time : " + elapsedTime + " ms");
	    System.out.println("-------------------------------Heuristic1--------------------------");
		
	    
		startTime = System.currentTimeMillis();
		System.out.println("Number of moves :"+Heuristic1.heuristics(R,false)); 
		System.out.println("h(R) = " + Heuristic1.h(R));
		stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		System.out.println("Execution time : " + elapsedTime + " ms");
		System.out.println("-------------------------------Heuristic2--------------------------");
		
	    
		startTime = System.currentTimeMillis();
		
		System.out.println("Number of moves :"+Heuristic2.heuristics(R,false));   
		System.out.println("h(R) = " + Heuristic2.h(R));
		stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		System.out.println("Execution time : " + elapsedTime + " ms");
		System.out.println("---------------------------------------------------------");
	}
	
	public static void printConfig(Vehicle [] vehicules){
		for (int i=0; i<vehicules.length;i++){
			System.out.println(vehicules [i]);
		}
		System.out.println("");
	}
}

