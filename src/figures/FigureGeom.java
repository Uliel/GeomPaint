/**
 * 
 * @authors Frédéric Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 *
 */

package figures;

import java.awt.Color;
import java.awt.Point;

public class FigureGeom {

	private Color couleur;
	private boolean plein;
	private boolean selection;
	private UnPoint[] tabMemo;
	private int nbMemo;
	private UnPoint[] tabSaisie;
	private int nbSaisie;

	public FigureGeom() {
		this.couleur = Color.BLACK;
		this.plein = false;
		this.selection = true;
	}

	public int getNbSaisie() {
		return nbSaisie;
	}

	public void setNbSaisie(int s) {
		this.nbSaisie = s;
	}

	public int getNbMemo() {
		return nbMemo;
	}

	public void setNbMemo(int s) {
		this.nbMemo = s;
	}

	public UnPoint[] getTabMemo() {
		return this.tabMemo;
	}

	public void setTabMemo(int taille) {
		this.tabMemo = new UnPoint[taille];
	}

	public UnPoint[] getTabSaisie() {
		return this.tabSaisie;
	}

	public void setTabSaisie(int taille) {
		this.tabSaisie = new UnPoint[taille];
	}

	public Color getCouleur() {
		return this.couleur;
	}

	public void setCouleur(Color coul) {
		this.couleur = coul;
	}

	public boolean getSelection() {
		return this.selection;
	}

	public void ajouterMemo(int pos, int x, int y) {
		this.tabMemo[pos] = new UnPoint(x, y);
	}

	public void ajouterSaisie(int pos, int x, int y) {
		this.tabSaisie[pos] = new UnPoint(x, y);
	}

	public void translater(int nouvX, int nouvY) {
		for (int i = 0; i < getTabMemo().length; i++) {
			this.getTabMemo()[i].move(nouvX, nouvY);
		}
		for (int i = 0; i < getTabSaisie().length; i++) {
			this.getTabSaisie()[i].move(nouvX, nouvY);
		}
	}

	public void remplir() {
		if (this.plein)
			this.plein = false;
		else
			this.plein = true;
	}

	public void selectionner() {
		if (this.selection)
			this.selection = false;
		else
			this.selection = true;
	}

	public void modifierForme(Point modif, int nouvX, int nouvY) {
		boolean trouve = false;
		for (int i = 0; i < getTabMemo().length && !trouve; i++)
			if (this.getTabMemo()[i] == modif) {
				trouve = true;
				this.getTabMemo()[i].move(nouvX, nouvY);
			}
		trouve = false;
		for (int i = 0; i < getTabSaisie().length && !trouve; i++) {
			if (this.getTabSaisie()[i] == modif) {
				trouve = true;
				this.getTabSaisie()[i].move(nouvX, nouvY);
			}
		}
	}

}