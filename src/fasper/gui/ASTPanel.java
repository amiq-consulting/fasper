package fasper.gui;

import javax.swing.*;

import java.awt.*;
import antlr.collections.*;

class ASTPanel extends JPanel {
   ASTSplitPanel parent;
   private JTree tree;
   private JScrollPane scroller = new JScrollPane();
   
   ASTPanel(ASTSplitPanel _parent, AST _ast) {
      super(new BorderLayout());
      parent = _parent;
      refresh(_ast);
   }
   
   ASTPanel(ASTSplitPanel _parent) {
      this(_parent, null);
   }
   
   JScrollPane getScroller() {
      return scroller;
   }
   
   void refresh(AST _ast) {
      this.removeAll();
      if (_ast != null) {
         tree = new ASTree(this, _ast);
         scroller = new JScrollPane(tree);
         this.add(scroller, BorderLayout.CENTER);
      } else
         add(new JLabel("AST is not available!"
            , Util.getImageIcon("resources/warning.gif")
            , JLabel.HORIZONTAL), BorderLayout.CENTER);
      repaint();
   }
   
   void syncScrolling(boolean _sync){
      parent.syncScrolling(_sync);
   }
}
