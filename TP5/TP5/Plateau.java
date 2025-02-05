import java.util.*;

class Plateau {
    private int[][] grille;
    private int taille;
    private int zeroX, zeroY;

    public Plateau(int[][] initial) {
        this.taille = initial.length;
        this.grille = new int[taille][taille];
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                this.grille[i][j] = initial[i][j];
                if (initial[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                }
            }
        }
    }

    // Affichage du plateau
    public void affichePlateau() {
        for (int[] ligne : grille) {
            for (int val : ligne) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Déplacements des tuiles
    public boolean dHaut() {
        return deplacer(-1, 0);
    }

    public boolean dBas() {
        return deplacer(1, 0);
    }

    public boolean dGauche() {
        return deplacer(0, -1);
    }

    public boolean dDroite() {
        return deplacer(0, 1);
    }

    private boolean deplacer(int dx, int dy) {
        int newX = zeroX + dx;
        int newY = zeroY + dy;
        if (newX >= 0 && newX < taille && newY >= 0 && newY < taille) {
            grille[zeroX][zeroY] = grille[newX][newY];
            grille[newX][newY] = 0;
            zeroX = newX;
            zeroY = newY;
            return true;
        }
        return false;
    }

    // Vérification si le plateau est résolu
    public boolean estResolu(int[][] solution) {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (grille[i][j] != solution[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}

