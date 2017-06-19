package fasper.base;

import antlr.collections.*;

/**
 * Saves the checked AST to a user-defined z-test format.
 * If you use your own test format you should implement this interface in order to be 
 * able to save your files in your format. <br>
 * Obs: <i>this feature will be available starting next release aka 1.0</i>
 */
public interface TestWriter {

   /**
    * Saves the given AST to the specified file.
    * @param _tree the AST tree to be saved
    * @param _fileName the full path of the file to be saved to 
    * @throws Throwable it's thrown in case of emergency
    */
   public void writeASTToFile(AST _tree, String _fileName) throws Throwable;

}