import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException, SuperpositionError {
		RushHour R = new RushHour("RushHour1.txt");
		R.show();
		for (RushHour v : R.AllPossibleMoves()){
			v.show();
		}
		System.out.println(R.AllPossibleMoves().size());
		System.out.println("R est résolu : " + R.isSolution());
		System.out.println(RushHour.BFS(R));
	}
	
	public static void printConfig(Vehicle [] vehicules){
		for (int i=0; i<vehicules.length;i++){
			System.out.println(vehicules [i]);
		}
		System.out.println("");
	}
}
