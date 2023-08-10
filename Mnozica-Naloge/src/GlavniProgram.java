
public class GlavniProgram {

	// Funkcija prejme stavek ter izpise (brez ponavljanja)
	// - crke, ki nastopajo v stavku
	// - crke, ki nastopajo v vsaki besedi stavka
	public static void crke(String stavek)
	{
		// stavek spremenimo v male crke in ga razdelimo na besede
		String[] besede = stavek.toLowerCase().split(" ");

		for (String beseda : besede) {
			System.out.println("Beseda " + beseda);
		}
		
		// mnozica za hranjenje vseh crk v stavku 
		Set vse = new Set();
		
		// mnozico za hranjenje crk, ki nastopajo v vsaki besedi stavka
		Set povsod = new Set();
		
		
		// sprehodimo se cez vse besede in zgradimo zahtevani mnozici

		for (int i = 0; i < besede.length; ++i) {
			for (int j = 0; j < besede[i].length(); ++j) {
				vse.insert(besede[i].charAt(j));
				povsod.insert(besede[i].charAt(j));
			}
		}

		for (int i = 0; i < besede.length; ++i) {
			Set trenutnaBeseda = new Set();
			for (int j = 0; j < besede[i].length(); ++j) {
				trenutnaBeseda.insert(besede[i].charAt(j));
			}
			povsod.intersection(trenutnaBeseda);
			System.out.println("Povsod: ");
			trenutnaBeseda.print();
		}
		
		System.out.println("V stavku se pojavljajo crke: ");
		vse.print();
		System.out.println("V vsaki besedi se pojavljajo crke: ");
		povsod.print();
	}
	
	public static Set createPowerSet(Set s)
	{
		// rezultat je mnozica mnozic
		Set result = new Set();
		
		// prvi element v rezultatu je prazna mnozica
		result.insert(new Set());
		
		// zaporedno uporabimo elemente iz izhodiscne mnozice za razsiritev rezultata tako,
		// da za vsako mnozico v rezultatu dodamo se mnozico razsirjeno s trenutnim elementom 

		if (s.empty()) {
			return result;
		}

		Object a = s.first().next.element;
		s.delete(s.locate(a));


		Set subsetsWithA = createPowerSet(s.copy());
		Set subsetsWithoutA = createPowerSet(s.copy());

		// System.out.println("With " + a);
		// printPowerSet(subsetsWithA);
		// System.out.println("Without " + a);
		// printPowerSet(subsetsWithoutA);
		// System.out.println("End print");

		SetElement el = subsetsWithA.first();
		while (!subsetsWithA.overEnd(el)) {
			// System.out.println("Before insert");
			// ((Set)el.next.element).print();
			((Set)el.next.element).insert(a);
			// ((Set)el.next.element).print();
			// System.out.println("After insert");
			el = subsetsWithA.next(el);
		}

		subsetsWithA.union(subsetsWithoutA);
		return subsetsWithA;
	}
	
	public static void printPowerSet(Set p)
	{
		for (SetElement iter = p.first(); !p.overEnd(iter); iter = p.next(iter))
		{
			Set s = (Set)p.retrieve(iter);
			s.print();
		}
	}
	
	public static void main(String[] args) 
	{
		// Set a = new Set();
		// a.insert(1);
		// a.insert(1);
		// a.print();
		
		// a.delete(a.locate(1));
		// a.print();
		
		// a.insert(1);
		// a.insert(2);
		// a.insert(3);
		// a.insert(2);
		// a.insert(3);
		// a.insert(4);
		// a.print();
		
		Set b = new Set();
		
		b.insert(10);
		b.insert(5);
		b.insert(3);

		// crke("Abstraktni podatkovni tip");
		
		System.out.print("Potencna mnozica mnozice ");
		b.print();
		
		Set p = createPowerSet(b);
		System.out.println("Printing power set");
		printPowerSet(p);
	}

}
