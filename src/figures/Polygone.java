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
		for (int i = 0;i<tabMemo.length;i++) {
			int tmp;
			tmp=tabMemo[i].x;
			tabMemo[i].x=tabMemo[i].y;
			tabMemo[i].y=-tmp;
		}
	}
}