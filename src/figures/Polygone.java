/**
 * 
 * @authors Frédéric Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 *
 */

package figures;

public class Polygone extends FigureGeom {
	
	public Polygone() {
		super();
	}
	
	public Polygone(int pts) {
		super();
		this.setNbSaisie(pts);
		this.setNbMemo(pts);
		this.setTabSaisie(this.getNbSaisie());
		this.setTabMemo(this.getNbMemo());
	}

}