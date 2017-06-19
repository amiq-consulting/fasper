package fasper;

import java.util.*;

/**
 * Main exception of this package.
 * It contains a mechanism by which you can add new codes to the list of error 
 * codes. It should be used as it is or a inherited version in all 
 * customised parser activations, test activations etc. In fasper package this 
 * is the only kind of thrown exception.
 * As an aditional feature you can encapsulate your exceptions in FasperException
 */
public class FasperException extends Exception {
   protected static HashSet errorCodes = new HashSet();
   static {
      newErrorCode("UNKNOWN_ERROR_CODE");
      newErrorCode("FILE_IS_NULL");
      newErrorCode("UNABLE_TO_SAVE_TEST");
   }
   protected String errorCode = "";
   
   public FasperException(){
      super();
   }

   public FasperException(String _mesaj) {
      super(_mesaj);
      this.errorCode = "UNKNOWN_ERROR_CODE";
   }

   public FasperException(String _mesaj, Throwable _cause) {
      super(_mesaj, _cause);
      this.errorCode = "UNKNOWN_ERROR_CODE";
   }
   
   public FasperException(String errorCode, String message) {
      super(message);
      assert errorCodes.contains(errorCode) : errorCode + " not found!";
      this.errorCode = errorCode;
   }

   public FasperException(String errorCode
                          , String message
                          , Exception _source) {
      super(message, _source);
      assert errorCodes.contains(errorCode) : errorCode + " not found!";
      this.errorCode = errorCode;
   }

   public String toString() {
      String _result = "*** Error: " + getMessage() + "."
         + "\n" + "    [see " + getErrorCode() + " spec for more details]";
      if (this.getCause() != null)
         _result += "\n" + "    Cause : " + this.getCause().toString();
      return _result;
   }

   public boolean equals(Object obj) {
      if (obj instanceof FasperException) {
         FasperException with = (FasperException)obj;
         if (this.getErrorCode().equals(with.getErrorCode())
            &&	this.getMessage().equals(with.getMessage())
            && this.getCause().equals(with.getCause()))
            return true;
      }
      return false;
	}
    
   public String getErrorCode() {
      return errorCode;
   }

   public void setErrorCode(String errorCode) {
      this.errorCode = errorCode;
   }

   protected static void newErrorCode(String newErrorCode) {
      assert !errorCodes.contains(newErrorCode) : "Duplicate error code!";
      errorCodes.add(newErrorCode);
   }
   
   public static void addErrorCode(String _code) {
      newErrorCode(_code);
   }
}