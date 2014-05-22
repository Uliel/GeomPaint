/**
 * 
 * @authors Nicolas Gambarini, Sarah Lequeuvre
 *
 */

package figures;

import java.util.ArrayList;

public class Losange extends Polygone {
	
	/**
	 * Constructeur par copie
	 * 
	 * @param rec
	 *            le rectangle a copier
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
		int hauteur = listePointsSaisie.get(0).y - listePointsSaisie.get(1).y;
		
		this.tabMemo[0] = new UnPoint(listePointsSaisie.get(0).x-longueur,
																	listePointsSaisie.get(0).y);
		this.tabMemo[1] = new UnPoint(listePointsSaisie.get(0).x,
																	listePointsSaisie.get(0).y+hauteur);
		this.tabMemo[2] = new UnPoint(listePointsSaisie.get(0).x+longueur,
																	listePointsSaisie.get(0).y);
		this.tabMemo[3] = new UnPoint(listePointsSaisie.get(0).x,
																	listePointsSaisie.get(0).y-hauteur);
			
	}
}