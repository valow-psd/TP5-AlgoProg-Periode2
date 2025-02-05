import java.util.*;

class Tas {
    private List<Plateau> elements = new ArrayList<>();

    public void ajouter(Plateau p) {
        elements.add(p);
        int i = elements.size() - 1;
        while (i > 0 && elements.get(i).h() < elements.get((i - 1) / 2).h()) {
            Collections.swap(elements, i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    public Plateau extraire() {
        if (elements.isEmpty()) return null;
        Plateau min = elements.get(0);
        Plateau dernier = elements.remove(elements.size() - 1);
        if (!elements.isEmpty()) {
            elements.set(0, dernier);
            tamiser(0);
        }
        return min;
    }

    private void tamiser(int i) {
        int gauche = 2 * i + 1;
        int droite = 2 * i + 2;
        int plusPetit = i;

        if (gauche < elements.size() && elements.get(gauche).h() < elements.get(plusPetit).h()) {
            plusPetit = gauche;
        }
        if (droite < elements.size() && elements.get(droite).h() < elements.get(plusPetit).h()) {
            plusPetit = droite;
        }
        if (plusPetit != i) {
            Collections.swap(elements, i, plusPetit);
            tamiser(plusPetit);
        }
    }

    public boolean estVide() {
        return elements.isEmpty();
    }
}