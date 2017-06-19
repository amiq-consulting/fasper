package fasper.base;

import java.io.*;

/**
 * Describes ParserActivation inherited classes.
 * The role of this class is to keep together all elements needed to <br>
 * get a new instance of a class based on it's name, it's arguments <br>
 * types and their values. Reflection mechanism is used to build such 
 * instances.<br> 
 * This class is used (created) in two places : fasper.base.TestActivation and 
 * in fasper.Factory. 
 * 
 * @see fasper.Factory
 * @see fasper.base.TestActivation
 */
public class ActivationDescriptor {
   // name of the target class (full path eg. woo.test.WooActivation)
   private String name;
   // arguments types as string and full path eg java.lang.String
   private String[] args;
   // values for the arguments
   private Object[] values;

   /**
    * Creates a new descriptor for the given class.
    * First parameter should be a <code>java.lang.String</code> and 
    * represent the source file name.   
    * @param _name
    *           <code>String</code> name of the class decribed by this 
    * descriptor (full path is required eg. woo.test.WooActivation)
    * @param _args
    *           <code>String[]</code> type names of the args used in class
    *           constructor (full path is required eg java.lang.String)
    * @param _values
    *           <code>Object[]</code> values of the args of the constructor
    */
   public ActivationDescriptor(String _name, String[] _args, Object[] _values) {
      this.name = _name;
      this.args = _args;
      this.values = _values;
   }

   /**
    * Returns target class name.
    * @return Returns the name of the class.
    */
   public String getName() {
      return name;
   }

   /**
    * Returns args types of the target class constructor. 
    * Strings are full path type names eg java.lang.String
    * @return Returns an array of arguments types.
    */
   public String[] getArgs() {
      return args;
   }

   /**
    * Returns the values for the constructor's arguments.
    * 
    * @return Returns values for the constructr's arguments
    */
   public Object[] getValues() {
      return values;
   }

   /**
    * Sets the values for the constructor's arguments. 
    * This is usefull when you want to reuse a descriptor 
    * and just change the constructors arguments.
    * 
    * @param values
    */
   public void setValues(Object[] values) {
      this.values = values;
   }

   /**
    * Converts this descriptor to the z-file format of the descripor.
    * For example:
    * <PRE>
    * dutActivationClass woo.test.WooActivation
    * argTypeAndValue java.lang.String #"test.e"#
    * </PRE>
    * @return Returns the string as it appears in z-file.
    */
   public String toZString() {
      String _res = "dutActivationClass " + getName() + "\n";
      for (int _i = 0; _i < args.length; _i++) {
         _res += "argTypeAndValue " + args[_i] + " #\"";
         if (values[_i] instanceof String) {
            String[] _split = values[_i].toString().split(File.separator);
            if (_split.length > 1)
               _res += _split[_split.length - 1] + "\"#\n";
            else
               _res += values[_i].toString() + "\"#\n";
         } else
            _res += values[_i].toString() + "\"#\n";
      }
      return _res;
   }
}