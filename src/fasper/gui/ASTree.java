package fasper.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import fasper.*;

import antlr.collections.*;
import java.util.*;

class ASTree extends JTree implements ActionListener {
   private AST ast;
   private ASTPanel parent;
   
   protected ASTree(ASTPanel _parent, AST _ast) {
      super();
      ast = _ast;
      parent = _parent;
      ASTModel model = new ASTModel(_ast);
      setModel(model);
      putClientProperty("JTree.lineStyle", "Angled");
      setCellRenderer(new ASTRenderer());
      setEditable(false);
      setShowsRootHandles(true);
      setRowHeight(20);
      ToolTipManager.sharedInstance().registerComponent(this);
      JPopupMenu popup = new JPopupMenu();
      JMenuItem _expand = new JMenuItem("Expand All");
      _expand.addActionListener(this);
      popup.add(_expand);
      JMenuItem _collapse = new JMenuItem("Collapse All");
      _collapse.addActionListener(this);
      popup.add(_collapse);
      JMenuItem _goToError = new JMenuItem("Go To Error");
      _goToError.addActionListener(this);
      popup.add(_goToError);
      MouseListener popupListener = new PopupListener(popup);
      this.addMouseListener(popupListener);
   }

    public void actionPerformed(ActionEvent e) {
      JMenuItem _jmi = (JMenuItem)e.getSource();
      SwingWorker _sw;
      final ASTree _tree = this;
      if (_jmi.getText().equals("Expand All"))
         _sw = new SwingWorker() {
         public Object construct() {
            Fasper.setWorkState(true);
            _tree.expandAllNodes();
            return null;
         }
         public void finished() {
            Fasper.setWorkState(false);
         }
      };
      else if (_jmi.getText().equals("Collapse All"))
         _sw = new SwingWorker() {
         public Object construct() {
            Fasper.setWorkState(true);
            _tree.collapseAllNodes();
            return null;
         }
         public void finished() {
            Fasper.setWorkState(false);
         }
      }; 
      else if (_jmi.getText().equals("Go To Error"))
         _sw = new SwingWorker() {
         public Object construct() {
            Fasper.setWorkState(true);
            // I need this try to overcome a strange bug : in the middle of the 
            // search, it throws me a NullPointerException; this appears only 
            // when there is no error.
            try {
               _tree.goToError();
            }catch(Exception _exc) {}
            return null;
         }
         public void finished() {
            Fasper.setWorkState(false);
         }
      }; 
      else 
         return;
      _sw.start();
   }   
    
    private class ASTModel implements TreeModel {

      AST root = null;

      protected ASTModel(AST t) {
         if (t == null) {
            throw new IllegalArgumentException("root is null");
         }
         root = t;
      }

      public void addTreeModelListener(TreeModelListener l) {
      }

      public Object getChild(Object parent, int index) {
         if (parent == null) {
            return null;
         }
         AST p = (AST) parent;
         AST c = p.getFirstChild();
         if (c == null) {
            throw new ArrayIndexOutOfBoundsException("node has no children");
         }
         int i = 0;
         while (c != null && i < index) {
            c = c.getNextSibling();
            i++;
         }
         return c;
      }

      public int getChildCount(Object parent) {
         if (parent == null) {
            throw new IllegalArgumentException("root is null");
         }
         AST p = (AST) parent;
         AST c = p.getFirstChild();
         int i = 0;
         while (c != null) {
            c = c.getNextSibling();
            i++;
         }
         return i;
      }

      public int getIndexOfChild(Object parent, Object child) {
         if (parent == null || child == null) {
            throw new IllegalArgumentException("root or child is null");
         }
         AST p = (AST) parent;
         AST c = p.getFirstChild();
         if (c == null) {
            throw new ArrayIndexOutOfBoundsException("node has no children");
         }
         int i = 0;
         while (c != null && c != child) {
            c = c.getNextSibling();
            i++;
         }
         if (c == child) {
            return i;
         }
         throw new java.util.NoSuchElementException("node is not a child");
      }

      public Object getRoot() {
         return root;
      }

      public boolean isLeaf(Object node) {
         if (node == null) {
            throw new IllegalArgumentException("node is null");
         }
         AST t = (AST) node;
         return t.getFirstChild() == null;
      }

      public void removeTreeModelListener(TreeModelListener l) {
      }

      public void valueForPathChanged(TreePath path, Object newValue) {
         Util.log("heh, who is calling this mystery method?");
      }
   }

   class ASTRenderer extends DefaultTreeCellRenderer {

      public ASTRenderer() {
         super();
      }

      public Component getTreeCellRendererComponent(JTree tree, Object value,
         boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

         super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
            row, hasFocus);

         AST nodeInfo = (AST) (value);
         String _title = nodeInfo.getText();
         if (_title.indexOf("ok") >= 0) {
            setIcon(Util.getImageIcon("resources/passed_node.gif"));
            setToolTipText("Comparision passed!");
         } else if (_title.indexOf("ko") >= 0) {
            setIcon(Util.getImageIcon("resources/failed_node.gif"));
            setToolTipText("Comparision failed here!");
         } else {
            setIcon(Util.getImageIcon("resources/ignored_node.gif"));
            setToolTipText("No comparision!");
         }
         return this;
      }
   }

   void expandAllNodes() {
      int currRows;
      do {
         currRows = this.getRowCount();
         for (int i = 0; i <= currRows; i++) {
            this.expandRow(i);
         }
      } while (currRows != this.getRowCount());
   }

   void collapseAllNodes() {
      int row = this.getRowCount() - 1;
      while (row >= 0) {
         this.collapseRow(row);
         row--;
      }
   }
   
   void goToError() {
      expandAllNodes();
      repaint();
      int currRows;
      currRows = getRowCount();
      TreePath _path = null;
      for (int i = 0; i <= currRows; i++) {
         _path = getPathForRow(i);
         Object _o = _path.getLastPathComponent();
         if (_o != null && _o instanceof AST) {
            AST _root = (AST)_o;
            if (_root.getText().indexOf("ko") >= 0)
               break;          
         }
         _path = null;
      }
      if (_path != null) {
         this.scrollPathToVisible(_path);
      }
   }
}