import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

class Plateau {
    private int[][] plateauInitial;
    private int[][] plateauActuel;
    private int[][] plateauFinal;
    private List<Integer> seqMin = new ArrayList<>();
    private int x0, y0, size;

    public Plateau(String fichier) {
        lireFichier(fichier);
        this.plateauActuel = plateauInitial;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (plateauInitial[i][j] == 0) {
                    x0 = i;
                    y0 = j;
                }
            }
        }
    }

    private void affichePlateau() {
        int[][] plateau = plateauActuel;
        for (int i = 0; i < size; i++) {
            System.out.print("|");
            for (int j = 0; j < size; j++) {
                if (plateau[i][j] == 0) {
                    System.out.print("# ");
                } else {
                    System.out.print(plateau[i][j] + " ");
                }
            }
            System.out.println("|");
        }
    }

    boolean estResolu() {
        for (int i = 0; i < this.plateauActuel.length; i++) {
            for (int j = 0; j < this.plateauActuel[i].length; j++) {
                if (this.plateauActuel[i][j] != this.plateauFinal[i][j])
                    return false; 
            }
        }
        return true;

    }


    // Lire fichier dans ./Taquin_tests
    public void lireFichier(String fichier) {
        try {
            File myObj = new File(fichier);
            Scanner myReader = new Scanner(myObj);
            int i = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine().trim();
                if (data.isEmpty()) continue; // Ignorer les lignes vides

                if (i == 0) {
                    size = Integer.parseInt(data);
                    plateauInitial = new int[size][size];
                    plateauFinal = new int[size][size];
                } else if (i <= size) {
                    String[] str = data.split(" ");
                    if (str.length != size) {
                        System.out.println("Erreur : ligne incorrecte dans la configuration initiale.");
                        continue;
                    }
                    for (int j = 0; j < size; j++) {
                        plateauInitial[i - 1][j] = Integer.parseInt(str[j]);
                    }
                } else {
                    String[] str = data.split(" ");
                    if (str.length != size) {
                        System.out.println("Erreur : ligne incorrecte dans la configuration finale.");
                        continue;
                    }
                    for (int j = 0; j < size; j++) {
                        plateauFinal[i - size - 1][j] = Integer.parseInt(str[j]);
                    }
                }
                i++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Erreur de format dans le fichier : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Plateau plateau = new Plateau("Taquin_tests/sp000.txt");
        plateau.affichePlateau();
    }
}
