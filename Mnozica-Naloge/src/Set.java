class SetElement
{
	Object element;
	SetElement next;

	SetElement()
	{
		element = null;
		next = null;
	}
}

public class Set 
{
	private SetElement first;
	
	public Set() 
	{
		makenull();
	}
	
	public void makenull()
	{
		// kazalec first kaze na glavo seznama
		first = new SetElement();
	}
	
	public SetElement first()
	{
		return first;
	}
	
	public SetElement next(SetElement pos)
	{
		return pos.next;
	}
	
	public boolean overEnd(SetElement pos)
	{
		if (pos.next == null)
			return true;
		else
			return false;
	}
	
	public boolean empty()
	{
		return first.next == null;
	}
	
	public Object retrieve(SetElement pos)
	{
		return pos.next.element;
	}
	
	public void print()
	{
		System.out.print("{");
		for (SetElement iter = first(); !overEnd(iter); iter = next(iter))
		{
			System.out.print(retrieve(iter));
			if (!overEnd(next(iter)))
				System.out.print(", ");
		}
		System.out.println("}");
	}
	
	
	public void insert(Object obj) 
	{
		SetElement el = first();

		while (!overEnd(el)) {
			el = next(el);
			if (el.element.equals(obj)) {
				return;
			}
		}

		SetElement novi = new SetElement();
		novi.element = obj;
		el.next = novi;
	}
	
	public void delete(SetElement pos)
	{
		SetElement el = first();

		while (!overEnd(el)) {
			if (el.next == pos) {
				el.next = pos.next;
			}
			if (!overEnd(el)) {
				el = next(el);
			}
		}
	}
	
	public SetElement locate(Object obj)
	{
		SetElement el = first();

		while (!overEnd(el)) {
			el = next(el);
			if (el.element.equals(obj)) {
				return el;
			}
		}
		return null;
	}
	
	public void union(Set a)
	{
		SetElement el = a.first();

		while (!overEnd(el)) {
			el = a.next(el);
			insert(el.element);
		}
	}
	
	public void intersection(Set a)
	{
		SetElement el = first();

		while (!overEnd(el)) {
			if (a.locate(el.next.element) == null) {
				el.next = el.next.next;
				if (overEnd(el)) {
					break;
				}
			}
			else {
				el = next(el);
			}
		}
	}

	public Set copy() {
		Set s = new Set();
		s.union(this);
		return s;
	}
}
