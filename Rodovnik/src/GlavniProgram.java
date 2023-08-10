
public class GlavniProgram 
{

	public static void main(String[] args) 
	{
		Rodovnik drevo = new Rodovnik("A");
		
		drevo.dodajSina("A", "B");
		drevo.dodajSina("A", "C");
		drevo.dodajSina("B", "D");
		drevo.dodajSina("B", "E");
		drevo.dodajSina("C", "F");
		drevo.dodajSina("F", "G");
		drevo.dodajSina("A", "H");
		
		System.out.println("Izpis drevesa:");
		drevo.izpis();
		
		System.out.println("Vseh vozlisc: " + drevo.prestejVozlisca());
		
		System.out.println("Izpis sinov vozlisca A:");
		drevo.izpisiSinove("A");
		
		System.out.println("Izpis vseh potomcev vozlisca A:");
		drevo.izpisiVsePotomce("A");
		
		System.out.println("Izpis vseh prednikov vozlisca G:");
		drevo.izpisiVsePrednike("G");
		
		System.out.println("Izpis vnukov vozlisca A:");
		drevo.izpisiVnuke("A");
		
		System.out.println("Izpis pravnukov vozlisca A:");
		drevo.izpisiPravnuke("A");
		
		System.out.println("Izpis stricev vozlisca F:");
		drevo.izpisiStrice("F");
		
		System.out.println("Izpis bratrancev vozlisca F:");
		drevo.izpisiBratrance("F");
	}
}
