/**
 * 
 * @authors Nicolas Gambarini, Sarah Lequeuvre
 *
 */

package figures;

public class Losange extends Polygone {
	
	private final static int LOSANGE_SAISIE = 2;
	private final static int LOSANGE_MEMO = 4;
	
	public Losange() {
		super();
		this.setNbMemo(LOSANGE_MEMO);
		this.setNbSaisie(LOSANGE_SAISIE);
		this.setTabMemo(this.getNbMemo());
		this.setTabSaisie(this.getNbSaisie());
	}

}