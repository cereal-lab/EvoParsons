/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
NumbersFactorialIterative

[#description#]
This program prompts the user to enter an integer value, then displays the factorial of that value.
The Factorial of a number n is computed as follows; Fact(0) = 0, Fact(1) = 1, and Fact(n) = Fact(n-1) * n.
Please note that this version uses a loop to compute its result.
The method getIntFromUser creates a new Scanner and, reads an int value from the user, then returns it.
Implement these method in the following order; main, factorial, getIntFromUser.

[#source#]
*/
import java.util.Scanner;\\npublic class NumbersFactorialIterative{
   public static void main(String[] args){   \\nSystem.out.println("Enter an int value:");\\nint n = getIntFromUser();\\nSystem.out.println("Fact(" + n + ") = " + factorial(n));      \\n} //end of main method
   public static long factorial(int value){
      int result = value; 
      while(value > 1)
         result = result * --value;
      return result;
   } // end of factorial method
   public static int getIntFromUser(){
      Scanner s = new Scanner(System.in);
      return s.nextInt();
   } // end of getIntFromUser method\\n} // end of class