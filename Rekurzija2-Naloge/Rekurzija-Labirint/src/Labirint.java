
public class Labirint {

	//
	// Oznake:
	//
	// '#' zid
	// ' ' hodnik
	// 'C' cilj
	// '.' oznaka, da smo trenutno lokacijo vkljucili v pot
	//
	
	// Rekurzivna funkcija, ki poi��e pot skozi labirint
	//
	// - labirint je podan z dvodimenzionalnim poljem "labirint"
	// - "x" in "y" sta trenutni koordinati potnika, ki se premika proti cilju
	//

	public static boolean najdiPot(char[][] labirint, int x, int y)
	{		
		//System.out.printf("Trenutno %d, %d, %c\n", x, y, labirint[x][y]);

		if (labirint[x][y] == 'C') {
			return true;
		}
		if (labirint[x][y] == '#') {
			return false;
		}
		if (labirint[x][y] == '.') {
			return false;
		}
		
		labirint[x][y] = '.';

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if ((i == 0 || j == 0) && najdiPot(labirint, x + i, y + j)) {
					return true;
				}
			}
		}
		
		labirint[x][y] = ' ';

		return false;
	}
	
	public static void izpis(char[][] labirint)
	{
		for (int i = 0; i < labirint.length; i++)
		{
			for (int j = 0;  j < labirint[i].length; j++)
				System.out.print(labirint[i][j]);
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		char[][] labirint = {
				{'#','#','#','#','#','#','#','#','#'},
				{'#',' ',' ',' ',' ',' ','#',' ','#'},
				{'#',' ','#','#','#',' ','#',' ','#'},
				{'#',' ','#','#','#',' ','#',' ','#'},
				{'#',' ',' ',' ','#','#','#',' ','#'},
				{'#',' ','#',' ','#',' ',' ',' ','#'},
				{'#',' ','#',' ',' ',' ','#',' ','#'},
				{'#',' ','#','#','#','#','#',' ','#'},
				{'#',' ',' ',' ','#',' ',' ','C','#'},
				{'#','#','#','#','#','#','#','#','#'}};

		System.out.println("Izgled labirinta:");
		izpis(labirint);

		System.out.println("\nNajdena pot skozi labirint:");
		// poi��imo izhod iz labirinta - izhodi��ni polo�aj je na koordinati (x=5,y=3)

		if (najdiPot(labirint, 5, 3))
			izpis(labirint);
		else
			System.out.println("Ne najdem poti skozi labirint!");
	}
}
