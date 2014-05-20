package paint;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
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
	private BoiteOutils outils;
	private boolean dessiner;
	private JMenuItem annuler = new JMenuItem("Annuler");
	private JMenuItem copier =new JMenuItem("Copier");
	private JMenuItem couper = new JMenuItem("Couper");
	private JMenuItem coller = new JMenuItem("Coller");
	private JMenuItem supprimer = new JMenuItem("Supprimer");
	private JMenuItem remplir = new JMenuItem("Remplir/Vider");
	private JMenuItem rotation = new JMenuItem("Rotation");
	private JMenuItem dupliquer = new JMenuItem("Dupliquer");
	private JMenuItem couleurs = new JMenuItem("Couleurs ...");
	private JMenuItem nouveau = new JMenuItem("Nouveau");
	private JMenuItem exporterJPG = new JMenuItem("Exporter en JPG");
	private JMenuItem exporterPNG = new JMenuItem("Exporter en PNG");
	private JMenuItem quitter = new JMenuItem("Quitter");
	private JMenuItem [] tabMenu = {annuler,copier,couper,coller,supprimer,remplir,
			rotation,dupliquer,couleurs,nouveau,exporterJPG,exporterPNG,quitter};

	/**
	 * Constructeur qui permet d'initialiser le menu
	 * 
	 * @param pl
	 */
	public UnMenu(Dessin des, BoiteOutils o) {
		this.d = des;
		outils = o;
		JMenu jm1 = new JMenu("Fichier");
		jm1.setFont(new Font("Arial", Font.BOLD, 14));
		JMenu jm2 = new JMenu("Edition");
		jm2.setFont(new Font("Arial", Font.BOLD, 14));
		JMenu jm3 = new JMenu("Image");
		jm3.setFont(new Font("Arial", Font.BOLD, 14));
		
		ActionListener menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dessiner = false;
				outils.desactiverOutils();
				outils.desactiverCoul();
				outils.desactiverRotation();
				outils.getSelectionner().doClick();
				if (((JMenuItem)(e.getSource())).getText().equals("Quitter")) {
					System.exit(0);
				}
				else if (((JMenuItem)(e.getSource())).getText().equals("Supprimer")) {
					outils.getSupprimer().doClick();
				}
				else if (((JMenuItem)(e.getSource())).getText().equals("Remplir/Vider")) {
					outils.getRemplir().doClick();
				}
				else if (((JMenuItem)(e.getSource())).getText().equals("Exporter en JPG")) {
					outils.getExporterJPG().doClick();
				}
				else if (((JMenuItem)(e.getSource())).getText().equals("Exporter en PNG")) {
					outils.getExporterPNG().doClick();
				}
				else if (((JMenuItem)(e.getSource())).getText().equals("Couleurs ...")) {
					outils.getPalette().doClick();
					
				}
				
			}
		};
		for (int i=0;i<tabMenu.length;i++) {
			tabMenu[i].addActionListener(menuListener);
		}		
		
		jm2.add(annuler);
		jm2.add(copier);
		jm2.add(couper);
		jm2.add(coller);
		jm2.add(supprimer);
		jm3.add(remplir);
		jm3.add(rotation);
		jm3.add(dupliquer);
		jm3.add(couleurs);
		jm1.add(nouveau);
		jm1.add(exporterJPG);
		jm1.add(exporterPNG);
		jm1.addSeparator();
		jm1.add(quitter);

		// On associe les deux JMenu à la JMenuBar
		this.add(jm1);
		this.add(new JLabel("  "));
		this.add(jm2);
		this.add(new JLabel("  "));
		this.add(jm3);
	}

	public JMenuItem getSupprimer() {
		return supprimer;
	}
	
	
}

