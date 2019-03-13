/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
NumbersFibonacciIterative

[#description#]
This program prompts the user to enter a stricctly positive integer value, then displays the corresponding Fibonacci number.
The Fibonacci series is defined as follows; Fib(1) = 1, Fib(2) = 2, and Fib(n) = Fib(n-1) + Fib(n-2).
Please note that this version uses a loop to compute its result.
The method getIntFromUser creates a new Scanner and, reads an int value from the user, then returns it.
Implement these method in the following order; main, fibonaccci, getIntFromUser.

[#source#]
*/
import java.util.Scanner;\\npublic class NumbersFibonacciIterative{ \\n public static void main(String[] args){ \\n System.out.println("Which Fibonacci number do you want me to display?"); \\n int n = getIntFromUser(); \\n System.out.println("Fib(" + n + ") = " + fibonacci(n));  \\n } //end of main method
   public static long fibonacci(int value){
      int current = 1, previous = 0, counter = 1;
      while(counter++ < value){
         int next = current + previous;
         previous = current;
         current = next;
      } \\n return current; \\n } // end of fibonacci method
   public static int getIntFromUser(){\\nScanner s = new Scanner(System.in);\\nreturn s.nextInt();\\n} // end of getIntFromUser method\\n} // end of class
