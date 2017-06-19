package fasper;

import java.util.*;
import java.net.*;
import java.io.*;

import fasper.base.*;

/**
 * A singleton class which keeps the default values.
 */
public final class Defs {
   private static Defs defaults = new Defs();

   // the height of the Fasper window 
   public int HEIGHT;
   // the width of the Fasper window
   public int WIDTH;
   // test file extension
   public String TEST_EXT;
   // source file extension
   public String DUT_EXT;
   // defaults file extension
   public String DEF_EXT;
   // if true compares parser & tree parser generated ASTs
   public boolean COMP_P_W;
   // if true compares reference AST with parser generated AST
   public boolean COMP_E_P;
   // if true syncs scrolling in AST views
   public boolean SYNC_SCROLL;
   // default filter to use
   public String FILTER;
   // default AST comparator class
   public String COMP_CLASS;

   private ActivationDescriptor defaultParser;
   private ActivationDescriptor defaultTest;

   private Defs() {
      try {
         this.loadDefaults("");
      } catch(FasperException _fe) {
         Utils.log(_fe.toString());
      }
   };

   // returns the only instance of the Defaults class
   public static Defs getMe() {
      return defaults;
   }

   /**
    * Load defaults from a file
    * @param _file The name of the file.
    * @throws FasperException Is thrown if couldn't load default values.
    */
   void loadDefaults(String _file) throws FasperException {
      if (!_file.equals("")) {
         Properties _props = Utils.loadProps(new File(_file));
         setDefaults(_props);
      } else {
         try {
            URL url = Defs.class.getResource("resources/init.init");
            Properties _props = new Properties();
            _props.load(url.openStream());
            setDefaults(_props);
         } catch (Exception e) {
            throw new FasperException("DEFAULTS_LOAD_FAILED",
               "Can not load defaults in 'resources/init.init'");
         }
      }
   }

   private void setDefaults(Properties _defs) {
      HEIGHT = Integer.parseInt(_defs.getProperty("HEIGHT", "700"));
      WIDTH = Integer.parseInt(_defs.getProperty("WIDTH", "700"));
      TEST_EXT = _defs.getProperty("TEST_EXT", "");
      DUT_EXT = _defs.getProperty("DUT_EXT", "");
      DEF_EXT = _defs.getProperty("DEF_EXT", "");
      SYNC_SCROLL = new Boolean(_defs.getProperty("SYNC_SCROLL"
         , "true")).booleanValue();
      COMP_P_W = new Boolean(_defs.getProperty("COMP_P_W"
         , "false")).booleanValue();
      COMP_E_P = new Boolean(_defs.getProperty("COMP_E_P"
         , "false")).booleanValue();
      FILTER = _defs.getProperty("FILTER", "null");
      COMP_CLASS = _defs.getProperty("COMP_CLASS", "null");
      defaultParser = new ActivationDescriptor(_defs.getProperty(
         "DUT_ACTIV_CLASS", ""), Utils.tokenize(_defs.getProperty(
         "DUT_ACTIV_ARGS", "")), Utils.tokenize(_defs.getProperty(
         "DUT_ACTIV_VALUES", "")));

      defaultTest = new ActivationDescriptor(_defs.getProperty(
         "TR_ACTIV_CLASS", ""), Utils.tokenize(_defs.getProperty(
         "TR_ACTIV_ARGS", "")), Utils.tokenize(_defs.getProperty(
         "TR_ACTIV_VALUES", "")));
   }

   ActivationDescriptor getDefaultParser() {
      return defaultParser;
   }

   ActivationDescriptor getDefaultTest() {
      return defaultTest;
   }
   
   /**
    * Returns the defaults as a string.
    */
   public String toString() {
      return "Defs = \nHEIGHT=" + HEIGHT + "\nWIDTH=" + WIDTH 
      + "\nTEST_EXT=" + TEST_EXT + "\nDUT_EXT=" + DUT_EXT
      + "\nDEF_EXT=" + DEF_EXT + "\nSYNC_SCROLL=" + SYNC_SCROLL
      + "\nCOMP_P_W=" + COMP_P_W + "\nCOMP_E_P=" + COMP_E_P
      + "\nFILTER=" + FILTER + "\nCOMP_CLASS=" + COMP_CLASS;
   }
}

