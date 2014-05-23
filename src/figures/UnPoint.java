/**
 * 
 * @authors Frederic Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 * Classe qui cree des objets UnPoint herites de la classe Point
 * Toutes les figures geometriques sont un ensemble d'objets UnPoint
 *
 */

package figures;

import java.awt.Point;

public class UnPoint extends Point implements Cloneable {

	/**
	 * Constructeur par heritage
	 * 
	 * @param x
	 *            abscisse du point
	 * @param y
	 *            ordonnee du point
	 */
	public UnPoint(int x, int y) {
		super(x, y);
	}

	// METHODES

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

	/**
	 * Méthode qui regarde si un point est situe au voisinage du segment
	 * reliant deux autres points
	 * 
	 * @param marge
	 *            valeur de la marge
	 * @param p1
	 *            premier point du segment
	 * @param p2
	 *            seconde point du segment
	 * @return vrai si le point est dans le voisinage du segment
	 */
	public boolean estVoisinSegment(int marge, UnPoint p1, UnPoint p2) {
		boolean res = false;
		int distanceP1 = this.dist(p1);
		int distanceP2 = this.dist(p2);
		int distanceP1P2 = p1.dist(p2);
		if (distanceP1P2 + marge > distanceP1 + distanceP2)
			res = true;
		return res;

	}

	/**
	 * Méthode qui deplace un point
	 * 
	 * @param nouvX
	 *            nouvelle abscisse du point
	 * @param nouvY
	 *            nouvelle ordonnee du point
	 */
	public void deplacerPt(int nouvX, int nouvY) {
		this.move(this.x + nouvX, this.y + nouvY);
	}

	/**
	 * Methode de clonage d'un point
	 * 
	 * @return point clone
	 */
	public UnPoint clone() {
		return (UnPoint) super.clone();
	}

	/**
	 * Methode qui effectue la rotation d'un point autour d'un autre point
	 * 
	 * @param centre
	 *            centre de la rotation
	 * @param angleDeg
	 *            angle de rotation
	 */
	public void rotatePoint(UnPoint centre, double angleDeg) {
		double angleRad = (angleDeg / 180) * Math.PI;
		double cosAngle = Math.cos(angleRad);
		double sinAngle = Math.sin(angleRad);
		double dx = (this.x - centre.x);
		double dy = (this.y - centre.y);

		this.x = centre.x + (int) (dx * cosAngle - dy * sinAngle);
		this.y = centre.y + (int) (dx * sinAngle + dy * cosAngle);
	}
}
