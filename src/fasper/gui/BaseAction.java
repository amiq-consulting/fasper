package fasper.gui;

import java.awt.event.*;
import javax.swing.*;

import fasper.gui.*;

/**
 * BaseAction
 */
class BaseAction extends AbstractAction {
   protected ModelHub model;
   protected KeyStroke shortcut = null;

   BaseAction(ModelHub _parent, String _name, String _icon) {
      super(_name, Util.getImageIcon(_icon));
      model = _parent;
   }

   BaseAction(ModelHub _parent, String _name) {
      super(_name);
      model = _parent;
   }
   
   BaseAction(String _name, String _icon) {
      super(_name, Util.getImageIcon(_icon));
   }
   
   RollOverButton getButton() {
      RollOverButton _result = new RollOverButton(this);
      _result.setFocusable(false);
      return _result;
   }
   
   JMenuItem getMenuItem() {
      JMenuItem _result = new JMenuItem(this);
      if (shortcut != null)
         _result.setAccelerator(shortcut);
      return _result;
   }
   
   public void actionPerformed(ActionEvent e) {}
}
