
public class LinkedList
{
	protected LinkedListElement first;
	protected LinkedListElement last;
	
	LinkedList()
	{
		makenull();
	}
	
	//Funkcija makenull inicializira seznam
	public void makenull()
	{
		//drzimo se implementacije iz knjige:
		//po dogovoru je na zacetku glava seznama (header)
		first = new LinkedListElement(null, null);
		last = null;
	}
	
	//Funkcija addLast doda nov element na konec seznama
	public void addLast(Object obj)
	{
		// System.out.println(first.element + " " + last.element);
		
		LinkedListElement novi = new LinkedListElement(obj, null);
		
		if (last == null) {
			first.next = novi;
			last = first;
		}
		else {
			last.next.next = novi;
			last = last.next;
		}
	}
	
	//Funkcija write izpise elemente seznama
	public void write()
	{
		LinkedListElement el = first;
		
		while(el != null) {
			System.out.print(el.element + " ");
			el = el.next;
		}
		System.out.println();
	}
	
	//Funkcija addFirst doda nov element na prvo mesto v seznamu (takoj za glavo seznama)
	void addFirst(Object obj)
	{
		//najprej naredimo nov element
		LinkedListElement novi = new LinkedListElement(obj, first.next);
		
		first.next = novi;
		
		if (last == null) {
			last = first;
		}
		else if (last == first) {
			last = novi;
		}
	}
	
	//Funkcija length() vrne dolzino seznama (pri tem ne uposteva glave seznama)
	int length()
	{
		int len = 0;
		
		LinkedListElement el = first;
		
		while(el != null) {
			len++;
			el = el.next;
		}
		return len - 1;
	}
	
	//Funkcija lengthRek() klice rekurzivno funkcijo za izracun dolzine seznama
	int lengthRek()
	{
		if (last == null) {
			return 0;
		}
		if (first == last) {
			return 1;
		}
		
		LinkedList l = new LinkedList();
		l.first = first.next;
		l.last = last;
		
		return l.lengthRek() + 1;
	}
	
	//Funkcija insertNth vstavi element na n-to mesto v seznamu
	//(prvi element seznama, ki se nahaja takoj za glavo seznama, je na indeksu 0)
	boolean insertNth(Object obj, int n)
	{
		LinkedListElement el = first;
		int i = 0;
		
		while (i < n) {
			i++;
			if (el == last.next) {
				System.out.println("Not valid position");
				return false;
			}
			el = el.next;
			
		}
		
		if (el == last.next) {
			LinkedListElement novi = new LinkedListElement(obj, el.next);
			el.next = novi;

			last = el;
			return true;
		}
				
		LinkedListElement novi = new LinkedListElement(obj, el.next);
		el.next = novi;
		
		return true;
	}
	
	//Funkcija deleteNth izbrise element na n-tem mestu v seznamu
	//(prvi element seznama, ki se nahaja takoj za glavo seznama, je na indeksu 0)
	boolean deleteNth(int n)
	{
		LinkedListElement el = first;
		int i = 0;
		
		System.out.println("Last.next " + last.next.element);
		
		while (i < n) {
			i++;
			if (el == last.next) {
				System.out.println("Not valid position");
				return false;
			}
			el = el.next;
		}
		
		el.next = el.next.next;
		
		return true;
	}
	
	//Funkcija reverse obrne vrstni red elementov v seznamu (pri tem ignorira glavo seznama)
	void reverse()
	{
		//ne pozabimo na posodobitev kazalca "last"!
	}
	
	//Funkcija reverseRek klice rekurzivno funkcijo, ki obrne vrstni red elementov v seznamu
	void reverseRek()
	{
		// pomagajte si z dodatno funkcijo void reverseRek(LinkedListElement el), ki
		// obrne del seznama za opazovanim elementom, nato ta element doda na konec (obrnjenega) seznama
	}
	
	//Funkcija removeDuplicates odstrani ponavljajoce se elemente v seznamu
	void removeDuplicates()
	{
		//ne pozabimo na posodobitev kazalca "last"!
	}
}
