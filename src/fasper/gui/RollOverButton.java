package fasper.gui;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;

import javax.swing.border.*;

/**
 * RollOverButton
 * 
 */
public class RollOverButton extends JButton {

   private static AlphaComposite c = AlphaComposite.getInstance(
      AlphaComposite.SRC_OVER, 0.5f);
   
   /**
    * 
    */
   public RollOverButton() {
      super();
      setBorder(new EtchedBorder());
      setBorderPainted(false);
      setMargin(new Insets(0, 0, 0, 0));

      setRequestFocusEnabled(false);
      setFocusable(false);
      addMouseListener(new MouseOverHandler());
      setHorizontalTextPosition(SwingConstants.CENTER);
      setVerticalTextPosition(SwingConstants.BOTTOM);
   }

   /**
    * @param text
    */
   public RollOverButton(String text) {
      this();
      this.setText(text);
   }

   /**
    * @param a
    */
   public RollOverButton(Action a) {
      this();
      this.setAction(a);
   }

   /**
    * @param icon
    */
   public RollOverButton(Icon icon) {
      this();
      this.setIcon(icon);
   }

   /**
    * @param text
    * @param icon
    */
   public RollOverButton(String text, Icon icon) {
      this();
      this.setIcon(icon);
      this.setText(text);
   }

   public void paint(Graphics g) {
      if (isEnabled()) {
        super.paint(g);
      }
      else {
        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(c);
        super.paint(g2);
      }
    }

   public boolean isOpaque() {
      return false;
    }

    public void setEnabled(boolean b) {
      super.setEnabled(b);
      setBorderPainted(false);
      repaint();
    }   
   
    /**
     * Make the border visible/invisible on rollovers
     */
    private class MouseOverHandler extends MouseAdapter {
      public void mouseEntered(MouseEvent e) {
        if (isEnabled()) {
          setBorderPainted(true);
        }
      }

      public void mouseExited(MouseEvent e) {
        setBorderPainted(false);
      }
    }
    
}
