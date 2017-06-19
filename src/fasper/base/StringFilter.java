package fasper.base;

/**
 * Makes implementation of string filters very easy. All you need to do is to
 * create a annonymous class like :<br>
 * 
 * <PRE>
 * StringFilter one = new StringFilter() { 
      public String filter(String input) {
      String res = input.replaceAll("\\\\\n", ""); 
      res = res.replaceAll("[ \\s]+","").trim(); 
      return res; 
      } 
   };
   // and filter a string based on <code>one</code> filter.
   String _mda = one.filter("sadsasd     dadsasdsa    dassads");
 * <PRE>
 * 
 * @see fasper.base description
 */
public interface StringFilter {

   /**
    * Filters a string.
    * @param _in A string to be filtered
    * @return Returns the filtered string.
    */
   public String filter(String _in);

}