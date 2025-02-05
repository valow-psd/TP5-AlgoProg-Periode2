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
        int noueds_a_par = 0;// Nombre de noeuds explorés (a changer)
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    p.dBas();
                    int[] tab = { SolRec(p)[0] + 1, SolRec(p)[1] + noueds_a_par };
                    return tab;
                case 1:
                    p.dHaut();
                    int[] tab1 = { SolRec(p)[0] + 1, SolRec(p)[1] + noueds_a_par };
                    return tab1;
                case 2:
                    p.dDroite();
                    int[] tab2 = { SolRec(p)[0] + 1, SolRec(p)[1] + noueds_a_par };
                    return tab2;
                case 3:
                    p.dGauche();
                    int[] tab3 = { SolRec(p)[0] + 1, SolRec(p)[1] + noueds_a_par };
                    return tab3;
            }
        }

  }
}