/**
 * 
 * @authors Nicolas Gambarini, Sarah Lequeuvre
 *
 */

package figures;

public class Triangle extends Polygone {
	
	private final static int TRIANGLE_POINTS = 3;
	
	public Triangle() {
		super();
		this.setNbSaisie(TRIANGLE_POINTS);
		this.setNbMemo(TRIANGLE_POINTS);
		this.setTabSaisie(this.getNbSaisie());
		this.setTabMemo(this.getNbMemo());
	}

}