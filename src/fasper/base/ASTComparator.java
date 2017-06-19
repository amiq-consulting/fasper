package fasper.base;

import antlr.collections.AST;
import java.util.*;

/**
 * Comparator common interface.
 * It should be inherited by any AST comparator in order to be plugin-able.
 * @see fasper.DefaultASTComparator
 * @see fasper.base.ASTFilter
 * @see fasper.gm.GMActivation
 * @see fasper.gm
 */
public interface ASTComparator {
   
   /**
    * Entry point for AST comparision process.
    * @param expected is the expected AST (the golden model).
    * @param resulted is the AST corresponding to the target source.
    * @throws Throwable which is the result of the comparision.
    * @see fasper.DefaultASTComparator
    * @see fasper.FasperException
    * @see fasper.ComparisonException
    */
   public void compare(AST expected, AST resulted) throws Throwable;
   
   /**
    * Specify the filter bank to be used.
    * There are situations when you would like to filter out some nodes or
    * ignore whole branches of the tree. Theese are handled by an ASTFilter.
    * @param _filter to be used by this comparator should inherit 
    * from ASTFilter
    * @see fasper.base.ASTFilter
    */
   public void useFilter(ASTFilter _filter);
   
   /**
    * Specify the tokenMap to be used. 
    * @see fasper.gm 
    * @see fasper.DefaultASTComparator
    * @see fasper.Factory
    * @param _tokenMap
    */
   public void useTokenMap(HashMap _tokenMap);
}








