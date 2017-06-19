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

public interface GMParserTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int START = 4;
	int TEST_LIST = 5;
	int TEST = 6;
	int OUT = 7;
	int LITERAL_dutActivationClass = 8;
	int ID = 9;
	int LITERAL_argTypeAndValue = 10;
	int STRING = 11;
	int STARTGM = 12;
	int ENDGM = 13;
	int VLINE = 14;
	int LBRACK = 15;
	int COMMA = 16;
	int RBRACK = 17;
}
