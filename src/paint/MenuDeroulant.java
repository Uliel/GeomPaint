package paint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.border.BevelBorder;

public class MenuDeroulant extends JPopupMenu{
private Dessin dessin;
private JMenuItem supprimer;
private JMenuItem couper=new JMenuItem("Couper");
private JMenuItem copier=new JMenuItem("Copier");
private JMenuItem coller= new JMenuItem("Coller");

private JMenuItem annuler = new JMenuItem("Annuler", new ImageIcon("images/annuler.png"));
  public MenuDeroulant(Dessin d) {
	  dessin=d;
    ActionListener menuListener = new ActionListener() {
      public void actionPerformed(ActionEvent event) {
    	  if (event.getActionCommand().equals("Quitter")) {
    		  System.exit(0);
    	  }
    	  else if (event.getActionCommand().equals("Exporter en jpg")) {
    		  dessin.exporter("jpg");
    	  }
    	  else if (event.getActionCommand().equals("Exporter en png")) {
    		  dessin.exporter("png");
    	  }
    	  else if (event.getActionCommand().equals("Supprimer")) {
    		  dessin.supprimer();
    	  }
    	  else if (event.getActionCommand().equals("Annuler")) {
    		  dessin.annuler();
    	  }
    	  else if (event.getActionCommand().equals("Copier")) {
    		  dessin.copier();
    	  }
    	  else if (event.getActionCommand().equals("Couper")) {
    		  dessin.couper();
    	  }
    	  else if (event.getActionCommand().equals("Coller")) {
    		  dessin.coller(1);
    	  }



    	  
        //System.out.println("Popup menu item ["
            //+ event.getActionCommand() + "] was pressed.");
      }
    };
    JMenuItem item;
    this.add(item = new JMenuItem("Exporter en jpg", new ImageIcon("images/exporter_jpg2.png")));
    item.addActionListener(menuListener);
    this.add(item = new JMenuItem("Exporter en png", new ImageIcon("images/exporter_png2.png")));
    item.addActionListener(menuListener);
    supprimer = new JMenuItem("Supprimer", new ImageIcon("images/poubelle.png"));
    this.add(item = supprimer );
    item.addActionListener(menuListener);
    this.add(item = annuler);
    item.addActionListener(menuListener);
    this.addSeparator();
    this.add(item = copier);
    item.addActionListener(menuListener);
    this.add(item = couper);
    item.addActionListener(menuListener);
    this.add(item = coller);
    item.addActionListener(menuListener);    
    this.addSeparator();
    this.add(item = new JMenuItem("Quitter"));
    item.addActionListener(menuListener);

    this.setBorder(new BevelBorder(BevelBorder.RAISED));
  }
	public JMenuItem getSupprimer() {
		return supprimer;
	}
	public JMenuItem getAnnuler() {
		return annuler;
	}
	public JMenuItem getCouper() {
		return couper;
	}
	public JMenuItem getCopier() {
		return copier;
	}
	public JMenuItem getColler() {
		return coller;
	}
	
	
  
}