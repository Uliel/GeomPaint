/**
 * 
 * @authors Frédéric Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 *
 */

package figures;

import java.util.ArrayList;

public class Ovale extends Cercle {
	
	//CONSTRUCTEURS
	public Ovale(ArrayList<UnPoint> listePointsSaisie) {
		super(listePointsSaisie);
		this.plein = false;
		// Remplissage du tableau de points de saisie
		this.nbSaisie = listePointsSaisie.size();
		this.tabSaisie = new UnPoint[this.nbSaisie];
		for (int i = 0; i < this.nbSaisie; i++)
			this.tabSaisie[i] = listePointsSaisie.get(i);
		// Remplissage du tableau de points de memorisation
		this.nbMemo = 2;
		this.tabMemo = new UnPoint[this.nbMemo];
		this.tabMemo[0] = listePointsSaisie.get(0);
		int dist = listePointsSaisie.get(0).dist(listePointsSaisie.get(1));
		this.tabMemo[1] = new UnPoint(listePointsSaisie.get(0).x + dist,
				listePointsSaisie.get(0).y);
	}
	
	/** constructeur par copie
	 * 
	 * @param ov
	 */
	public Ovale(Ovale ov) {
		super(ov);
	}
}