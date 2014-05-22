package paint;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * 
 * @author Frederic Euriot, Nicolas Gambarini, Sarah Lequeuvre, Sylvain Riess
 * 
 */
public class UnMenu extends JMenuBar {

	// ATTRIBUTS
	private Dessin d;

	private JMenuItem annuler = new JMenuItem("Annuler");
	private JMenuItem copier = new JMenuItem("Copier");
	private JMenuItem couper = new JMenuItem("Couper");
	private JMenuItem coller = new JMenuItem("Coller");
	private JMenuItem supprimer = new JMenuItem("Supprimer");
	private JMenuItem remplir = new JMenuItem("Remplir/Vider");
	private JMenuItem dupliquer = new JMenuItem("Dupliquer");
	private JMenuItem couleurs = new JMenuItem("Couleurs ...");
	private JMenuItem toutSelectionner = new JMenuItem("Tout selectionner");
	private JMenuItem nouveau = new JMenuItem("Nouveau");
	private JMenu rotation = new JMenu("Rotation");
	private JMenuItem exporterJPG = new JMenuItem("Exporter en JPG");
	private JMenuItem exporterPNG = new JMenuItem("Exporter en PNG");
	private JMenuItem quitter = new JMenuItem("Quitter");
	private JMenuItem rotationDroite = new JMenuItem("Rotation vers la droite");
	private JMenuItem rotationGauche = new JMenuItem("Rotation vers la gauche");
	private JMenuItem[] tabMenu = { annuler, copier, couper, coller, supprimer,
			remplir, rotation, dupliquer, couleurs, nouveau, exporterJPG,
			exporterPNG, quitter, toutSelectionner };

	// CONSTRUCTEUR
	/**
	 * Constructeur qui permet d'initialiser le menu à partir de
	 * @param des attribut Dessin qui permet de lier le menu deroulant au dessin
	 */
	public UnMenu(Dessin des) {
		this.d = des;
		//Ajout des JMenus
		JMenu fichier = new JMenu("Fichier");
		fichier.setFont(new Font("Arial", Font.BOLD, 14));
		JMenu edition = new JMenu("Edition");
		edition.setFont(new Font("Arial", Font.BOLD, 14));
		JMenu image = new JMenu("Image");
		image.setFont(new Font("Arial", Font.BOLD, 14));
		rotation.add(rotationDroite);
		rotation.add(rotationGauche);

		//ActionListner lie au menu
		ActionListener menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JMenuItem) (e.getSource())).getText().equals("Quitter")) {
					System.exit(0);
				} else if (((JMenuItem) (e.getSource())).getText().equals(
						"Supprimer")) {
					d.supprimer();
				} else if (((JMenuItem) (e.getSource())).getText().equals(
						"Remplir/Vider")) {
					d.remplir();
				} else if (((JMenuItem) (e.getSource())).getText().equals(
						"Exporter en JPG")) {
					d.exporter("jpg");
				} else if (((JMenuItem) (e.getSource())).getText().equals(
						"Exporter en PNG")) {
					d.exporter("png");
				} else if (((JMenuItem) (e.getSource())).getText().equals(
						"Nouveau")) {
					// Boîte du message préventif
					String[] choix = { "Oui", "Non" };
					int res = JOptionPane.showOptionDialog(null,
							"Voulez-vous vraiment tout effacer ?",
							"Attention !", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, new ImageIcon(
									"images/attention.png"), choix, choix[1]);
					if (res == JOptionPane.YES_OPTION) {
						d.nouveau();
					}
				} else if (((JMenuItem) (e.getSource())).getText().equals(
						"Rotation vers la droite")) {
					d.rotation(1);
				} else if (((JMenuItem) (e.getSource())).getText().equals(
						"Rotation vers la gauche")) {
					d.rotation(2);
				} else if (((JMenuItem) (e.getSource())).getText().equals(
						"Dupliquer")) {
					d.dupliquer();
				} else if (((JMenuItem) (e.getSource())).getText().equals(
						"Tout selectionner")) {
					d.toutSelectionner();
				} else if (((JMenuItem) (e.getSource())).getText().equals(
						"Annuler")) {
					d.annuler();
				} else if (((JMenuItem) (e.getSource())).getText().equals(
						"Copier")) {
					d.copier();
				} else if (((JMenuItem) (e.getSource())).getText().equals(
						"Couper")) {
					d.couper();
				} else if (((JMenuItem) (e.getSource())).getText().equals(
						"Coller")) {
					d.coller(2);
				}
			}
		};
		//on ajoute l'actionListener
		for (int i = 0; i < tabMenu.length; i++) {
			if (!tabMenu[i].getText().equals("Rotation")) {
				tabMenu[i].addActionListener(menuListener);
			}
			
		}
		rotationDroite.addActionListener(menuListener);
		rotationGauche.addActionListener(menuListener);
		
		//Raccourcis clavier
		copier.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				KeyEvent.CTRL_MASK));
		coller.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				KeyEvent.CTRL_MASK));
		couper.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				KeyEvent.CTRL_MASK));
		toutSelectionner.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				KeyEvent.CTRL_MASK));
		annuler.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
				KeyEvent.CTRL_MASK));
		quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				KeyEvent.CTRL_MASK));
		supprimer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				KeyEvent.CTRL_MASK));
		nouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				KeyEvent.CTRL_MASK));
		exporterJPG.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				KeyEvent.CTRL_MASK));
		exporterPNG.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
				KeyEvent.CTRL_MASK));

		//On ajoute les JMenuItem
		edition.add(annuler);
		edition.add(toutSelectionner);
		edition.addSeparator();
		edition.add(copier);
		edition.add(couper);
		edition.add(coller);
		edition.add(supprimer);
		image.add(remplir);
		image.add(rotation);
		image.add(dupliquer);
		fichier.add(nouveau);
		fichier.add(exporterJPG);
		fichier.add(exporterPNG);
		fichier.addSeparator();
		fichier.add(quitter);

		// On associe les deux JMenu à la JMenuBar
		this.add(fichier);
		this.add(new JLabel("  "));
		this.add(edition);
		this.add(new JLabel("  "));
		this.add(image);
	}

	// ACCESSEURS

	public JMenuItem getSupprimer() {
		return supprimer;
	}

	public JMenuItem getDupliquer() {
		return dupliquer;
	}

	public JMenuItem getAnnuler() {
		return annuler;
	}

	public JMenuItem getCopier() {
		return copier;
	}

	public JMenuItem getCouper() {
		return couper;
	}

	public JMenuItem getColler() {
		return coller;
	}

}
