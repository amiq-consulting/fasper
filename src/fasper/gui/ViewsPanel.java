package fasper.gui;

import javax.swing.*;

import fasper.*;

class ViewsPanel extends JTabbedPane {
   private SourceViewer source = new SourceViewer();
   private ASTSplitPanel erPanel = new ASTSplitPanel();
   private ASTSplitPanel pwPanel = new ASTSplitPanel();
   private ModelHub model;
   
   ViewsPanel(ModelHub _model) {
      super();
      this.model = _model;
      model.setViews(this);
      erPanel.setLeftAST("Expected AST", new ASTPanel(erPanel));
      erPanel.setRightAST("Resulted AST", new ASTPanel(erPanel));
      pwPanel.setLeftAST("Parser AST", new ASTPanel(pwPanel));
      pwPanel.setRightAST("TreeParser AST", new ASTPanel(pwPanel));
      addTab("E-R AST"
         , Util.getImageIcon("resources/e_r_ast.gif")
         , erPanel 
         ,  "Show Expected and Resulted ASTs");
      addTab("P-W AST"
         , Util.getImageIcon("resources/p_w_ast.gif")
         , pwPanel
         , "Shows Parser and TreeParser ASTs");
      addTab("Source"
         , Util.getImageIcon("resources/source.gif")
         , source.getViewer()
         , "Shows Source File");
   }
   
   void refresh()  {
      erPanel.refresh(model.getTool().getExpectedAST()
         , model.getTool().getVerifiedAST());
      pwPanel.refresh(model.getTool().getParserAST()
         , model.getTool().getTreeParserAST());
      source.refresh(model.lastFile);
   }
}