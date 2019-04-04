/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
NumbersFactorial

[#description#]
This program prompts the user to enter an integer value (greater than or equal to zero) , then displays the factorial of that value.
The Factorial of a number n is computed as follows; Fact(0) = 1 Fact(1) = 1, and Fact(n) = Fact(n-1) * n.
Please note that this version uses recursion to compute its result.
The method getIntFromUser creates a new Scanner and, reads an integer value from the user, then returns it.
Implement these method in the following order; main, factorial, getIntFromUser.

[#source#]
*/
import java.util.Scanner;\\npublic class NumbersFactorial{ \\n public static void main(String[] args){ \\n System.out.println("Enter an int value:"); \\n int n = getIntFromUser(); \\n System.out.println("Fact(" + n + ") = " + factorial(n));  \\n } //end of main method
   public static long factorial(int value){
      if(value >= 1) {  \\n return factorial(value-1) * value; \\n }
      else { \\n return 1; \\n }
   } // end of factorial method
   public static int getIntFromUser(){ \\nScanner s = new Scanner(System.in);\\nreturn s.nextInt();\\n} // end of getIntFromUser method\\n} // end of class
