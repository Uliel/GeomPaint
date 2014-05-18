package paint;

import javax.swing.*;

public class Bouton extends JToggleButton {
	private String valeur;
	
	public Bouton (String s,ImageIcon i) {
		super(i);
		valeur = s;
	}

	public String getValeur() {
		return valeur;
	}

	
}
