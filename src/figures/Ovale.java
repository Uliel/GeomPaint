/**
 * 
 * @authors Frédéric Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 *
 */

package figures;


public class Ovale extends FigureGeom {
	
	private final static int OVALE_POINTS = 3;
	
	public Ovale() {
		super();
		this.setNbSaisie(OVALE_POINTS);
		this.setNbMemo(OVALE_POINTS);
		this.setTabSaisie(this.getNbSaisie());
		this.setTabMemo(this.getNbMemo());
	}
	
	public Ovale(Ovale ov) {
		super(ov);
	}

	@Override
	public void rotation(int r) {
		// TODO Auto-generated method stub
		
	}

}