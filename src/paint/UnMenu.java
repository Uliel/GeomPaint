package paint;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * 
 * @author Nicolas Gambarini
 * 
 */
public class UnMenu extends JMenuBar {
	/**
	 * Attribut Plateau auquel est associé le menu
	 */
	private Dessin d;

	/**
	 * Constructeur qui permet d'initialiser le menu
	 * 
	 * @param pl
	 */
	public UnMenu(Dessin des) {
		this.d = des;
		// Un JMenu "Couleur"
		JMenu jm1 = new JMenu("Couleur");
		jm1.setFont(new Font("Arial", Font.BOLD, 20));
		// Un JMenu "Taille"
		JMenu jm2 = new JMenu("Taille");
		jm2.setFont(new Font("Arial", Font.BOLD, 20));
		// On cree des JMenuItem pour des tailles de 5 à 10 qu'on associe au
		// JMenu taille
		for (int i = 5; i < 11; i++) {
			jm2.add(new JMenuItem("essai"));
		}
		// Puis des JMenuItem pour les différentes couleurs qu'on associe au
		// JMenu couleur
		jm1.add(new JMenuItem("essai"));
		jm1.add(new JMenuItem("essai"));
		jm1.add(new JMenuItem("essai"));
		jm1.add(new JMenuItem("essai"));
		jm1.add(new JMenuItem("essai"));
		jm1.add(new JMenuItem("essai"));
		jm1.add(new JMenuItem("essai"));
		jm1.add(new JMenuItem("essai"));
		jm1.add(new JMenuItem("essai"));
		// On associe les deux JMenu à la JMenuBar
		this.add(jm1);
		this.add(new JLabel("      "));
		this.add(jm2);
	}
}

