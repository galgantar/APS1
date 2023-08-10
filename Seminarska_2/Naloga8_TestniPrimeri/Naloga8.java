import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;


public class Naloga8 {

    public static boolean vozlisciNepovezani(ArrayList<ArrayList<Integer>> seznamSosedov, int u, int v) {
        Queue<Integer> q = new LinkedList<>();
        HashSet<Integer> dosezeni = new HashSet<>();
        q.add(u);

        while (q.size() > 0) {
            int w = q.remove();
            
            if (dosezeni.contains(w)) {
                continue;
            }
            dosezeni.add(w);

            if (w == v) {
                return false;
            }

            for (Integer sosed : seznamSosedov.get(w)) {
                q.add(sosed);
            }
        }
        return true;
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

            int P = sc.nextInt();

            ArrayList<ArrayList<Integer>> seznamSosedov = new ArrayList<>();

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < P; i++) {
                int input[] = StringToIntArray(sc.next().split(","));
                int u = input[0];
                int v = input[1];

                while (seznamSosedov.size() < Math.max(u, v) + 1) {
                    seznamSosedov.add(new ArrayList<>());
                }

                if (vozlisciNepovezani(seznamSosedov, u, v)) {
                    seznamSosedov.get(u).add(v);
                    seznamSosedov.get(v).add(u);
                }
                else {
                    sb.append(String.format("%d,%d\n", u, v));
                }
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
