package fasper.gui;

import java.awt.*;
import javax.swing.*;
import java.io.*;

class SourceViewer {
   private JEditorPane inputFile = new JEditorPane();
   private JScrollPane inScroller = new JScrollPane(inputFile);
   
   SourceViewer() {
      inputFile.setFont(new Font("Courier", Font.PLAIN, 14));
      inputFile.setEditable(false);      
   }
   
   void refresh(File _file) {
      if (_file == null)
         return;
      try {
         FileReader fr = new FileReader(_file);
         inputFile.read(fr, null);
         fr.close();
         inScroller.setBorder(BorderFactory.createCompoundBorder(
                     BorderFactory.createTitledBorder(_file.getName()),
                     BorderFactory.createEmptyBorder(3,3,3,3)));
      } catch (IOException e) {
         Util.log("Refresh source viewer contents " + e.toString());
      }
   }
   
   JScrollPane getViewer() {
      return inScroller;
   }
}
