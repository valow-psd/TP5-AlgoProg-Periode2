import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

class TaquinSolveur {
    public boolean SolRec(Plateau initial) {
        boolean trouve = false;
        int i = 0;
        List<Plateau> sequence = null;
        int[] appels = { 0 };
        long debut = System.currentTimeMillis();
        // On essaie avec un nombre de coups croissant jusqu'à une limite
        while (!trouve && i <= 100000) {
            appels[0] = 0;
            sequence = new ArrayList<>();
            // On utilise un HashSet pour éviter les cycles sur le chemin
            trouve = existeSolution(initial, i, new HashSet<>(), sequence,appels);
            i++;
            
        }

        if (trouve) {
            long fin = System.currentTimeMillis();
            long delta = fin - debut;
            try (FileWriter writer = new FileWriter("resultat.txt", true)) {
                writer.write("Solution trouvée en " + (sequence.size() - 1) + " coups :\n");
                writer.write("Nombre d'appels recursive effectué : " + appels[0] + "\n");
                writer.write("Temps ecoulé : " + delta + "ms\n");
                int etape = 0;
                for (Plateau p : sequence) {
                    ecrireEtape(writer, p, etape);
                    etape++;
                }
                
                writer.write("=============================\n");
            } catch (IOException e) {
                System.out.println("Erreur lors de l'écriture du fichier de solution.");
                e.printStackTrace();
            }
        }
        return trouve;
    }
    
    private boolean existeSolution(Plateau p, int moves, Set<Plateau> visited, List<Plateau> sequence,int[] appels) {
        if (moves == 0) {
            if (p.estResolu()) {
                sequence.add(p);
                return true;
            }
            return false;
        }

        visited.add(p);

        // Pour chaque mouvement possible, on crée une copie, on effectue le mouvement
        // et on effectue la recherche en un nombre de move limité

      
        if (p.peutAllerADroite()) {
            Plateau pRight = new Plateau(p);
            pRight.dDroite();
            appels[0]++;
            if (!visited.contains(pRight)) {
                List<Plateau> seqLocal = new ArrayList<>();
                if (existeSolution(pRight, moves - 1, visited, seqLocal,appels)) {
                    sequence.add(p); 
                    sequence.addAll(seqLocal); 
                    return true;
                }
            }
        }

        
        if (p.peutAllerEnBas()) {
            Plateau pDown = new Plateau(p);
            pDown.dBas();
            if (!visited.contains(pDown)) {
                appels[0]++;
                List<Plateau> seqLocal = new ArrayList<>();
                if (existeSolution(pDown, moves - 1, visited, seqLocal,appels)) {
                    sequence.add(p);
                    sequence.addAll(seqLocal);
                    return true;
                }
            }
        }

        
        if (p.peutAllerAGauche()) {
            Plateau pLeft = new Plateau(p);
            pLeft.dGauche();
            if (!visited.contains(pLeft)) {
                appels[0]++;;
                List<Plateau> seqLocal = new ArrayList<>();
                if (existeSolution(pLeft, moves - 1, visited, seqLocal,appels)) {
                    sequence.add(p);
                    sequence.addAll(seqLocal);
                    return true;
                }
            }
        }

        if (p.peutAllerEnHaut()) {
            Plateau pUp = new Plateau(p);
            pUp.dHaut();
            appels[0]++;;
            if (!visited.contains(pUp)) {
                List<Plateau> seqLocal = new ArrayList<>();
                if (existeSolution(pUp, moves - 1, visited, seqLocal,appels)) {
                    sequence.add(p);
                    sequence.addAll(seqLocal);
                    
                    return true;
                }
            }
        }

        visited.remove(p);
        return false;
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
        String cheminFichier = "Taquin_tests/sp0400.txt";

        Plateau plateau = new Plateau(cheminFichier);
        System.out.println("Chargement du plateau...");
        System.out.println("Plateau initial");
        plateau.affichePlateau();
        boolean methode = false; // Changer en True si on veut utiliser les methode solSE, profondeurDabord et largeurDabord
        TaquinSolveur solveur = new TaquinSolveur();
        System.out.println("Résolution en cours...");
        /*Différentes méthodes de résolution*/
        if (methode) {
            solveur.solSE(plateau);
            solveur.profondeurDAbord(plateau);
            solveur.largeurDAbord(plateau);
            System.out.println("Résultats comparés dans resultat.txt");
        }
        else {
            solveur.SolRec(plateau);
            System.out.println("Résultats dans resultat.txt");
        }
        
        scanner.close();
    }
}
