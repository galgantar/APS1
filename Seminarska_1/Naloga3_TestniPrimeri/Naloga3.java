import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;


class SeznamElement {
    public int element;
    public SeznamElement naslednji;

    public SeznamElement(int element, SeznamElement naslednji) {
        this.element = element;
        this.naslednji = naslednji;
    }
}


class Seznam {
    public SeznamElement prvi;

    public Seznam(int array[]) {
        this.prvi = new SeznamElement(-1111111111, null);

        SeznamElement el = prvi;

        for (int i = 0; i < array.length; i++) {
            el.naslednji = new SeznamElement(array[i], null);
            el = el.naslednji;
        }
    }

    public void print() {
        SeznamElement el = prvi;

        System.out.print("{ ");
        while (el.naslednji != null) {
            System.out.print(el.naslednji.element + " ");
            el = el.naslednji;
        }
        System.out.println("}");
    }

    public void dodajSb(StringBuilder sb) {
        SeznamElement el = prvi;

        while (el.naslednji != null) {
            sb.append(el.naslednji.element);
            if (el.naslednji.naslednji != null) {
                sb.append(",");
            }
            el = el.naslednji;
        }
        sb.append("\n");
    }


    void preslikaj(char op, int val) {
        SeznamElement el = prvi;

        while (el.naslednji != null) {
            if (op == '+') {
                el.naslednji.element += val;
            }
            else if (op == '*') {
                el.naslednji.element *= val;
            }
            el = el.naslednji;
        }
    }


    void ohrani(char op, int val) {
        SeznamElement el = prvi;

        while (el.naslednji != null) {
            if ((op == '<' && el.naslednji.element >= val) ||
                (op == '>' && el.naslednji.element <= val) ||
                (op == '=' && el.naslednji.element != val)) {
                el.naslednji = el.naslednji.naslednji;
            }
            else {
                el = el.naslednji;
            }
        }
    }

    void zdruzi(char op) {
        int rezultat = 0;
        if (op == '*') {
            rezultat = 1;
        }

        SeznamElement el = prvi;

        while (el.naslednji != null) {
            if (op == '*') {
                rezultat *= el.naslednji.element;
            }
            else if (op == '+') {
                rezultat += el.naslednji.element;
            }
            
            el = el.naslednji;
        }

        this.prvi.naslednji = new SeznamElement(rezultat, null);
    }
}


public class Naloga3 {
    public static void main(String[] args) {        
        File file = new File(args[0]);

        try {
            Scanner sc = new Scanner(file);

            String input[] = sc.next().split(",");
            int elements[] = new int[input.length];

            for (int i = 0; i < input.length; i++) {
                elements[i] = Integer.parseInt(input[i]);
            }

            Seznam seznam = new Seznam(elements);

            int stOperacij = sc.nextInt();

            StringBuilder sb = new StringBuilder();

            for (int k = 0; k < stOperacij; k++) {
                String[] vrstica = sc.next().split(",");
                
                if (vrstica[0].charAt(0) == 'o') {
                    seznam.ohrani(vrstica[1].charAt(0), Integer.parseInt(vrstica[2]));
                }
                else if (vrstica[0].charAt(0) == 'p') {
                    seznam.preslikaj(vrstica[1].charAt(0), Integer.parseInt(vrstica[2]));
                }
                else if (vrstica[0].charAt(0) == 'z') {
                    seznam.zdruzi(vrstica[1].charAt(0));
                }

                seznam.dodajSb(sb);
            }
            
            //seznam.print();

            FileWriter writer = new FileWriter(args[1]);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.append(sb);
            bw.close();
            sc.close();
        }
        
        catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
        
        catch (IOException ex) {
            System.out.println("Not allowed to modify file");
        }
    }
}
