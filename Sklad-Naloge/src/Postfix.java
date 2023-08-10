
public class Postfix 
{

	public static void main(String[] args) 
	{
		
		Stack stack = new Stack();
		
		String[] izraz = {"2","3","2","*","1","+","+","4","-"};
		
		for (int i = 0; i < izraz.length; i++)
		{
			String token = izraz[i];
			Double arg1;
			Double arg2;
			
			if (token.compareTo("+") == 0)
			{
				// trenutni znak je operator '+'
				
				arg1 = (Double)stack.top();
				stack.pop();
				arg2 = (Double)stack.top();
				stack.pop();

				stack.push(arg1 + arg2);
				// s sklada vzemi nazadnje dodana argumenta 
				// (POZOR! Upostevajte vrstni red dobljenih argumentov!)
				
				// na sklad dodaj rezultat operacije
			}
			else
			if (token.compareTo("-") == 0)	
			{
				// trenutni znak je operator '-'
				arg1 = (Double)stack.top();
				stack.pop();
				arg2 = (Double)stack.top();
				stack.pop();

				stack.push(arg1 - arg2);
				// s sklada vzemi nazadnje dodana argumenta 
				// (POZOR! Upostevajte vrstni red dobljenih argumentov!)
				
				// na sklad dodaj rezultat operacije
			}
			else
			if (token.compareTo("*") == 0)
			{
				// trenutni znak je operator '*'
				arg1 = (Double)stack.top();
				stack.pop();
				arg2 = (Double)stack.top();
				stack.pop();

				stack.push(arg1 * arg2);
				// s sklada vzemi nazadnje dodana argumenta 
				// (POZOR! Upostevajte vrstni red dobljenih argumentov!)
				
				// na sklad dodaj rezultat operacije
			}
			else
			if (token.compareTo("/") == 0)
			{
				// trenutni znak je operator '/'
				arg1 = (Double)stack.top();
				stack.pop();
				arg2 = (Double)stack.top();
				stack.pop();

				stack.push(arg1 / arg2);
				// s sklada vzemi nazadnje dodana argumenta 
				// (POZOR! Upostevajte vrstni red dobljenih argumentov!)
				
				// na sklad dodaj rezultat operacije
			}
			else
			{
				if (token == "+" || token == "*" || token == "/" || token == "-") {
					stack.push(token);
				}
				else {
					stack.push(Double.parseDouble(token));
				}
				// opazovani element je argument
				// dodaj ga na sklad	
			}
		}
		
		System.out.print("Vrednost postfix izraza ");
		for (int i = 0; i < izraz.length; i++)
			System.out.print(izraz[i]+" ");
		System.out.println(" je ...");
		System.out.println(stack.top());
		// Izracunana vrednost postfix izraza se nahaja na vrhu sklada.
		// Preberite jo in izpisite.
	}
}
