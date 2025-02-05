import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class TaquinSolveur {
    public int[] SolRec(Plateau p, List<Plateau> dejaVisites) {
        /* BUT DE LA METHODE ?? */
    }

    public int[] SolRec(Plateau p) {// Renvoie un tableau de 2 entiers, le premier est le nombre de mouvements, le deuxième est le nombre de noeuds explorés
        if (p.estResolu()) {
            p.affichePlateau();
            return new int[]{0, 0};
        }

        // Vérifier si l'état actuel a déjà été visité
        for (Plateau plat : dejaVisites) {
            if (plat.equals(p)) {
                return new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
            }
        }

        int noueds_a_par = p.nombreDepossibilites();

        int[] tab1 = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        int[] tab2 = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        int[] tab3 = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        int[] tab4 = {Integer.MAX_VALUE, Integer.MAX_VALUE};

        Plateau meilleurPlateau = null;

        List<Plateau> cDejaVisites = new ArrayList<>(dejaVisites);
        cDejaVisites.add(p);

        Plateau p1 = null, p2 = null, p3 = null, p4 = null;

        for (int i = 0; i < 4; i++) {
            int[] res = {Integer.MAX_VALUE, Integer.MAX_VALUE};

            switch (i) {
                case 0:
                    if (p.peutAllerADroite()) {
                        p1 = new Plateau(p);
                        p1.dDroite();
                        res = SolRec(p1, cDejaVisites);
                        tab1[0] = res[0] + 1;
                        tab1[1] = res[1] + noueds_a_par;
                    }
                    break;
                case 1:
                    if (p.peutAllerEnBas()) {
                        p2 = new Plateau(p);
                        p2.dBas();
                        res = SolRec(p2, cDejaVisites);
                        tab2[0] = res[0] + 1;
                        tab2[1] = res[1] + noueds_a_par;
                    }
                    break;
                case 2:
                    if (p.peutAllerAGauche()) {
                        p3 = new Plateau(p);
                        p3.dGauche();
                        res = SolRec(p3, cDejaVisites);
                        tab3[0] = res[0] + 1;
                        tab3[1] = res[1] + noueds_a_par;
                    }
                    break;
                case 3:
                    if (p.peutAllerEnHaut()) {
                        p4 = new Plateau(p);
                        p4.dHaut();
                        res = SolRec(p4, cDejaVisites);
                        tab4[0] = res[0] + 1;
                        tab4[1] = res[1] + noueds_a_par;
                    }
                    break;
            }
        }

        // Trouver le minimum en fonction du nombre de mouvements
        int[][] options = {tab1, tab2, tab3, tab4};
        Plateau[] plateaux = {p1, p2, p3, p4};

        int[] minTab = options[0];
        meilleurPlateau = plateaux[0];

        for (int i = 1; i < 4; i++) {
            if (options[i][0] < minTab[0]) {
                minTab = options[i];
                meilleurPlateau = plateaux[i];
            }
        }

        // Si un meilleur plateau a été trouvé, on applique la transformation sur `p`
        if (meilleurPlateau != null) {
            p.copierDepuis(meilleurPlateau.plateauActuel); // Il faut implémenter cette méthode dans Plateau
        }

        return minTab;
    }


    /*A ENLEVER OU METTRE DANS LE MAIN PRINCIPAL*/
   /* public static void main(String[] args) {
        Plateau plateau = new Plateau("Taquin_tests/sp000.txt");
        plateau.affichePlateau();
        List<Plateau> dejaVisites = new ArrayList<>();
        TaquinSolveur solveur = new TaquinSolveur();
        solveur.SolRec(plateau, dejaVisites);
        plateau.affichePlateau();

    }*/

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
