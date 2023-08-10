class HashMapNode 
{
	public HashMapNode(Object key, Object value) 
	{
		this.key = key;
		this.value = value;
	}

	public Object getKey() 
	{
		return key;
	}

	public Object getValue() 
	{
		return value;
	}

	public void setKey(Object key) 
	{
		this.key = key;
	}

	public void setValue(Object value) 
	{
		this.value = value;
	}

	public boolean equals(Object obj)
	{
		if (obj instanceof HashMapNode)
		{
			HashMapNode node = (HashMapNode)obj;
			return key.equals(node.key);
		}
		
		return false;
	}
	
	public String toString()
	{
		return "[" + key + ", " + value + "]";
	}
	
	private Object key;
	private Object value;
}



public class HashMap
{
	public static final int DEFAULT_SIZE = 7;

	Set[] table;
	
	public HashMap() 
	{
		makenull(DEFAULT_SIZE);
	}
	
	public HashMap(int size) 
	{
		makenull(size);
	}

	public void makenull() 
	{
		makenull(DEFAULT_SIZE);
	}
	
	public void makenull(int size) 
	{
		// create an empty table and initialize the linked lists
		table = new Set[size];
		
		for (int i = 0; i < table.length; i++) 
		{
			table[i] = new Set();
		}
	}

	private int hash(Object d) 
	{
		return Math.abs(d.hashCode()) % table.length;
	}

	public void print()
	{
		for (int i = 0; i < table.length; i++)
			table[i].print();
	}
	
	public void assign(Object d, Object r) 
	{
		// Funkcija doda nov par (d, r) v preslikavo M.
		// To pomeni, da velja M(d) = r.
		//
		// V primeru, da v preslikavi M ze obstaja par s kljucem d,
		// se obstojeci shranjeni par spremeni v (d, r).
		
		delete(d);
		table[hash(d)].insert(new HashMapNode(d, r));
	}

	public Object compute(Object d) 
	{
		// Funkcija vrne vrednost M(d).
		// Ce vrednost M(d) ni definirana, funkcija vrne null.
		
		Set l = table[hash(d)];
		SetElement pos = l.locate(new HashMapNode(d, null));
		if (pos != null) {
			return ((HashMapNode) l.retrieve(pos)).getValue();
		}
		return null;
	}

	public void delete(Object d) 
	{
		// Funkcija zbrise par (d, r) iz preslikave M.
		// To pomeni, da vrednost M(d) ni vec definirana.
		
		Set l = table[hash(d)];
		SetElement pos = l.locate(new HashMapNode(d, null));
		if (pos != null) {
			l.delete(pos);
		}
	}

	public void rehash(int size)
	{
		// Funkcija zgradi novo zgosceno tabelo podane velikosti.
		// Obstojeci pari (d, r) se prenesejo v novo tabelo.
		
		Set newTable[] = new Set[size];
		for (int j = 0; j < size; j++) {
			newTable[j] = new Set();
		}

		for (int i = 0; i < table.length; i++) {
			Set currentSet = table[i];
			for (SetElement iter = currentSet.first(); !currentSet.overEnd(iter); iter = currentSet.next(iter)) {
				
				HashMapNode el = (HashMapNode) currentSet.retrieve(iter);
				newTable[hash(el)].insert(el);
			}
		}

		this.table = newTable;
	}
}
