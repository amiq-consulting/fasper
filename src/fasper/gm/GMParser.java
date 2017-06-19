// $ANTLR 2.7.5 (20050207): "GMParser.g" -> "GMParser.java"$

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

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import java.util.Hashtable;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

public class GMParser extends antlr.LLkParser       implements GMParserTokenTypes
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
   

protected GMParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public GMParser(TokenBuffer tokenBuf) {
  this(tokenBuf,2);
}

protected GMParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public GMParser(TokenStream lexer) {
  this(lexer,2);
}

public GMParser(ParserSharedInputState state) {
  super(state,2);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void start() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST start_AST = null;
		
		try {      // for error handling
			lexerTreeParserTestCase();
			astFactory.addASTChild(currentAST, returnAST);
			start_AST = (AST)currentAST.root;
			
			start_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(START,"START")).add(start_AST)); 
			
			currentAST.root = start_AST;
			currentAST.child = start_AST!=null &&start_AST.getFirstChild()!=null ?
				start_AST.getFirstChild() : start_AST;
			currentAST.advanceChildToEnd();
			start_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
		returnAST = start_AST;
	}
	
	public final void lexerTreeParserTestCase() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST lexerTreeParserTestCase_AST = null;
		
		try {      // for error handling
			dutActivationClassDecl();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop4:
			do {
				if ((LA(1)==LITERAL_argTypeAndValue)) {
					argDecl();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop4;
				}
				
			} while (true);
			}
			{
			goldenModelList();
			astFactory.addASTChild(currentAST, returnAST);
			}
			lexerTreeParserTestCase_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
		returnAST = lexerTreeParserTestCase_AST;
	}
	
	public final void dutActivationClassDecl() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST dutActivationClassDecl_AST = null;
		Token  id = null;
		AST id_AST = null;
		
		try {      // for error handling
			AST tmp1_AST = null;
			tmp1_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp1_AST);
			match(LITERAL_dutActivationClass);
			id = LT(1);
			id_AST = astFactory.create(id);
			astFactory.addASTChild(currentAST, id_AST);
			match(ID);
			
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
			
			dutActivationClassDecl_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_1);
		}
		returnAST = dutActivationClassDecl_AST;
	}
	
	public final void argDecl() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST argDecl_AST = null;
		Token  id = null;
		AST id_AST = null;
		Token  val = null;
		AST val_AST = null;
		
		try {      // for error handling
			AST tmp2_AST = null;
			tmp2_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp2_AST);
			match(LITERAL_argTypeAndValue);
			id = LT(1);
			id_AST = astFactory.create(id);
			astFactory.addASTChild(currentAST, id_AST);
			match(ID);
			val = LT(1);
			val_AST = astFactory.create(val);
			astFactory.addASTChild(currentAST, val_AST);
			match(STRING);
			
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
			
			argDecl_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_1);
		}
		returnAST = argDecl_AST;
	}
	
	public final void goldenModelList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST goldenModelList_AST = null;
		
		try {      // for error handling
			{
			int _cnt10=0;
			_loop10:
			do {
				if ((LA(1)==STARTGM)) {
					lexerTreeParserTest();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					if ( _cnt10>=1 ) { break _loop10; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt10++;
			} while (true);
			}
			goldenModelList_AST = (AST)currentAST.root;
			
			goldenModelList_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TEST_LIST,"TEST_LIST")).add(goldenModelList_AST)); 
			
			currentAST.root = goldenModelList_AST;
			currentAST.child = goldenModelList_AST!=null &&goldenModelList_AST.getFirstChild()!=null ?
				goldenModelList_AST.getFirstChild() : goldenModelList_AST;
			currentAST.advanceChildToEnd();
			goldenModelList_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
		returnAST = goldenModelList_AST;
	}
	
	public final void lexerTreeParserTest() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST lexerTreeParserTest_AST = null;
		AST out_AST = null;
		
		try {      // for error handling
			match(STARTGM);
			tree();
			out_AST = (AST)returnAST;
			match(ENDGM);
			lexerTreeParserTest_AST = (AST)currentAST.root;
			
			lexerTreeParserTest_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TEST,"TEST")).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(OUT,"OUT")).add(out_AST)))); 
			expectedAST = out_AST;
			
			currentAST.root = lexerTreeParserTest_AST;
			currentAST.child = lexerTreeParserTest_AST!=null &&lexerTreeParserTest_AST.getFirstChild()!=null ?
				lexerTreeParserTest_AST.getFirstChild() : lexerTreeParserTest_AST;
			currentAST.advanceChildToEnd();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
		returnAST = lexerTreeParserTest_AST;
	}
	
	public final void tree() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST tree_AST = null;
		root = true;
		
		try {      // for error handling
			{
			int _cnt14=0;
			_loop14:
			do {
				if ((LA(1)==VLINE||LA(1)==LBRACK)) {
					row();
				}
				else {
					if ( _cnt14>=1 ) { break _loop14; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt14++;
			} while (true);
			}
			tree_AST = (AST)currentAST.root;
			
			tree_AST = (CommonAST)stack.get(0); 
			
			currentAST.root = tree_AST;
			currentAST.child = tree_AST!=null &&tree_AST.getFirstChild()!=null ?
				tree_AST.getFirstChild() : tree_AST;
			currentAST.advanceChildToEnd();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
		returnAST = tree_AST;
	}
	
	public final void row() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST row_AST = null;
		AST t_AST = null;
		
		try {      // for error handling
			{
			_loop17:
			do {
				if ((LA(1)==VLINE)) {
					AST tmp5_AST = null;
					tmp5_AST = astFactory.create(LT(1));
					match(VLINE);
				}
				else {
					break _loop17;
				}
				
			} while (true);
			}
			
			if ( root ) {
			count = 0;
			offset = LT(1).getColumn();
			root = false;
			} else {
			int pos = LT(1).getColumn();
			count = (pos - offset)/3;
			}
			
			treeParserToken();
			t_AST = (AST)returnAST;
			
			if( count > 0) {
			((CommonAST)stack.get(count-1)).addChild(t_AST);
			}
			if( stack.size() <= count ) {
			stack.add(t_AST);
			} else {
			stack.set(count, t_AST);
			}
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_4);
		}
		returnAST = row_AST;
	}
	
	public final void treeParserToken() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST treeParserToken_AST = null;
		
		try {      // for error handling
			token();
			astFactory.addASTChild(currentAST, returnAST);
			treeParserToken_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_4);
		}
		returnAST = treeParserToken_AST;
	}
	
	public final void token() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST token_AST = null;
		Token  tk = null;
		AST tk_AST = null;
		Token  txt = null;
		AST txt_AST = null;
		
		try {      // for error handling
			match(LBRACK);
			tk = LT(1);
			tk_AST = astFactory.create(tk);
			match(ID);
			match(COMMA);
			txt = LT(1);
			txt_AST = astFactory.create(txt);
			astFactory.addASTChild(currentAST, txt_AST);
			match(STRING);
			match(RBRACK);
			
			try {
			// here I build the token map: a mapping between type and it's name
			int tokenInt = toolClass.getField(tk_AST.getText()).getInt(null);
			txt_AST.setType(tokenInt);
			tokenMap.put(new Integer(tokenInt), tk_AST.getText());
			} catch( Exception ex) {
			d("GMParser Error : error while adding "
			+ tk_AST.getText() + " to token map");
			ex.printStackTrace();
			}
			
			token_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_4);
		}
		returnAST = token_AST;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"START",
		"TEST_LIST",
		"TEST",
		"OUT",
		"\"dutActivationClass\"",
		"ID",
		"\"argTypeAndValue\"",
		"STRING",
		"STARTGM",
		"ENDGM",
		"VLINE",
		"LBRACK",
		"COMMA",
		"RBRACK"
	};
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 5120L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 4098L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 8192L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 57344L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	
	}
