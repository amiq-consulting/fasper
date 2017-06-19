package fasper;

/**
 * ComparisonException
 */
public class ComparisonException extends FasperException {
   static { 
      FasperException.addErrorCode("NOT_USED");
      FasperException.addErrorCode("PASSED");
      FasperException.addErrorCode("EXPECTED_AST_IS_NULL");
      FasperException.addErrorCode("RESULTED_AST_IS_NULL");
      FasperException.addErrorCode("BOTH_ASTS_ARE_NULL");
      FasperException.addErrorCode("EXPECTED_NODE_IS_NULL");
      FasperException.addErrorCode("RESULTED_NODE_IS_NULL");
      FasperException.addErrorCode("BOTH_NODES_ARE_NULL");
      FasperException.addErrorCode("TOKEN_TEXT_MISMATCH");
      FasperException.addErrorCode("TOKEN_TYPE_MISMATCH");
      FasperException.addErrorCode("DIFFERENT_AST_STRUCTURE");  
   }
   
   private String context = "";

   public ComparisonException() {
      super();
   }

   /**
    * @param _mesaj
    */
   public ComparisonException(String _mesaj) {
      super(_mesaj);
   }

   /**
    * @param _mesaj
    * @param _cause
    */
   public ComparisonException(String _mesaj, Throwable _cause) {
      super(_mesaj, _cause);
   }

   /**
    * @param errorCode
    * @param message
    */
   public ComparisonException(String errorCode, String message) {
      super(errorCode, message);
   }

   /**
    * @param errorCode
    * @param message
    * @param _source
    */
   public ComparisonException(String errorCode, String message,
      Exception _source) {
      super(errorCode, message, _source);
   }
   
   public String toString() {
      if (this.getErrorCode().equals("PASSED"))
         return "***Fasper: AST Comparision Status = PASSED" 
         	+ "\nContext : " + context;
      String _result = "*** Error: " + getMessage() + "."
         + "\n" + "    [see " + getErrorCode() + " spec for more details]";
      _result += "Context : " + context;
      if (this.getCause() != null)
         _result += "\n" + "    Cause : " + this.getCause().toString();
      return _result;
   }

   
   public String getContext() {
      return context;
   }
   
   public void setContext(String context) {
      this.context = context;
   }
}
