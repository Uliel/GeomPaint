package paint;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import figures.Cercle;

/**
 * Classe Menu qui permet de choisir la couleur et la taille du plateau
 * 
 * @author Nicolas Gambarini
 * 
 * esssssaaiiii
 * 
 */
public class Menu extends JPanel{
	/**
	 * Attribut Dessin auquel est associé le menu
	 */
	private Dessin d;
	private boolean dessiner;
	private JToolBar formes=new JToolBar("Formes");

	/**
	 * Constructeur qui permet d'initialiser le plateau de jeu
	 * 
	 * @param pl
	 */
	public Menu() {
		formes.setLayout(new GridLayout(2,4));
		JButton cercle = new JButton(new ImageIcon("images/cercle.jpg"));
		JButton rectangle = new JButton(new ImageIcon("images/rectangle.jpg"));
		JButton carre = new JButton(new ImageIcon("images/carre.jpg"));
		JButton triangle = new JButton(new ImageIcon("images/triangle.jpg"));
		JButton elipse = new JButton(new ImageIcon("images/ovale.jpg"));
		JButton polygone = new JButton(new ImageIcon("images/polygone.jpg"));
		JButton losange = new JButton(new ImageIcon("images/losange.jpg"));
		JButton trait = new JButton(new ImageIcon("images/trait.jpg"));
		
		cercle.setBackground(Color.white);
		rectangle.setBackground(Color.white);
		carre.setBackground(Color.white);
		triangle.setBackground(Color.white);
		elipse.setBackground(Color.white);
		polygone.setBackground(Color.white);
		losange.setBackground(Color.white);
		trait.setBackground(Color.white);
		
		ActionListener dessinCercle = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				dessiner = true;
			}
		};
		
		cercle.addActionListener(dessinCercle);
		formes.add(cercle);	
		formes.add(rectangle);
		formes.add(triangle);
		formes.add(carre);
		formes.add(elipse);
		formes.add(polygone);
		formes.add(losange);
		formes.add(trait);
		this.add(formes);
		}
	
	public boolean getDessiner() {
		return this.dessiner;
	}
	
	public void setDessiner(boolean d) {
		this.dessiner = d;
	}

}
