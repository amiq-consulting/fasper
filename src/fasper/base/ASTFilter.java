package fasper.base;

import antlr.collections.*;
import java.util.*;
/**
 * Common interface for token text filters.
 * See examples in this package's description.
 * @see fasper.DefaultASTComparator
 */
public interface ASTFilter {
   /**
    * Filters the input string of the specified AST node. 
    * You can use some regex to filter the strings (token's text). 
    * Ussualy filters that inherit this interface are put in a HashMap and mapped
    * by token type string (eg SUPER_TOKEN). 
    * @param _node the string to be filtered
    * @return Returns the filtered string
    */
   public String filterNode(AST _node);
   
   /**
    * Computes ignore state of the given node.
    * Returns true if node is ignored (and also it's children).
    * @param _node The node to be filtered.
    * @return Returns <code>true</code> if <code>_node</code> and it's kids 
    * should be ignored  
    */
   public boolean isNodeIgnored(AST _node);
   
   /**
    * Specify the tokenMap to be used. 
    * @param _tokenMap
    */
   public void useTokenMap(HashMap _tokenMap);
   
}
