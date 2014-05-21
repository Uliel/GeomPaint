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
		this.selection = true;
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
	public void modifierTaille(int valAbs, int valOrd) {
		this.tabMemo[0].move(-valAbs, -valOrd);
		this.tabMemo[1].move(valAbs, -valOrd);
		this.tabMemo[2].move(valAbs, valOrd);
		this.tabMemo[3].move(-valAbs, valOrd);
	}

	public void rotation(int r) {
		UnPoint[] tmp = this.tabMemo;
		if (r == 1) {
			this.tabMemo[0] = new UnPoint(tmp[3].x, tmp[3].y
					- tmp[0].dist(tmp[1]));
			this.tabMemo[1] = new UnPoint(tabMemo[0].x + tmp[0].dist(tmp[3]),
					tabMemo[0].y);
			this.tabMemo[2] = new UnPoint(tabMemo[1].x, tmp[3].y);
			this.tabMemo[3] = tmp[3];
		} else {
			this.tabMemo[1] = new UnPoint(tmp[2].x, tmp[2].y
					- tmp[0].dist(tmp[1]));
			this.tabMemo[2] = tmp[2];
			this.tabMemo[3] = new UnPoint(tmp[2].y - tmp[0].dist(tmp[3]),
					tmp[2].y);
			this.tabMemo[0] = new UnPoint(tabMemo[3].x, tabMemo[1].y);
		}
	}
}