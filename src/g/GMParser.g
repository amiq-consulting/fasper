header {
package fasper.gm;
import antlr.collections.*;
import antlr.*;    
import java.util.*;
import java.lang.reflect.*;
import java.io.*;
/**
 * GMParser is used for parsing the test files. The role of a test file is to 
 * keep golden model (expected tree) & activation in one place.
 * In this case a test file has next format:
 <code>
dutActivationClass myparser.MyParserActivation<br>
argTypeAndValue java.lang.String #"e_0.e"#<br>
--><br>
[C_PROGRAM, #"top: a_c.c"#]<br>
|--[C_MODULE, #"module: a_c.c"#]<br>
|  |  |--[LITERAL_struct, #"struct"#]<br>
|  |  |  |--[ID, #"s_1"#]<br>
|  |  |--[LITERAL_struct, #"struct"#]<br>
|  |  |  |--[ID, #"s_2"#]<br>
 </code>
 On the short :<br>
 - strings are enclosed in <b>#"</b><i>a_string</i><b>"#</b><br>
 - <b>dutActivationClass</b> : activates the parser under test. This class must 
 implement fasper.base.Activation<br>
 - <b>argTypeAndValue</b> : pair type-value. The type must be used with 
 full path eg. java.lang.String.<br>
   As you can see the structure of the golden model tree is very similar to <br>
the one displayed by the ASTFrame.
 * @author stefan@amiq.ro
 */
}

class GMParser extends Parser;

options
{
   // lookeahead depth
   k = 2; 
   buildAST = true;
}

tokens
{
   START; 
   TEST_LIST; 
   TEST; 
   OUT; 
}

{         
   
   private AST expectedAST;
   
   /**
    * @return Returns golden model AST (the expected one)
    */
   AST getExpectedAST() {
   	return expectedAST;
   }
   
   private HashMap tokenMap = new HashMap();

	/**
	 * @return Returns the mapping between token's name and it's 
	 * int value
	 */
	HashMap getTokenMap() {
		return tokenMap;
	}

	/**
    *  name of the parser under test activation class
    */ 
   private String activationClassName = null;
   
   /**
   * @return Returns the name of the class which activates the
   * parser under test
   */
   String getActivationClassName() {
   	return activationClassName;
   }
   
   /**
    * @return Returns an array of arguments of the activation constructor
    * They should be primitive types : string, int...
    */
   String[] getArgTypes() {
      return (String[])argTypes.toArray(new String[argTypes.size()]);
   }

   /**
    * @return Returns an array of values of the arguments.
    * to be used in activation constructor
    */
   Object[] getArgValues() {
      return (Object[])argValues.toArray(new Object[argTypes.size()]);
   }
   
   /**
    * Constructor based on a lexer.
    * @param lexer The lexer to be used.
    * Builds a golden model parser using the given lexer.
    * This can be used only with z-files.
    */
   GMParser(GMLexer lexer){
      this((TokenStream) lexer);
      this.loadedFile = lexer.loadedFile;
   }
   
   /**
    * The file to be parsed by golden model parser
    */
   File loadedFile;

   // get Lexer, Parser and finally TreeWalker Class
   private Class toolClass;
   // used during tree construction
   private boolean root = false;
   private int count;
   private int offset;
   private ArrayList stack = new ArrayList();
   private ArrayList argTypes = new ArrayList();
   private ArrayList argValues = new ArrayList();
   
   /**
    * Prints debug info.
    */
   private void d(String s) { 
   	System.out.println(s);
   }
   
}

start
   : lexerTreeParserTestCase
      { 
## = #([START,"START"], ##); 
      }
   ;

lexerTreeParserTestCase
   : dutActivationClassDecl (argDecl)* (goldenModelList)
   ;

dutActivationClassDecl
   : "dutActivationClass"^ id:ID
      {
         activationClassName = id.getText();
         try {
            toolClass = Class.forName(id.getText());
            String tcn = (String) toolClass.getField("CLASS_UNDER_TEST").get(null);
            toolClass = Class.forName(tcn);
         } catch(Exception exc) {
            d("GMParser Error : error while parsing statement : "
              + "dutActivationClass " + id.getText());
            d("Maybe you specified wrong activation class name or "
              + "you forgot to put something like this in your activation class : \n" 
              + "public static final String CLASS_UNDER_TEST = \"yourPackage.YourTreeParser\";");
            exc.printStackTrace();
         }
      }
   ;

argDecl
   : "argTypeAndValue"^ id:ID val:STRING
      {
         try {
            argTypes.add(new String(id.getText()));
            argValues.add(new String(val.getText()));
         }catch(Exception exc) {
            d("GMParser Error : error while parsing statement :" 
              + "argTypeAndValue " + id.getText() + "#\"" 
              + val.getText()+ "\"#");
            d("Maybe you specified wrong class name!!!");
            exc.printStackTrace();
         };
      }
   ;

goldenModelList
   : (lexerTreeParserTest)+                
      { 
## = #([TEST_LIST,"TEST_LIST"], ##); 
      }
   ;

lexerTreeParserTest!
   : STARTGM! out:tree ENDGM!
      {   
## = #([TEST,"TEST"], #([OUT,"OUT"], out)); 
         expectedAST = #out;
      }
   ;   

tree! { root = true; }
   : (row)+     
      { 
## = (CommonAST)stack.get(0); 
      }
   ;

row!
   : (VLINE)* 
      { 
         if ( root ) {
            count = 0;
            offset = LT(1).getColumn();
            root = false;
         } else {
            int pos = LT(1).getColumn();
            count = (pos - offset)/3;
         }
      }
      t:treeParserToken 
      {
         if( count > 0) {
            ((CommonAST)stack.get(count-1)).addChild(#t);
         }
         if( stack.size() <= count ) {
            stack.add(#t);
         } else {
            stack.set(count, #t);
         }
      }     
   ;

treeParserToken
   : token
   ;

token
   : LBRACK! tk:ID! COMMA! txt:STRING RBRACK!
      {
         try {
            // here I build the token map: a mapping between type and it's name
            int tokenInt = toolClass.getField(#tk.getText()).getInt(null);
            #txt.setType(tokenInt);
            tokenMap.put(new Integer(tokenInt), #tk.getText());
         } catch( Exception ex) {
            d("GMParser Error : error while adding "
              + #tk.getText() + " to token map");
            ex.printStackTrace();
         }
      }
	;










