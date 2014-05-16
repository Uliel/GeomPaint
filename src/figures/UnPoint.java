/**
 * @authors Frédéric Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 */

package figures;

import java.awt.Point;

public class UnPoint extends Point {

	/**
	 * Constructeur
	 * 
	 * @param x
	 *            abscisse du point
	 * @param y
	 *            ordonnée du point
	 */
	public UnPoint(int x, int y) {
		super(x, y);
	}

	/**
	 * Calcul de la distance entre deux points
	 * 
	 * @param pt
	 * 
	 * @return la distance
	 */
	public int distance(UnPoint pt) {
		return ((int) Math.sqrt((this.x - pt.x) * (this.x - pt.x)
				+ (this.y - pt.y) * (this.y - pt.y))) * 2;
	}

}
