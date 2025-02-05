import java.util.ArrayList;
import java.util.List;

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
}