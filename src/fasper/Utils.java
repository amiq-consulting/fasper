package fasper;

import java.io.*;
import java.net.URL;
import javax.swing.ImageIcon;

import fasper.base.*;


import java.util.*;

/**
 * Contains utility methods.
 */
class Utils {
   static {
      FasperException.addErrorCode("DEFAULTS_LOAD_FAILED");
   }
   
   /**
    * Logs messages to system.out.
    * @param _s The message.
    */
   static void log(String _s) {
      System.out.println(_s);
   }

   /**
    * Loads a file of properties.
    * @param _file The file handle to load.
    * @return Returns the properties read from file.
    * @throws FasperException
    */
   static Properties loadProps(File _file) throws FasperException {
      Properties properties = new Properties();
      try {
         properties.load(new FileInputStream(_file));
         return properties;
      } catch (IOException e) {
         throw new FasperException("DEFAULTS_LOAD_FAILED"
            , "Error while loading properties file : " + e.toString());
      }
   }
   
   static String[] tokenize(String input) {
      ArrayList v = new ArrayList();
      StringTokenizer t = new StringTokenizer(input);
      
      while (t.hasMoreTokens())
         v.add(t.nextToken());
      return (String[]) v.toArray(new String[v.size()]);
   }
   
   /**
    * Returns the extension of a file.
    * @param f The file handle.
    * @return Returns the extension of the file.
    */
   public static String getExtension(File f) {
      String ext = null;
      String s = f.getName();
      int i = s.lastIndexOf('.');
      if (i > 0 &&  i < s.length() - 1)
         ext = s.substring(i+1).toLowerCase();
      return ext;
   }
   

}
