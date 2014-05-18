package paint;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Bouton extends JToggleButton implements MouseListener{
	private int valeur;
	private Border bord;
	
	public Bouton (int n,ImageIcon i) {
		super(i);
		UIManager.put("ToggleButton.select", Color.WHITE);
		SwingUtilities.updateComponentTreeUI(this);
		valeur = n;
		this.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		bord=this.getBorder();
		this.addMouseListener(this);
	}
	
	public Bouton (java.awt.Color c) {
		super();
		this.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		this.addMouseListener(this);
		this.setBackground(c);
		bord=this.getBorder();
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
		if (!this.isSelected())
		this.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Stub de la méthode généré automatiquement
		if (!this.isSelected())
			this.setBorder(bord);
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
