class Konj
{
	int x;
	int y;
	String poteze;
	
	Konj(int x, int y, String poteze)
	{
		// hranimo trenutno pozicijo ('x', 'y') ter pot do nje ('poteze')
		this.x = x;
		this.y = y;
		this.poteze = poteze + " -> [" + x + "," + y + "]";
	}
}

	
public class NajkrajsaPot {
	
	public static void main(String[] args) {
		
		// podana je mapa v obliki dvodimenzionalnega polja 'mapa'
		// 'C' oznacuje ciljno polje
		// 'B' oznacuje polje z bombo
		// ' ' oznacuje varno polje, na katerega lahko stopimo
		
		char[][] mapa = {
				{'B',' ',' ',' ','B',' ',' ','B'},
				{' ','B',' ',' ',' ','B','B',' '},
				{' ','B','B',' ',' ',' ',' ',' '},
				{' ','B',' ',' ','B','B','B',' '},
				{'B','B','B','B','B',' ','B','B'},
				{'B',' ',' ','B',' ','C',' ',' '},
				{' ','B','B','B',' ','B','B','B'},
				{' ','B',' ',' ','B','B',' ',' '}};
		
		
		// zacetni polozaj konja
		int zacetni_x = 0;
		int zacetni_y = 1;
		
		//
		// Zelimo poiskati najkrajso varno pot sahovskega konja iz zacetne pozicije do ciljnega polja.
		// Pri tem si pomagamo z abstraktnim podatkovnim tipom vrsta.
		//
		
		Queue queue = new Queue();
		
		System.out.println("Zacetna pozicija konja je [" + zacetni_x + "," + zacetni_y + "]");
		
		Konj k = new Konj(zacetni_x, zacetni_y, "");
		queue.enqueue(k);
		
		boolean obstajaPot = false;
		while (!queue.empty())
		{
			// - preberemo zacetni element in ga odstranimo iz vrste
			Konj vrh = (Konj)queue.front();
			// System.out.printf("Vrh (%d, %d)\n", vrh.x, vrh.y);

			queue.dequeue();
			
			// - preverimo ali prebrani element oznacuje veljavno pozicijo na mapi (ali so koordinate znotraj dovoljenih vrednosti)
			//   ce pozicija ni veljavna, izvedemo naslednjo iteracijo zanke
			if (vrh.x < 0 || vrh.x >= mapa[0].length ||
					vrh.y < 0 || vrh.y >= mapa.length) {
				continue;
			}

			if (mapa[vrh.y][vrh.x] == 'B') {
				continue;
			}

			if (mapa[vrh.y][vrh.x] == 'C') {
				obstajaPot = true;
				System.out.println(vrh.poteze);
				break;
			}

			
			queue.enqueue(new Konj(vrh.x + 2, vrh.y + 1, vrh.poteze));
			queue.enqueue(new Konj(vrh.x + 2, vrh.y - 1, vrh.poteze));
			queue.enqueue(new Konj(vrh.x - 2, vrh.y + 1, vrh.poteze));
			queue.enqueue(new Konj(vrh.x - 2, vrh.y + 1, vrh.poteze));

			queue.enqueue(new Konj(vrh.x - 1, vrh.y - 2, vrh.poteze));
			queue.enqueue(new Konj(vrh.x + 1, vrh.y - 2, vrh.poteze));
			queue.enqueue(new Konj(vrh.x - 1, vrh.y + 2, vrh.poteze));
			queue.enqueue(new Konj(vrh.x + 1, vrh.y + 2, vrh.poteze));

			// queue.print();
			// - ce je pozicija veljavna, preverimo ali smo na cilju 'C' -  v tem primeru izpisemo resitev in izstopimo iz zanke
			// - ce je pozicija veljavna, preverimo ali smo na bombi 'B' - v tem primeru izvedemo naslednjo iteracijo zanke 
			// - ce je pozicija veljavna in nismo ne na cilju ne na bombi, generiramo naslednje pozicije konja in jih dodamo v vrsto
		}
		
		if (!obstajaPot)
			System.out.println("Varne poti do ciljnega polja ni!");
	}

}
