import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Runtime.Version;
import java.nio.file.FileStore;
import java.util.Scanner;

import java.util.LinkedList;
import java.util.ListIterator;


class QueueElement {
    public Object element;
    public QueueElement next;

    public QueueElement(Object element, QueueElement next) {
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
        end = null; // zadnji pred koncem
        size = 0;
    }

    public void enqueue(Object element) {
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

    public Object first() {
        if (start == null) {
            return null;
        }
        return start.element;
    }

    @Override
    public String toString() {
        QueueElement el = start;

        String s = "{ ";

        //System.out.print("{ ");
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


class Kupec {
    public int id;
    public int dolzinaSeznama;
    public int casNabiranja;

    public int preostaloNabiranje;
    public int preostaloSkeniranje;

    public Kupec(int id, int dolzinaSeznama, int casNabiranja) {
        this.id = id;
        this.dolzinaSeznama = dolzinaSeznama;
        this.casNabiranja = casNabiranja;
        this.preostaloNabiranje = dolzinaSeznama * casNabiranja;
        this.preostaloSkeniranje = -1;
    }
    
    @Override
    public String toString() {
        return String.format("[Id: %d, dolzina: %d, hitrost: %d, OST_N: %d, OST_S: %d]", id, dolzinaSeznama, casNabiranja, preostaloNabiranje, preostaloSkeniranje);
    }
}


public class Naloga4_BACKUP {
    
    public static int[] StringToIntArray(String arr[]) {
        int res[] = new int[arr.length];
        
        for (int i = 0; i < arr.length; ++i) {
            res[i] = Integer.parseInt(arr[i]);
        }

        return res;
    }

    public static int NajkrajsaVrsta(Queue vrste[]) {
        if (vrste.length == 0) {
            return Integer.MAX_VALUE;
        }
        
        int minDolzina = vrste[0].size();

        for (Queue vrsta : vrste) {
            if (vrsta.size() < minDolzina) {
                minDolzina = vrsta.size();
            }
        }

        return minDolzina;
    }
    
    public static void main(String[] args) {        
        File file = new File(args[0]);

        try {
            Scanner sc = new Scanner(file);

            int T = sc.nextInt();

            int casiSkeniranja[] = StringToIntArray(sc.next().split(","));

            int zamikiPrihodov[] = StringToIntArray(sc.next().split(","));

            int dolzineSeznamov[] = StringToIntArray(sc.next().split(","));

            int casNabiranjaIzdelka[] = StringToIntArray(sc.next().split(","));

            int toleranceDoGnece[] = StringToIntArray(sc.next().split(","));

            int stBlagajn = casiSkeniranja.length;
            
            int stVsehKupcev = 0;
            int naslednjaGeneracija = zamikiPrihodov[0];
            int indeksZamika = 0;

            Queue vrstePredBlagajnami[] = new Queue[stBlagajn];
            for (int i = 0; i < stBlagajn; i++) {
                vrstePredBlagajnami[i] = new Queue();
            }

            LinkedList<Kupec> koncaniKupci[] = new LinkedList[stBlagajn];
            for (int i = 0; i < stBlagajn; i++) {
                koncaniKupci[i] = new LinkedList<Kupec>();
            }

            LinkedList<Kupec> odsli = new LinkedList<>();

            LinkedList<Kupec> cakajociKupci = new LinkedList<Kupec>();


            for (int korak = 1; korak <= T; korak++) {
                //System.out.println();
                //System.out.println("KORAK: " + korak);

                // zakljucki nakupov
                for (int i = 0; i < stBlagajn; i++) {
                    // System.out.println("DEBUG " + i + " " + vrstePredBlagajnami[i]);

                    Kupec prvi = (Kupec) vrstePredBlagajnami[i].first();
                    if (prvi == null) {
                        continue;
                    }

                    if (prvi.preostaloSkeniranje == prvi.dolzinaSeznama * casiSkeniranja[i]) {
                        //System.out.printf("Kupec %d začne s skeniranjem izdelkov (blagajna %d).\n", prvi.id, i + 1);
                    }
                    
                    if (prvi.preostaloSkeniranje > 0) {
                        prvi.preostaloSkeniranje -= 1;
                    }

                    if (prvi.preostaloSkeniranje == 0) {
                        //System.out.printf("Kupec %d dokonča skeniranje izdelkov in zapusti trgovino (blagajna %d).\n", prvi.id, i + 1);
                        vrstePredBlagajnami[i].dequeue();
                        koncaniKupci[i].add(prvi);
                    }
                }

                // postavljanje kupcev v vrste
                ListIterator itr = cakajociKupci.listIterator();

                while (itr.hasNext()) {
                    Kupec k = (Kupec) itr.next();
                    
                    k.preostaloNabiranje -= 1;

                    //System.out.println("DEBUG: " + k + " nabiranje " + k.preostaloNabiranje);

                    
                    if (k.preostaloNabiranje == 0) {
                        int minDolzina = NajkrajsaVrsta(vrstePredBlagajnami);
                        for (int i = 0; i < stBlagajn; i++) {
                            if (vrstePredBlagajnami[i].size() == minDolzina) {
                                vrstePredBlagajnami[i].enqueue(k);
                                k.preostaloSkeniranje = k.dolzinaSeznama * casiSkeniranja[i];
                                //System.out.printf("Kupec %d dokonča nabiranje izdelkov in se postavi v čakalno vrsto (blagajna %d).\n", k.id, i + 1);
                                if (vrstePredBlagajnami[i].size() == 1) {
                                    // System.out.printf("Kupec %d začne s skeniranjem izdelkov (blagajna %d).\n", k.id, i + 1);
                                }
                                break;
                            }
                        }

                        itr.remove();
                    }
                }

                // generacija novega cakajocega kupca
                if (korak == naslednjaGeneracija) {
                    //System.out.println("GENERIRAAAAAAAAAM " + korak);


                    stVsehKupcev++;
                    int trenutnaToleranca = toleranceDoGnece[(stVsehKupcev - 1) % toleranceDoGnece.length];
                    
                    Kupec k = new Kupec(stVsehKupcev, 
                        dolzineSeznamov[(stVsehKupcev - 1) % dolzineSeznamov.length],
                        casNabiranjaIzdelka[(stVsehKupcev - 1) % casNabiranjaIzdelka.length]);
                    
                    //System.out.println("Generiranje kupca " + k.id + " " + k);


                    if (trenutnaToleranca < NajkrajsaVrsta(vrstePredBlagajnami)) {
                        //System.out.printf("Kupec %d se premisli\n", k.id);
                        odsli.add(k);
                    }
                    else {
                        cakajociKupci.add(k);
                        //System.out.printf("Kupec %d vstopi in začne z nabiranjem izdelkov.\n", k.id);
                    }
                    
                    indeksZamika++;
                    indeksZamika %= zamikiPrihodov.length;
                    naslednjaGeneracija += zamikiPrihodov[indeksZamika];

                    //System.out.println("TRENUTNIII ZAMIK INDEKS " + indeksZamika);
                }
            }


            // izpis rezultata
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < stBlagajn; i++) {
                if (koncaniKupci[i].size() > 0) {
                    boolean first = true;
                    for (Kupec k : koncaniKupci[i]) {
                        if (!first) {
                            sb.append(",");
                        }
                        sb.append(k.id);
                        first = false;
                    }
                }
                else {
                    sb.append(0);
                }
                sb.append("\n");
            }
            
            if (odsli.size() > 0) {
                boolean first = true;
                for (Kupec k : odsli) {
                    if (!first) {
                        sb.append(",");
                    }
                    sb.append(k.id);
                    first = false;
                }
            }
            else {
                sb.append(0);
            }
            sb.append("\n");

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
