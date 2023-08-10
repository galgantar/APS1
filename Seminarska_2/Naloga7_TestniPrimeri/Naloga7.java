import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;



class Povezava {
    public int vozlisce;
    public int linija;

    public Povezava(int vozlisce, int linija) {
        this.vozlisce = vozlisce;
        this.linija = linija;
    }

    public boolean equals(Povezava p2) {
        return vozlisce == p2.vozlisce && linija == p2.linija;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", vozlisce, linija);
    }
}


class VozliscePoti {
    public int vozlisce;
    public int linija;
    public VozliscePoti prejsnje;

    public VozliscePoti(int vozlisce, int linija, VozliscePoti prejsnje) {
        this.vozlisce = vozlisce;
        this.linija = linija;
        this.prejsnje = prejsnje;
    }

    @Override
    public boolean equals(Object v2) {
        return vozlisce == ((VozliscePoti)v2).vozlisce && linija == ((VozliscePoti)v2).linija;
    }

    @Override
    public int hashCode() {
        return 31 * vozlisce + 43 * linija;
    }

    public void izpisiPot() {
        if (prejsnje == null) {
            System.out.print(vozlisce + ", ");
            return;
        }
        prejsnje.izpisiPot();
        System.out.print(vozlisce + ", ");
    }

    public int prestejPrestopanja() {
        if (prejsnje == null) {
            return -1; // vstop na prvo linijo stejem kot prestop
        }
        if (prejsnje.linija != linija) {
            return 1 + prejsnje.prestejPrestopanja();
        }
        return prejsnje.prestejPrestopanja();
    }

    public int dolzinaPoti() {
        if (prejsnje == null) {
            return 0;
        }
        return 1 + prejsnje.dolzinaPoti();
    }
}


public class Naloga7 {
    
    public static VozliscePoti najmanjPrestopanj(ArrayList<ArrayList<Povezava>> seznamSosedov, int zacetno, int koncno) {
        Queue<VozliscePoti> prestopi = new LinkedList<>();
        Queue<VozliscePoti> trenutnaLinija = new LinkedList<>();
        int indeksTrenutneLinije = -1;
        HashSet<VozliscePoti> obiskanaVozlisca = new HashSet<>();
        prestopi.add(new VozliscePoti(zacetno, -1, null));

        while (prestopi.size() > 0) {
            VozliscePoti v = prestopi.remove();

            indeksTrenutneLinije = v.linija;
            trenutnaLinija.add(v);

            while (trenutnaLinija.size() > 0) {
                VozliscePoti u = trenutnaLinija.remove();
                if (obiskanaVozlisca.contains(u)) {
                    continue;
                }
                obiskanaVozlisca.add(u);

                if (u.vozlisce == koncno) {
                    return u;
                }

                for (Povezava p : seznamSosedov.get(u.vozlisce)) {
                    if (p.linija == indeksTrenutneLinije) {
                        trenutnaLinija.add(new VozliscePoti(p.vozlisce, indeksTrenutneLinije, u));
                    }
                    else {
                        prestopi.add(new VozliscePoti(p.vozlisce, p.linija, u));
                    }
                }
            }
        }

        return new VozliscePoti(-1, -1, null);
    }


    public static VozliscePoti najkrajsaPot(ArrayList<ArrayList<Povezava>> seznamSosedov, int zacetno, int koncno) {
        Queue<VozliscePoti> q = new LinkedList<>();
        HashSet<Integer> obiskana = new HashSet<>();
        q.add(new VozliscePoti(zacetno, -1, null));

        while (q.size() > 0) {
            VozliscePoti v = q.remove();
            if (obiskana.contains(v.vozlisce)) {
                continue;
            }
            obiskana.add(v.vozlisce);

            if (v.vozlisce == koncno) {
                return v;
            }

            for (Povezava p : seznamSosedov.get(v.vozlisce)) {
                q.add(new VozliscePoti(p.vozlisce, p.linija, v));
            }
        }
        return new VozliscePoti(-1, -1, null);
    }

    public static int[] StringToIntArray(String arr[]) {
        int res[] = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = Integer.parseInt(arr[i]);
        }
        return res;
    }

    public static void main(String[] args) {
        File file = new File(args[0]);

        try {
            Scanner sc = new Scanner(file);

            int N = sc.nextInt();

            ArrayList<ArrayList<Povezava>> seznamSosedov = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                int inputArr[] = StringToIntArray(sc.next().split(","));

                for (int j = 0; j < inputArr.length - 1; j++) {
                    while (inputArr[j] >= seznamSosedov.size()) {
                        seznamSosedov.add(new ArrayList<Povezava>());
                    }
                    //System.out.printf("Linija %d povezuje %d int %d\n", i, inputArr[j], inputArr[j + 1]);
                    seznamSosedov.get(inputArr[j]).add(new Povezava(inputArr[j + 1], i + 1));
                }
            }

            int input[] = StringToIntArray(sc.next().split(","));
            int zacetno = input[0];
            int koncno = input[1];

            //System.out.println(seznamSosedov);
            
            VozliscePoti prestopanja = najmanjPrestopanj(seznamSosedov, zacetno, koncno);
            VozliscePoti najkrajsa = najkrajsaPot(seznamSosedov, zacetno, koncno);

            StringBuilder sb = new StringBuilder();
            
            if (prestopanja.vozlisce != -1) {
                sb.append(prestopanja.prestejPrestopanja() + "\n");
                sb.append(najkrajsa.dolzinaPoti() + "\n");
                if (najkrajsa.dolzinaPoti() == prestopanja.dolzinaPoti()) {
                    sb.append("1\n");
                }
                else {
                    sb.append("0\n");
                }

            }
            else {
                sb.append("-1\n-1\n-1\n");
            }

            //System.out.println(sb);

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
