package fasper.gui;

import java.io.*;
import javax.swing.*;

import fasper.*;
import fasper.base.*;

import java.awt.*;
import antlr.collections.*;

/**
 * ModelHub
 */
class ModelHub {
   private Fasper main;
   Tool tool;
   private final BaseFileChooser fc = new BaseFileChooser();
   private Defs defs = Defs.getMe();
   private SwingWorker currentCommand = null;
   private ViewsPanel views;

   File lastFile = null;

   ModelHub(Fasper _main, Tool _tool) {
      this.main = _main;
      this.tool = _tool;
   }

   void load(boolean _open) {
      if (_open) {
         int returnVal = fc.showRunDialog(main);
         if (returnVal != JFileChooser.APPROVE_OPTION)
            return;
         lastFile = fc.getSelectedFile();
         String _ext = Util.getExtension(lastFile);
         if (!_ext.equals(defs.TEST_EXT) && !_ext.equals(defs.DUT_EXT)) {
            Util.log("Can not load this kind of file!"
               + "File should be of type : " + defs.TEST_EXT + " or "
               + defs.DUT_EXT);
            return;
         }
      }
      currentCommand = new ZCommand(lastFile) {
         public Object construct() {
            try {
               Util.log("Loading file : " + file);
               tool.load(file);
            } catch (ComparisonException _cex) {
               Util.log("Load result : \n" + _cex.toString());
            } catch(FasperException _fe) {
               Util.log("Load failed : \n" + _fe.toString());
            } catch (Throwable _thr) {
               Util.log("Load failed : \n" + _thr.toString());
            }
            return null;
         }

         public void finished() {
            views.refresh();
            main.setWorkState(false);
         }
      };
      executeCommand(currentCommand);
   }

   void load(String _file) {
      lastFile = new File(_file);
      load(false);
   }

   void reload() {
      this.load(false);
   }

   void save() {
      int returnVal = fc.showSaveDialog(main);
      if (returnVal != JFileChooser.APPROVE_OPTION)
         return;
      lastFile = fc.getSelectedFile();
      String _name = lastFile.getName();
      String[] _mda = _name.split("\\.");
      if (_mda.length < 2)
         lastFile = new File(lastFile.getAbsolutePath() + "." + defs.TEST_EXT);
      currentCommand = new ZCommand(lastFile) {
         public Object construct() {
            try {
               Util.log("Saving file : " + file);
               tool.saveTest(file);
            } catch (FasperException _fe) {
               Util.log("Test save failed : \n" + _fe.toString());
            } catch (Throwable _thr) {
               Util.log("Test save failed : \n" + _thr.toString());
            }
            return null;
         }

         public void finished() {
            main.setWorkState(false);
         }
      };
      executeCommand(currentCommand);
   }

   private void executeCommand(SwingWorker _sw) {
      main.setWorkState(true);
      _sw.start();
   }

   void setViews(ViewsPanel _views) {
      views = _views;
   }

   Tool getTool() {
      return tool;
   }

   /**
    * A class to represent the command.
    */
   private class ZCommand extends SwingWorker {
      protected File file;

      ZCommand(File _file) {
         super();
         this.file = _file;
      }

      public Object construct() {
         return null;
      }

      public void interrupt() {
         super.interrupt();
      }
   }

}