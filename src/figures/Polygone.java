/**
 * 
 * @authors Frederic Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 *
 */

package figures;

import java.util.ArrayList;

public class Polygone extends FigureGeom {

	// CONSTRUCTEURS

	/**
	 * Constructeur vide
	 */
	public Polygone() {
		super();

	}

	/**
	 * Constructeur par copie
	 * 
	 * @param pol
	 *            le polygone a copier
	 */
	public Polygone(Polygone pol) {
		super(pol);
	}

	/**
	 * Contructeur prenant en compte une ArrayList
	 * 
	 * @param listePoints
	 *            la liste des points saisis par l'utilisateur
	 */
	public Polygone(ArrayList<UnPoint> listePoints) {
		super(listePoints);
		this.nbMemo = this.nbSaisie;
		this.tabMemo = new UnPoint[this.nbMemo];
		for (int i = 0; i < this.nbMemo; i++)
			this.tabMemo[i] = listePoints.get(i);
	}

	// METHODES
	
	/**
	 * TODO m�thode rotation pour le polygone
	 */
	public void rotation(int r) {
		int graviteX=0;
		int graviteY=0;
		for (int i = 0;i<tabMemo.length;i++) {
			graviteX = graviteX + tabMemo[i].x;
			graviteY = graviteY + tabMemo[i].y;
		}
		graviteX=graviteX/nbMemo;
		graviteY=graviteY/nbMemo;
		UnPoint centreGravite = new UnPoint(graviteX,graviteY);
		
		for (int i = 0;i<tabMemo.length;i++) {
			if (r==1) {
				tabMemo[i].rotatePoint(centreGravite, 90);
			}
			else 
				tabMemo[i].rotatePoint(centreGravite, -90);

		}
	}
}
