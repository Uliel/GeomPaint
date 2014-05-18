package paint;

import javax.swing.*;

public class Bouton extends JToggleButton {
	private int valeur;
	
	public Bouton (int n,ImageIcon i) {
		super(i);
		valeur = n;
	}

	public int getValeur() {
		return valeur;
	}

	
}
