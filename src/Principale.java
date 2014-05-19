/**
 * 
 * @authors Nicolas Gambarini, Sarah Lequeuvre
 *
 */

import javax.swing.JFrame;
import paint.Dessin;
import paint.MenuDeroulant;
import figures.*;

public class Principale {
	
	public static void main(String[] args) {
		JFrame fenetre = new JFrame("GeomPaint");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setVisible(true);
		fenetre.setResizable(true);
		Dessin d = new Dessin();
		fenetre.setContentPane(d);
		d.setFocusable(true);
		fenetre.pack();
	}
	
}
