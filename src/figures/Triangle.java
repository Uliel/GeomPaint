/**
 * 
 * @authors Frederic Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 * Classe qui cree des triangles quelconques, elle herite de Polygone
 *
 */

package figures;

import java.util.ArrayList;

public class Triangle extends Polygone {

	// CONSTRUCTEURS

	/**
	 * Constructeur par copie
	 * 
	 * @param tr
	 *            le triangle a copier
	 */
	public Triangle(Triangle tr) {
		super(tr);
	}

	/**
	 * Constructeur prenant en parametre une ArrayList
	 * 
	 * @param listePointsSaisie
	 */
	public Triangle(ArrayList<UnPoint> listePointsSaisie) {
		super(listePointsSaisie);
	}
}