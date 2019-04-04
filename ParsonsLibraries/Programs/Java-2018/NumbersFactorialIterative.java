/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
NumbersFactorialIterative

[#description#]
This program prompts the user to enter an integer value (greater than or equal to zero), then displays the factorial of that value.
The Factorial of a number n is computed as follows; Fact(0) = 1, Fact(1) = 1, and Fact(n) = Fact(n-1) * n.
Please note that this version uses a loop to compute its result.
The method getIntFromUser creates a new Scanner, reads an int value from the user, then returns it.
Implement these method in the following order; main, factorial, getIntFromUser.

[#source#]
*/
import java.util.Scanner;\\npublic class NumbersFactorialIterative{
   public static void main(String[] args){   \\nSystem.out.println("Enter an int value:");\\nint n = getIntFromUser();\\nSystem.out.println("Fact(" + n + ") = " + factorial(n));\\n} //end of main method
   public static long factorial(int value){
      int result = 1;
      while(value > 1) {\\n result = result * value;
        value = value - 1;
      }//end of while loop insed factorial method \\n return result; \\n } // end of factorial method
   public static int getIntFromUser(){ \\n Scanner s = new Scanner(System.in); \\n return s.nextInt(); \\n } // end of getIntFromUser method\\n} // end of class
