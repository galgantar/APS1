import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import java.util.Collections;
import java.util.Arrays;


class Vozlisce {
    public int max;
    public int min;
    public Vozlisce zl;
    public Vozlisce zd;
    public Vozlisce sl;
    public Vozlisce sd;

    public Vozlisce(int min, int max, Vozlisce zl, Vozlisce zd, Vozlisce sl, Vozlisce sd) {
        this.min = min;
        this.max = max;
        this.zl = zl;
        this.zd = zd;
        this.sl = sl;
        this.sd = sd;
    }
}


class Rezultat {
    public int potopljeni;
    public int obiskani;

    public Rezultat(int potopljeni, int obiskani) {
        this.potopljeni = potopljeni;
        this.obiskani = obiskani;
    }

    public void pristej(Rezultat r2) {
        potopljeni += r2.potopljeni;
        obiskani += r2.obiskani;
    }
}


public class Naloga9 {    
    
    public static Vozlisce izgradiDrevo(int polje[][], int dimenzija, int x, int y) {
        if (dimenzija == 1) {
            return new Vozlisce(polje[y][x], polje[y][x], null, null, null, null);
        }

        Vozlisce zl = izgradiDrevo(polje, dimenzija / 2, x, y);
        Vozlisce zd = izgradiDrevo(polje, dimenzija / 2, x + dimenzija / 2, y);
        Vozlisce sl = izgradiDrevo(polje, dimenzija / 2, x, y + dimenzija / 2);
        Vozlisce sd = izgradiDrevo(polje, dimenzija / 2, x + dimenzija / 2, y + dimenzija / 2);

        int max = Collections.max(Arrays.asList(zl.max, zd.max, sl.max, sd.max));
        int min = Collections.min(Arrays.asList(zl.min, zd.min, sl.min, sd.min));

        if (max != min) {
            return new Vozlisce(min, max, zl, zd, sl, sd);
        }
        return new Vozlisce(min, max, null, null, null, null);
    }

    public static Rezultat prestejPotopljene(Vozlisce drevo, int dimenzija, int gladina) {
        if (drevo.max <= gladina) {
            return new Rezultat(dimenzija * dimenzija, 1);
        }
        if (drevo.min > gladina) {
            return new Rezultat(0, 1);
        }

        Rezultat r = new Rezultat(0, 1);

        r.pristej(prestejPotopljene(drevo.zl, dimenzija / 2, gladina));
        r.pristej(prestejPotopljene(drevo.zd, dimenzija / 2, gladina));
        r.pristej(prestejPotopljene(drevo.sl, dimenzija / 2, gladina));
        r.pristej(prestejPotopljene(drevo.sd, dimenzija / 2, gladina));

        return r;
    }
    
    public static void StringToIntArrayCopy(String arr[], int[] dest) {
        for (int i = 0; i < arr.length; i++) {
            dest[i] = Integer.parseInt(arr[i]);
        }
    }

    public static void main(String[] args) {
        File file = new File(args[0]);

        try {
            Scanner sc = new Scanner(file);

            int A = sc.nextInt();

            int polje[][] = new int[A][A];

            for (int i = 0; i < A; i++) {
                StringToIntArrayCopy(sc.next().split(","), polje[i]);
            }
            
            // for (int i = 0; i < A; i++) {
            //     for (int j = 0; j < A; j++) {
            //         System.out.print(polje[i][j] + ",");
            //     }
            //     System.out.println();
            // }


            Vozlisce drevo = izgradiDrevo(polje, A, 0, 0);

            int B = sc.nextInt();

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < B; i++) {
                int gladina = sc.nextInt();

                Rezultat r = prestejPotopljene(drevo, A, gladina);
                sb.append(String.format("%d,%d\n", r.potopljeni, r.obiskani));
            }


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
