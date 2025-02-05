import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// Solveur du jeu du Taquin
class TaquinSolveur {

    public int[] SolRec(Plateau p) {// Renvoie un tableau de 2 entiers, le premier est le nombre de mouvements, le deuxième est le nombre de noeuds explorés
        if (p.estResolu()) {
            p.affichePlateau();
            int[] tab = { 0, 0 };
            return tab;
        }
        int noueds_a_par = p.nombreDepossibilites();// Nombre de noeuds explorés (a changer)
        if (p.peutAllerADroite()) {
            p.dDroite();
            int[] tab = { SolRec(p)[0] + 1, SolRec(p)[1] + noueds_a_par };
            return tab;
        }
        if (p.peutAllerEnBas()) {
            p.dBas();
            int[] tab = { SolRec(p)[0] + 1, SolRec(p)[1] + noueds_a_par };
            return tab;
        }

        if (p.peutAllerAGauche()) {
            p.dGauche();
            int[] tab = { SolRec(p)[0] + 1, SolRec(p)[1] + noueds_a_par };
            return tab;
        }
        if (p.peutAllerEnHaut()) {
            p.dHaut();
            int[] tab = { SolRec(p)[0] + 1, SolRec(p)[1] + noueds_a_par };
            return tab;
        }
        int[] tab = { 0, 0 };
        return tab;

    }
    public void solSE(Plateau p) {
        long debut = System.currentTimeMillis();
        Tas tas = new Tas();
        tas.ajouter(p);

        int noeudsExplores = 0;

        while (!tas.estVide()) {
            Plateau courant = tas.extraire();
            noeudsExplores++;

            if (courant.estResolu()) {
                long fin = System.currentTimeMillis();
                long tempsCalcul = fin - debut;
                ecrireResultat(tempsCalcul, noeudsExplores, courant);
                return;
            }

            List<Plateau> enfants = courant.genererEnfants();
            for (Plateau enfant : enfants) {
                tas.ajouter(enfant);
            }
        }
    }

    // Méthode pour écrire les résultats dans un fichier
    private void ecrireResultat(long tempsCalcul, int noeudsExplores, Plateau p) {
        try (FileWriter writer = new FileWriter("resultat.txt")) {
            writer.write("Résultat trouvé en " + tempsCalcul + " ms\n");
            writer.write("Nombre de noeuds explorés : " + noeudsExplores + "\n");
            writer.write("Configuration finale :\n");
            int[][] plateau = p.getPlateauActuel();
            for (int[] ligne : plateau) {
                for (int val : ligne) {
                    writer.write(val + " ");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture du fichier de résultat.");
            e.printStackTrace();
        }
    }

    private static void fauxChargement(String texte) {
        String baseMessage = texte;
        for (int i = 0; i < 5; i++) { // 5 secondes environ
            try {
                System.out.print("\r" + baseMessage);
                for (int j = 0; j <= (i % 3); j++) {
                    System.out.print(".");
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Le chargement a été interrompu.");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        fauxChargement("Chargement en cours");
        String cheminFichier = "Taquin_tests/sp000.txt";

        Plateau plateau = new Plateau(cheminFichier);
        System.out.println("Plateau initial :");
        plateau.affichePlateau();

        TaquinSolveur solveur = new TaquinSolveur();
        fauxChargement("Résolution en cours");
        solveur.solSE(plateau);

        System.out.println("Résultat écrit dans resultat.txt");
        scanner.close();
    }
}