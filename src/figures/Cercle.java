/**
 * 
 * @authors Frédéric Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 *
 */

package figures;

public class Cercle extends Ovale {
	
	private final static int CERCLE_POINTS = 2;
	
	public Cercle() {
		super();
		this.setNbSaisie(CERCLE_POINTS);
		this.setNbMemo(CERCLE_POINTS);
		this.setTabSaisie(this.getNbSaisie());
		this.setTabMemo(this.getNbMemo());
	}
}
