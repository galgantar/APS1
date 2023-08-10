class BSTNode {
	Comparable key;
	BSTNode left;
	BSTNode right;
	
	public BSTNode(Comparable k) {
		key = k;
		left = null;
		right = null;
	}
}

class BSTree {
	BSTNode root;
	
	public BSTree() {
		makenull();
	}
	
	// Funkcija naredi prazno drevo
	void makenull() {
		root = null;
	}
	
	// Rekurzivna funkcija za vstavljanje elementa v list drevesa
	private BSTNode insertRek(BSTNode node, Comparable k)
	{
		if (node == null)
			node = new BSTNode(k);
		else if (k.compareTo(node.key) < 0)
			node.left = insertRek(node.left, k);
		else if (k.compareTo(node.key) > 0)
			node.right = insertRek(node.right, k);
		else
			;//element je ze v drevesu, ne naredimo nicesar
		return node;
	}
	
	// Rekurzivna funkcija za vstavljanje elementa v list drevesa
	public void insertRek(Comparable k) {
		root = insertRek(root, k);
	}
	
	// Rekurzivna funkcija za izpis poddrevesa s podanim korenom
	private void write(BSTNode node) {
		if (node != null) {
			System.out.print("(");
			write(node.left);
			System.out.print(", " + node.key + ", ");
			write(node.right);
			System.out.print(")");
		}
		else
			System.out.print("null");
	}
	
	// Funkcija za izpis drevesa
	public void write() {
		write(root);
		System.out.println();
	}
	
	// Rekurzivna funkcija, ki preveri, ali se podani element nahaja v podanem poddrevesu
	private boolean memberRek(BSTNode node, Comparable k) {
		if (node == null)
			return false;
		else if (k.compareTo(node.key) == 0)
			return true;
		else if (k.compareTo(node.key) < 0)
			return memberRek(node.left, k);
		else
			return memberRek(node.right, k);
	}
	
	// Funkcija preveri, ali se podani element nahaja v drevesu
	public boolean memberRek(Comparable k) {
		return memberRek(root, k);
	}
	
	
	//Samostojno delo

	// Funkcija, ki poreze liste drevesa
	public void prune() {
		root = prune(root);
	}
	
	protected BSTNode prune(BSTNode node) {
		if (node == null)
			return null;
		
		if (node.left == null && node.right == null)
			return null;
		
		node.left = prune(node.left);
		node.right = prune(node.right);
		return node;
	}
	
	// Funkcija, ki vrne visino drevesa
	public int height() {
		return height(root);
	}

	protected int height(BSTNode node) {
		if (node == null) {
			return 0;
		}
		
		int hLeft = height(node.left);
		int hRight = height(node.right);

		return Math.max(hLeft, hRight) + 1;
	}
	
	// Funkcija, ki preveri, ali je drevo uravnoveseno
	public boolean isBalanced() {
		return isBalanced(root);
	}

	protected boolean isBalanced(BSTNode node) {
		if (node == null) {
			return true;
		}

		if (Math.abs(height(node.left) - height(node.right)) >= 2) {
			return false;
		}

		return isBalanced(node.left) && isBalanced(node.right);
	}

	// Funkcija, ki vrne stevilo elementov v drevesu
	public int numOfElements() {
		return numOfElements(root);
	}

	protected int numOfElements(BSTNode node) {
		if (node == null) {
			return 0;
		}

		return numOfElements(node.left) + numOfElements(node.right) + 1;
	}
	
	// Iterativna funkcija za vstavljanje elementa v list drevesa
	public void insertIter(Comparable k) {
		if (root == null) {
			root = new BSTNode(k);
			return;
		}

		BSTNode el = root;

		while (k.compareTo(el.key) != 0) {
			if (k.compareTo(el.key) < 0) {
				if (el.left != null) {
					el = el.left;
				}
				else {
					el.left = new BSTNode(k);
				}
			}
			else if (k.compareTo(el.key) > 0) {
				if (el.right != null) {
					el = el.right;
				}
				else {
					el.right = new BSTNode(k);
				}
			}
		}
	}
	
	// Iterativna funkcija, ki preveri, ali se podani element nahaja v drevesu
	public boolean memberIter(Comparable k) {
		// VSTAVITE SVOJO KODO
		
		return false;
	}
	
	// Funkcija, ki izpise elemente drevesa v padajocem vrstnem redu
	public void descending() {
		// VSTAVITE SVOJO KODO
	}
	
	// Funkcija, ki vrne kazalec na element drevesa s prvo manjso vrednostjo kljuca od podane vrednosti
	public BSTNode predecessor(Comparable k) {
		// VSTAVITE SVOJO KODO
		
		return null;
	}
	
	// Funkcija, ki vrne kazalec na element drevesa s prvo vecjo vrednostjo kljuca od podane vrednosti
	public BSTNode successor(Comparable k) {
		// VSTAVITE SVOJO KODO
		
		return null;
	}
}	

