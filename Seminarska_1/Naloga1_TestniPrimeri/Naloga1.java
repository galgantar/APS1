import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;



class Polozaj {
    public int startX, startY, endX, endY, dirX, dirY;

    public Polozaj(int startX, int startY, int endX, int endY, int dirX, int dirY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.dirX = dirX;
        this.dirY = dirY;
    }
};


public class Naloga1 {

    public static boolean resi(char tabela[][], char najdene[][], String besede[], Polozaj polozaji[], int indeksTrenutne) {
        if (indeksTrenutne == besede.length) {
            // nasli smo vse besede
            return true;
        }

        for (int y = 0; y < tabela.length; y++) {
            for (int x = 0; x < tabela[0].length; x++) {
                
                if (tabela[y][x] != besede[indeksTrenutne].charAt(0)) {
                    continue;
                }
                // matchaj besedo
                for (int dirY = -1; dirY <= 1; dirY++) {
                    for (int dirX = -1; dirX <= 1; dirX++) {
                        // loh pol zbrises
                        if (dirX == 0 && dirY == 0) {
                            continue;
                        }
        
                        boolean found = true;
                        for (int i = 0; i < besede[indeksTrenutne].length(); ++i) {
                            if (y + i * dirY < 0 || y + i * dirY >= tabela.length ||
                                    x + i * dirX < 0 || x + i * dirX >= tabela[0].length ||
                                    tabela[y + i * dirY][x + i * dirX] != besede[indeksTrenutne].charAt(i) ||
                                    najdene[y + i * dirY][x + i * dirX] != '0') {
                                found = false;
                                break;
                            }
                        }
                        if (found) {
                            Polozaj p = new Polozaj(x, y, x + (besede[indeksTrenutne].length() - 1) * dirX, y + (besede[indeksTrenutne].length() - 1) * dirY, dirX, dirY);
                            
                            for (int j = 0; j < besede[indeksTrenutne].length(); j++) {
                                najdene[p.startY + j * p.dirY][p.startX + j * p.dirX] = besede[indeksTrenutne].charAt(j);
                            }
                            polozaji[indeksTrenutne] = p;
                            //System.out.println("Match je valid za " + besede[i]);
                            if (resi(tabela, najdene, besede, polozaji, indeksTrenutne + 1)) {
                                return true;
                            }
                            polozaji[indeksTrenutne] = new Polozaj(-1, -1, -1, -1, -1, -1);
                            for (int j = 0; j < besede[indeksTrenutne].length(); j++) {
                                najdene[p.startY + j * p.dirY][p.startX + j * p.dirX] = '0';
                            }
                        }
                    }
                }
            }
        }

        // System.out.println("Ne najdem nic");
        return false;
    }

    public static void main(String[] args) {        
        File file = new File(args[0]);

        try {
            Scanner sc = new Scanner(file);

            String sizes[] = sc.next().split(",");
            int V = Integer.parseInt(sizes[0]);
            int S = Integer.parseInt(sizes[1]);
            char tabela[][] = new char[V][S];

            for (int i = 0; i < V; ++i) {
                String vrstica[] = sc.next().split(",");
                for (int j = 0; j < S; ++j) {
                    tabela[i][j] = vrstica[j].charAt(0);
                }
            }

            int stBesed = sc.nextInt();
            String besede[] = new String[stBesed];
            for (int i = 0; i < stBesed; ++i) {
                besede[i] = sc.next();
            }
            sc.close();

            // sort besede
            for (int i = 0; i < besede.length; i++) {
                String max = besede[i];
                int maxId = i;
                for (int j = i + 1; j < besede.length; j++) {
                    if (besede[j].length() > max.length()) {
                        max = besede[j];
                        maxId = j;
                    }
                }
                String temp = besede[i];
                besede[i] = max;
                besede[maxId] = temp;
            }

            
            Polozaj polozaji[] = new Polozaj[stBesed];
            for (int i = 0; i < stBesed; i++) {
                polozaji[i] = new Polozaj(-1, -1, -1, -1, -1, -1);
            }

            char najdene[][] = new char[V][S];
            for (int i = 0; i < V; ++i) {
                for (int j = 0; j < S; ++j) {
                    najdene[i][j] = '0';
                }
            }


            resi(tabela, najdene, besede, polozaji, 0);
            

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < polozaji.length; i++) {
                sb.append(String.format("%s,%d,%d,%d,%d\n", besede[i], polozaji[i].startY, polozaji[i].startX, 
                   polozaji[i].endY, polozaji[i].endX));
            }

            FileWriter writer = new FileWriter(args[1]);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.append(sb);
            bw.close();


            // TESTING
            // System.out.println("TESTING");

            // char testTabela[][] = new char[V][S];
            // for (int i = 0; i < V; ++i) {
            //     for (int j = 0; j < S; ++j) {
            //         testTabela[i][j] = '0';
            //     }
            // }

            // for (int b = 0; b < stBesed; ++b) {
            //     Polozaj p = polozaji[b];
                
            //     int difX = 0;
            //     if (p.endX - p.startX != 0) {
            //         difX = (p.endX - p.startX) / Math.abs(p.endX - p.startX);
            //     }

            //     int difY = 0;
            //     if (p.endY - p.startY != 0) {
            //         difY = (p.endY - p.startY) / Math.abs(p.endY - p.startY);
            //     }

            //     for (int i = 0; i < besede[b].length(); ++i) {
            //         testTabela[p.startY + i * difY][p.startX + i * difX] = besede[b].charAt(i);
            //     }
            // }

            // for (int i = 0; i < testTabela.length; ++i) {
            //     for (int j = 0; j < testTabela[0].length; ++j) {
            //         if (testTabela[i][j] != '0') {
            //             System.out.printf("%c ", najdene[i][j]);
            //         }
            //         else {
            //             System.out.printf("  ");
            //         }
            //     }
            //     System.out.println();
            // }
        
        }
        
        catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
        
        catch (IOException ex) {
            System.out.println("Not allowed to modify file");
        }
    }
}
