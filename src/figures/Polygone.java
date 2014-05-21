/**
 * 
 * @authors Fr�d�ric Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 *
 */

package figures;

import java.util.ArrayList;

public class Polygone extends FigureGeom {
	
	// CONSTRUCTEURS
		// Constructeur vide
	public Polygone() {
		super();
		
	}
	
		// Constructeur prenant en paramètre une ArrayList
	public Polygone(ArrayList<UnPoint> listePoints) {
		super(listePoints);
		this.nbMemo = this.nbSaisie;
		this.tabMemo = new UnPoint[this.nbMemo];
		for(int i = 0 ; i < this.nbMemo ; i++)
			this.tabMemo[i] = listePoints.get(i);
	}
	
	public Polygone(Polygone pol) {
		super(pol);
	}

	@Override
	public void rotation(int r) {
		// TODO Auto-generated method stub
		
	}
}