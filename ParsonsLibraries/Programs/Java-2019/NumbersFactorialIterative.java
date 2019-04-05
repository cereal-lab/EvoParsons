//<fragment>
import java.util.Scanner;
public class NumbersFactorialIterative{
//</fragment>
//<fragment>
   public static void main(String[] args){   
System.out.println("Enter an int value:");
int n = getIntFromUser();
System.out.println("Fact(" + n + ") = " + factorial(n));
} //end of main method
//</fragment>
//<fragment>
   public static long factorial(int value){
//</fragment>
//<fragment>
      int result = 1;
//</fragment>
//<fragment>
      while(value > 1) {
 result = result * value;
//</fragment>
//<fragment>
        value = value - 1;
//</fragment>
//<fragment>
      }//end of while loop insed factorial method 
 return result; 
 } // end of factorial method
//</fragment>
//<fragment>
   public static int getIntFromUser(){ 
 Scanner s = new Scanner(System.in); 
 return s.nextInt(); 
 } // end of getIntFromUser method
} // end of class
//</fragment>
