/**
 * Viennet3.java
 *
 * @author Antonio J. Nebro
 * @author Juan J. Durillo
 * @version 1.0
 */

package jmetal.problems;

import jmetal.base.*;
import jmetal.base.Configuration.SolutionType_;
import jmetal.base.Configuration.VariableType_;
import jmetal.util.JMException;

/**
 * Class representing problem Viennet4
 */
public class Viennet4 extends Problem{           
  
 /** 
  * Constructor.
  * Creates a default instance of the Viennet4 problem.
  * @param solutionType The solution type must "Real" or "BinaryReal".
  */
  public Viennet4(String solutionType) {
    numberOfVariables_   = 2 ;
    numberOfObjectives_  = 3 ;
    numberOfConstraints_ = 3 ;
    problemName_         = "Viennet4";
        
    upperLimit_ = new double[numberOfVariables_];
    lowerLimit_ = new double[numberOfVariables_];
    for (int var = 0; var < numberOfVariables_; var++){
      lowerLimit_[var] =  -4.0;
      upperLimit_[var] =   4.0;
    } // for
        
    solutionType_ = Enum.valueOf(SolutionType_.class, solutionType) ; 
    
    // All the variables are of the same type, so the solutionType name is the
    // same than the variableType name
    variableType_ = new VariableType_[numberOfVariables_];
    for (int var = 0; var < numberOfVariables_; var++){
      variableType_[var] = Enum.valueOf(VariableType_.class, solutionType) ;    
    } // for
  } //Viennet4
  
  
  /** 
   * Evaluates a solution
   * @param solution The solution to evaluate
   * @throws JMException 
   */  
  public void evaluate(Solution solution) throws JMException {          
    double [] x = new double[numberOfVariables_];
    double [] f = new double[numberOfObjectives_];
        
    for (int i = 0; i < numberOfVariables_; i++) {
      x[i] = solution.getDecisionVariables().variables_[i].getValue();
    }
        
    f[0] = (x[0]-2.0)*(x[0]-2.0)/2.0 + 
             (x[1]+1.0)*(x[1]+1.0)/13.0 + 3.0;
        
    f[1] = (x[0]+ x[1]-3.0)*(x[0]+x[1]-3.0)/175.0 +
             (2.0*x[1]-x[0])*(2.0*x[1]-x[0])/17.0 -13.0;
        
    f[2] = (3.0*x[0]-2.0*x[1]+4.0)*(3.0*x[0]-2.0*x[1]+4.0)/8.0 + 
             (x[0]-x[1]+1.0)*(x[0]-x[1]+1.0)/27.0 + 15.0;
        
        
    for (int i = 0; i < numberOfObjectives_; i++) {
      solution.setObjective(i,f[i]);        
    }
  } // evaluate


  /** 
   * Evaluates the constraint overhead of a solution 
   * @param solution The solution
   * @throws JMException 
   */  
  public void evaluateConstraints(Solution solution) throws JMException {
    double [] constraint = new double[numberOfConstraints_];
        
    double x1 = solution.getDecisionVariables().variables_[0].getValue();
    double x2 = solution.getDecisionVariables().variables_[1].getValue();
        
    constraint[0] = -x2 - (4.0 * x1) + 4.0  ;
    constraint[1] = x1 + 1.0 ;
    constraint[2] = x2 - x1 + 2.0 ;
        
    int number = 0;
    double total = 0.0;
    for (int i = 0; i < numberOfConstraints_; i++) {
      if (constraint[i]<0.0){
        number++;
        total+=constraint[i];
      }
    }
    solution.setOverallConstraintViolation(total);    
    solution.setNumberOfViolatedConstraint(number);
  } // evaluateConstraints
} // Viennet4

