import java.util.function.ObjLongConsumer;

class StackElement
{
	Object element;
	StackElement next;

	StackElement()
	{
		element = null;
		next = null;
	}
}

class Stack
{
	//StackElement -> StackElement -> StackElement -> ... -> StackElement
	//     ^
	//     |
	//    top                                                   
	//
	// elemente vedno dodajamo in brisemo na zacetku seznama (kazalec top)
	
	
	private StackElement top;
	
	public Stack()
	{
		makenull();
	}
	
	public void makenull()
	{
		top = null;
	}
	
	public boolean empty()
	{
		return (top == null);
	}
	
	public Object top()
	{
		if (empty()) {
			return null;
		}
		// Funkcija vrne vrhnji element sklada (nanj kaze kazalec top).
		// Elementa NE ODSTRANIMO z vrha sklada!
		
		return top.element;
	}
	
	public void push(Object obj)
	{
		StackElement el = top;

		top = new StackElement();
		top.element = obj;
		top.next = el;
		// Funkcija vstavi nov element na vrh sklada (oznacuje ga kazalec top)
	}
	
	public void pop()
	{
		top = top.next;
		// Funkcija odstrani element z vrha sklada (oznacuje ga kazalec top)
	}

	public static void main(String[] args) {
		Stack stack = new Stack();

		stack.push(1);
		stack.push(2);
		stack.push(3);

		System.out.println(stack.top());
		stack.pop();
		System.out.println(stack.top());
		stack.pop();
		System.out.println(stack.top());
	}
}
