/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
ArraysPalindrom

[#description#]
This program implements a method isPalindrom that returns true if its array parameter is a palindrom.
A palindrom is an array that features the same sequence of characters when read from left to right or backward.
The main method calls isPalindrom on a series of arrays used as tests and displays a message only if one of the tests fails.
Implement these method in the following order; main, isPalindrom.

[#source#]
*/
public class ArraysPalindrom {\\n   public static void main(String[] args){ \\nint[] test1 = {1,2,3,4,5}, test2 = {1,2,3,2,1};  \\nif(!isPalindrom(test1))  \\nSystem.out.println("Test #1 failed!");  \\nif(!isPalindrom(test2))  \\nSystem.out.println("Test #2 failed!");  \\n} //end of main method
   public static boolean isPalindrom(int[] a){
      int low = 0, high = a.length - 1;
      while (high > low) {
         if (a[low] != a[high])
            return false;
         low++ ; \\n high--; \\n } // end of while loop
      return true;
   } // end of isPalindrom method \\n} // end of class
