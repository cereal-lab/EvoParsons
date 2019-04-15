//<fragment>
import java.util.Scanner;
public class NumbersFibonacciIterative{ 
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
      int current = 1, previous = 1; 
 if (value <= 1) return value;
//</fragment>
//<fragment>
      for(int i = 2; i < value; i++){
//</fragment>
//<fragment>
         int next = current + previous;
//</fragment>
//<fragment>
         previous = current;
//</fragment>
//<fragment>
         current = next;
//</fragment>
//<fragment>
      } 
 return current; 
 } // end of fibonacci method
//</fragment>
//<fragment>
   public static int getIntFromUser(){
Scanner s = new Scanner(System.in);
return s.nextInt();
} // end of getIntFromUser method
} // end of class
//</fragment>
