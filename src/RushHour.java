/* Class containing all the programs asked in the PI project */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class RushHour {

////////////   Constructor   ///////////////////

	int size;
	Vehicle[] vehicules;
	
	RushHour(String fileName) throws FileNotFoundException {
		File fichier = new File(fileName);
		java.util.Scanner lecteur = new Scanner(fichier);
		this.size = lecteur.nextInt();
		this.vehicules = new Vehicle[lecteur.nextInt()];
		for (int i = 0; i<vehicules.length; i++) {
			vehicules[i] = new Vehicle(lecteur.nextInt(), lecteur.next(), lecteur.nextInt(), lecteur.nextInt(), lecteur.nextInt());	
		}
		lecteur.close();
	}
	
///// Method used to display a Rush Hour Grid /////
	
	public int[][] display() throws SuperpositionError {
		int[][] tableau = new int[this.size][this.size];
		for (Vehicle v:this.vehicules) {
			if(v.getOrientation()=='h') {
				for (int i=0; i<v.getLength(); i++){
					if (tableau[v.getOrdinate()-1][v.getAbsissa()+i-1]!=0) throw new SuperpositionError();
					tableau[v.getOrdinate()-1][v.getAbsissa()+i-1] = v.name;
				}
			}
			else {
				for (int i=0; i<v.getLength(); i++){
					if (tableau[v.getOrdinate()+i-1][v.getAbsissa()-1]!=0) throw new SuperpositionError();
					tableau[v.getOrdinate()+i-1][v.getAbsissa()-1] = v.name;
				}
			}
		}
		return tableau;
	}
	
	
/// Find all the neighbors from a given state in a complexity O(numbervehicles*gamesize). They are all store in a LinkedList.
	public LinkedList<RushHour> AllPossibleMoves(RushHour R) throws SuperpositionError {
		LinkedList<RushHour> L = new LinkedList<RushHour>();
		int[][] tableau = R.display();
		for (Vehicle v:R.vehicules) {
			if (v.getOrientation()=='h') {
				int i = - 1;
				while (tableau[v.getOrdinate()-1][v.getAbsissa()-1 + i]==0) {
					v.move(i);
					L.add(R);
					v.move(-i);
				}
				i=1;
				while (tableau[v.getOrdinate()-1][v.getAbsissa()-1 + v.getLength() + i]==0) {
					v.move(i);
					L.add(R);
					v.move(-i);
				}
			}
			else {
				int i = - 1;
				while (tableau[v.getOrdinate()-1 + i][v.getAbsissa()-1]==0) {
					v.move(i);
					L.add(R);
					v.move(-i);
				}
				i=1;
				while (tableau[v.getOrdinate()-1 + v.getLength() + i][v.getAbsissa()-1]==0) {
					v.move(i);
					L.add(R);
					v.move(-i);
				}
			}
		}
		
		return L;
	}

}
