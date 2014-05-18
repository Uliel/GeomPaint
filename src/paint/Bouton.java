package paint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Bouton extends JToggleButton implements MouseListener{
	private int valeur;
	
	public Bouton (int n,ImageIcon i) {
		super(i);
		valeur = n;
		this.addMouseListener(this);

	}

	public int getValeur() {
		return valeur;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Stub de la méthode généré automatiquement

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Stub de la méthode généré automatiquement
		this.setBackground(new Color(191,239,255));
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Stub de la méthode généré automatiquement
		this.setBackground(Color.white);

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Stub de la méthode généré automatiquement
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Stub de la méthode généré automatiquement
		
	}
}
