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

	public Rectangle(Rectangle rec) {
		super(rec);
	}

	// Constructeur prenant en paramètre une ArrayList
	public Rectangle(ArrayList<UnPoint> listePointsSaisie) {
		this.plein = false;
		// Remplissage du tableau de points de saisie
		this.nbSaisie = listePointsSaisie.size();
		this.tabSaisie = new UnPoint[this.nbSaisie];
		for (int i = 0; i < this.nbSaisie; i++)
			this.tabSaisie[i] = listePointsSaisie.get(i);
		// Remplissage du tableau de points de mémorisation
		this.nbMemo = 4;
		this.tabMemo = new UnPoint[this.nbMemo];
		// 1er point en haut � gauche, 2nd en bas � droite
		if (listePointsSaisie.get(1).x > listePointsSaisie.get(0).x) {
			if (listePointsSaisie.get(1).y > listePointsSaisie.get(0).y) {
				this.tabMemo[0] = listePointsSaisie.get(0);
				this.tabMemo[1] = new UnPoint(listePointsSaisie.get(1).x,
						listePointsSaisie.get(0).y);
				this.tabMemo[2] = listePointsSaisie.get(1);
				this.tabMemo[3] = new UnPoint(listePointsSaisie.get(0).x,
						listePointsSaisie.get(1).y);
			} // 1er point en bas � gauche, 2nd en haut � droite
			else {
				this.tabMemo[0] = new UnPoint(listePointsSaisie.get(0).x,
						listePointsSaisie.get(1).y);
				this.tabMemo[1] = listePointsSaisie.get(1);
				this.tabMemo[2] = new UnPoint(listePointsSaisie.get(1).x,
						listePointsSaisie.get(0).y);
				this.tabMemo[3] = listePointsSaisie.get(0);
			}
		} // 1er point en haut � droite, 2nd en bas � gauche
		else {
			if (listePointsSaisie.get(1).y > listePointsSaisie.get(0).y) {
				this.tabMemo[0] = new UnPoint(listePointsSaisie.get(1).x,
						listePointsSaisie.get(0).y);
				this.tabMemo[1] = listePointsSaisie.get(0);
				this.tabMemo[2] = new UnPoint(listePointsSaisie.get(0).x,
						listePointsSaisie.get(1).y);
				this.tabMemo[3] = listePointsSaisie.get(1);
			} // 1er point en bas � droite, 2nd en haut � gauche
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

	// Méthode de deplacement de point specifique au rectangle
	public void modifierTaille(UnPoint pt, int valAbs, int valOrd) {
		if(pt.equals(tabMemo[0])) {
			this.tabMemo[0].move(tabMemo[0].x+valAbs, tabMemo[0].y+valOrd);
			this.tabMemo[1].move(tabMemo[1].x, tabMemo[1].y+valOrd);
			this.tabMemo[2].move(tabMemo[2].x, tabMemo[2].y);
			this.tabMemo[3].move(tabMemo[3].x+valAbs, tabMemo[3].y);
		}
		else
			if(pt.equals(tabMemo[1])) {
				this.tabMemo[0].move(tabMemo[0].x, tabMemo[0].y+valOrd);
				this.tabMemo[1].move(tabMemo[1].x+valAbs, tabMemo[1].y+valOrd);
				this.tabMemo[2].move(tabMemo[2].x+valAbs, tabMemo[2].y);
				this.tabMemo[3].move(tabMemo[3].x, tabMemo[3].y);
			}
			else
				if(pt.equals(tabMemo[2])) {
					this.tabMemo[0].move(tabMemo[0].x, tabMemo[0].y);
					this.tabMemo[1].move(tabMemo[1].x+valAbs, tabMemo[1].y);
					this.tabMemo[2].move(tabMemo[2].x+valAbs, tabMemo[2].y+valOrd);
					this.tabMemo[3].move(tabMemo[3].x, tabMemo[3].y+valOrd);
				}
				else
					if(pt.equals(tabMemo[3])) {
						this.tabMemo[0].move(tabMemo[0].x+valAbs, tabMemo[0].y);
						this.tabMemo[1].move(tabMemo[1].x, tabMemo[1].y);
						this.tabMemo[2].move(tabMemo[2].x, tabMemo[2].y+valOrd);
						this.tabMemo[3].move(tabMemo[3].x+valAbs, tabMemo[3].y+valOrd);
					}
	}

	public void rotation(int r) {
		int largeur = tabMemo[0].dist(tabMemo[1]);
		int hauteur = tabMemo[2].dist(tabMemo[1]);
		UnPoint gravite = new UnPoint(tabMemo[0].x+largeur/2,tabMemo[0].y+hauteur/2);
		this.tabMemo[0] = new UnPoint(gravite.x - hauteur/2, gravite.y-largeur/2);
		this.tabMemo[1] = new UnPoint(gravite.x+hauteur/2,gravite.y-largeur/2);
		this.tabMemo[2] = new UnPoint(gravite.x+hauteur/2,gravite.y+largeur/2);
		this.tabMemo[3] = new UnPoint(gravite.x-hauteur/2,gravite.y+largeur/2);

	}
}