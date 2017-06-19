package fasper.gui;

import javax.swing.*;

import fasper.*;

import java.awt.*;
import java.awt.event.*;

public class Fasper extends JFrame {
   private static Tool tool = new Tool();
   private static ModelHub model;
   private static Console console;
   private static BaseMenuBar menu = null;
   private static Fasper frame;
   private Defs defs = Defs.getMe();

   Fasper() {
      super("Fasper");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent evt) {
            System.out.println("\nFasper exits . . .");
            System.exit(0);
         }
      });
      setSize(defs.WIDTH, defs.HEIGHT);
      setResizable(true);
      model = new ModelHub(this, tool);
      initComponents();
   }

   private void initComponents() {
      ConsolePanel _console = new ConsolePanel(model, console);
      JSplitPane _jspv = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
      _jspv.setResizeWeight(.25D);
      _jspv.setOneTouchExpandable(true);
      _jspv.setDividerLocation(2 * defs.HEIGHT / 3);
      _jspv.setPreferredSize(new Dimension(defs.WIDTH, defs.HEIGHT));
      ViewsPanel views = new ViewsPanel(model);
      _jspv.setTopComponent(views);
      _jspv.setBottomComponent(_console);
      getContentPane().add(_jspv, BorderLayout.CENTER);
      menu = new BaseMenuBar(this, model);
   }

   public static void main(String[] args) {
      String _test = "";
      String _defs = null;
      String _filt = null;
      String _comp = null;
      int _j = 0;
      for (int _i = 0; _i < args.length; _i++) {
         _j = args[_i].split("=").length;
         if (args[_i].startsWith("-test") && _j == 2)
            _test = args[_i].split("=")[1].trim();
         else if (args[_i].startsWith("-defs") && _j == 2)
            _defs = args[_i].split("=")[1].trim();
         else if (args[_i].startsWith("-filter") && _j == 2)
            _filt = args[_i].split("=")[1].trim();
         else if (args[_i].startsWith("-comp") && _j == 2)
            _comp = args[_i].split("=")[1].trim();
         else
            usage();
      }
      if (_defs != null) 
         try {
            tool.loadDefaults(_defs);
         } catch (Throwable _thr) {
            Util.log("Could not load defaults file " + _defs + " because of : \n"
               + _thr.toString());
         }
      if (_filt != null)
         tool.setFilter(_filt);
      if (_comp != null)
         tool.setComparator(_comp);
      try {
         console = new Console();
      } catch (Exception e) {
         System.out.println(e.getMessage());
         System.exit(0);
      }
      initGui(_test);
   }

   private static void initGui(String _test) {
      frame = new Fasper();
      Runnable runner = new FrameThread(frame);
      EventQueue.invokeLater(runner);
      if (!_test.equals(""))
         model.load(_test);
   }

   static void setWorkState(boolean _ste) {
      if (_ste) {
         menu.setToolBarEnable(false);
         frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      } else {
         menu.setToolBarEnable(true);
         frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
      }
      frame.validate();
   }

   private static void usage() {
      System.out.println("-------\nUsage : \n" + "Runs Fasper in gui mode.\n"
         + "java fasper.gui.Fasper[ -test=test.z][ -defs=a.defs]"
         + "[ -filter=my.pack.AFilter][ -comp=my.pack.AComparator]\n"
         + "-test=<test.z>   : specify the test/file to be loaded\n"
         + "-defs=<defs>     : specify the properties file to be loaded\n"
         + "-filter=<filter> : specify the filter class\n"
         + "-comp=<comp>     : specify the comparator class\n" + "\nExample :"
         + "java fasper.gui.Fasper -test=gica.z -filter=mda.AFilter "
         + "-comp=mda.AComparator ");
   }

   private static class FrameThread implements Runnable {
      private final JFrame frame;

      FrameThread(JFrame frame) {
         this.frame = frame;
      }

      public void run() {
         frame.pack();
         frame.setVisible(true);
      }
   }
}