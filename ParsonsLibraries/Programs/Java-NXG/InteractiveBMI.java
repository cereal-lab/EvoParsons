//<fragment>
import java.util.Scanner;
public class InteractiveBMI {
//</fragment>
//<fragment>
   public static void main(String[] args){
System.out.println("Enter you weight (in pounds): "); 
double weight = getDoubleFromUser();
System.out.println("Enter you height (in inches): "); 
double height = getDoubleFromUser();
double bmi = computeBMI(weight*0.45, height*0.025);
String status = interpretBMI(bmi);
System.out.println("Your BMI is " + bmi + " which corresponds to " + status);
} //end of main method
//</fragment>
//<fragment>
   public static double computeBMI(double w, double h){ 
 return w / (h*h);   
 } // end of computeBMI method
//</fragment>
//<fragment>
   public static String interpretBMI(double value){
//</fragment>
//<fragment>
      if (value < 18.5) return "Underweight";
//</fragment>
//<fragment>
      else if (value < 25) return "Normal"; 
 else if (value < 30) return "Overweight";
//</fragment>
//<fragment>
      else return "Obese"; 
 } // end of interpretBMI method
//</fragment>
//<fragment>
   public static double getDoubleFromUser(){ 
 Scanner s = new Scanner(System.in);  
 return s.nextDouble(); 
 } // end of getDoubleFromUser method
} // end of class
//</fragment>
