/* Class containing all the programs asked in the PI project */

import java.io.File;
import java.io.FileNotFoundException;
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

}
