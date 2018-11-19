/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
NumbersFibonacci

[#description#]
This program prompts the user to enter a strictly positive integer value, then displays the corresponding Fibonacci number.
The Fibonacci series is defined as follows; Fib(1) = 1, Fib(2) = 1, and Fib(n) = Fib(n-1) + Fib(n-2).
Please note that this version uses recursion to compute its result.
The method getIntFromUser creates a new Scanner and, reads an int value from the user, then returns it.
Implement these method in the following order; main, fibonaccci, getIntFromUser.

[#source#]
*/
import java.util.Scanner;\\npublic class NumbersFibonacci{
   public static void main(String[] args){   
      System.out.println("Which Fibonacci number do you want me to display?");
      int n = getIntFromUser();
      System.out.println("Fib(" + n + ") = " + fibonacci(n));      
   } //end of main method
   public static long fibonacci(int value){
      if((value==1)||(value==2)) return 1;
      else return fibonacci(value-1) + fibonacci(value-2);
   } // end of fibonacci method
   public static int getIntFromUser(){
      Scanner s = new Scanner(System.in);
      return s.nextInt();
   } // end of getIntFromUser method\\n} // end of class