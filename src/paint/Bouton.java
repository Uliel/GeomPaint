package paint;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Bouton extends JToggleButton implements MouseListener{
	private int valeur;
	private Border bord;
	private Color coul;
	
	/**
	 * Constructeur à partir de 
	 * @param n un entier
	 * @param i une ImageIcon
	 */
	public Bouton (int n,ImageIcon i) {
		super(i);
		coul=Color.white;
		//Modification de la couleur de fond quand un bouton est selectionné
		UIManager.put("ToggleButton.select", Color.WHITE);
		SwingUtilities.updateComponentTreeUI(this);
		
		valeur = n;
		
		//Modification de la couleur de la bordure
		this.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		
		//On recupere cette bordure dans une variable
		bord=this.getBorder();
		
		//Ajout du mouseListener
		this.addMouseListener(this);
	}
	
	/**
	 * Constructeur à partir de
	 * @param n un entier
	 * @param c une couleur
	 */
	public Bouton (int n,java.awt.Color c) {
		super();
		valeur=n;
		UIManager.put("ToggleButton.select",Color.TRANSLUCENT);
		SwingUtilities.updateComponentTreeUI(this);
		coul=c;
		this.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		this.addMouseListener(this);
		this.setBackground(c);
		bord=this.getBorder();
	}

	public int getValeur() {
		return valeur;
	}
	
	

	public Color getCoul() {
		return coul;
	}
	
	public void setCoul(Color c) {
		this.coul=c;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Stub de la méthode généré automatiquement

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Stub de la méthode généré automatiquement
		if (this.isEnabled()){
			if (coul.getBlue()<150&&
					coul.getRed()<150&&
					coul.getGreen()<150)
				this.setBorder(BorderFactory.createLineBorder(Color.orange, 2));
			else 
				this.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Stub de la méthode généré automatiquement
		if (!this.isSelected())
			this.setBorder(bord);
		else 
			if (coul.getBlue()<150&&
					coul.getRed()<150&&
					coul.getGreen()<150)
				this.setBorder(BorderFactory.createLineBorder(new Color(255,230,90), 2));
			else 
				this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
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
