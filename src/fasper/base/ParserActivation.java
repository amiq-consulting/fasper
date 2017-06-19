package fasper.base;

import antlr.collections.AST;
import java.util.*;

/**
 * Common interface for all parser activation.
 * It offers a common way to activate parsers and retrieve the resulted AST's.
 * It also is able to return a token map and a list of errors.
 * @see fasper.base.TestActivation
 * @see fasper.gm.GMActivation
 */
public interface ParserActivation {
   /**
    * Activates the parser.
    * Here should be put the code for parser activation. 
    * @throws <code>Throwable</code>
    */
   public void activate() throws Throwable;
   
   /**
    * Returns parser AST.
    * @return <code>AST</code> from the parser
    */
   public AST getParserAST();
   
   /**
    * Returns tree parser AST.
    * @return <code>AST</code> from the treeparser
    */
   public AST getTreeParserAST();

   /**
    * Returns a token map of the parser under test.
    * @return <code>HashMap</code> 
    */
   public HashMap getTokenMap();
   
   /**
    * Returns the list of errors which may appear during parsing process.
    * This is very usefull if your parser handles multiple errors. 
    * @return <code>ArrayList</code> contains the reported errors.
    */
   public ArrayList getAllErrors();
}
