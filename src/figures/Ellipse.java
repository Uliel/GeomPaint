/**
 * 
 * @authors Frederic Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 *
 */

package figures;

import java.util.ArrayList;

public class Ellipse extends Cercle {

		//CONSTRUCTEURS
	/** 
	 * Constructeur construisant une ellipse a partir d'une ArrayList
	 * 
	 * @param listePointsSaisie liste des points
	 */
	public Ellipse(ArrayList<UnPoint> listePointsSaisie) {
		super(listePointsSaisie);
		// Remplissage du tableau de points de memorisation
		this.nbMemo = 2;
		this.tabMemo = new UnPoint[this.nbMemo];
		
		int longueur = listePointsSaisie.get(0).x - listePointsSaisie.get(1).x;
		if(longueur < 0) {longueur = -longueur;}
		int hauteur = listePointsSaisie.get(0).y - listePointsSaisie.get(1).y;
		if(hauteur < 0) {hauteur = -hauteur;}
		
		this.tabMemo[0] = listePointsSaisie.get(0);
		this.tabMemo[1] = listePointsSaisie.get(1);
	}
	
	/** 
	 * Constructeur par copie
	 * 
	 * @param e ellipse a copier 
	 */
	public Ellipse(Ellipse e) {
		super(e);
	}


	// METHODE
	
	/**
	 * Deplacement specifique à l'ellipse
	 * 
	 * @param pt
	 *            le point modifie
	 * @param valAbs
	 *            deplacement selon l'axe des abscisses
	 * @param valOrd
	 *            deplacement selon l'axe des ordonnees
	 */
	public void modifierTaille(UnPoint pt, int valAbs, int valOrd) {
		if (pt.equals(tabMemo[0])) {
			this.tabMemo[0].move(tabMemo[0].x + valAbs, tabMemo[0].y + valOrd);
			this.tabMemo[1].move(tabMemo[1].x, tabMemo[1].y);
		} else if (pt.equals(tabMemo[1])) {
			this.tabMemo[0].move(tabMemo[0].x, tabMemo[0].y + valOrd);
			this.tabMemo[1].move(tabMemo[1].x + valAbs, tabMemo[1].y);
		}
	}
}