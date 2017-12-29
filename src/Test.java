import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException, SuperpositionError {
		RushHour R = new RushHour("RushHour2.txt");
		R.show();
		for (RushHour v : RushHour.AllPossibleMoves(R)){
			v.show();
		}
		System.out.println(RushHour.AllPossibleMoves(R).size());
		System.out.println("R est résolu : " + R.isSolution());
		System.out.println(RushHour.BFS(R));
	}
}
