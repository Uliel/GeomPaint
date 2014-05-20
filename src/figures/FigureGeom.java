/**
 * 
 * @authors Fr�d�ric Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 *
 */

package figures;

import java.awt.Color;
import java.awt.Point;
import java.util.Observable;
import java.util.ArrayList;

public class FigureGeom extends Observable {
	
	// ATTRIBUTS
	private Color couleur;
	protected boolean plein;
	protected boolean selection;
	protected UnPoint[] tabMemo;
	protected int nbMemo;
	protected UnPoint[] tabSaisie;
	protected int nbSaisie;

	
	// CONSTRUCTEURS
		// Constructeur vide
	public FigureGeom() {
		this.plein = false;
		this.selection = true;
	}
	
		// Constructeur prenant en paramètres une ArrayList
	public FigureGeom(ArrayList<UnPoint> listePointsSaisie) {
		this.plein = false;
		this.selection = true;
		this.nbSaisie = listePointsSaisie.size();
		this.tabSaisie = new UnPoint[this.nbSaisie];
		for(int i = 0 ; i < this.nbSaisie ; i++)
			this.tabSaisie[i] = listePointsSaisie.get(i);
	}
	
	
	// ACCESSEURS
	
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
	
	public void setSelection(boolean b) {
		this.selection = b;
	}
	
	public boolean getPlein() {
		return this.plein;
	}
	
	public void setPlein(boolean b) {
		this.plein = b;
	}

	public void ajouterMemo(int pos, int x, int y) {
		this.tabMemo[pos] = new UnPoint(x, y);
	}

	public void ajouterSaisie(int pos, int x, int y) {
		this.tabSaisie[pos] = new UnPoint(x, y);
	}

	public void translater(int nouvX, int nouvY) {
		System.out.println(nouvX + "  " + nouvY);
		for (int i = 0; i < getTabMemo().length; i++) {
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