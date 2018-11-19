/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
NumbersDisplayDigits

[#description#]
This program generates a random 5 digits integer, then displays the digits composing that value, one by one.
The method getRandomInt uses (Math.random()*(upper+1-lower)+lower) to return a random value in [upper..lower].
Implement these method in the following order; main, getIntFromUser.

[#source#]
*/
import java.util.Scanner;\\npublic class NumbersDisplayDigits{
   public static void main(String[] args){   \\nint n = getRandomInt(10000,99999);\\nSystem.out.println("Random number is " + n);\\nSystem.out.println("Here are the digits composing it, from right to left:");
      while(n>0){
         System.out.println(n%10);
         n = (n - n%10) / 10;
      } // end of while loop \\n} //end of main method
   public static int getRandomInt(int lower, int upper){
      return (int)(Math.random()*(upper+1-lower)+lower);
   } // end of getRandomInt method \\n} // end of class