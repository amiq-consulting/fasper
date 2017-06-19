package fasper.gui;

import javax.swing.*;
import javax.swing.filechooser.*;

import fasper.*;

import java.io.File;

/**
 * A file chooser which knows to filter test & source files from the rest
 * of the files.
 */
class BaseFileChooser extends JFileChooser {
   Defs defs = Defs.getMe();
   
   public BaseFileChooser() {
      super();
      String curDir = System.getProperty("user.dir");
      setCurrentDirectory(new File(curDir));
      setFileFilter(new ZFileFilter());
      setFileView(new ZFileView());
   }

   int showRunDialog(JFrame _frame) {
      super.setDialogTitle("Load Source File");
      super.setApproveButtonText("LOAD");
      super.setApproveButtonToolTipText("Loads the selected source file");
      try {
         return super.showDialog(_frame, null);
      }catch (Exception ex){
         Util.log("BaseFileChooser.showRunDialog() : " + ex.toString());
         return super.ERROR_OPTION;
      }
   }
   
   int showSaveDialog(JFrame _frame) {
      super.setDialogTitle("Save Test File");
      super.setApproveButtonText("SAVE");
      super.setApproveButtonToolTipText("Saves test for loaded source file");
      try {
         return super.showDialog(_frame, null);
      }catch (Exception ex){
         Util.log("BaseFileChooser.showSaveDialog() : " + ex.toString());
         return super.ERROR_OPTION;
      }
   }

   public File getSelectedFile() {
      return super.getSelectedFile();
   }

   public File[] getSelectedFiles() {
      return super.getSelectedFiles();
   }
 
   private class ZFileFilter extends FileFilter {

      public boolean accept(File f) {
         if (f.isDirectory())
            return true;
         String extension = Util.getExtension(f);
         if (extension == null) 
            return false;
         if (extension.equals(defs.DUT_EXT)
             || extension.equals(defs.TEST_EXT)
             || extension.equals(defs.DEF_EXT))
            return true;
         return false;  
      }

      // The description of this filter
      public String getDescription() {
         return "*." + defs.DUT_EXT + ", *." + defs.TEST_EXT 
         + ", *." + defs.DEF_EXT;
      }
   }
   
   private class ZFileView extends FileView {
      ImageIcon testFile = Util.getImageIcon("resources/test_file.gif");
      ImageIcon dutFile = Util.getImageIcon("resources/dut_file.gif");
      ImageIcon propertiesFile = Util
      .getImageIcon("resources/properties_file.gif");
         
      public ZFileView(){
         super();
      }

      public String getName(File f) {
         return null; // let the L&F FileView figure this out
      }
       
      public String getDescription(File f) {
         return null; // let the L&F FileView figure this out
      }
       
      public Boolean isTraversable(File f) {
         return null; // let the L&F FileView figure this out
      }
       
      public String getTypeDescription(File f) {
         String extension = Util.getExtension(f);
         String type = null;

         if (extension != null) {
            if (extension.equals(defs.TEST_EXT)) {
               type = "Test file";
            } else if (extension.equals(defs.DUT_EXT)){
               type = "dut File";
            } else if (extension.equals(defs.DEF_EXT)){
               type = "properties File";
            } 
         }
         return type;
      }
       
      public Icon getIcon(File f) {
         String extension = Util.getExtension(f);
         Icon icon = null;

         if (extension != null) {
            if (extension.equals(defs.TEST_EXT)){
               icon = testFile;
            } else if (extension.equals(defs.DUT_EXT)) {
               icon = dutFile;
            }else if (extension.equals(defs.DEF_EXT)) {
               icon = propertiesFile;
            }
         }
         return icon;
      }
   }
   
}


