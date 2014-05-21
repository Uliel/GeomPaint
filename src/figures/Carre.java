/**
 * 
 * @authors Frédéric Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 *
 */

package figures;

import java.util.ArrayList;

public class Carre extends Rectangle {
	
  //ATTRIBUTS
	private final static int CARRE_SAISIE = 2;
	private final static int CARRE_MEMO = 4;
	
	//CONSTRUCTEURS
	public Carre() {
		super();
	}
	
	// Constructeur prenant en parametre une ArrayList
	public Carre(ArrayList<UnPoint> listePointsSaisie) {
		this.plein = false;
		// Remplissage du tableau de points de saisie
			this.nbSaisie = listePointsSaisie.size();
			this.tabSaisie = new UnPoint[this.nbSaisie];
			for (int i = 0; i < this.nbSaisie; i++)
				this.tabSaisie[i] = listePointsSaisie.get(i);
			// Remplissage du tableau de points de memorisation
			this.nbMemo = 4;
			this.tabMemo = new UnPoint[this.nbMemo];
			
			//calcul de la longueur d'un cote du carre
			int d = listePointsSaisie.get(0).x - listePointsSaisie.get(1).x;
			if(d < 0)
			{
				d = -d;
			}
			
			// 1er point en haut a gauche, 2nd en bas a droite
			if (listePointsSaisie.get(1).x > listePointsSaisie.get(0).x) 
			{
				if (listePointsSaisie.get(1).y > listePointsSaisie.get(0).y) 
				{
					this.tabMemo[0] = listePointsSaisie.get(0);
					this.tabMemo[1] = new UnPoint(listePointsSaisie.get(0).x + d,
																				listePointsSaisie.get(0).y);
					this.tabMemo[2] = new UnPoint(listePointsSaisie.get(0).x + d,
																				listePointsSaisie.get(0).y + d);
					this.tabMemo[3] = new UnPoint(listePointsSaisie.get(0).x,	
																				listePointsSaisie.get(0).y + d);
				} // 1er point en bas a gauche, 2nd en haut a droite
				else 
				{
					this.tabMemo[0] = new UnPoint(listePointsSaisie.get(0).x,
																				listePointsSaisie.get(0).y - d);
					this.tabMemo[1] = listePointsSaisie.get(0);
					this.tabMemo[2] = new UnPoint(listePointsSaisie.get(0).x + d,
																				listePointsSaisie.get(0).y);
					this.tabMemo[3] = new UnPoint(listePointsSaisie.get(0).x + d,
																				listePointsSaisie.get(0).y - d);
				}
			} // 1er point en haut a droite, 2nd en bas a gauche
			else {
				if (listePointsSaisie.get(1).y > listePointsSaisie.get(0).y) 
				{
					this.tabMemo[0] = new UnPoint(listePointsSaisie.get(0).x ,
																				listePointsSaisie.get(0).y + d);
					this.tabMemo[1] = new UnPoint(listePointsSaisie.get(0).x - d,
																				listePointsSaisie.get(0).y + d);
					this.tabMemo[2] = new UnPoint(listePointsSaisie.get(0).x - d,
																				listePointsSaisie.get(0).y );
					this.tabMemo[3] = listePointsSaisie.get(0);
				} // 1er point en bas a droite, 2nd en haut a gauche
				else 
				{
					this.tabMemo[0] = new UnPoint(listePointsSaisie.get(0).x - d,
																				listePointsSaisie.get(0).y - d);
					this.tabMemo[1] = new UnPoint(listePointsSaisie.get(0).x,
																				listePointsSaisie.get(0).y - d);
					this.tabMemo[2] = listePointsSaisie.get(0);
					this.tabMemo[3] = new UnPoint(listePointsSaisie.get(0).x -d,
																				listePointsSaisie.get(0).y);
				}
			}
	}
}