
public class GlavniProgram {

	public static void main(String[] args) {
		
		BSTree tree = new BSTree();
		
		tree.insertRek(2);
		tree.insertRek(9);
		tree.insertRek(4);
		//tree.write();
		
		// int key = 2;
		// if (tree.memberRek(key))
		// 	System.out.println("Element s kljucem " + key + " je v drevesu.");
		// else
		// 	System.out.println("Elementa s kljucem " + key + " ni v drevesu.");
		
		tree.makenull();
		
		tree.insertIter(5);
		tree.insertIter(7);
		tree.insertIter(9);
		tree.insertIter(2);
		
		tree.write();
		
		System.out.println("Visina drevesa: " + tree.height());
		
		if (tree.isBalanced())
			System.out.println("Drevo je poravnano.");
		else
			System.out.println("Drevo ni poravnano.");	
		
		tree.insertIter(2);
		tree.write();
		System.out.println("Visina drevesa: " + tree.height());
		
		if (tree.isBalanced())
			System.out.println("Drevo je poravnano.");
		else
			System.out.println("Drevo ni poravnano.");
		
		System.out.println("Izpis elementov drevesa od najvecjega proti najmanjsemu:");
		tree.descending();
		
		System.out.println("Stevilo elementov v drevesu: " + tree.numOfElements());
		
		

		
		
		
		tree.makenull();
		
		tree.insertIter(5);
		tree.insertIter(7);
		tree.insertIter(9);
		tree.insertIter(2);
		tree.insertIter(4);
		tree.insertIter(8);
		tree.insertIter(3);
		tree.insertIter(1);
		
		tree.write();
		tree.prune();
		tree.write();

	}

}