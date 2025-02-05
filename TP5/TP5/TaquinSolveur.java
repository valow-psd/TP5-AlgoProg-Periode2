import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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

        try (FileWriter writer = new FileWriter("resultat.txt", true)) {
            writer.write("Méthode : Meilleur d'abord\n");

            while (!tas.estVide()) {
                Plateau courant = tas.extraire();
                noeudsExplores++;

                ecrireEtape(writer, courant, noeudsExplores);

                if (courant.estResolu()) {
                    long fin = System.currentTimeMillis();
                    long tempsCalcul = fin - debut;
                    ecrireResultat(writer, tempsCalcul, noeudsExplores, courant);
                    return;
                }

                List<Plateau> enfants = courant.genererEnfants();
                for (Plateau enfant : enfants) {
                    tas.ajouter(enfant);
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture du fichier de résultat.");
            e.printStackTrace();
        }
    }

    public void profondeurDAbord(Plateau p) {
        long debut = System.currentTimeMillis();
        Tas tas = new Tas();
        tas.ajouter(p);

        int noeudsExplores = 0;

        try (FileWriter writer = new FileWriter("resultat.txt", true)) {
            writer.write("Méthode : Profondeur d'abord\n");

            while (!tas.estVide()) {
                Plateau courant = tas.extraire();
                noeudsExplores++;

                ecrireEtape(writer, courant, noeudsExplores);

                if (courant.estResolu()) {
                    long fin = System.currentTimeMillis();
                    long tempsCalcul = fin - debut;
                    ecrireResultat(writer, tempsCalcul, noeudsExplores, courant);
                    return;
                }

                List<Plateau> enfants = courant.genererEnfants();
                for (int i = enfants.size() - 1; i >= 0; i--) {
                    tas.ajouter(enfants.get(i));
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture du fichier de résultat.");
            e.printStackTrace();
        }
    }

    public void largeurDAbord(Plateau p) {
        long debut = System.currentTimeMillis();
        Tas tas = new Tas();
        tas.ajouter(p);

        int noeudsExplores = 0;

        try (FileWriter writer = new FileWriter("resultat.txt", true)) {
            writer.write("Méthode : Largeur d'abord\n");

            while (!tas.estVide()) {
                Plateau courant = tas.extraire();
                noeudsExplores++;

                ecrireEtape(writer, courant, noeudsExplores);

                if (courant.estResolu()) {
                    long fin = System.currentTimeMillis();
                    long tempsCalcul = fin - debut;
                    ecrireResultat(writer, tempsCalcul, noeudsExplores, courant);
                    return;
                }

                List<Plateau> enfants = courant.genererEnfants();
                for (Plateau enfant : enfants) {
                    tas.ajouter(enfant);
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture du fichier de résultat.");
            e.printStackTrace();
        }
    }

    // ecriture etape par etape dans le fichier
    private void ecrireEtape(FileWriter writer, Plateau p, int etape) throws IOException {
        writer.write("Étape " + etape + ":\n");
        int[][] plateau = p.getPlateauActuel();
        for (int[] ligne : plateau) {
            for (int val : ligne) {
                writer.write(val + " ");
            }
            writer.write("\n");
        }
        writer.write("-----------------------------\n");
    }

    // ecriture des résultats dans un fichier
    private void ecrireResultat(FileWriter writer, long tempsCalcul, int noeudsExplores, Plateau p) throws IOException {
        writer.write("Résultat trouvé en " + tempsCalcul + " ms\n");
        writer.write("Nombre de noeuds explorés : " + noeudsExplores + "\n");
        writer.write("Configuration finale : \n");
        int[][] plateau = p.getPlateauActuel();
        for (int[] ligne : plateau) {
            for (int val : ligne) {
                writer.write(val + " ");
            }
            writer.write("\n");
        }
        writer.write("=============================\n");
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String cheminFichier = "Taquin_tests/sp000.txt";

        Plateau plateau = new Plateau(cheminFichier);
        System.out.println("Chargement du plateau...");
        System.out.println("Plateau initial");
        plateau.affichePlateau();


        TaquinSolveur solveur = new TaquinSolveur();
        System.out.println("Résolution en cours...");
        /*Différentes méthodes de résolution*/
        solveur.solSE(plateau);
        solveur.profondeurDAbord(plateau);
        solveur.largeurDAbord(plateau);

        System.out.println("Résultats comparés dans resultat.txt");
        scanner.close();
    }
}
