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
	

}
