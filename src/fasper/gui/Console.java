package fasper.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.text.*;

class Console extends JPanel implements ActionListener {

   /** The output area canvas added to the frame */
   private JTextArea text = new JTextArea();

   /** The new output stream for System.out */
   private PipedOutputStream newOut = new PipedOutputStream();

   /** The new output stream for System.err */
   private PipedOutputStream newErr = new PipedOutputStream();

   /** The thread that sends output from m_POO to the output box */
   private Thread outServant;

   /** The thread that sends output from newErr to the output box */
   private Thread errServant;

   /*
    * A class that sends all lines from a reader to a TextArea component
    */
   private class ReaderToTextArea extends Thread {

      /** The reader being monitored */
      protected LineNumberReader inText;

      /** The output text component */
      protected JTextArea outText;

      /**
       * Sets up the ReaderToTextArea
       * 
       * @param input
       *           the Reader to monitor
       * @param output
       *           the TextArea to send output to
       */
      ReaderToTextArea(Reader input, JTextArea output) {
         inText = new LineNumberReader(input);
         outText = output;
      }

      /**
       * Sit here listening for lines of input and appending them straight to
       * the text component.
       */
      public void run() {
         while (true) {
            try {
               outText.append(inText.readLine() + '\n');
               outText.setCaretPosition(outText.getDocument().getLength());
            } catch (Exception ex) {
               try {
                  sleep(100);
               } catch (Exception e) {
               }
            }
         }
      }
   }

   Console() throws Exception {
      super(new GridLayout(1, 1));
      text.setEditable(false);
      //text.setFont(new Font("Monospaced", Font.PLAIN, 12));
      text.setFont(new Font("Courier", Font.PLAIN, 14));
      JScrollPane scroller = new JScrollPane(text);
      this.add(scroller);
      JPopupMenu popup = new JPopupMenu();
      JMenuItem menuItem = new JMenuItem("Clear Console");
      MouseListener popupListener = new ConsolePopupListener(popup);
      popup.add(menuItem);
      menuItem.addActionListener(this);
      text.addMouseListener(popupListener);
      this.validate();
      // Redirect System.out to the text area
      PipedInputStream pio = new PipedInputStream(newOut);
      System.setOut(new PrintStream(newOut));
      Reader r = new InputStreamReader(pio);
      outServant = new ReaderToTextArea(r, text);
      outServant.start();
      // Redirect System.err to the text area
      PipedInputStream pie = new PipedInputStream(newErr);
      System.setErr(new PrintStream(newErr));
      r = new InputStreamReader(pie);
      errServant = new ReaderToTextArea(r, text);
      errServant.start();
   }

   public void actionPerformed(ActionEvent e) {
      this.clear();
   }

   private void clear() {
      text.setText(" \n");
   }
   
   class ConsolePopupListener extends MouseAdapter {
      JPopupMenu popup;
      
      public ConsolePopupListener() {
         super();
      }
      
      public ConsolePopupListener(JPopupMenu _jpm){
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
}

