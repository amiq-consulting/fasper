header {
package fasper.gm;
import java.io.*;
}

class GMLexer extends Lexer;

options
{
   // lookahead depth
   k = 3;
   charVocabulary = '\3'..'\377';
   filter = false; 
   testLiterals = false;
   importVocab = GMParser;
}

{
	/**
	 * current loaded file
	 */
   File loadedFile;
   /**
   * @param _file The test file. 
   * @see GMParser
   */
   GMLexer(File _file) throws FileNotFoundException {
      this(new FileReader(_file));
      this.loadedFile = _file;
   }
   
}

// horizontal line -- ignored
HLINE	:	"--" { $setType(Token.SKIP); } ;

STARTGM	:	"-->";

ENDGM 	:	"===";

COLON	:	':'		;

EQ      :   "="     ;

COMMA   :   ','     ;

SEMI    :   ';'	    ;

LBRACK  :   '['     ;

RBRACK  :   ']'     ;

LCURLY  :   '{'     ;

RCURLY  :   '}'     ;

ACC     :   '`'     ;

SLASH   :   '/'     ;

// whitespace -- ignored
WS	
   : (	' '
			// handle newlines
      |	(	options {generateAmbigWarnings=false;}
         :	"\r\n"  // DOS
         |	'\r'    // Macintosh
         |	'\n'    // Unix 
         )
         { newline(); }
      )+
      { _ttype = Token.SKIP; }
   ;


// tabs -- not allowed!
TAB	
   : 	'\t'
      { 
         throw new RecognitionException("Illegal tab found on line " + getLine() 
                                        + ", column " + getColumn() + ".\n Replace all tabs with spaces and try again.", "", -1, -1); 
      } 
   ;

// single-line comments
SL_COMMENT
   :   "//"
      (~('\n'|'\r'))* ('\n'|'\r'('\n')?)
      { $setType(Token.SKIP); newline(); }
   ;

// multiple-line comments
ML_COMMENT
   :   "/*"
      (   options { generateAmbigWarnings=false; }
      :
         { LA(2)!='/' }? '*'
      |   '\r' '\n'       { newline(); }
      |   '\r'            { newline(); }
      |   '\n'            { newline(); }
      |   ~('*'|'\n'|'\r')
      )*
      "*/"
      { $setType(Token.SKIP); }
   ;

// id
ID  options { testLiterals=true; }
   : 	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_'|'.')* 
   ;

// string : #"some string"# in order to be easy to see any kind of string
STRING 
   : '#' '"'
      (
         ('\\' '"') => '\\' '"'
      |   ('"' ~('#')) => '"'
      |   ~('"')
      )*
      '"' '#'
      {
         String txt = $getText;
         txt = txt.substring(2, txt.length() - 2);
         $setText(txt);
      }
   ;

// int
INT
   : ('0'..'9')+
   ;

// vertical line    
VLINE
   : (P_VLINE (~('\n'|'\r'|'-'))* "--") => P_VLINE
   |  P_VLINE { $setType(Token.SKIP); }
   ;

// protected vertical line
protected 
P_VLINE
   : ('|' | '\'')
   ;
