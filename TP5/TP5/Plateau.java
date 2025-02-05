import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
class Plateau {
    private int[][] plateauInitial;
    private int[][] plateauActuel;
    private int[][] plateauFinal;
    private List<Integer> seqMin = new ArrayList<>();
    private int x0, y0, size;

    public Plateau(int[][] plateauInitial, int[][] plateauFinal) {
        this.plateauInitial = plateauInitial;
        this.plateauActuel = plateauInitial;
        this.plateauFinal = plateauFinal;
        this.size = plateauInitial.length;
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
                    System.out.print("#");
                } else {
                    System.out.print(plateau[i][j] + " ");
                }
            }
            System.out.println("|");
        }
    }

    boolean estResolu() {
        return Arrays.deepEquals(this.plateauActuel, this.plateauFinal);
    }
}

