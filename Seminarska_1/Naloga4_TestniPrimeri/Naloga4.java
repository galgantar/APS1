import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


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
        end = null;
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

class SpecialListElement {
    public Kupec element;
    public SpecialListElement next;

    public SpecialListElement(Kupec element, SpecialListElement next) {
        this.element = element;
        this.next = next;
    }
}


class SpecialList {
    public SpecialListElement start;
    public SpecialListElement end;
    public int size;

    public SpecialList() {
        start = null;
        end = null;
        size = 0;
    }

    public void add(Kupec element) {
        if (start == null) {
            start = new SpecialListElement(element, null);
            end = start;
        }
        else {
            end.next = new SpecialListElement(element, null);
            end = end.next;
        }
        size++;
    }

    public Object first() {
        if (start == null) {
            return null;
        }
        return start.element;
    }

    // debug purposes
    @Override
    public String toString() {
        SpecialListElement el = start;

        String s = "{ ";

        while (el != null) {
            s += el.element + " ";
            el = el.next;
        }
        s += "}";
        return s;
    }

    public void toStringBuilder(StringBuilder sb) {
        SpecialListElement el = start;

        while (el != null) {
            if (el != start) {
                sb.append(",");
            }
            sb.append(el.element);
            el = el.next;
        }
    }

    public int size() {
        return size;
    }

    public SpecialListElement iterStart() {
        return start;
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

    // @Override
    // public String toString() {
    //     return String.format("(%d, %d)", id, preostaloNabiranje);
    // }
}


public class Naloga4 {
    
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

    public static void izberiNajkrajsoVrsto(Kupec k, Queue[] vrstePredBlagajnami, int[] casiSkeniranja) {
        int minDolzina = NajkrajsaVrsta(vrstePredBlagajnami);
        for (int i = 0; i < vrstePredBlagajnami.length; i++) {
            if (vrstePredBlagajnami[i].size() == minDolzina) {
                vrstePredBlagajnami[i].enqueue(k);
                k.preostaloSkeniranje = k.dolzinaSeznama * casiSkeniranja[i];
                //System.out.printf("Kupec %d dokonča nabiranje izdelkov in se postavi v čakalno vrsto (blagajna %d).\n", k.id, i + 1);
                break;
            }
        }
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

            SpecialList koncaniKupci[] = new SpecialList[stBlagajn];
            for (int i = 0; i < stBlagajn; i++) {
                koncaniKupci[i] = new SpecialList();
            }

            SpecialList odsli = new SpecialList();

            SpecialList cakajociKupci = new SpecialList();
            SpecialList cakajociKupci2 = new SpecialList();


            for (int korak = 1; korak <= T; korak++) {
                //System.out.println();
                //System.out.println("KORAK: " + korak);

                // 1. KORAK: zakljucki nakupov
                for (int i = 0; i < stBlagajn; i++) {
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

                // 2. KORAK: postavljanje kupcev v vrste
                SpecialListElement el = cakajociKupci2.iterStart();
                while (el != null) {
                    el.element.preostaloNabiranje -= 1;
                    el = el.next;
                }
                
                el = cakajociKupci2.iterStart();

                while (el != null && el.element.preostaloNabiranje == 0) {
                    izberiNajkrajsoVrsto(el.element, vrstePredBlagajnami, casiSkeniranja);
                    cakajociKupci2.start = el.next;
                    el = el.next;
                }
                

                while (el != null && el.next != null) {
                    if (el.next.element.preostaloNabiranje == 0) {
                        izberiNajkrajsoVrsto(el.next.element, vrstePredBlagajnami, casiSkeniranja);
                        if (el.next == cakajociKupci2.end) {
                            cakajociKupci2.end = el;
                        }
                        el.next = el.next.next;
                    }
                    else {
                        el = el.next;
                    }                    
                }


                // 3.KORAK: generacija novega cakajocega kupca
                if (korak == naslednjaGeneracija) {
                    stVsehKupcev++;
                    int trenutnaToleranca = toleranceDoGnece[(stVsehKupcev - 1) % toleranceDoGnece.length];
                    
                    Kupec k = new Kupec(stVsehKupcev, 
                        dolzineSeznamov[(stVsehKupcev - 1) % dolzineSeznamov.length],
                        casNabiranjaIzdelka[(stVsehKupcev - 1) % casNabiranjaIzdelka.length]);


                    if (trenutnaToleranca < NajkrajsaVrsta(vrstePredBlagajnami)) {
                        //System.out.printf("Kupec %d se premisli\n", k.id);
                        odsli.add(k);
                    }
                    else {
                        cakajociKupci.add(k);
                        cakajociKupci2.add(k);
                        //System.out.printf("Kupec %d vstopi in začne z nabiranjem izdelkov.\n", k.id);
                    }
                    
                    indeksZamika++;
                    indeksZamika %= zamikiPrihodov.length;
                    naslednjaGeneracija += zamikiPrihodov[indeksZamika];
                }
            }


            // izpis rezultata
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < stBlagajn; i++) {
                if (koncaniKupci[i].size() > 0) {
                    boolean first = true;
                    SpecialListElement el = koncaniKupci[i].iterStart();
                    while (el != null) {
                        if (!first) {
                            sb.append(",");
                        }
                        sb.append(el.element.id);
                        first = false;
                        el = el.next;
                    }
                }
                else {
                    sb.append(0);
                }
                sb.append("\n");
            }
            
            if (odsli.size() > 0) {
                boolean first = true;
                SpecialListElement el = odsli.iterStart();
                while (el != null) {
                    if (!first) {
                        sb.append(",");
                    }
                    sb.append(el.element.id);
                    first = false;
                    el = el.next;
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
