/**
 * 
 * @authors Frederic Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 *
 */

package figures;

import java.util.ArrayList;

public class Losange extends Polygone {
	
	/**
	 * Constructeur par copie
	 * 
	 * @param rec
	 *            le losange a copier
	 */
	public Losange(Losange los) {
		super(los);
	}	
	
	/** constructeur qui prend en parametre une array liste
	 * le premier point donne le centre du losange
	 * le second point determine l'angle du losange ainsi que la taille des cotes
	 * 
	 * @param listePointsSaisie	(doit contenir au minimum 2 points)
	 */
	public Losange(ArrayList<UnPoint> listePointsSaisie) {
		super(listePointsSaisie);
		// Remplissage du tableau de points de memorisation
		this.nbMemo = 4;
		this.tabMemo = new UnPoint[this.nbMemo];
		

		int longueur = listePointsSaisie.get(0).x - listePointsSaisie.get(1).x;
		if(longueur < 0) {longueur = -longueur;}
		int hauteur = listePointsSaisie.get(0).y - listePointsSaisie.get(1).y;
		if(hauteur < 0) {hauteur = -hauteur;}
		
		this.tabMemo[0] = new UnPoint(listePointsSaisie.get(0).x-longueur,
																	listePointsSaisie.get(0).y);
		this.tabMemo[1] = new UnPoint(listePointsSaisie.get(0).x,
																	listePointsSaisie.get(0).y+hauteur);
		this.tabMemo[2] = new UnPoint(listePointsSaisie.get(0).x+longueur,
																	listePointsSaisie.get(0).y);
		this.tabMemo[3] = new UnPoint(listePointsSaisie.get(0).x,
																	listePointsSaisie.get(0).y-hauteur);
			
	}
	
	/**
	 * Deplacement d'un point specifique au losange
	 * 
	 * @param pt
	 *            le point modifie
	 * @param valAbs
	 *            la nouvelle valeur de l'abscisse
	 * @param valOrd
	 *            la nouvelle valeur de l'ordonnee
	 */
	public void modifierTaille(UnPoint pt, int valAbs, int valOrd) {
		
	//calcul du centre du losange
			UnPoint centre = new UnPoint(tabMemo[1].x + tabMemo[1].x - tabMemo[3].x,
																	 tabMemo[0].y + tabMemo[0].y - tabMemo[2].y);
		
		if (pt.equals(tabMemo[0])) {
			this.tabMemo[0].move(tabMemo[0].x + valAbs*2, tabMemo[0].y);
			this.tabMemo[1].move(tabMemo[1].x + valAbs, tabMemo[1].y - valOrd);
			this.tabMemo[2].move(tabMemo[2].x, tabMemo[2].y);
			this.tabMemo[3].move(tabMemo[3].x + valAbs, tabMemo[3].y + valOrd);
		} else if (pt.equals(tabMemo[1])) {
			this.tabMemo[0].move(tabMemo[0].x - valAbs, tabMemo[0].y + valOrd);
			this.tabMemo[1].move(tabMemo[1].x, tabMemo[1].y + valOrd*2);
			this.tabMemo[2].move(tabMemo[2].x + valAbs, tabMemo[2].y+ valOrd);
			this.tabMemo[3].move(tabMemo[3].x, tabMemo[3].y);
		} else if (pt.equals(tabMemo[2])) {
			this.tabMemo[0].move(tabMemo[0].x, tabMemo[0].y);
			this.tabMemo[1].move(tabMemo[1].x + valAbs, tabMemo[1].y - valOrd);
			this.tabMemo[2].move(tabMemo[2].x + valAbs*2, tabMemo[2].y );
			this.tabMemo[3].move(tabMemo[3].x + valAbs, tabMemo[3].y + valOrd);
		} else if (pt.equals(tabMemo[3])) {
			this.tabMemo[0].move(tabMemo[0].x + valAbs, tabMemo[0].y + valOrd);
			this.tabMemo[1].move(tabMemo[1].x, tabMemo[1].y);
			this.tabMemo[2].move(tabMemo[2].x - valAbs, tabMemo[2].y + valOrd);
			this.tabMemo[3].move(tabMemo[3].x, tabMemo[3].y + valOrd*2 );
		}
	}

}