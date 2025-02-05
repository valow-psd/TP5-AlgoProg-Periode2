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
}

