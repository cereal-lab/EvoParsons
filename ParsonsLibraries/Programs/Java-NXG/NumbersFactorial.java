//<fragment>
import java.util.Scanner;
public class NumbersFactorial{ 
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
      if(value >= 1) {  
 return factorial(value-1) * value; 
 }
//</fragment>
//<fragment>
      else { 
 return 1; 
 }
//</fragment>
//<fragment>
   } // end of factorial method
//</fragment>
//<fragment>
   public static int getIntFromUser(){ 
Scanner s = new Scanner(System.in);
return s.nextInt();
} // end of getIntFromUser method
} // end of class
//</fragment>
