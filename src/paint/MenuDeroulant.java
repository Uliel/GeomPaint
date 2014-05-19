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
Dessin dessin;
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


    	  
        //System.out.println("Popup menu item ["
            //+ event.getActionCommand() + "] was pressed.");
      }
    };
    JMenuItem item;
    this.add(item = new JMenuItem("Exporter en jpg", new ImageIcon("images/exporter_jpg2.png")));
    item.setHorizontalTextPosition(JMenuItem.RIGHT);
    item.addActionListener(menuListener);
    this.add(item = new JMenuItem("Exporter en png", new ImageIcon("images/exporter_png2.png")));
    item.setHorizontalTextPosition(JMenuItem.RIGHT);
    item.addActionListener(menuListener);
    this.add(item = new JMenuItem("Supprimer", new ImageIcon("images/poubelle.png")));
    item.setHorizontalTextPosition(JMenuItem.RIGHT);
    item.addActionListener(menuListener);
    this.add(item = new JMenuItem("Annuler", new ImageIcon("images/annuler.png")));
    item.setHorizontalTextPosition(JMenuItem.RIGHT);
    item.addActionListener(menuListener);
    this.addSeparator();
    this.add(item = new JMenuItem("Quitter"));
    item.addActionListener(menuListener);

    this.setBorder(new BevelBorder(BevelBorder.RAISED));
  }

}