
class Oseba implements Comparable<Oseba>
{
	String ime;
	String priimek;
	
	Oseba(String ime, String priimek)
	{
		this.ime = ime;
		this.priimek = priimek;
	}
	
	public int compareTo(Oseba o)
	{
		int r = priimek.compareTo(o.priimek);
		
		if (r == 0)
			return ime.compareTo(o.ime);
		else
			return r;
	}
	
	public boolean equals(Oseba o)
	{
		return compareTo(o) == 0;
	}
	
	public String toString()
	{
		return priimek + ", " + ime;
	}
	
	public int hashCode()
	{
		// Trenutno je uporabljena privzeta funkcija hashCode() razreda String.
		//
		// Lahko eksperimentirate z razlicnimi implementacijami izracuna zgoscene kode osebe
		// in primerjate case izvajanja v glavnem programu.
		//
		// Nekaj idej:
		//
		// - zgosceno kodo osebe predstavlja prvi znak priimka
		// - zgosceno kodo osebe predstavlja vsota vseh znakov imena in priimka
		// - zgosceno kodo osebe predstavlja utezena vsota vseh znakov imena in priimka
		
		return (ime + priimek).hashCode();
	}
}