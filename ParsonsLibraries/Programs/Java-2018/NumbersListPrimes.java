/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
NumbersListPrimes

[#description#]
This program prompts the user to enter an integer value (n) and then proceeds to display the first n prime numbers, five per line.
The method getIntFromUser creates a new Scanner and, reads an int value from the user, then returns it.
Implement these method in the following order; main, displayPrimes, isPrime, getIntFromUser.

[#source#]
*/
import java.util.Scanner;\\npublic class NumbersListPrimes{
   public static void main(String[] args){   \\nSystem.out.println("How many prime numbers do you want me to display?");\\nint n = getIntFromUser();\\ndisplayPrimes(n);\\n} //end of main method
   public static void displayPrimes(int primesToFind){
      int primesFound = 0, number = 2, primesPerLine = 5;
      while(primesFound < primesToFind){
         if(isPrime(number)){
            System.out.print(number + "\t");
            if(++primesFound % primesPerLine == 0)
               System.out.println();\\n} // end of if statement\\nnumber++;\\n} // end of while loop\\n} // end of displayPrimes method
   public static boolean isPrime(int value){
      for (int divisor = value/2; divisor > 1 ; divisor--)
         if(value % divisor == 0)
            return false;
      return true;
   } // end of isPrimemethod
   public static int getIntFromUser(){\\nScanner s = new Scanner(System.in);\\nreturn s.nextInt();\\n} // end of getIntFromUser method\\n} // end of class