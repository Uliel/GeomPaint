/**
 * 
 * @authors Fr�d�ric Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 *
 */

package figures;

import java.util.ArrayList;

public class Rectangle extends Polygone {
	
	// ATTRIBUTS
	private final static int RECTANGLE_SAISIE = 2;
	private final static int RECTANGLE_MEMO = 4;
	
	
	// CONSTRUCTEURS
		// Constructeur vide
	public Rectangle() {
		super();
	}
	
		// Constructeur prenant en paramètre une ArrayList
	public Rectangle(ArrayList<UnPoint> listePointsSaisie) {
		this.plein = false;
		this.selection = true;
		// Remplissage du tableau de points de saisie
		this.nbSaisie = listePointsSaisie.size();
		this.tabSaisie = new UnPoint[this.nbSaisie];
		for(int i = 0 ; i < this.nbSaisie ; i++)
			this.tabSaisie[i] = listePointsSaisie.get(i);
		// Remplissage du tableau de points de mémorisation
		this.nbMemo = 4;
		this.tabMemo = new UnPoint[this.nbMemo];
		this.tabMemo[0] = listePointsSaisie.get(0);
		this.tabMemo[1] = new UnPoint(listePointsSaisie.get(0).x, listePointsSaisie.get(1).y);
		this.tabMemo[2] = listePointsSaisie.get(1);
		this.tabMemo[3] = new UnPoint(listePointsSaisie.get(1).x, listePointsSaisie.get(0).y);
	}
}