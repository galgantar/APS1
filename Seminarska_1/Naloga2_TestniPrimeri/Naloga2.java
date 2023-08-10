import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;



public class Naloga2 {

    public static void izpisi(int arr[]) {
        for (int j = 0; j < arr.length; j++) {
            if (arr[j] != -1) {
                System.out.print(arr[j]);
            }
            else {
                System.out.print("_");
            }
        }
        System.out.println();
    }

    public static void izpisiSb(int arr[], StringBuilder sb) {
        for (int j = 0; j < arr.length; j++) {
            sb.append(arr[j]);
        }
        sb.append("\n");
    }

    public static void main(String[] args) {        
        File file = new File(args[0]);

        try {
            Scanner sc = new Scanner(file);

            int N = sc.nextInt();
            String stevilo = sc.next();

            int stevke[] = new int[stevilo.length()];
            for (int i = 0; i < stevilo.length(); i++) {
                stevke[i] = stevilo.charAt(i) - '0';
            }
            int temp[] = new int[stevilo.length()];
            
            for (int i = 0; i < stevke.length; i++) {
                temp[i] = -1;
            }
            

            int velikostV2 = -1;
            int corLen = stevke.length - (stevke.length % 6);
            
            if (stevke.length % 6 == 0) {
                velikostV2 = corLen / 2;
            }
            else if (stevke.length % 6 == 1) {
                velikostV2 = corLen / 2;
            }
            else if (stevke.length % 6 == 2) {
                velikostV2 = corLen / 2;
            }
            else if (stevke.length % 6 == 3) {
                velikostV2 = corLen / 2;
            }
            else if (stevke.length % 6 == 4) {
                velikostV2 = corLen / 2 + 1;
            }
            else if (stevke.length % 6 == 5) {
                velikostV2 = corLen / 2 + 2;
            }

            for (int korak = 0; korak < N; ++korak) {
                
                for (int k = 0; k < stevke.length; ++k) {
                    temp[k] = -1;
                }
                
                int stTrenutno = 0;
                int ti = stevke.length - 4;
                for (int j = 0; j < velikostV2; j++) {
                    temp[ti] = stevke[j * 2 + 1];
                    stevke[j * 2 + 1] = -1;
                    ti--;
                    stTrenutno++;
                    if (stTrenutno == 3) {
                        stTrenutno = 0;
                        ti -= 3;
                    }
                }
                
                ti = 0;
                while (temp[ti] != -1) {
                    ti++;
                }

                for (int j = 0; j < stevke.length; ++j) {
                    if (stevke[j] != -1) {
                        temp[ti] = stevke[j];
                        while (ti != stevke.length && temp[ti] != -1) {
                            ti++;
                        }
                    }
                }
                
                for (int k = 0; k < stevke.length; k++) {
                    stevke[k] = -1;
                }
                
                for (int k = 0; k < stevke.length; k += 2) {
                    stevke[k] = temp[k];
                }
                
                int corr = 0;
                if (stevke.length % 2 == 0) {
                    corr = 0;
                }
                else {
                    corr = -1;
                }

                for (int k = 1; k < stevke.length; k += 2) {
                    stevke[stevke.length + corr - k] = temp[k];
                }

                //System.out.print("Korak : ");
                //izpisi(stevke);
            }
            
            StringBuilder sb = new StringBuilder();

            izpisiSb(stevke, sb);

            // System.out.print(sb);
            
            // System.out.println("Koncni rez: ");
            // izpisi(stevke);

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
