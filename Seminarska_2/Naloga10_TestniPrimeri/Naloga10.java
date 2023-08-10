import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.print.event.PrintJobEvent;

import java.util.HashMap;


class Vozlisce {
    public int id;
    public char label;
    public Vozlisce ls; // levi sin
    public Vozlisce db; // desni brat

    public Vozlisce(int id, char label, Vozlisce ls, Vozlisce db) {
        this.id = id;
        this.label = label;
        this.ls = ls;
        this.db = db;
    }

    public int velikostDrevesa() {
        int s = 1;
        if (ls != null) {
            s += ls.velikostDrevesa();
        }
        if (db != null) {
            s += db.velikostDrevesa();
        }
        return s;
    }

    public int stopnja() {
        Vozlisce el = ls;
        int s = 0;
        while (el != null) {
            s += 1;
            el = el.db;
        }
        return s;
    }
}


public class Naloga10 {

    public static boolean seUjemata(Vozlisce T, Vozlisce P) {
        if (T == null) {
            if (P == null) {
                return true;
            }
            return false;
        }
        if (P == null) {
            return true;
        }

        if (T.label == P.label && (P.ls == null || T.stopnja() == P.stopnja())) {
            return seUjemata(T.ls, P.ls) && seUjemata(T.db, P.db);
        }
        return false;
    }

    public static int posiciPonovitve(Vozlisce T, Vozlisce P) {
        if (T == null) {
            return 0;
        }

        int rez = 0;

        if (seUjemata(T, P)) {
            //System.out.printf("Ujemanje (%d %d)\n", T.id, P.id);
            rez = 1;
        }

        return rez + posiciPonovitve(T.ls, P) + posiciPonovitve(T.db, P);
    }

    public static Vozlisce izgradiDrevo(Scanner sc, int size) {
        Vozlisce koren = null;
        HashMap<Integer, Vozlisce> presjsnjaVozlisca = new HashMap<>();

        for (int i = 0; i < size; i++) {
            String input[] = sc.next().split(",");
            int id = Integer.parseInt(input[0]);
            char label = input[1].charAt(0);
            
            Vozlisce v = null;
            if (presjsnjaVozlisca.containsKey(id)) {
                v = presjsnjaVozlisca.get(id);
                v.label = label;
            }
            else {
                v = new Vozlisce(id, label, null, null);
                presjsnjaVozlisca.put(id, v);
            }

            if (input.length > 2) {
                int children[] = StringToIntArray(input, 2);
                v.ls = new Vozlisce(children[0], '_', null, null);
                presjsnjaVozlisca.put(children[0], v.ls);
                
                Vozlisce el = v.ls;
                for (int k = 1; k < children.length; k++) {
                    el.db = new Vozlisce(children[k], '_', null, null);
                    presjsnjaVozlisca.put(children[k], el.db);
                    el = el.db;
                }
            }

            if (koren == null) {
                koren = v;
            }
        }
        return koren;
    }

    public static int[] StringToIntArray(String arr[], int start) {
        int res[] = new int[arr.length - start];
        for (int i = start; i < arr.length; i++) {
            res[i - start] = Integer.parseInt(arr[i]);
        }
        return res;
    }

    public static void main(String[] args) {
        File file = new File(args[0]);

        try {
            Scanner sc = new Scanner(file);

            int N = sc.nextInt();
            Vozlisce P = izgradiDrevo(sc, N);
            int M = sc.nextInt();
            Vozlisce T = izgradiDrevo(sc, M);

            FileWriter writer = new FileWriter(args[1]);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.append(posiciPonovitve(T, P) + "\n");
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
