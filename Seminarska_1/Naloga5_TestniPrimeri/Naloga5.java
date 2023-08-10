import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import java.util.Arrays;


class HashTabelaElement {
 
    public Stanje element;
    public HashTabelaElement next;

    public HashTabelaElement(Stanje element, HashTabelaElement next)
    {
        this.element = element;
        next = null;
    }
}

class HashSet {
    public HashTabelaElement[] seznami;
 
    public int size;
    public int stSeznamov;
 
    public double MEJA_ZASEDENOSTI = 0.8;
 
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
            // ne naredi nic
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
 
        double zasedenost = (1.0 * size) / stSeznamov; // seznamov je vec kot elementov
 
        if (zasedenost > MEJA_ZASEDENOSTI) {
            rehash();
        }
    }
 
    private void rehash() {
        HashTabelaElement stariSeznami[] = seznami;

        seznami = new HashTabelaElement[2 * stSeznamov];
        
 
        for (int i = 0; i < 2 * stSeznamov; i++) {
            seznami[i] = null;
        }
        
        
        size = 0;
        stSeznamov *= 2;
 
        for (int i = 0; i < stariSeznami.length; i++) {
 
            HashTabelaElement el = stariSeznami[i];
 
            while (el != null) {
                add(el.element);
                el = el.next;
            }
        }
    }
    
    @Override
    public String toString() {        
        String rez = "{ ";
        for (int i = 0; i < seznami.length; i++) {
            // System.out.println("i: " + i);
            HashTabelaElement el = seznami[i];
 
            while (el != null) {
                rez += (el.element + " ");
 
                el = el.next;
            }
        }
        rez += "}\n";
        return rez;
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

class QueueElement {
    public Stanje element;
    public QueueElement next;

    public QueueElement(Stanje element, QueueElement next) {
        this.element = element;
        this.next = next;
    }
}


class Queue {
    private QueueElement start;
    private QueueElement end;
    private int size;

    public Queue() {
        start = null;
        end = null;
        size = 0;
    }

    public void enqueue(Stanje element) {
        if (start == null) {
            start = new QueueElement(element, null);
            end = start;
        }
        else {
            end.next = new QueueElement(element, null);
            end = end.next;
        }
        size++;
    }

    public void dequeue() {
        if (start != null) {
            start = start.next;
            size--;
        }
    }

    public Stanje first() {
        if (start == null) {
            return null;
        }
        return start.element;
    }

    @Override
    public String toString() {
        QueueElement el = start;

        String s = "{ ";

        while (el != null) {
            s += el.element + " ";
            el = el.next;
        }
        s += "}";
        return s;
    }

    public int size() {
        return size;
    }
}

class PolozajCrke {
    public char crka;
    public int trak;
    public int polozajNaTraku; // 0 - vrh traku

    public PolozajCrke(char crka, int trak, int polozajNaTraku) {
        this.crka = crka;
        this.trak = trak;
        this.polozajNaTraku = polozajNaTraku;
    }

    @Override
    public String toString() {
        return String.format("(%c, %d, %d)", crka, trak, polozajNaTraku);
    }

    @Override
    public int hashCode() {
        return 17 * (int)crka + 31 * trak + 41 * polozajNaTraku;
    }

    public boolean equals(PolozajCrke p) {
        return crka == p.crka && trak == p.trak && polozajNaTraku == p.polozajNaTraku;
    }
}


class Stanje {
    public int N;
    public int P;
    public PolozajCrke crkeNaTrakovih[];
    public int stCrk;
    public int polozajVozicka;
    public int vsebinaVozicka;
    public Stanje prejsnje;
    public int smerOdPrejsnjega;


    public Stanje(int N, int P, int velikostSeznamaCrk) {
        this.N = N;
        this.P = P;

        this.crkeNaTrakovih = new PolozajCrke[velikostSeznamaCrk];
        for (int k = 0; k < crkeNaTrakovih.length; k++) {
            crkeNaTrakovih[k] = new PolozajCrke('0', -1, -1);
        }


        this.polozajVozicka = 0;
        this.vsebinaVozicka = -1; // indeks crke v this.crkeNaTrakovih
        this.stCrk = 0;
        this.prejsnje = null;
        this.smerOdPrejsnjega = -1;
    }

    public int crkaNaIndeksu(int trak, int polozajNaTraku) {
        for (int k = 0; k < crkeNaTrakovih.length; k++) {
            if (crkeNaTrakovih[k].trak == trak && crkeNaTrakovih[k].polozajNaTraku == polozajNaTraku) {
                return k;
            }
        }
        return -1;
    }

    public Stanje levo() {
        Stanje s = copy();
        s.prejsnje = this;
        s.smerOdPrejsnjega = 1;
        if (s.polozajVozicka >= 1) {
            s.polozajVozicka--;
        }
        return s;
    }

    public Stanje desno() {
        Stanje s = copy();
        s.prejsnje = this;
        s.smerOdPrejsnjega = 2;
        if (s.polozajVozicka < N - 1) {
            s.polozajVozicka++;
        }
        return s;
    }

    public Stanje nalozi() {
        Stanje s = copy();
        s.prejsnje = this;
        s.smerOdPrejsnjega = 3;
        int indeksCrke = crkaNaIndeksu(polozajVozicka, P - 1);
        if (s.vsebinaVozicka == -1 && indeksCrke != -1) {
            s.vsebinaVozicka = indeksCrke;
            s.crkeNaTrakovih[indeksCrke].polozajNaTraku = -1;
            s.crkeNaTrakovih[indeksCrke].trak = -1;
        }
        return s;
    }

    public Stanje odlozi() {
        Stanje s = copy();
        s.prejsnje = this;
        s.smerOdPrejsnjega = 4;
        int indeksCrke = crkaNaIndeksu(polozajVozicka, P - 1);
        if (s.vsebinaVozicka != -1 && indeksCrke == -1) {
            s.crkeNaTrakovih[vsebinaVozicka].trak = polozajVozicka;
            s.crkeNaTrakovih[vsebinaVozicka].polozajNaTraku = P - 1;
            s.vsebinaVozicka = -1;
        }
        return s;
    }

    public Stanje gor() {
        Stanje s = copy();
        s.prejsnje = this;
        s.smerOdPrejsnjega = 5;
        if (s.crkaNaIndeksu(polozajVozicka, 0) != -1) {
            s.stCrk--;
        }

        for (int k = 0; k < s.crkeNaTrakovih.length; k++) {
            if (s.crkeNaTrakovih[k].trak == polozajVozicka) {
                if (s.crkeNaTrakovih[k].polozajNaTraku == 0) {
                    s.crkeNaTrakovih[k].trak = -1;
                    s.crkeNaTrakovih[k].polozajNaTraku = -1;
                }
                else {
                    s.crkeNaTrakovih[k].polozajNaTraku--;
                }
            }
        }
        return s;
    }

    public Stanje dol() {
        Stanje s = copy();
        s.prejsnje = this;
        s.smerOdPrejsnjega = 6;
        if (s.crkaNaIndeksu(polozajVozicka, P - 1) != -1) {
            s.stCrk--;
        }
        for (int k = 0; k < s.crkeNaTrakovih.length; k++) {
            if (s.crkeNaTrakovih[k].trak == polozajVozicka) {
                if (s.crkeNaTrakovih[k].polozajNaTraku == P - 1) {
                    s.crkeNaTrakovih[k].trak = -1;
                    s.crkeNaTrakovih[k].polozajNaTraku = -1;
                }
                else {
                    s.crkeNaTrakovih[k].polozajNaTraku++;
                }
            }
        }
        return s;
    }

    public boolean equals(Object s) {
        for (int k = 0; k < crkeNaTrakovih.length; k++) {
            if (!crkeNaTrakovih[k].equals(((Stanje)s).crkeNaTrakovih[k])) {
                return false;
            }
        }
        if (polozajVozicka != ((Stanje)s).polozajVozicka || vsebinaVozicka != ((Stanje)s).vsebinaVozicka) {
            return false;
        }

        return true;
    }

    public boolean trakoviEquals(Stanje s) {
        for (int k = 0; k < crkeNaTrakovih.length; k++) {
            if (!crkeNaTrakovih[k].equals(((Stanje)s).crkeNaTrakovih[k])) {
                return false;
            }
        }
        return true;
    }

    public Stanje copy() {
        Stanje s = new Stanje(N, P, crkeNaTrakovih.length);

        for (int k = 0; k < crkeNaTrakovih.length; k++) {
            s.crkeNaTrakovih[k] = new PolozajCrke(crkeNaTrakovih[k].crka, crkeNaTrakovih[k].trak, crkeNaTrakovih[k].polozajNaTraku);
        }

        s.polozajVozicka = polozajVozicka;
        s.vsebinaVozicka = vsebinaVozicka;
        s.stCrk = stCrk;

        return s;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += Arrays.hashCode(crkeNaTrakovih);
        hash += polozajVozicka;
        hash += vsebinaVozicka;
        //hash += stCrk;

        if (hash < 0) {
            hash *= -1;
        }
        //System.out.println("Stanje ima " + this);
        //System.out.println("HASH: " + hash);
        return hash;
    }

    public StringBuilder pot() {
        StringBuilder sb = new StringBuilder();
        dobiPot(sb);
        return sb;
    }

    private void dobiPot(StringBuilder sb) {
        if (prejsnje == null) {
            return;
        }
        prejsnje.dobiPot(sb);
        String smer = "";
        if (smerOdPrejsnjega == 1) {
            smer = "LEVO";
        }
        else if (smerOdPrejsnjega == 2) {
            smer = "DESNO";
        }
        else if (smerOdPrejsnjega == 3) {
            smer = "NALOZI";
        }
        else if (smerOdPrejsnjega == 4) {
            smer = "ODLOZI";
        }
        else if (smerOdPrejsnjega == 5) {
            smer = "GOR";
        }
        else if (smerOdPrejsnjega == 6) {
            smer = "DOL";
        }
        
        sb.append(smer + "\n");
    }

    @Override
    public String toString() {
        String res = "Stanje: \n";
        
        for (int k = 0; k < crkeNaTrakovih.length; k++) {
            res += (crkeNaTrakovih[k] + ", ");
        }
        res += "\n";
        res += String.format("Vozicek[vsebinaINDEKS: %d, polozaj %d]\n", vsebinaVozicka, polozajVozicka);
        res += String.format("St crk: %d\n", stCrk);

        return res;
    }

    public int indeksCrke(char c) {
        for (int k = 0; k < crkeNaTrakovih.length; k++) {
            if (crkeNaTrakovih[k].crka == c) {
                return k;
            }
        }
        return -1;
    }
}


public class Naloga5 {

    public static Stanje preisciResitve(Stanje zacetno, Stanje koncno) {
        Queue q = new Queue();
        HashSet hs = new HashSet();
        q.enqueue(zacetno);

        //int stKorakov = 0;

        while (q.size() > 0) {
            Stanje s = q.first();
            q.dequeue();

            if (s.trakoviEquals(koncno)) {
                //System.out.println("ZMAGA " + stKorakov);
                return s;
            }

            if (s.stCrk < koncno.stCrk) {
                continue;
            }

            if (hs.contains(s)) {
                //System.out.println("Collusion z " + s);
                continue;
            }

            hs.add(s);

            //stKorakov++;
            if (s.smerOdPrejsnjega != 2) {
                q.enqueue(s.levo());
            }
            if (s.smerOdPrejsnjega != 1) {
                q.enqueue(s.desno());
            }
            q.enqueue(s.gor());
            q.enqueue(s.dol());
            if (s.smerOdPrejsnjega != 4) {
                q.enqueue(s.nalozi());
            }
            if (s.smerOdPrejsnjega != 3) {
                q.enqueue(s.odlozi());
            }
        }
        //System.out.println("ne najdem nic");
        return null;
    }

    // prebere vhod in vrne stevilo zaznanih crk
    public static int preberiVhod(Scanner sc, char rez[][], int N, int P) {
        int stCrk = 0;
        for (int i = 0; i < P; i++) {
            for (int j = 0; j < N; j++) {
                rez[i][j] = '0';
            }
        }

        for (int i = 0; i < N; i++) {
            String trak[] = sc.next().substring(2).split(",");
            for (int j = 0; j < trak.length; j++) {
                if (trak[j].length() > 0) {
                    rez[rez.length - 1 - j][i] = trak[j].charAt(0);
                    stCrk++;
                }
            }
        }
        return stCrk;
    }

    public static void main(String[] args) {        
        File file = new File(args[0]);

        try {
            Scanner sc = new Scanner(file);

            String input[] = sc.next().split(",");
            int N = Integer.parseInt(input[0]);
            int P = Integer.parseInt(input[1]);

            char tempTrakovi[][] = new char[P][N];
            
            int zacetnoStCrk = preberiVhod(sc, tempTrakovi, N, P);
            Stanje zacetno = new Stanje(N, P, zacetnoStCrk);
            zacetno.stCrk = zacetnoStCrk;

            int indeksCrke = 0;
            for (int i = 0; i < P; i++) {
                for (int j = 0; j < N; j++) {
                    if (tempTrakovi[i][j] != '0') {
                        zacetno.crkeNaTrakovih[indeksCrke] = new PolozajCrke(tempTrakovi[i][j], j, i);
                        indeksCrke++;
                    }
                }
            }

            //System.out.println("ZACETNO: " + zacetno);

            int koncnoStCrk = preberiVhod(sc, tempTrakovi, N, P);
            Stanje koncno = new Stanje(N, P, zacetnoStCrk);
            for (int k = 0; k < zacetno.crkeNaTrakovih.length; k++) {
                koncno.crkeNaTrakovih[k] = new PolozajCrke(zacetno.crkeNaTrakovih[k].crka, -1, -1);
            }

            koncno.stCrk = koncnoStCrk;

            for (int i = 0; i < P; i++) {
                for (int j = 0; j < N; j++) {
                    if (tempTrakovi[i][j] != '0') {
                        koncno.crkeNaTrakovih[zacetno.indeksCrke(tempTrakovi[i][j])] = new PolozajCrke(tempTrakovi[i][j], j, i);
                    }
                }
            }


            Stanje resitev = preisciResitve(zacetno, koncno);

            FileWriter writer = new FileWriter(args[1]);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.append(resitev.pot());
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
