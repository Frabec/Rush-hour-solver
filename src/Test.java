import java.io.FileNotFoundException;

public class Test {
	
public static void main(String[] args) throws FileNotFoundException {
	int [][] grid = (new Game ("RushHour1.txt")).grid;
	for (int i =0; i<grid.length; i++){
		for (int j=0 ; j<grid.length; j++){
			System.out.print(grid[i][j] +" ");
		}
		System.out.println("");
	}
}
}
