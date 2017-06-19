package fasper.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

/**
 * PopupListener
 */
public class PopupListener extends MouseAdapter {
   JPopupMenu popup;
   
   public PopupListener() {
      super();
   }
   
   public PopupListener(JPopupMenu _jpm){
      super();
      popup = _jpm;
   }
   public void mousePressed(MouseEvent e) {
      showPopup(e);
   }
   public void mouseReleased(MouseEvent e) {
      showPopup(e);
   }
   private void showPopup(MouseEvent e) {
      if (e.isPopupTrigger())
         popup.show(e.getComponent(), e.getX(), e.getY());
   }
}
