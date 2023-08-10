import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashSet;


class VozliscePoti {
    public VozliscePoti prejsnje;
    public int vozlisce;

    public VozliscePoti(VozliscePoti prejsnje, int vozlisce) {
        this.prejsnje = prejsnje;
        this.vozlisce = vozlisce;
    }

    @Override
    public boolean equals(Object v) {
        return vozlisce == ((VozliscePoti)v).vozlisce;
    }

    public void izpisiPot() {
        if (prejsnje == null) {
            System.out.print(vozlisce + ", ");
            return;
        }
        prejsnje.izpisiPot();
        System.out.print(vozlisce +  ", ");
    }

    public void pristejNoveMimoidoce(int stevilaMimoidocih[], int mimoidoci) {
        stevilaMimoidocih[vozlisce] += mimoidoci;
        if (prejsnje == null) {
            return;
        }
        prejsnje.pristejNoveMimoidoce(stevilaMimoidocih, mimoidoci);
    }
}


public class Naloga6 {

    public static void prehodiPot(ArrayList<ArrayList<VozliscePoti>> precomputedPaths, ArrayList<ArrayList<Integer>> seznamSosedov,
            int stevilaMimoidocih[], int zacetno, int koncno, int mimoidoci) {
        
        if (!precomputedPaths.get(Math.max(zacetno, koncno)).get(Math.min(zacetno, koncno)).equals(new VozliscePoti(null, -1))) {
            precomputedPaths.get(Math.max(zacetno, koncno)).get(Math.min(zacetno, koncno)).pristejNoveMimoidoce(stevilaMimoidocih, mimoidoci);
            return;
        }
        
        Queue<VozliscePoti> q = new LinkedList<VozliscePoti>();
        q.add(new VozliscePoti(null, zacetno));
        HashSet<Integer> hs = new HashSet<Integer>();
                
        while (q.size() > 0) {
            VozliscePoti v = q.remove();

            if (hs.contains(v.vozlisce)) {
                continue;
            }
            
            precomputedPaths.get(Math.max(zacetno, v.vozlisce)).set(Math.min(zacetno, v.vozlisce), v);
            hs.add(v.vozlisce);

            if (v.vozlisce == koncno) {
                v.pristejNoveMimoidoce(stevilaMimoidocih, mimoidoci);
                break;
            }

            for (Integer a : seznamSosedov.get(v.vozlisce)) {
                q.add(new VozliscePoti(v, a));
            }
        }

    }
    
    public static double[] StringToDoubleArray(String arr[]) {
        double res[] = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = Double.parseDouble(arr[i]);
        }
        return res;
    }

    public static void main(String[] args) {
        File file = new File(args[0]);

        try {
            Scanner sc = new Scanner(file);

            double uspesnostiProdaje[] = StringToDoubleArray(sc.next().split(","));

            String input[] = sc.next().split(",");
            int N = Integer.parseInt(input[0]);
            int M = Integer.parseInt(input[1]);
            int stVozlisc = uspesnostiProdaje.length;


            ArrayList<ArrayList<Integer>> seznamSosedov = new ArrayList<ArrayList<Integer>>(stVozlisc + 1); // indeksi zacnejo z 1, prvo mesto je prazno
            for (int i = 0; i < stVozlisc + 1; i++) {
                seznamSosedov.add(new ArrayList<Integer>());
            }
            for (int i = 0; i < N; i++) {
                String vrstica[] = sc.next().split(",");
                Integer u = Integer.parseInt(vrstica[0]);
                Integer v = Integer.parseInt(vrstica[1]);
                seznamSosedov.get(u).add(v);
                seznamSosedov.get(v).add(u);
            }
            for (ArrayList<Integer> l : seznamSosedov) {
                Collections.sort(l);
                Collections.reverse(l);
            }

            int stevilaMimoidocih[] = new int[stVozlisc + 1];
            
            ArrayList<ArrayList<VozliscePoti>> precomputedPaths = new ArrayList<ArrayList<VozliscePoti>>(stVozlisc + 1);
            for (int i = 0; i < stVozlisc + 1; i++) {
                precomputedPaths.add(new ArrayList<>(i + 1));
                for (int j = 0; j < i + 1; j++) {
                    precomputedPaths.get(i).add(new VozliscePoti(null, -1));
                }
            }
            

            for (int i = 0; i < M; i++) {
                String vrstica[] = sc.next().split(",");
                Integer zacetno = Integer.parseInt(vrstica[0]);
                Integer koncno = Integer.parseInt(vrstica[1]);
                Integer mimoidoci = Integer.parseInt(vrstica[2]);
                prehodiPot(precomputedPaths, seznamSosedov, stevilaMimoidocih, zacetno, koncno, mimoidoci);
            }

            ArrayList<Integer> koncnaStevila = new ArrayList<Integer>(stevilaMimoidocih.length - 1);
            ArrayList<Double> prodanCaj = new ArrayList<Double>(stevilaMimoidocih.length - 1);
            
            for (int i = 1; i < stevilaMimoidocih.length; i++) {
                koncnaStevila.add(stevilaMimoidocih[i]);
                prodanCaj.add(stevilaMimoidocih[i] * uspesnostiProdaje[i - 1]);
            }

            String s = String.format("%d,%d\n", prodanCaj.indexOf(Collections.max(prodanCaj)) + 1,
                koncnaStevila.indexOf(Collections.max(koncnaStevila)) + 1);
            
            FileWriter writer = new FileWriter(args[1]);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.append(s);
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
