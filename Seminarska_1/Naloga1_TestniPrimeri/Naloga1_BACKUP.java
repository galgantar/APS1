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

    public boolean isValid() {
        return (
            this.startX != -1 &&
            this.startY != -1 &&
            this.endX != -1 &&
            this.endY != -1
        );
    }
};


public class Naloga1_BACKUP {
    
    public static Polozaj match(char tabela[][], char najdene[][], String beseda, int x, int y) {
        // vodoravno
        boolean found = true;
        for (int i = 0; i < beseda.length(); i++) {
            if (x + i >= tabela[0].length ||
                tabela[y][x + i] != beseda.charAt(i) || najdene[y][x + i] != '0') {
                found = false;
                break;
            }
        }
        if (found) {
            //System.out.println("Nasel vodoravni match za " + beseda + " x: " + x + " y: " + y);
            return new Polozaj(x, y, x + beseda.length() - 1, y, 1, 0);
        }
        
        // navpicno
        found = true;
        for (int i = 0; i < beseda.length(); i++) {
            if (y + i >= tabela.length ||
                tabela[y + i][x] != beseda.charAt(i) || najdene[y + i][x] != '0') {
                found = false;
                break;
            }
        }
        if (found) {
            //System.out.println("Nasel navpicni match za " + beseda + " x: " + x + " y: " + y);
            return new Polozaj(x, y, x, y + beseda.length() - 1, 0, 1);
        }

        // diagonalno
        found = true;
        for (int i = 0; i < beseda.length(); ++i) {
            if (x + i >= tabela[0].length || y + i >= tabela.length ||
                tabela[y + i][x + i] != beseda.charAt(i) || najdene[y + i][x + i] != '0') {
                found = false;
                break;
            }
        }
        if (found) {
            //System.out.println("Nasel diagonalni match za " + beseda + " x: " + x + " y: " + y);
            return new Polozaj(x, y, x + beseda.length() - 1, y + beseda.length() - 1, 1, 1);
        }

        // diagonalno levo-dol
        found = true;
        for (int i = 0; i < beseda.length(); ++i) {
            if (x - i < 0 || y + i >= tabela.length ||
                tabela[y + i][x - i] != beseda.charAt(i) || najdene[y + i][x - i] != '0') {
                found = false;
                break;
            }
        }
        if (found) {
            //System.out.println("Nasel levo-dol-diagonalni match za " + beseda + " x: " + x + " y: " + y);
            return new Polozaj(x, y, x - beseda.length() + 1, y + beseda.length() - 1, -1, 1);
        }

        return new Polozaj(-1, -1, -1, -1, -1, -1);
    }

    public static boolean resi(char tabela[][], char najdene[][], String besede[], int uporabljeneBesede[], Polozaj polozaji[], int stNajdenih, int x, int y) {
        // if (stNajdenih > 33) {
        //     System.out.println("Resujem stN: " + stNajdenih + " (x: " + x + " y: " + y + ")");

        //     for (int i = 0; i < tabela.length; ++i) {
        //         for (int j = 0; j < tabela[0].length; ++j) {
        //             if (najdene[i][j] != '0') {
        //                 System.out.printf("%c ", najdene[i][j]);
        //             }
        //             else {
        //                 System.out.printf("  ");
        //             }
        //         }
        //         System.out.println();
        //     }

        //     for (int i = 0; i < besede.length; ++i) {
        //         if (uporabljeneBesede[i] == 0) {
        //             System.out.print(besede[i] + " ");
        //         }
        //     }
        //     System.out.println();
        // }

        if (stNajdenih == besede.length) {
            //System.out.println("KONEC\n");
            return true;
        }

        if (x == tabela[0].length || y == tabela.length) {
            //System.out.println("NAPAKA\n");
            return false;
        }

        int newX = x + 1;
        int newY = y;
        if (newX == tabela[0].length) {
            newY += 1;
            newX = 0;
        }
        
        if (najdene[y][x] != '0') {
            return resi(tabela, najdene, besede, uporabljeneBesede, polozaji, stNajdenih, newX, newY);
        }

        for (int i = 0; i < uporabljeneBesede.length; ++i) {
            if (uporabljeneBesede[i] == 0) {
                //System.out.println("Cekiram " + besede[i]);
                Polozaj p = match(tabela, najdene, besede[i], x, y);
                Polozaj p2 = match(tabela, najdene, new StringBuilder(besede[i]).reverse().toString(), x, y);
                if (!p.isValid()) {
                    // dirX, dirY zdej predstavljata smer iz trenutne tocke, ne (startX, startY)
                    p = new Polozaj(p2.endX, p2.endY, p2.startX, p2.startY, -p2.dirX, -p2.dirY);
                }

                if (p.isValid()) {
                    for (int j = 0; j < besede[i].length(); j++) {
                        najdene[p.startY + j * p.dirY][p.startX + j * p.dirX] = besede[i].charAt(j);
                    }
                    uporabljeneBesede[i] = 1;
                    polozaji[i] = p;
                    //System.out.println("Match je valid za " + besede[i]);
                    if (resi(tabela, najdene, besede, uporabljeneBesede, polozaji, stNajdenih + 1, newX, newY)) {
                        return true;
                    }
                    uporabljeneBesede[i] = 0;
                    polozaji[i] = new Polozaj(-1, -1, -1, -1, -1, -1);
                    for (int j = 0; j < besede[i].length(); j++) {
                        najdene[p.startY + j * p.dirY][p.startX + j * p.dirX] = '0';
                    }
                }
            }
        }

        //System.out.println("Ne najdem nic");
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

            // System.out.println("Besede sorted : ");
            // for (String beseda : besede) {
            //     System.out.print(beseda + " ");
            // }
            // System.out.println();

            sc.close();
            
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

            int uporabljeneBesede[] = new int[stBesed];

            resi(tabela, najdene, besede, uporabljeneBesede, polozaji, 0, 0, 0);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < polozaji.length; i++) {
                sb.append(String.format("%s,%d,%d,%d,%d\n", besede[i], polozaji[i].startY, polozaji[i].startX, 
                   polozaji[i].endY, polozaji[i].endX));
            }

            //System.out.println(sb);

            FileWriter writer = new FileWriter(args[1]);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.append(sb);
            bw.close();


            // TESTING
            // char testTabela[][] = new char[V][S];
            // for (int i = 0; i < V; ++i) {
            //     for (int j = 0; j < S; ++j) {
            //         testTabela[i][j] = '0';
            //     }
            // }

            // for (int b = 0; b < stBesed; ++b) {
            //     for (int i = 0; i < besede[b].length(); ++i) {
            //         Polozaj p = polozaji[b];
            //         testTabela[p.startY + i * p.dirY][p.startX + i * p.dirX] = besede[b].charAt(i);
            //     }
            // }

        //     System.out.println("TESTING");
        //     for (int i = 0; i < testTabela.length; ++i) {
        //         for (int j = 0; j < testTabela[0].length; ++j) {
        //             if (testTabela[i][j] != '0') {
        //                 System.out.printf("%c ", najdene[i][j]);
        //             }
        //             else {
        //                 System.out.printf("  ");
        //             }
        //         }
        //         System.out.println();
        //     }
        }
        
        catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
        
        catch (IOException ex) {
            System.out.println("Not allowed to modify file");
        }
    }
}
