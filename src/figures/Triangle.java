/**
 * 
 * @authors Frederic Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 *
 */

package figures;

import java.util.ArrayList;

public class Triangle extends Polygone {

	// ATTRIBUTS
	private final static int TRIANGLE_POINTS = 3;

	// CONSTRUCTEURS

	/**
	 * Constructeur vide, on initialise les tableaux
	 */
	public Triangle() {
		super();
		this.setNbSaisie(TRIANGLE_POINTS);
		this.setNbMemo(TRIANGLE_POINTS);
		this.setTabSaisie(this.getNbSaisie());
		this.setTabMemo(this.getNbMemo());
	}

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