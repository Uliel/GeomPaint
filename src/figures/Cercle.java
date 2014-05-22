/**
 * 
 * @authors Frederic Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 *
 */

package figures;

import java.util.ArrayList;

public class Cercle extends FigureGeom {

	// CONSTRUCTEUR

	/**
	 * Constructeur prenant en parametre une ArrayList
	 * 
	 * @param listePointsSaisie
	 *            la liste de points saisis par l'utilisateur
	 */
	public Cercle(ArrayList<UnPoint> listePointsSaisie) {
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

	/**
	 * Constructeur par copie
	 * 
	 * @param c
	 *            le cercle a copier
	 */
	public Cercle(Cercle c) {
		super(c);
	}
	
	@Override
	public void rotation(int r) {
		// TODO Auto-generated method stub
		
	}
}
