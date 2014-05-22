/**
 * 
 * @authors Fr�d�ric Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 *
 */

package figures;

import java.util.ArrayList;

public class Ellipse extends Cercle {
	
	//CONSTRUCTEURS
	public Ellipse(ArrayList<UnPoint> listePointsSaisie) {
		super(listePointsSaisie);
		// Remplissage du tableau de points de memorisation
		this.nbMemo = 2;
		this.tabMemo = new UnPoint[this.nbMemo];
		
		int longueur = listePointsSaisie.get(0).x - listePointsSaisie.get(1).x;
		if(longueur < 0) {longueur = -longueur;}
		int hauteur = listePointsSaisie.get(0).y - listePointsSaisie.get(1).y;
		if(hauteur < 0) {hauteur = -hauteur;}
		
		// 1er point de saisie en haut a gauche, 2nd en bas a droite
		if (listePointsSaisie.get(1).x > listePointsSaisie.get(0).x) {
			if (listePointsSaisie.get(1).y > listePointsSaisie.get(0).y) {
				this.tabMemo[0] = listePointsSaisie.get(0);
				this.tabMemo[1] = listePointsSaisie.get(1);
			} // 1er point en bas a gauche, 2nd en haut a droite
			else {
				this.tabMemo[0] = new UnPoint(listePointsSaisie.get(0).x,
						listePointsSaisie.get(1).y);
				this.tabMemo[1] = new UnPoint(listePointsSaisie.get(1).x,
						listePointsSaisie.get(0).y);
			}
		} // 1er point en haut a droite, 2nd en bas a gauche
		else {
			if (listePointsSaisie.get(1).y > listePointsSaisie.get(0).y) {
				this.tabMemo[0] = new UnPoint(listePointsSaisie.get(1).x,
						listePointsSaisie.get(0).y);
				this.tabMemo[1] = new UnPoint(listePointsSaisie.get(0).x,
						listePointsSaisie.get(1).y);
			} // 1er point en bas a droite, 2nd en haut a gauche
			else {
				this.tabMemo[0] = listePointsSaisie.get(1);
				this.tabMemo[1] = listePointsSaisie.get(0);
			}
		}
	}
	
	/** constructeur par copie
	 * 
	 * @param ov
	 */
	public Ellipse(Ellipse ov) {
		super(ov);
	}


	// METHODES
	
	/**
	 * Deplacement specifique à l'ellipse
	 * 
	 * @param pt
	 *            le point modifie
	 * @param valAbs
	 *            la nouvelle valeur de l'abscisse
	 * @param valOrd
	 *            la nouvelle valeur de l'ordonnee
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