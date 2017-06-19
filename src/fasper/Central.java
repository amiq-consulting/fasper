package fasper;

import java.io.*;

import antlr.collections.*;

import java.util.*;

import fasper.base.*;

/**
 * Here are done all the computations.
 */
class Central {
   private Defs defs = Defs.getMe();
   private Factory factory = new Factory();

   private ActivationDescriptor testActiv = Defs.getMe().getDefaultTest();
   private ActivationDescriptor parserActiv = Defs.getMe().getDefaultParser();

   private AST expectedAST = null;
   private AST parserAST = null;
   private AST treeParserAST = null;
   private ASTComparator comp = null;
   private ASTFilter filter = null;
   private HashMap tokenMap = null;

   Central() {
   }

   void loadTest(File _file) throws Throwable {
      Object[] _val = new Object[] { _file.getAbsolutePath() };
      clearAST();
      TestActivation _activ = (TestActivation) factory.getActivationFor(
         testActiv, _val);
      _activ.activate();
      ActivationDescriptor _ap = _activ.getPADescriptor();
      Object[] _vals = _ap.getValues();
      if (_file.getParent() != null)
         _vals[0] = _file.getParent() + File.separator + _vals[0];
      ParserActivation _dut = factory.getActivationFor(_ap, _vals);
      _dut.activate();
      expectedAST = _activ.getParserAST();
      parserAST = _dut.getParserAST();
      treeParserAST = _dut.getTreeParserAST();
      tokenMap = _dut.getTokenMap();
      compare();
   }

   void loadFile(File _file) throws Throwable {
      Object[] _val = new Object[] { _file.getAbsolutePath() };
      clearAST();
      ParserActivation _activ = factory.getActivationFor(parserActiv, _val);
      _activ.activate();
      expectedAST = null;
      parserAST = _activ.getParserAST();
      treeParserAST = _activ.getTreeParserAST();
      tokenMap = _activ.getTokenMap();
      compare();
   }

   private void compare() throws Throwable {
      if (defs.COMP_CLASS.equals("null"))
         comp = factory.getComparator("fasper.DefaultASTComparator");
      else
         comp = factory.getComparator(defs.COMP_CLASS);
      if (defs.FILTER.equals("null"))
         filter = factory.getFilter(null);
      else
         filter = factory.getFilter(defs.FILTER);
      filter.useTokenMap(tokenMap);
      comp.useFilter(filter);
      comp.useTokenMap(this.tokenMap);
      if (expectedAST != null) {
         if (defs.COMP_E_P)
            comp.compare(expectedAST, parserAST);
         else
            comp.compare(expectedAST, treeParserAST);
      }
      if (defs.COMP_P_W)
         comp.compare(treeParserAST, parserAST);
      else 
         throw new ComparisonException("PASSED", "There was no comparision"
         		+ " between Parser AST and TreeParserAST");
   }

   void loadDefaults(String _file) throws FasperException {
      defs.loadDefaults(_file);
      testActiv = defs.getDefaultTest();
      parserActiv = defs.getDefaultParser();
   }

   void saveTest(File _file) throws Throwable {
      AST _ast = getVerifiedAST();
      if (_ast == null) {
         Utils.log("Cannot save to file : AST is null");
         return;
      }
      Utils.log("Saving activation . . .");
      StringBuffer _sb = new StringBuffer(parserActiv.toZString());
      _sb.append("-->\n");
      Utils.log("Saving tree . . .");
      printAST(_ast, 0, _sb);
      _sb.append("===");
      try {
         BufferedWriter w = new BufferedWriter(new FileWriter(_file));
         w.write(_sb.toString());
         w.flush();
         w.close();
      } catch (IOException _ioe) {
         throw new FasperException("UNABLE_TO_SAVE_TEST"
            , "Unable to save test to file :" + _file.getAbsolutePath()
            , _ioe);
      }
   }

   private void printAST(AST _tree, int _depth, StringBuffer _sb) {
      String _str = _tree.getText();
      if (_str.endsWith(" - ok") || _str.endsWith(" - ko"))
         _str = _str.substring(0, _str.length() - 5);
      _sb.append(getIndent(_depth) + "[" + 
         (String)tokenMap.get(new Integer(_tree.getType())) 
         + ", #\"" + _str + "\"#]\n"); 
      if (_tree.getFirstChild() != null)
         printAST(_tree.getFirstChild(), _depth + 1, _sb);
      if (_tree.getNextSibling() != null)
         printAST(_tree.getNextSibling(), _depth, _sb);
   }
   
   AST getExpectedAST() {
      return expectedAST;
   }

   AST getParserAST() {
      return parserAST;
   }

   AST getTreeParserAST() {
      return treeParserAST;
   }

   AST getVerifiedAST() {
      if (expectedAST != null && defs.COMP_E_P)
         return parserAST;
      else if ((expectedAST != null && !defs.COMP_E_P)
         || (expectedAST == null && defs.COMP_P_W))
         return treeParserAST;
      else
         return null;
   }
   
   private String getIndent(int _k) {
      if (_k == 0)
         return "";
      String _res = "";
      for (int _i = 0; _i < _k ; _i++ ) {
         if (_i != _k - 1)
            _res = _res + "|  ";
         else
            _res = _res + "|--";
      }
      return _res;
   }
   
   private void clearAST() {
      expectedAST = null;
      parserAST = null;
      treeParserAST = null;
   }

}