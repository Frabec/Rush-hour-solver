import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game {
	public int size; // size of the grid
	public int nbVehicles; // number of vehicles
	public int[][] grid;

	public Game(int[][] grid, int size, int nbVehicles) {

	}

	public Game(String fileName) throws FileNotFoundException {
		File fichier = new File(fileName);
		java.util.Scanner lecteur = new Scanner(fichier);
		int size;
		int nbVehicles;
		int currentVehicle;
		int currentSize;
		int currentx;
		int currenty;
		size = lecteur.nextInt();
		nbVehicles = lecteur.nextInt();
		int[][] grid = new int[size][size];
		for (int i = 0; i < nbVehicles; i++) {
			currentVehicle = lecteur.nextInt();
			if (lecteur.next().equals("h")) {
				currentSize = lecteur.nextInt();
				currentx = lecteur.nextInt();
				currenty = lecteur.nextInt();
				for (int v = 0; v < currentSize; v++) {
					if (grid[currenty - 1][currentx - 1 + v] != 0) {
						throw new IllegalArgumentException();
					}

					else {
						grid[currenty - 1][currentx - 1 + v] = currentVehicle;
					}
				}
			} else {
				currentSize = lecteur.nextInt();
				currentx = lecteur.nextInt();
				currenty = lecteur.nextInt();
				for (int v = 0; v < currentSize; v++) {
					if (grid[currenty - 1 + v][currentx - 1] != 0) {
						throw new IllegalArgumentException();
					} else {
						grid[currenty - 1 + v][currentx - 1] = currentVehicle;
					}
				}
			}
		}
		lecteur.close();
		this.grid = grid;
		this.size = size;
		this.nbVehicles = nbVehicles;
	}
	
	public void display(){
		for (int i =0; i<grid.length; i++){
			for (int j=0 ; j<grid.length; j++){
				System.out.print(grid[i][j] +" ");
			}
			System.out.println("");
		}
	}

}
