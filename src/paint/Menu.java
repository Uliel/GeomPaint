import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Classe Menu qui permet de choisir la couleur et la taille du plateau
 * 
 * @author Nicolas Gambarini
 * 
 * yabadou badouuuuuuu
 * bouh
 * 
 */
public class Menu {
	/**
	 * Attribut Dessin auquel est associé le menu
	 */
	private Dessin d;

	/**
	 * Constructeur qui permet d'initialiser le plateau de jeu
	 * 
	 * @param pl
	 */
	public Menu(Dessin des) {
		this.d = des;
	}

	/**
	 * Methode sans paramètre
	 * 
	 * @return JMenuBar
	 */
	public JMenuBar creerBarreMenu() {
		// On cree une JMenuBar
		JMenuBar bar = new JMenuBar();
		// Un JMenu "Couleur"
		JMenu jm1 = new JMenu("Couleur");
		jm1.setFont(new Font("Arial", Font.BOLD, 20));
		// Un JMenu "Taille"
		JMenu jm2 = new JMenu("Taille");
		jm2.setFont(new Font("Arial", Font.BOLD, 20));
		// On cree des JMenuItem pour des tailles de 5 à 10 qu'on associe au
		// JMenu taille
		for (int i = 5; i < 11; i++) {
			jm2.add(creerItemTaille(i));
		}
		// Puis des JMenuItem pour les différentes couleurs qu'on associe au
		// JMenu couleur
		jm1.add(creerItemCouleur("Vert"));
		jm1.add(creerItemCouleur("Rouge"));
		jm1.add(creerItemCouleur("Bleu"));
		jm1.add(creerItemCouleur("Jaune"));
		jm1.add(creerItemCouleur("Noir&Blanc"));
		jm1.add(creerItemCouleur("Cyan"));
		jm1.add(creerItemCouleur("Magenta"));
		jm1.add(creerItemCouleur("Orange"));
		jm1.add(creerItemCouleur("Violet"));
		// On associe les deux JMenu à la JMenuBar
		bar.add(jm1);
		col = new JLabel("     ");
		col.setOpaque(true);
		col.setBackground(p.getCouleur());
		bar.add(col);
		bar.add(new JLabel("      "));
		bar.add(jm2);
		taille = new JLabel(" : " + Integer.toString(p.getTaille()));
		taille.setFont(new Font("Arial", Font.BOLD, 19));
		bar.add(taille);
		return bar;
	}

	/**
	 * Methode qui permet de créer les JMenuItem du JMenu taille
	 * 
	 * @param n
	 *            la taille
	 * @return JMenuItem
	 */
	public JMenuItem creerItemTaille(final int n) {
		// On cree le JMenuItem
		final JMenuItem jMenuItem = new JMenuItem(Integer.toString(n));
		// On cree l'ActionListener correspondant et on lui associe
		jMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p.setTaille(n);
				taille.setText(" : " + Integer.toString(p.getTaille()));
			}
		});
		jMenuItem.setFont(new Font("Arial", Font.BOLD, 19));
		return jMenuItem;
	}

	/**
	 * Methode qui permet de créer les JMenuItem du JMenu couleur
	 * 
	 * @param c
	 *            la couleur
	 * @return JMenuItem
	 */
	public JMenuItem creerItemCouleur(final String c) {
		// On cree le JMenuItem
		final JMenuItem jMenuItem = new JMenuItem(c);
		// puis l'ActionListener qu'on lui assoie
		jMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// On teste les différentes couleurs
				// et on effectue les différentes modifications associées
				if (c.equals("Rouge")) {
					p.setCouleur(new Color(100, 0, 0));
					p.getPres().setBackground(new Color(100, 0, 0));
					p.getBout().setBackground(new Color(100, 0, 0));
					p.getDebPartie().setForeground(Color.white);
				} else if (c.equals("Vert")) {
					p.setCouleur(new Color(0, 100, 0));
					p.getPres().setBackground(new Color(0, 100, 0));
					p.getBout().setBackground(new Color(0, 100, 0));
					p.getDebPartie().setForeground(Color.white);
				} else if (c.equals("Bleu")) {
					p.setCouleur(new Color(0, 0, 100));
					p.getPres().setBackground(new Color(0, 0, 100));
					p.getBout().setBackground(new Color(0, 0, 100));
					p.getDebPartie().setForeground(Color.white);
				} else if (c.equals("Jaune")) {
					p.setCouleur(new Color(120, 120, 0));
					p.getPres().setBackground(new Color(120, 120, 0));
					p.getBout().setBackground(new Color(120, 120, 0));
					p.getDebPartie().setForeground(Color.black);
				} else if (c.equals("Noir&Blanc")) {
					p.setCouleur(new Color(0, 0, 0));
					p.getPres().setBackground(new Color(0, 0, 0));
					p.getBout().setBackground(new Color(0, 0, 0));
					p.getDebPartie().setForeground(Color.white);
				} else if (c.equals("Cyan")) {
					p.setCouleur(new Color(0, 134, 139));
					p.getPres().setBackground(new Color(0, 134, 139));
					p.getBout().setBackground(new Color(0, 134, 139));
					p.getDebPartie().setForeground(Color.black);
				} else if (c.equals("Magenta")) {
					p.setCouleur(new Color(139, 0, 139));
					p.getPres().setBackground(new Color(139, 0, 139));
					p.getBout().setBackground(new Color(139, 0, 139));
					p.getDebPartie().setForeground(Color.white);
				} else if (c.equals("Orange")) {
					p.setCouleur(new Color(205, 55, 0));
					p.getPres().setBackground(new Color(205, 55, 0));
					p.getBout().setBackground(new Color(205, 55, 0));
					p.getDebPartie().setForeground(Color.white);
				} else if (c.equals("Violet")) {
					p.setCouleur(new Color(85, 26, 139));
					p.getPres().setBackground(new Color(85, 26, 139));
					p.getBout().setBackground(new Color(85, 26, 139));
					p.getDebPartie().setForeground(Color.white);
				}
				// On modifie la couleur du JLable col correspond à l'affichage
				// de la couleur
				col.setBackground(p.getCouleur());
			}
		});
		// On teste la couleur pour modifier l'affichage des JMenuItem du JMenu
		// couleur
		if (c.equals("Rouge")) {
			jMenuItem.setForeground(new Color(100, 0, 0));
		} else if (c.equals("Vert")) {
			jMenuItem.setForeground(new Color(0, 100, 0));
		} else if (c.equals("Bleu")) {
			jMenuItem.setForeground(new Color(0, 0, 100));
		} else if (c.equals("Jaune")) {
			jMenuItem.setForeground(new Color(120, 120, 0));
		}

		else if (c.equals("Noir&Blanc")) {
			jMenuItem.setForeground(new Color(0, 0, 0));
		} else if (c.equals("Cyan")) {
			jMenuItem.setForeground(new Color(0, 134, 139));
		} else if (c.equals("Magenta")) {
			jMenuItem.setForeground(new Color(139, 0, 139));
		} else if (c.equals("Orange")) {
			jMenuItem.setForeground(new Color(205, 55, 0));
		} else if (c.equals("Violet")) {
			jMenuItem.setForeground(new Color(85, 26, 139));
		}

		jMenuItem.setFont(new Font("Arial", Font.BOLD, 19));
		return jMenuItem;
	}

}

	
	public boolean getDessiner() {
		return this.dessiner;
	}
	
	public void setDessiner(boolean d) {
		this.dessiner = d;
	}

}
