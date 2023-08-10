import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


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
 
        double zasedenost = (1.0 * size) / stSeznamov;
 
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
 
    public void printHashset() {        
        for (int i = 0; i < seznami.length; i++) {
            // System.out.println("i: " + i);
            HashTabelaElement el = seznami[i];
 
            while (el != null) {
                System.out.print(el.element + " ");
 
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


class Stanje {
    public int N;
    public int P;
    public char trakovi[][];
    public int polozajVozicka;
    public char vsebinaVozicka;
    public int stCrk;
    public Stanje prejsnje;
    public int smerOdPrejsnjega;


    public Stanje(int N, int P) {
        this.N = N;
        this.P = P;

        this.trakovi = new char[P][N];
        for (int i = 0; i < P; i++) {
            for (int j = 0; j < N; j++) {
                trakovi[i][j] = '0';
            }
        }

        this.polozajVozicka = 0;
        this.vsebinaVozicka = '0';
        this.stCrk = 0;
        this.prejsnje = null;
        this.smerOdPrejsnjega = -1;
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
        if (s.vsebinaVozicka == '0' && s.trakovi[trakovi.length - 1][polozajVozicka] != '0') {
            s.vsebinaVozicka = s.trakovi[trakovi.length - 1][polozajVozicka];
            s.trakovi[trakovi.length - 1][polozajVozicka] = '0';
        }
        return s;
    }

    public Stanje odlozi() {
        Stanje s = copy();
        s.prejsnje = this;
        s.smerOdPrejsnjega = 4;
        if (s.vsebinaVozicka != '0' && s.trakovi[trakovi.length - 1][polozajVozicka] == '0') {
            s.trakovi[trakovi.length - 1][polozajVozicka] = s.vsebinaVozicka;
            s.vsebinaVozicka = '0';
        }
        return s;
    }

    public Stanje gor() {
        Stanje s = copy();
        s.prejsnje = this;
        s.smerOdPrejsnjega = 5;
        if (s.trakovi[0][polozajVozicka] != '0') {
            s.stCrk--;
        }

        for (int i = 0; i < P - 1; i++) {
            s.trakovi[i][polozajVozicka] = s.trakovi[i + 1][polozajVozicka];
        }
        s.trakovi[trakovi.length - 1][polozajVozicka] = '0';
        return s;
    }

    public Stanje dol() {
        Stanje s = copy();
        s.prejsnje = this;
        s.smerOdPrejsnjega = 6;
        if (s.trakovi[trakovi.length - 1][polozajVozicka] != '0') {
            s.stCrk--;
        }

        for (int i = 0; i < P - 1; i++) {
            s.trakovi[trakovi.length - 1 - i][polozajVozicka] = s.trakovi[trakovi.length - 2 - i][polozajVozicka];
        }
        s.trakovi[0][polozajVozicka] = '0';
        return s;
    }

    public boolean equals(Object s) {
        //System.out.printf("S1: %d %d %d %d\n", trakovi.length, trakovi[0].length, N, P);
        //System.out.printf("S2: %d %d %d %d\n", ((Stanje)s).trakovi.length, ((Stanje)s).trakovi[0].length, ((Stanje)s).N, ((Stanje)s).P);
        
        for (int i = 0; i < P; i++) {
            for (int j = 0; j < N; j++) {
                if (trakovi[i][j] != ((Stanje)s).trakovi[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Stanje copy() {
        Stanje s = new Stanje(N, P);

        for (int i = 0; i < P; i++) {
            for (int j = 0; j < N; j++) {
                s.trakovi[i][j] = trakovi[i][j];
            }
        }

        s.polozajVozicka = polozajVozicka;
        s.vsebinaVozicka = vsebinaVozicka;
        s.stCrk = stCrk;

        return s;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        int primes[] = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};

        for (int i = 0; i < P; i++) {
            for (int j = 0; j < N; j++) {
                hash += trakovi[i][j] * primes[(i + j) % primes.length];
            }
        }

        hash += polozajVozicka;
        hash += vsebinaVozicka;
        // hash += stCrk * 31;

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
        
        for (int i = 0; i < trakovi[0].length; i++) {
            res += ((i + 1) + " ");
        }
        res += "\n";

        for (int i = 0; i < trakovi[0].length; i++) {
            res += ("- ");
        }
        res += "\n";
        
        for (int i = 0; i < trakovi.length; i++) {
            for (int j = 0; j < trakovi[0].length; j++) {
                if (trakovi[i][j] != '0') {
                    res += trakovi[i][j] + " ";
                }
                else {
                    res += "  ";
                }
            }
            res += "\n";
        }

        for (int i = 0; i < trakovi[0].length; i++) {
            res += ("- ");
        }
        res += "\n";

        for (int i = 0; i < polozajVozicka; i++) {
            res += "  ";
        }
        res += vsebinaVozicka + "\n";

        return res;
    }
}


public class Naloga5_BACKUP {

    public static Stanje preisciResitve(Stanje zacetno, Stanje koncno) {
        Queue q = new Queue();
        HashSet hs = new HashSet();
        q.enqueue(zacetno);

        //int stKorakov = 0;

        while (q.size() > 0) {
            Stanje s = q.first();
            q.dequeue();

            if (s.equals(koncno)) {
                //System.out.println("ZMAGA " + stKorakov);
                //System.out.println(s.pot());
                //System.out.println(s);
                return s;
            }

            if (s.stCrk < koncno.stCrk) {
                continue;
            }

            if (hs.contains(s)) {
                //System.out.println("Collusion");
                continue;
            }

            hs.add(s);

            //stKorakov++;

            q.enqueue(s.levo());
            q.enqueue(s.desno());
            q.enqueue(s.gor());
            q.enqueue(s.dol());
            q.enqueue(s.nalozi());
            q.enqueue(s.odlozi());
        }
        return null;
    }

    public static void main(String[] args) {        
        File file = new File(args[0]);

        try {
            Scanner sc = new Scanner(file);

            String input[] = sc.next().split(",");
            int N = Integer.parseInt(input[0]);
            int P = Integer.parseInt(input[1]);

            Stanje zacetno = new Stanje(N, P);
            for (int i = 0; i < N; i++) {
                String trak[] = sc.next().substring(2).split(",");
                for (int j = 0; j < trak.length; j++) {
                    if (trak[j].length() > 0) {
                        zacetno.trakovi[zacetno.trakovi.length - 1 - j][i] = trak[j].charAt(0);
                        zacetno.stCrk++;
                    }
                }
            }

            Stanje koncno = new Stanje(N, P);
            for (int i = 0; i < N; i++) {
                String trak[] = sc.next().substring(2).split(",");
                for (int j = 0; j < trak.length; j++) {
                    if (trak[j].length() > 0) {
                        koncno.trakovi[koncno.trakovi.length - 1 - j][i] = trak[j].charAt(0);
                        koncno.stCrk++;
                    }
                }
            }

            //System.out.println(zacetno);
            //System.out.println("crke: " + zacetno.desno().gor().dol().desno().nalozi().levo().levo().odlozi().stCrk);

            Stanje resitev = preisciResitve(zacetno, koncno);
            System.out.println(resitev.pot());

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
