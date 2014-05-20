/**
 * 
 * @authors Nicolas Gambarini, Sarah Lequeuvre
 *
 */

package figures;

import java.util.ArrayList;

public class Triangle extends Polygone {
	
	// ATTRIBUTS
	private final static int TRIANGLE_POINTS = 3;
	
	// CONSTRUCTEURS
		// Constructeur vide
	public Triangle() {
		super();
		this.setNbSaisie(TRIANGLE_POINTS);
		this.setNbMemo(TRIANGLE_POINTS);
		this.setTabSaisie(this.getNbSaisie());
		this.setTabMemo(this.getNbMemo());
	}
	
		// Constructeur prenant en paramètre une ArrayList
	public Triangle(ArrayList<UnPoint> listePointsSaisie) {
		super(listePointsSaisie);
	}
	
	public Triangle(Triangle tr) {
		super(tr);
	}

}