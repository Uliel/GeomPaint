/**
 * @authors Fr�d�ric Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
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
	 *            ordonn�e du point
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
	public int dist(UnPoint pt) {
		return (int) Math.sqrt((this.x - pt.x) * (this.x - pt.x)
				+ (this.y - pt.y) * (this.y - pt.y));
	}
	
	
	// Méthode qui regarde si un point est situé au voisinage du segment reliant deux autres points
	public boolean estVoisinSegment(int marge, UnPoint p1, UnPoint p2) {
		boolean res = false;
		int distanceP1 = this.dist(p1);
		int distanceP2 = this.dist(p2);
		int distanceP1P2 = p1.dist(p2);
		if(distanceP1P2 < distanceP1 + distanceP2 + marge)
			res = true;
		return res;
		
	}

}
