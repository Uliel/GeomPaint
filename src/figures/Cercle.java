/**
 * 
 * @authors Fr�d�ric Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 *
 */

package figures;

import java.util.ArrayList;

public class Cercle extends Ovale {
	
	// ATTRIBUTS
	private final static int CERCLE_POINTS = 2;
	
	// CONSTRUCTEUR
		// Constructeur prenant en paramètre une ArrayList
	public Cercle(ArrayList<UnPoint> listePointsSaisie) {
		this.plein = false;
		this.selection = true;
		// Remplissage du tableau de points de saisie
		this.nbSaisie = listePointsSaisie.size();
		this.tabSaisie = new UnPoint[this.nbSaisie];
		for(int i = 0 ; i < this.nbSaisie ; i++)
			this.tabSaisie[i] = listePointsSaisie.get(i);
		// Remplissage du tableau de points de mémorisation
		this.nbMemo = 2;
		this.tabMemo = new UnPoint[this.nbMemo];
		this.tabMemo[0] = listePointsSaisie.get(0);
		int dist = listePointsSaisie.get(0).dist(listePointsSaisie.get(1));
		this.tabMemo[1] = new UnPoint(listePointsSaisie.get(0).x + dist, listePointsSaisie.get(0).y);	
	}
	
	public Cercle(Cercle c) {
		super(c);
	}
}
