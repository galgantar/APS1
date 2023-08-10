import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

 
class Stanje {
    int t;

    public Stanje(int t) {
        this.t = t;
    }


    public boolean equals(Stanje s) {
        return (t == s.t);
    }

    public int hashCode() {
        return (Integer.valueOf(t).hashCode());
}
}


class HashSet {
 
    class HashTabelaElement {
 
        Stanje element;
        HashTabelaElement next;
 
        public HashTabelaElement(Stanje element, HashTabelaElement next)
        {
            this.element = element;
            next = null;
        }
    }
 
    HashTabelaElement[] seznami;
 
    int size;
    int stSeznamov;
 
    double MEJA_ZASEDENOSTI = 0.8;
 
    public HashSet() {
        stSeznamov = 5;
        size = 0;
 
        seznami = new HashTabelaElement[stSeznamov];
 
        for (int i = 0; i < stSeznamov; i++) {
            seznami[i] = null;
        }
    }
 
    private int indeksSeznama(Stanje s) {
        int hashCode = s.hashCode();
 
        return (hashCode % stSeznamov);
    }
 
    public void add(Stanje s) {
        int indeks = indeksSeznama(s);
 
        HashTabelaElement el = seznami[indeks];

        if (el == null) {
            seznami[indeks] = new HashTabelaElement(s, null);
            size++;
        }
        else if (el.element.equals(s)) {
            // do nothing
        }
        else {
            boolean found = false;
            while (el.next != null) {
                if (el.next.element.equals(s)) {
                    found = true;
                    break;
                }
                el = el.next;
            }
            if (found == false) {
                el.next = new HashTabelaElement(s, null);
                size++;
            }
        }
 
        // System.out.println("Stanje " + s.t + " inserted successfully into " + indeks + "\n");
 
 
        double zasedenost = (1.0 * size) / stSeznamov;
 
        // System.out.println("Current Load factor = " + zasedenost);
 
        if (zasedenost > MEJA_ZASEDENOSTI) {
            // System.out.println(zasedenost + " is greater than " + MEJA_ZASEDENOSTI);
            // System.out.println("Therefore Rehashing will be done.\n");
 
            rehash();
 
            // System.out.println("New Size of Map: " + stSeznamov + "\n");
        }
    }
 
    private void rehash() {
 
        // System.out.println("\n***Rehashing Started***\n");
        
        HashTabelaElement stariSeznami[] = seznami;

        seznami = new HashTabelaElement[2 * stSeznamov];
        
 
        for (int i = 0; i < 2 * stSeznamov; i++) {
            seznami[i] = null;
        }
        
        
        size = 0;
        stSeznamov *= 2;
 
        for (int i = 0; i < stariSeznami.length; i++) {
 
            // head of the chain at that index
            HashTabelaElement el = stariSeznami[i];
 
            while (el != null) {
                add(el.element);
                el = el.next;
            }
        }

        // System.out.println("\n***Rehashing Ended***\n");
    }
 
    public void printHashset() {
        // System.out.println("Current HashMap:");
        
        for (int i = 0; i < seznami.length; i++) {
            // System.out.println("i: " + i);
            HashTabelaElement el = seznami[i];
 
            while (el != null) {
                System.out.print(el.element.t + " ");
 
                el = el.next;
            }
        }
        // System.out.println();
    }
   
      public boolean contains(Stanje s) {
        
        int indeks = indeksSeznama(s);
        
        HashTabelaElement el = seznami[indeks];

        while (el != null){
            if (el.element.equals(s)) {
                return true;
            }
            el = el.next;
        }

        return false;
    }
}
 
public class HashSetTest {
 
    public static void main(String[] args)
    {
 
        // Creating the Map
       HashSet ht = new HashSet();
 
        // Inserting elements
        ht.add(new Stanje(1));
        ht.add(new Stanje(2));
        ht.add(new Stanje(3));
        ht.add(new Stanje(4));
        ht.add(new Stanje(5));
        ht.add(new Stanje(6));
        ht.add(new Stanje(7));

        // System.out.println("S1 : " + ht.size);

        ht.add(new Stanje(1));

        // System.out.println("S1 : " + ht.size);

       
      //Get element from Map
    }
}
