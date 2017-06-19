package fasper.gui;

import java.io.*;
import java.net.URL;
import javax.swing.ImageIcon;

import fasper.Tool;

import java.util.*;

class Util {

   static ImageIcon getImageIcon(String path) {
      URL imgURL = Tool.class.getResource(path);
      if (imgURL != null)
         return new ImageIcon(imgURL);
      Util.log("Icon " + path  + " can not be created!");
      return null;
   }
   
   static void log(String _s) {
      System.out.println(_s);
   }
   
   static String getExtension(File f) {
      String ext = null;
      String s = f.getName();
      int i = s.lastIndexOf('.');
      if (i > 0 &&  i < s.length() - 1)
         ext = s.substring(i+1).toLowerCase();
      return ext;
   }
   
   static String getPathFrom(String _name, File _file) {
      if (_file != null)
         return _file.getParent() + File.separator + _name; 
      return _name;
   } 
}
