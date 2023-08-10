class Oseba 
{
	String ime;
	
	Oseba oce;
	Oseba brat;
	Oseba sin;
	
	public Oseba(String ime, Oseba oce) 
	{
		this.ime = ime;
		this.oce = oce;
	}
}

public class Rodovnik 
{
	Oseba koren;
	
	public Rodovnik(String ime) 
	{
		koren = new Oseba(ime, null);
	}
	
	//Metoda doda sina podanemu ocetu
	public boolean dodajSina(String oce, String sin) 
	{
		// Vstavite svojo kodo
			
		return false;
	}

	public Oseba poisciOsebo(String ime) {
		// gre cez vse brate in za vsazga cez vse sinove
	}
		
	//Metoda za izpis druzinskega drevesa
	public void izpis()
	{
		izpis(0, koren);
	}

	private void izpis(int zamik, Oseba v) {
		for (int i = 0; i < zamik; i++) {
			System.out.print(" ");
		}

		System.out.println(v.ime);
		Oseba sin = v.sin;

		while (sin != null) {
			izpis(zamik + 1, sin);
			sin = sin.brat;
		}
	}
	
	
	//Metoda izpise vse sinove oceta, ki ga dolocimo na podlagi podanega imena
	public void izpisiSinove(String ime) 
	{
		// Vstavite svojo kodo
	}
	
	//Metoda izpise vse vnuke osebe, ki je podana z imenom
	public void izpisiVnuke(String ime) 
	{
		// Vstavite svojo kodo
	}
	
	//Metoda izpise vse pravnuke osebe, ki je podana z imenom
	public void izpisiPravnuke(String ime) 
	{
		// Vstavite svojo kodo
	}
	
	//Metoda izpise vse strice osebe, ki je podana z imenom
	public void izpisiStrice(String ime) 
	{
		// Vstavite svojo kodo
	}
	
	//Metoda izpise vse bratrance osebe, ki je podana z imenom
	public void izpisiBratrance(String ime) 
	{
		// Vstavite svojo kodo
	}
	
	//Metoda izpise vse potomce osebe, ki je podana z imenom
	public void izpisiVsePotomce(String ime) 
	{
		// Vstavite svojo kodo
	}
		
	//Metoda izpise vse prednike osebe, ki je podana z imenom
	public void izpisiVsePrednike(String ime) 
	{
		// Vstavite svojo kodo
	}
		
	//Metoda presteje vozlisca v celotnem drevesu
	public int prestejVozlisca() 
	{
		// Vstavite svojo kodo 
		
		return 0;
	}
}
