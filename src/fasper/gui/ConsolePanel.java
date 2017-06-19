package fasper.gui;

import javax.swing.*;

/**
 * ConsolePanel
 */
public class ConsolePanel extends JTabbedPane {
   private ModelHub model; 
   
   protected ConsolePanel(ModelHub _model, JPanel _console) {
      super();
      model = _model;
      setFocusable(false);
      addTab("Console", Util.getImageIcon("resources/console.gif")
         , _console, "Console");
   }
   
}
