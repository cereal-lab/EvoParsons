/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
InteractiveBMI

[#description#]
This program prompts the user to first enter their weight (in pounds) and then their height (in inches).
It then converts the weight into kilograms (multiply by 0.45) and the height in meters (multiply by 0.025).
These values are then passed to a method computeBMI that returns the body mass index, based on these two parameters.
The formula to compute the BMI is to divide the weight by the square of the height.
The BMI value returned by computeBMI is then passed as parameter to the method interpretBMI that returns
a string indicating, based on the BMI value, whether the user is underweight (< 18.5), normal (< 25), overweight (< 30), or obese.
The method getDoubleFromUser creates a new Scanner, reads a double value from the user, then returns it.
Implement these method in the following order; main, computeBMI, interpretBMI, getDoubleFromUser.

[#source#]
*/
import java.util.Scanner;\\npublic class InteractiveBMI {
   public static void main(String[] args){\\nSystem.out.println("Enter you weight (in pounds): "); \\ndouble weight = getDoubleFromUser();\\nSystem.out.println("Enter you height (in inches): "); \\ndouble height = getDoubleFromUser();\\ndouble bmi = computeBMI(weight*0.45, height*0.025);\\nString status = interpretBMI(bmi);\\nSystem.out.println("Your BMI is " + bmi + " which corresponds to " + status);\\n} //end of main method
   public static double computeBMI(double w, double h){ \\n return w / (h*h);   \\n } // end of computeBMI method
   public static String interpretBMI(double value){
      if (value < 18.5) return "Underweight";
      else if (value < 25) return "Normal"; \\n else if (value < 30) return "Overweight";
      else return "Obese"; \\n } // end of interpretBMI method
   public static double getDoubleFromUser(){ \\n Scanner s = new Scanner(System.in);  \\n return s.nextDouble(); \\n } // end of getDoubleFromUser method\\n} // end of class
