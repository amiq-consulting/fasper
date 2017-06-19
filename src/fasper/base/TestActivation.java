package fasper.base;

import java.util.*;
/**
 * Common interface for test activations.
 * It extends the functionality of the ParserActivation with a method 
 * for retrieving the parser under test activation descriptor. 
 * @see fasper.gm.GMActivation
 * @see fasper.base.ParserActivation 
 */
public interface TestActivation extends ParserActivation {
   
   /**
    * Returns an activation descriptor of the parser under test.
    * @return <code>ActivationDescriptor</code> activation descriptor 
    * of the parser under test.
    */
   public ActivationDescriptor getPADescriptor();
   
}
