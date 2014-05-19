package paint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.BevelBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class MenuDeroulant extends JPopupMenu{
Dessin dessin;
  public MenuDeroulant(Dessin d) {
	  dessin=d;
    ActionListener menuListener = new ActionListener() {
      public void actionPerformed(ActionEvent event) {
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

    this.setLabel("Justification");
    this.setBorder(new BevelBorder(BevelBorder.RAISED));

    addMouseListener(new MousePopupListener());
  }

  // An inner class to check whether mouse events are the popup trigger
  class MousePopupListener extends MouseAdapter {
    public void mousePressed(MouseEvent e) {
      checkPopup(e);
    }

    public void mouseClicked(MouseEvent e) {
      checkPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
      checkPopup(e);
    }

    private void checkPopup(MouseEvent e) {
    }
  }
}