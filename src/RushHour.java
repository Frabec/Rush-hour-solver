/* Class containing all the programs asked in the PI project */

import java.io.*;

public class RushHour {

////////////   Constructor   ///////////////////

	int size;
	Vehicle[] vehicules;
	
	RushHour(String File) throws IOException {
		BufferedReader br;
		br = new BufferedReader(new FileReader(File));
		String line;
		line = br.readLine();
		this.size = (int) line.charAt(0);
		line = br.readLine();
		int n = (int) line.charAt(0);
		this.vehicules = new Vehicle[n];
		for (int i=0; i<n; i++) {
			line = br.readLine();
			this.vehicules[i] = new Vehicle(line);
		}
		br.close();
	}
	
///// Method used to display a Rush Hour Grid /////
	
	public int[][] display() throws SuperpositionError {
		int[][] tableau = new int[this.size][this.size];
		for (Vehicle v:this.vehicules) {
			if(v.getOrientation()=='h') {
				for (int i=0; i<v.getLength(); i++){
					if (tableau[v.getAbsissa()-1][v.getOrdinate()+i-1]!=0) throw new SuperpositionError();
					tableau[v.getAbsissa()-1][v.getOrdinate()+i-1] = v.name;
				}
			}
			else {
				for (int i=0; i<v.getLength(); i++){
					if (tableau[v.getAbsissa()+i-1][v.getOrdinate()-1]!=0) throw new SuperpositionError();
					tableau[v.getAbsissa()+i-1][v.getOrdinate()-1] = v.name;
				}
			}
		}
		return tableau;
	}

}
