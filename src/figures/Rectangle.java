/**
 * 
 * @authors Frederic Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 *
 */

package figures;

import java.util.ArrayList;

public class Rectangle extends Polygone {

	// CONSTRUCTEURS

	/**
	 * Constructeur par copie
	 * 
	 * @param rec
	 *            le rectangle a copier
	 */
	public Rectangle(Rectangle rec) {
		super(rec);
	}

	/**
	 * Constructeur prenant en parametre une ArrayListe
	 * 
	 * @param listePointsSaisie
	 *            la liste de points saisis par l'utilisateur
	 */
	public Rectangle(ArrayList<UnPoint> listePointsSaisie) {
		this.plein = false;
		// Remplissage du tableau de points de saisie
		this.nbSaisie = listePointsSaisie.size();
		this.tabSaisie = new UnPoint[this.nbSaisie];
		for (int i = 0; i < this.nbSaisie; i++)
			this.tabSaisie[i] = listePointsSaisie.get(i);
		// Remplissage du tableau de points de memorisation
		this.nbMemo = 4;
		this.tabMemo = new UnPoint[this.nbMemo];
		// 1er point de saisie en haut a gauche, 2nd en bas a droite
		if (listePointsSaisie.get(1).x > listePointsSaisie.get(0).x) {
			if (listePointsSaisie.get(1).y > listePointsSaisie.get(0).y) {
				this.tabMemo[0] = listePointsSaisie.get(0);
				this.tabMemo[1] = new UnPoint(listePointsSaisie.get(1).x,
						listePointsSaisie.get(0).y);
				this.tabMemo[2] = listePointsSaisie.get(1);
				this.tabMemo[3] = new UnPoint(listePointsSaisie.get(0).x,
						listePointsSaisie.get(1).y);
			} // 1er point en bas a gauche, 2nd en haut a droite
			else {
				this.tabMemo[0] = new UnPoint(listePointsSaisie.get(0).x,
						listePointsSaisie.get(1).y);
				this.tabMemo[1] = listePointsSaisie.get(1);
				this.tabMemo[2] = new UnPoint(listePointsSaisie.get(1).x,
						listePointsSaisie.get(0).y);
				this.tabMemo[3] = listePointsSaisie.get(0);
			}
		} // 1er point en haut a droite, 2nd en bas a gauche
		else {
			if (listePointsSaisie.get(1).y > listePointsSaisie.get(0).y) {
				this.tabMemo[0] = new UnPoint(listePointsSaisie.get(1).x,
						listePointsSaisie.get(0).y);
				this.tabMemo[1] = listePointsSaisie.get(0);
				this.tabMemo[2] = new UnPoint(listePointsSaisie.get(0).x,
						listePointsSaisie.get(1).y);
				this.tabMemo[3] = listePointsSaisie.get(1);
			} // 1er point en bas a droite, 2nd en haut a gauche
			else {
				this.tabMemo[0] = listePointsSaisie.get(1);
				this.tabMemo[1] = new UnPoint(listePointsSaisie.get(0).x,
						listePointsSaisie.get(1).y);
				this.tabMemo[2] = listePointsSaisie.get(0);
				this.tabMemo[3] = new UnPoint(listePointsSaisie.get(1).x,
						listePointsSaisie.get(0).y);
			}
		}
	}

	// METHODES

	/**
	 * Deplacement specifique au rectangle
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
			this.tabMemo[1].move(tabMemo[1].x, tabMemo[1].y + valOrd);
			this.tabMemo[2].move(tabMemo[2].x, tabMemo[2].y);
			this.tabMemo[3].move(tabMemo[3].x + valAbs, tabMemo[3].y);
		} else if (pt.equals(tabMemo[1])) {
			this.tabMemo[0].move(tabMemo[0].x, tabMemo[0].y + valOrd);
			this.tabMemo[1].move(tabMemo[1].x + valAbs, tabMemo[1].y + valOrd);
			this.tabMemo[2].move(tabMemo[2].x + valAbs, tabMemo[2].y);
			this.tabMemo[3].move(tabMemo[3].x, tabMemo[3].y);
		} else if (pt.equals(tabMemo[2])) {
			this.tabMemo[0].move(tabMemo[0].x, tabMemo[0].y);
			this.tabMemo[1].move(tabMemo[1].x + valAbs, tabMemo[1].y);
			this.tabMemo[2].move(tabMemo[2].x + valAbs, tabMemo[2].y + valOrd);
			this.tabMemo[3].move(tabMemo[3].x, tabMemo[3].y + valOrd);
		} else if (pt.equals(tabMemo[3])) {
			this.tabMemo[0].move(tabMemo[0].x + valAbs, tabMemo[0].y);
			this.tabMemo[1].move(tabMemo[1].x, tabMemo[1].y);
			this.tabMemo[2].move(tabMemo[2].x, tabMemo[2].y + valOrd);
			this.tabMemo[3].move(tabMemo[3].x + valAbs, tabMemo[3].y + valOrd);
		}
	}

	/**
	 * Rotation specifique au rectangle
	 */
	public void rotation(int r) {
		int largeur = tabMemo[0].dist(tabMemo[1]);
		int hauteur = tabMemo[2].dist(tabMemo[1]);
		UnPoint gravite = new UnPoint(tabMemo[0].x + largeur / 2, tabMemo[0].y
				+ hauteur / 2);
		this.tabMemo[0] = new UnPoint(gravite.x - hauteur / 2, gravite.y
				- largeur / 2);
		this.tabMemo[1] = new UnPoint(gravite.x + hauteur / 2, gravite.y
				- largeur / 2);
		this.tabMemo[2] = new UnPoint(gravite.x + hauteur / 2, gravite.y
				+ largeur / 2);
		this.tabMemo[3] = new UnPoint(gravite.x - hauteur / 2, gravite.y
				+ largeur / 2);

	}
}