/**
 * 
 * @authors Frederic Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 *
 */

package figures;

import java.awt.Color;
import java.util.ArrayList;

public abstract class FigureGeom implements Cloneable {

	// ATTRIBUTS
	private Color couleur;
	protected int epaisseur = 1;
	protected boolean plein;
	protected UnPoint[] tabMemo;
	protected int nbMemo;
	protected UnPoint[] tabSaisie;
	protected int nbSaisie;

	// CONSTRUCTEURS
	
	/**
	 * Contructeur vide
	 */
	public FigureGeom() {
		this.plein = false;
	}

	/**
	 * Constructeur par copie
	 * 
	 * @param fig
	 *            la figure à copier
	 */
	public FigureGeom(FigureGeom fig) {
		this.plein = fig.getPlein();
		this.couleur = fig.getCouleur();
		this.epaisseur = fig.getEpaisseur();
		this.nbMemo = fig.getNbMemo();
		this.tabMemo = new UnPoint[nbMemo];
		UnPoint[] tab = fig.getTabMemo();
		for (int i = 0; i < nbMemo; i++) {
			tabMemo[i] = new UnPoint(tab[i].x + 20, tab[i].y + 20);
		}
		this.nbSaisie = fig.getNbSaisie();
		this.tabSaisie = new UnPoint[nbSaisie];
		tab = fig.getTabSaisie();
		for (int i = 0; i < nbSaisie; i++) {
			tabSaisie[i] = new UnPoint(tab[i].x + 20, tab[i].y + 20);
		}
	}

	/**
	 * Constructeur prenant en parametre une ArrayList
	 * 
	 * @param listePointsSaisie
	 *            la liste des points saisis par l'utilisateur
	 */
	public FigureGeom(ArrayList<UnPoint> listePointsSaisie) {
		this.plein = false;
		this.nbSaisie = listePointsSaisie.size();
		this.tabSaisie = new UnPoint[this.nbSaisie];
		for (int i = 0; i < this.nbSaisie; i++)
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

	public int getEpaisseur() {
		return epaisseur;
	}

	public void setEpaisseur(int epaisseur) {
		this.epaisseur = epaisseur;
	}

	public boolean getPlein() {
		return this.plein;
	}

	public void setPlein(boolean b) {
		this.plein = b;
	}

	// METHODES

	/**
	 * Deplace les points de memorisation de la figure
	 * 
	 * @param nouvX
	 *            la nouvelle abscisse des points
	 * @param nouvY
	 *            la nouvelle ordonnee des points
	 */
	public void translater(int nouvX, int nouvY) {
		for (int i = 0; i < getTabMemo().length; i++) {
			this.getTabMemo()[i].deplacerPt(nouvX, nouvY);
		}
	}

	/**
	 * Remplit ou vide la figure en fonction de l'attribut plein
	 */
	public void remplir() {
		if (this.plein)
			this.plein = false;
		else
			this.plein = true;
	}

	/**
	 * Copie un objet sans copier la reference
	 */
	public FigureGeom clone() throws CloneNotSupportedException {
		return (FigureGeom) super.clone();
	}

	public abstract void rotation(int r);

}
