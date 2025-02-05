import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

class Plateau {
    public int[][] plateauInitial;
    public int[][] plateauActuel;
    public int[][] plateauFinal;
    private List<Integer> seqMin = new ArrayList<>();
    public int x0, y0, size;

    public Plateau(Plateau p) {
        // Copie profonde des tableaux bidimensionnels
        this.plateauInitial = new int[p.plateauInitial.length][];
        for (int i = 0; i < p.plateauInitial.length; i++) {
            this.plateauInitial[i] = new int[p.plateauInitial[i].length];
            System.arraycopy(p.plateauInitial[i], 0, this.plateauInitial[i], 0, p.plateauInitial[i].length);
        }

        this.plateauActuel = new int[p.plateauActuel.length][];
        for (int i = 0; i < p.plateauActuel.length; i++) {
            this.plateauActuel[i] = new int[p.plateauActuel[i].length];
            System.arraycopy(p.plateauActuel[i], 0, this.plateauActuel[i], 0, p.plateauActuel[i].length);
        }

        this.plateauFinal = new int[p.plateauFinal.length][];
        for (int i = 0; i < p.plateauFinal.length; i++) {
            this.plateauFinal[i] = new int[p.plateauFinal[i].length];
            System.arraycopy(p.plateauFinal[i], 0, this.plateauFinal[i], 0, p.plateauFinal[i].length);
        }

        // Copie des types primitifs
        this.x0 = p.x0;
        this.y0 = p.y0;
        this.size = p.size;
    }
    
    

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

    public boolean equals(Plateau p) {
        for (int i = 0; i < this.plateauActuel.length; i++) {
            for (int j = 0; j < this.plateauActuel[i].length; j++) {
                if (this.plateauActuel[i][j] != p.plateauActuel[i][j])
                    return false;
            }
        }
        return true;
    }
    public void copierDepuis(int[][] plateauActuel) {
        this.plateauActuel = plateauActuel;
    }
    public Plateau(int[][] plateau, int x0, int y0, int[][] plateauFinal) {
        this.size = plateau.length;
        this.plateauActuel = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(plateau[i], 0, this.plateauActuel[i], 0, size);
        }
        this.x0 = x0;
        this.y0 = y0;
        this.plateauFinal = plateauFinal;
    }

    public int[][] getPlateauActuel() {
        return plateauActuel;
    }

    public int getSize() {
        return size;
    }

    public void affichePlateau() {
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

    public void dHaut() {
        if (x0 > 0) {
            plateauActuel[x0][y0] = plateauActuel[x0 - 1][y0];
            plateauActuel[x0 - 1][y0] = 0;
            x0--;
        }
    }

    public void dBas() {
        if (this.x0 < this.size - 1) {
            this.plateauActuel[this.x0][this.y0] = this.plateauActuel[this.x0 + 1][this.y0];
            this.plateauActuel[this.x0 + 1][this.y0] = 0;
            this.x0++;
        }
    }

    public void dGauche() {
        if (this.y0 > 0) {
            this.plateauActuel[this.x0][this.y0] = this.plateauActuel[this.x0][this.y0 - 1];
            this.plateauActuel[this.x0][this.y0 - 1] = 0;
            this.y0--;
        }
    }

    public void dDroite() {
        if (this.y0 < this.size - 1) {
            this.plateauActuel[this.x0][this.y0] = this.plateauActuel[this.x0][this.y0 + 1];
            this.plateauActuel[this.x0][this.y0 + 1] = 0;
            this.y0++;
        }
    }

    public boolean peutAllerEnHaut() {
        return x0 > 0;
    }

    public boolean peutAllerEnBas() {
        return x0 < size - 1;
    }

    public boolean peutAllerAGauche() {
        return y0 > 0;
    }

    public boolean peutAllerADroite() {
        return y0 < size - 1;
    }

    public List<Plateau> genererEnfants() {
        List<Plateau> enfants = new ArrayList<>();
        if (peutAllerEnHaut()) enfants.add(copierPlateau(-1, 0));
        if (peutAllerEnBas()) enfants.add(copierPlateau(1, 0));
        if (peutAllerAGauche()) enfants.add(copierPlateau(0, -1));
        if (peutAllerADroite()) enfants.add(copierPlateau(0, 1));
        return enfants;
    }

    private Plateau copierPlateau(int dx, int dy) {
        int[][] nouveauPlateau = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(plateauActuel[i], 0, nouveauPlateau[i], 0, size);
        }
        int newX = x0 + dx;
        int newY = y0 + dy;
        nouveauPlateau[x0][y0] = nouveauPlateau[newX][newY];
        nouveauPlateau[newX][newY] = 0;
        return new Plateau(nouveauPlateau, newX, newY, plateauFinal);
    }

    public int nombreDepossibilites() {
        int res = 0;
        if (peutAllerAGauche()) res++;
        if (peutAllerADroite()) res++;
        if (peutAllerEnBas()) res++;
        if (peutAllerEnHaut()) res++;
        return res;
    }

    public int h() {
        int distance = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int val = plateauActuel[i][j];
                if (val != 0) {
                    int targetX = (val - 1) / size;
                    int targetY = (val - 1) % size;
                    distance += Math.abs(i - targetX) + Math.abs(j - targetY);
                }
            }
        }
        return distance;
    }

    public void lireFichier(String fichier) {
        try {
            File myObj = new File(fichier);
            Scanner myReader = new Scanner(myObj);
            int i = 0;

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine().trim();
                if (data.isEmpty()) continue;

                if (i == 0) {
                    size = Integer.parseInt(data);
                    plateauInitial = new int[size][size];
                    plateauFinal = new int[size][size];
                } else if (i <= size) {
                    String[] str = data.trim().split("\\s+"); // Gestion des espaces multiples
                    if (str.length != size) {
                        throw new IllegalArgumentException("Erreur : ligne incorrecte dans la configuration initiale.");
                    }
                    for (int j = 0; j < size; j++) {
                        plateauInitial[i - 1][j] = Integer.parseInt(str[j]);
                    }
                } else {
                    String[] str = data.trim().split("\\s+"); // Gestion des espaces multiples
                    if (str.length != size) {
                        throw new IllegalArgumentException("Erreur : ligne incorrecte dans la configuration finale.");
                    }
                    for (int j = 0; j < size; j++) {
                        plateauFinal[i - size - 1][j] = Integer.parseInt(str[j]);
                    }
                }
                i++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erreur : fichier non trouvé.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Erreur de format dans le fichier : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        Plateau plateau = new Plateau("Taquin_tests/sp002.txt");

        plateau.affichePlateau();
    }
}
