/**
 * 
 * @authors Frédéric Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 *
 */

package figures;

public class Rectangle extends Polygone {
	
	private final static int RECTANGLE_SAISIE = 2;
	private final static int RECTANGLE_MEMO = 4;
	
	public Rectangle() {
		super();
		this.setNbMemo(RECTANGLE_MEMO);
		this.setNbSaisie(RECTANGLE_SAISIE);
		this.setTabMemo(this.getNbMemo());
		this.setTabSaisie(this.getNbSaisie());
	}

}