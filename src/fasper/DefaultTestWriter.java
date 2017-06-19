package fasper;

import antlr.collections.AST;
import java.util.*;
import java.io.File;

import fasper.base.*;

/**
 * This is not connected yet. Will be available in the future.
 * More than that, it may use stringtemplates.
 */ 
public class DefaultTestWriter implements TestWriter {
   /**
    * Basic constructor.
    */
   public DefaultTestWriter() {
   }

   /**
    * Writes the AST tree to a file.
    * @param _tree The tree to save.
    * @param _fileName The name of the file to save to.
    * @throws Throws an exception related to saving process. 
    */
   public void writeASTToFile(AST _tree, String _fileName) 
      throws Throwable {
      
   }

   void saveTest(File _file) throws Throwable {
//       AST _ast = getVerifiedAST();
//       if (_ast == null) {
//          Utils.log("Cannot save to file : AST is null");
//          return;
//       }
//       Utils.log("Saving activation . . .");
//       StringBuffer _sb = new StringBuffer(parserActiv.toZString());
//       _sb.append("-->\n");
//       Utils.log("Saving tree . . .");
//       printAST(_ast, 0, _sb);
//       _sb.append("===");
//       try {
//          BufferedWriter w = new BufferedWriter(new FileWriter(_file));
//          w.write(_sb.toString());
//          w.flush();
//          w.close();
//       } catch (IOException _ioe) {
//          throw new FasperException("UNABLE_TO_SAVE_TEST"
//             , "Unable to save test to file :" + _file.getAbsolutePath()
//             , _ioe);
//       }
   }

   private void printAST(AST _tree, int _depth, StringBuffer _sb) {
//       String _str = _tree.getText();
//       if (_str.endsWith(" - ok") || _str.endsWith(" - ko"))
//          _str = _str.substring(0, _str.length() - 5);
//       _sb.append(getIndent(_depth) + "[" + 
//          (String)tokenMap.get(new Integer(_tree.getType())) 
//          + ", #\"" + _str + "\"#]\n"); 
//       if (_tree.getFirstChild() != null)
//          printAST(_tree.getFirstChild(), _depth + 1, _sb);
//       if (_tree.getNextSibling() != null)
//          printAST(_tree.getNextSibling(), _depth, _sb);
   }

   private String getIndent(int _k) {
      return "";
//       if (_k == 0)
//          return "";
//       String _res = "";
//       for (int _i = 0; _i < _k ; _i++ ) {
//          if (_i != _k - 1)
//             _res = _res + "|  ";
//          else
//             _res = _res + "|--";
//       }
//       return _res;
   }
}