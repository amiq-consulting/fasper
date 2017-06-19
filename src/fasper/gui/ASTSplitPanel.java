package fasper.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import fasper.*;
import antlr.collections.*;


class ASTSplitPanel extends JPanel {
   Defs defs = Defs.getMe();
   private JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); 
   private AdjustmentListener lrSync = new SyncListener()
      , rlSync = new SyncListener();
   private ASTPanel left, right;
   private JCheckBox syncScroll = new JCheckBox("Sync scrolling");
   
   ASTSplitPanel() {
      super(new BorderLayout());
      splitter.setResizeWeight(.5D);
      splitter.setOneTouchExpandable(true);
      splitter.setDividerLocation(defs.WIDTH / 2);
      splitter.setPreferredSize(new Dimension(defs.WIDTH, defs.HEIGHT));
      syncScroll.setSelected(defs.SYNC_SCROLL);
      syncScroll.addItemListener(new ItemListener(){
         public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.DESELECTED)
               syncScrolling(false);
            if (e.getStateChange() == ItemEvent.SELECTED)
               syncScrolling(true);
         }
      });
      this.add(syncScroll, BorderLayout.NORTH);
      this.add(splitter, BorderLayout.CENTER);
   }
   
   void setLeftAST(String title, JComponent _left) {
      left = (ASTPanel)_left;
      left.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(title),
                        BorderFactory.createEmptyBorder(3,3,3,3)));
      splitter.setLeftComponent(left);
      splitter.setDividerLocation(defs.WIDTH/2);
   }
   
   void setRightAST(String title, JComponent _right) {
      right = (ASTPanel)_right;
      right.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(title),
                        BorderFactory.createEmptyBorder(3,3,3,3)));
      splitter.setRightComponent(right);
      splitter.setDividerLocation(defs.WIDTH/2);
   }
   
   void refresh(AST _left, AST _right) {
      left.refresh(_left);
      right.refresh(_right);
      ((SyncListener)lrSync).setScrollers(left, right);
      ((SyncListener)rlSync).setScrollers(right, left);
      syncScrolling(defs.SYNC_SCROLL);
      repaint();
   }
   
   public void syncScrolling(boolean sync){
      ((SyncListener)lrSync).keepSync = sync;
      ((SyncListener)rlSync).keepSync = sync;
      defs.SYNC_SCROLL = sync;
   }
   
   private class SyncListener implements AdjustmentListener {
      public boolean keepSync = true;
      private JScrollBar leftHoriz = null, leftVert = null
         , rightHoriz = null, rightVertic = null;
      
      public SyncListener() {
         super();
      }

      public SyncListener(JComponent left, JComponent right) {
         this();
         setScrollers(left, right);
      }

      public void adjustmentValueChanged(AdjustmentEvent evt) {
         if (!keepSync)
            return;
         if (evt.getValueIsAdjusting())
            return;

         Adjustable source = evt.getAdjustable();

         // Determine which scrollbar fired the event
         int orient = source.getOrientation();
         if (orient == Adjustable.HORIZONTAL)
            rightHoriz.setValue(evt.getValue());
         else
            rightVertic.setValue(evt.getValue());
      }

      public void setScrollers(JComponent left, JComponent right) {
         JScrollPane leftScroll = ((ASTPanel)left).getScroller();
         JScrollPane rightScroll = ((ASTPanel)right).getScroller();
         leftHoriz = leftScroll.getHorizontalScrollBar();
         leftVert = leftScroll.getVerticalScrollBar();
         rightHoriz = rightScroll.getHorizontalScrollBar();
         rightVertic = rightScroll.getVerticalScrollBar();
         leftHoriz.addAdjustmentListener(this);
         leftVert.addAdjustmentListener(this);
      }
   }  
}
