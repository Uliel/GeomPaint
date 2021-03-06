/**
 * 
 * @authors Frederic Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 * Classe principale
 *
 */

import java.awt.Dimension;

import javax.swing.JFrame;

import paint.Dessin;

public class Principale {
	
	public static void main(String[] args) {
		JFrame fenetre = new JFrame("GeomPaint");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setVisible(true);
		fenetre.setMinimumSize(new Dimension(900, 400));
		fenetre.setResizable(true);
		Dessin d = new Dessin();
		fenetre.setContentPane(d);
		d.setFocusable(true);
		fenetre.pack();
	}
	
}
