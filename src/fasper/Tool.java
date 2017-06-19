package fasper;

import java.io.*;

import fasper.base.*;

import antlr.collections.*;

/**
 * A proxy for the Central class.
 * This class should be used in tests.
 */
public class Tool {
   private Defs defs = Defs.getMe();
   private Central central = new Central();
   
   /**
    * A basic constructor.
    */
   public Tool() {
   }
   
   /**
    * Loads a source or test file.
    * @param _file A source or test file.
    * @throws Throwable is thrown if errors occured during 
    * load process
    */
   public void load(File _file) throws Throwable {
      if (_file == null)
         throw new FasperException("FILE_IS_NULL"
            , "The file you are tring to load is null");
      if (_file.getName().endsWith(defs.TEST_EXT))
         central.loadTest(_file);
      else
         central.loadFile(_file);
   }
   
   /**
    * Loads a source or test file.
    * @param _file is the name of the file to be loaded
    * @throws Throwable is thrown if errors occured during 
    * load process
    */
   public void load(String _file) throws Throwable {
      this.load(new File(_file));
   }
   
   /**
    * Loads a default values file.
    * @param _file the default values file.
    * @see fasper.Defs
    * @throws Throwable throws an Exception if could not load
    * defaults
    */
   public void loadDefaults(String _file) throws Throwable {
      central.loadDefaults(_file);
   }
   
   /**
    * Saves the current verifies AST in z-file
    * @param _file the z-file.
    * @throws Throwable throws an Exception if couldn't save 
    * the test.
    */
   public void saveTest(File _file) throws Throwable {
      central.saveTest(_file);
   }
   
   /**
    * Sets the AST filter class name (eg. woo.test.WooFilter)
    * @param _filt
    */
   public void setFilter(String _filt) {
      defs.FILTER = _filt;
   }
   
   /**
    * Sets the comparator class name (eg. woo.test.WooComparator)
    * @param _comp the name of the comparator class
    */
   public void setComparator(String _comp) {
      defs.COMP_CLASS = _comp;
   }
   
   /**
    * Returns current expected AST or null if there isn't one.
    * @return AST current expected AST
    */
   public AST getExpectedAST() {
      return central.getExpectedAST();
   }
   
   /**
    * Returns current parser AST or null if there isn't one.
    * @return AST current parser AST
    */
   public AST getParserAST() {
      return central.getParserAST();
   }
   
   /**
    * Returns current treeParser AST or null if there isn't one.
    * @return AST current treeParser AST
    */
   public AST getTreeParserAST() {
      return central.getTreeParserAST();
   }
   
   /**
    * 
    * @return AST currently verified AST
    */
   public AST getVerifiedAST() {
      return central.getVerifiedAST();
   }   
}
