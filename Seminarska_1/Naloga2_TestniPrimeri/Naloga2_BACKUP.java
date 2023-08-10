import java.io.File;
import java.io.FileNotFoundException;
import java.beans.BeanProperty;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;



public class Naloga2_BACKUP {

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

    public static void main(String[] args) {        
        File file = new File(args[0]);

        try {
            Scanner sc = new Scanner(file);

            int N = sc.nextInt();
            String stevilo = sc.next();
            //String stevilo = "148536729";

            int stevke[] = new int[stevilo.length()];
            for (int i = 0; i < stevilo.length(); i++) {
                stevke[i] = stevilo.charAt(i) - '0';
            }
            int temp[] = new int[stevilo.length()];
            
            // delete later
            for (int i = 0; i < stevke.length; i++) {
                temp[i] = -1;
            }
            
            int brezPara = -1; // kok iz vecje mnozice jih bo na konc brez para
            int zadnjaTrojicaV2 = -1;
            if (stevke.length % 6 == 0) {
                brezPara = 0;
                zadnjaTrojicaV2 = 0;
            }
            else if (stevke.length % 6 == 1) {
                brezPara = 1;
                zadnjaTrojicaV2 = 0;
            }
            else if (stevke.length % 6 == 2) {
                brezPara = 2;
                zadnjaTrojicaV2 = 0;
            }
            else if (stevke.length % 6 == 3) {
                brezPara = 3;
                zadnjaTrojicaV2 = 0;
            }
            else if (stevke.length % 6 == 4) {
                brezPara = 2;
                zadnjaTrojicaV2 = 2; // manjka se 1
            }
            else if (stevke.length % 6 == 5) {
                brezPara = 1;
                zadnjaTrojicaV2 = 1; // manjkata se 2
            }

            System.out.println("Brez para : " + brezPara);

            
            
            for (int korak = 0; korak < N; ++korak) {
                int stTrenutno = 0;
                int i = 0;
                int ti = 0;

                while (i < stevke.length - (stevke.length % 6)) {
                    //System.out.printf("i: %d = %d, ti: %d, stTrenutno: %d\n", i, stevke[i], ti, stTrenutno);
                    
                    temp[ti] = stevke[i];
                    if (i < 7 && korak < 1) {
                        //System.out.println("Normal line i: " + i + " ti: " + ti + " el: " + stevke[i]);
                    }
                    i += 2;
                    ti += 1;
                    stTrenutno++;

                    if (stTrenutno == 3) {
                        ti += 3;
                        stTrenutno = 0;
                    }
                
                }

                // for (int k = 0; k < brezPara; k++) {
                //     temp[temp.length - 1 - k] = stevke[stevke.length - 1 - k];
                // }

                if (korak < 1) {
                    System.out.print("Sodi indeksi : ");
                    izpisi(temp);
                }
                
                // testing
                // int lihi[] = new int[temp.length];
                // for (int k = 0; k < lihi.length; k++) {
                //     lihi[k] = -1;
                // }

                i = 1;
                ti = stevke.length - (stevke.length % 6)- 1;
                stTrenutno = 0;

                if (korak < 1) {
                    //System.out.println("Init cond i : " + i + " ti : " + ti + " zadnja : " + zadnjaTrojicaV2);
                }

                int lihi[] = new int[stevke.length];
                for (int k = 0; k < lihi.length; ++k) {
                    lihi[k] = -1;
                }

                
                while (i < stevke.length - (stevke.length % 6)) {
                    
                    if (korak < 1) {
                        //System.out.printf("i: %d = %d, ti: %d, stTrenutno: %d\n", i, stevke[i], ti, stTrenutno);
                    }
                    
                    lihi[ti] = stevke[i];
                    i += 2;
                    ti -= 1;
                    stTrenutno++;
                
                    if (stTrenutno == 3) {
                        ti -= 3;
                        stTrenutno = 0;
                    }
                }

                if (korak < 1) {
                    System.out.print("Lihi indeksi : ");
                    izpisi(lihi);
                }
                
                // correction
                int corLen = stevke.length - (stevke.length % 6);
                
                if (stevke.length % 6 == 0) {
                    // brezPara = 0;
                    // zadnjaTrojicaV2 = 0;
                }
                else if (stevke.length % 6 == 1) {
                    // brezPara = 1;
                    // zadnjaTrojicaV2 = 0;
                    temp[corLen + 0] = stevke[corLen + 0];
                }
                else if (stevke.length % 6 == 2) {
                    temp[corLen + 0] = stevke[corLen + 0];
                    temp[corLen + 1] = stevke[corLen + 1];
                }
                else if (stevke.length % 6 == 3) {
                    temp[corLen + 0] = stevke[corLen + 0];
                    temp[corLen + 1] = stevke[corLen + 1];
                    temp[corLen + 2] = stevke[corLen + 2];
                }
                else if (stevke.length % 6 == 4) {
                    temp[corLen + 0] = stevke[corLen + 0];
                    temp[corLen + 1] = stevke[corLen + 2];
                    temp[corLen + 2] = stevke[corLen + 3];
                    temp[corLen + 3] = stevke[corLen + 1];

                }
                else if (stevke.length % 6 == 5) {
                    temp[corLen + 0] = stevke[corLen + 0];
                    temp[corLen + 1] = stevke[corLen + 2];
                    temp[corLen + 2] = stevke[corLen + 4];
                    temp[corLen + 3] = stevke[corLen + 1];
                    temp[corLen + 4] = stevke[corLen + 3];
                }

                // drugi in prvi korak

                for (int k = 0; k < stevke.length; k++) {
                    stevke[k] = -1;
                }
                
                for (int k = 0; k < stevke.length; k += 2) {
                    stevke[k] = temp[k];
                }
                
                int corr = 999999999;
                if (stevke.length % 2 == 0) {
                    corr = 0;
                }
                else {
                    corr = -1;
                }

                for (int k = 1; k < stevke.length; k += 2) {
                    stevke[stevke.length + corr - k] = temp[k];
                }

                if (korak < 1) {
                    System.out.print("Vmesni : ");
                    izpisi(stevke);
                }
                
            }
            System.out.println("Koncni rez: ");
            izpisi(stevke);
        }
        
        catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
        
        // catch (IOException ex) {
        //     System.out.println("Not allowed to modify file");
        // }
    }
}
