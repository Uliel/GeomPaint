/**
 * @authors Fr�d�ric Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 */

package figures;

import java.awt.Point;

public class UnPoint extends Point implements Cloneable {

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
		if(distanceP1P2 + marge > distanceP1 + distanceP2)
			res = true;
		return res;
		
	}
	
	// Méthode qui déplace un point
	public void deplacerPt(int nouvX, int nouvY) {
		this.move(this.x + nouvX, this.y + nouvY);
	}
	
	public UnPoint clone() {
		return (UnPoint) super.clone();
	}
	
	public static UnPoint rotatePoint(UnPoint pt, UnPoint center, double angleDeg){
	    double angleRad = (angleDeg/180)*Math.PI;
	    double cosAngle = Math.cos(angleRad );
	    double sinAngle = Math.sin(angleRad );
	    double dx = (pt.x-center.x);
	    double dy = (pt.y-center.y);

	    pt.x = center.x + (int) (dx*cosAngle-dy*sinAngle);
	    pt.y = center.y + (int) (dx*sinAngle+dy*cosAngle);
	    return pt;
	}

}
