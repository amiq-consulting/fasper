package fasper;

import antlr.collections.AST;
import java.util.*;
import java.io.File;

import fasper.base.*;

/**
 * DefaultASTComparator is a default implementation of the AST comparator.
 */
class DefaultASTComparator implements ASTComparator {
   
   private final static int NOT_USED = -1;
   private final static int PASSED = 0;
   private final static int EXPECTED_AST_IS_NULL = 1;
   private final static int RESULTED_AST_IS_NULL = 2;
   private final static int BOTH_ASTS_ARE_NULL = 3;
   private final static int EXPECTED_NODE_IS_NULL = 4;
   private final static int RESULTED_NODE_IS_NULL = 5;
   private final static int BOTH_NODES_ARE_NULL = 6;
   private final static int TOKEN_TEXT_MISMATCH = 7;
   private final static int TOKEN_TYPE_MISMATCH = 8;
   private final static int DIFFERENT_AST_STRUCTURE = 9;
   
   private int status = NOT_USED;
   private String msg = "This comparator hasn't been used";
   private HashMap tokenMap;
   private ASTFilter filter;
   
   /**
    * A simple cosntructor.
    */
   public DefaultASTComparator() {}
   
   /**
    * Sets the filter to use.
    * @param _filter The filter to use during comparison.
    */
   public void useFilter(ASTFilter _filter) {
      this.filter = _filter;
   }
   
   /**
    * Sets the token map to use.
    * @param _tokenMap The token to use during test.
    */
   public void useTokenMap(HashMap _tokenMap) {
      this.tokenMap = _tokenMap;
   };
   
   /**
    * Compares two ASTs.
    * @param expected The reference AST.
    * @param resulted The resulted AST.
    * @throws The exception is in fact a ComparisonException.
    */
   public void compare(AST expected, AST resulted) throws FasperException {
      msg = "AST Comparision Status : ";
      if (expected == null)
         status = EXPECTED_AST_IS_NULL;
      if (resulted == null)
         status = RESULTED_AST_IS_NULL;
      if (expected == null && resulted == null)
         status = BOTH_ASTS_ARE_NULL;
      if (status == 1 || status == 2 || status == 3)
         throw new ComparisonException(errorCode(status), msg 
            + errorCode(status));
      status = compareTree(expected, resulted);
      throw new ComparisonException(errorCode(status), msg);
   }
   
   // is t2 an exact structural and equals() match of t1?
   // t1 and t2 are considered as sibling lists
   private int compareTree(AST t1, AST t2) {
      AST sibling;
      // start walking sibling lists
      // first mismatch, return false
      for (sibling = t1; sibling != null && t2 != null
              ; sibling = sibling.getNextSibling()
              , t2 = t2.getNextSibling()) {
         // as a quick optimization, check roots first.
         if ((status = compareNode(sibling, t2)) != 0)
            return status;
         // if roots match, do full list match test on children.
         if ((sibling.getFirstChild() != null && t2.getFirstChild() == null)
             || (sibling.getFirstChild() == null && t2.getFirstChild() != null)) {
               status = DIFFERENT_AST_STRUCTURE;
               msg = "AST Comparision Status : " + errorCode(status);
               return status;
         }
         if ((status = compareTree(sibling.getFirstChild()
                                   , t2.getFirstChild())) != 0) 
            return status;
      }
      if (sibling == null && t2 == null) {
         // sibling lists match
         status = PASSED;
         msg = "AST Comparision Status : " + errorCode(status);
      } else {
         // one sibling list has more than the other
         status = DIFFERENT_AST_STRUCTURE;
         msg = "AST Comparision Status : " + errorCode(status);
      }
      return status;
   }
   
   private int compareNode(AST n1, AST n2) {
      msg = "Token Comparision Status : ";
      if (n1 == null)
         status = EXPECTED_NODE_IS_NULL;
      if (n2 == null)
         status = RESULTED_NODE_IS_NULL;
      if (n1 == null && n2 == null)
         status = BOTH_NODES_ARE_NULL;
      if (status == 1 || status == 2 || status == 3) {
         msg = msg + errorCode(status);
         return status;
      };
      if (n1.getType() != n2.getType()) {
         status = TOKEN_TYPE_MISMATCH;
         msg = msg + errorCode(status) 
            + "\nToken types mismatch!!! \n"
            + "Expected : ["
            + mapToString(n1.getType()) + ",\""
            + n1.getText() + "\"]\n " 
            + "Resulted : ["
            + mapToString(n2.getType()) + ",\""
            + n2.getText() + "\"]";
         	n1.setText(n1.getText() + " - ko");
         	n2.setText(n2.getText() + " - ko");
      } else {
         String s1 = filter.filterNode(n1);
         String s2 = filter.filterNode(n2);
         if (!s1.equals(s2)) {
            status = TOKEN_TEXT_MISMATCH;
            msg = msg + errorCode(status)
               + "\nToken text mismatch!!!\n"
               + "Expected : ["
               + mapToString(n1.getType()) + ",\""
               + s1 + "\"]\n"
               + "Resulted : ["
               + mapToString(n2.getType()) + ",\""
               + s2 + "\"]\n";
            int a = (mapToString(n1.getType()) + "  ").length();
            msg = msg + "Mismatch : " 
               + computeOffset(a, "_") + computeMismatch(s1, s2);
            n1.setText(n1.getText() + " - ko");
            n2.setText(n2.getText() + " - ko");
         } else {
            n1.setText(n1.getText() + " - ok");
            n2.setText(n2.getText() + " - ok");
            status = PASSED;
            msg = msg + errorCode(status);
         }
      }
      return status;
   }

   private String computeMismatch(String _s1, String _s2){
      String res = "";
      if (_s1 == null || _s2 == null)
         return "_";
      for (int i = 0; i < Math.min(_s1.length() - 1, _s2.length() -1); i++){

         if (_s1.substring(i,i+1).equals(_s2.substring(i,i+1)))
            res = res + "_";
         else 
            break;
      };
      return res + "^";
   }
   
   private String computeOffset(int _a, String _s){
      String res="";
      for (int i = 0; i <= _a; i++) {
         res = res + _s;
      };
      return res;
   }
   
   private String mapToString(int _type) {
      if (tokenMap != null)
         return (String) tokenMap.get(new Integer(_type));
      return "" + _type;
   }
   
   private String errorCode(int _code){
      switch (_code) {
      case -1: return "NOT_USED";
      case 0 : return "PASSED";
      case 1 : return "EXPECTED_AST_IS_NULL";
      case 2 : return "RESULTED_AST_IS_NULL";
      case 3 : return "BOTH_ASTS_ARE_NULL";
      case 4 : return "EXPECTED_NODE_IS_NULL";
      case 5 : return "RESULTED_NODE_IS_NULL";
      case 6 : return "BOTH_NODES_ARE_NULL";
      case 7 : return "TOKEN_TEXT_MISMATCH";
      case 8 : return "TOKEN_TYPE_MISMATCH";
      case 9 : return "DIFFERENT_AST_STRUCTURE";
      };
      return "";
   }
   
}
