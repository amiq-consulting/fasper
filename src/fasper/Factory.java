package fasper;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.HashSet;

import fasper.base.*;

import antlr.collections.AST;

/**
 * Knows how to create instances of the plugin components using reflection.
 */
class Factory {
   static {
      FasperException.addErrorCode("UNKNOWN_ACTIVATION_CLASS");
      FasperException.addErrorCode("UNKNOWN_ACTIVATION_CONSTRUCTOR");
      FasperException.addErrorCode("ACTIVATION_INSTANCE_FAILED");
      FasperException.addErrorCode("UNKNOWN_ARGUMENT_CLASS");
      FasperException.addErrorCode("PARSER_ACTIVATION_FAILED");
      FasperException.addErrorCode("WRONG_ACTIVATION_CLASS");
  
      FasperException.addErrorCode("UNKNOWN_FILTER_CLASS");
      FasperException.addErrorCode("UNKNOWN_FILTER_CONSTRUCTOR");
      FasperException.addErrorCode("FILTER_INSTANCE_FAILED");
      FasperException.addErrorCode("WRONG_FILTER_CLASS");
      
      FasperException.addErrorCode("UNKNOWN_COMPARATOR_CLASS");
      FasperException.addErrorCode("UNKNOWN_COMPARATOR_CONSTRUCTOR");
      FasperException.addErrorCode("COMPARATOR_INSTANCE_FAILED");
      FasperException.addErrorCode("WRONG_COMPARATOR_CLASS");
   }
   
   /**
    * Basic constructor.
    */
   Factory() {
   }
   
   /**
    * Return a parser activation class
    * @param _act The activation descriptor.
    * @param _values The values for which to create instance.
    * @return Returns a ParserActivation instance.
    * @throws FasperException Is thrown if it couldn't create the desired instance.
    */
   ParserActivation getActivationFor(ActivationDescriptor _act
      , Object[] _values) throws FasperException {
      _act.setValues(_values);
      return getActivationFor(_act);
   }
   
   /**
    * Return a parser activation class
    * @param _act The activation descriptor.
    * @return Returns a parser activation class.
    * @throws FasperException  Is thrown if it couldn't create the desired instance.
    */
   ParserActivation getActivationFor(ActivationDescriptor _act) 
   	throws FasperException {
      String[] _sargs = _act.getArgs();
      Class[] _args = new Class[_sargs.length];
      for (int i = 0; i <= _sargs.length - 1; i++) {
         try {
            _args[i] = Class.forName(_sargs[i]);           
         }catch(ClassNotFoundException _cnfe) { 
            throw new FasperException(
               "UNKNOWN_ARGUMNET_CLASS"
               , "Can not create argument class : " + _sargs[i]
                  + " for activation :" + _act.getName()
               , _cnfe);
         };
      };
       try {
         Class _class = Class.forName(_act.getName());
         Constructor _cons = _class.getConstructor(_args);
         ParserActivation _ac = (ParserActivation) _cons.newInstance(_act.getValues());
         return _ac;
      } catch(ClassNotFoundException _cnfe) {
         throw new FasperException(
            "UNKNOWN_ACTIVATION_CLASS"
            , "Can not create instance of " + _act.getName());
      } catch (NoSuchMethodException _nsme) {
         throw new FasperException(
            "UNKNOWN_ACTIVATION_CONSTRUCTOR"
            , "Could not find a constructor for " + _act.getName());
      } catch(ClassCastException _cce) {
         throw new FasperException(
            "WRONG_ACTIVATION_CLASS"
            , "Class " + _act.getName() + " should implement " 
            + "fasper.base.TestActivation or " 
            + "fasper.base.ParserActivation");
      } catch (Exception _ex) {
         throw new FasperException(
            "ACTIVATION_INSTANCE_FAILED"
            , "Could not get an instance of " + _act.getName());
      }
   }
   
   /**
    * Creates a filter instance.
    * @param _filt The full name of the filter class.
    * @return Returns a filter instance of the specified type.
    * @throws FasperException  
    */
   ASTFilter getFilter(String _filt) throws FasperException {
      if (_filt == null) 
         return new ASTFilter() {
         	public String filterNode(AST _node){
         	   return _node.getText();
            }
         	public boolean isNodeIgnored(AST _node) {
         	   return false;
         	}
         	public void useTokenMap(HashMap _map){
         	}
         };
      try {
         Class _class = Class.forName(_filt);
         Constructor _cons = _class.getConstructor(new Class[0]);
         ASTFilter _ac = (ASTFilter) _cons.newInstance(new Object[0]);
         return _ac;
      } catch(ClassNotFoundException _cnfe) {
         throw new FasperException(
            "UNKNOWN_FILTER_CLASS"
            , "Can not create instance of " + _filt);
      } catch (NoSuchMethodException _nsme) {
         throw new FasperException(
            "UNKNOWN_FILTER_CONSTRUCTOR"
            , "Could not find a constructor for " + _filt);
      } catch (ClassCastException _cce) {
         throw new FasperException(
            "WRONG_FILTER_CLASS"
            , "Class " + _filt + " should implement fasper.base.FilterBank");
      } catch (Exception _ex) {
         throw new FasperException(
            "FILTER_INSTANCE_FAILED"
            , "Could not get an instance of " + _filt);
      }
   }
   
   /**
    * Creates an AST comparator. 
    * @param _comp The full name of the AST comparator class.
    * @return Returns an AST comparator instance of the specified type.
    * @throws FasperException
    */
   ASTComparator getComparator(String _comp) throws FasperException { 
      try {
         Class _class = Class.forName(_comp);
         Constructor _cons = _class.getConstructor(new Class[0]);
         ASTComparator _ac = (ASTComparator) _cons.newInstance(new Object[0]);
         return _ac;
      } catch(ClassNotFoundException _cnfe) {
         throw new FasperException(
            "UNKNOWN_COMPARATOR_CLASS"
            , "Can not create instance of " + _comp);
      } catch (NoSuchMethodException _nsme) {
         throw new FasperException(
            "UNKNOWN_COMPARATOR_CONSTRUCTOR"
            , "Could not find a constructor for " + _comp);
      } catch (ClassCastException _cce) {
         throw new FasperException(
            "WRONG_COMPARATOR_CLASS"
            , "Class " + _comp 
            + " should implement fasper.base.FilterBank");
      } catch (Exception _ex) {
         throw new FasperException(
            "FILTER_COMPARATOR_FAILED"
            , "Could not get an instance of " + _comp);
      }
   }
   
   
}