//<fragment>
import java.util.Scanner;
public class NumbersFibonacci{ 
 public static void main(String[] args){ 
 System.out.println("Which Fibonacci number do you want me to display?"); 
 int n = getIntFromUser(); 
 System.out.println("Fib(" + n + ") = " + fibonacci(n)); 
 } //end of main method
//</fragment>
//<fragment>
   public static long fibonacci(int value){
//</fragment>
//<fragment>
      if((value==1)||(value==2)){ 
 return 1; 
 }
//</fragment>
//<fragment>
      else { 
 return fibonacci(value-1) + fibonacci(value-2); 
 }
//</fragment>
//<fragment>
   } // end of fibonacci method
//</fragment>
//<fragment>
   public static int getIntFromUser(){ 
 Scanner s = new Scanner(System.in); 
 return s.nextInt(); 
 } // end of getIntFromUser method
} // end of class
//</fragment>
