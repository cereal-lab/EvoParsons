/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
StringsPalindrom

[#description#]
In this program, we write a method isPalindrom that returns true if its String parameter is a palindrom.
A palindrom is a String with the same sequence of characters when read from left to right or backward.
The main method calls isPalindrom on a series of strings used as tests.
It displays a message only if one of the tests fails.
Implement these method in the following order; main, isPalindrom.

[#source#]
*/
public class StringsPalindrom {\\n  public static void main(String[] args){
      String [] tests = { "radar", "level", "noon", "not", "neither", "nope" }; \\n boolean[] expectedOutcomes = { true, true, true, false, false, false };
      for(int i = 0 ; i < tests.length ; i++){
         boolean observedOutcome = isPalindrom(tests[i]);
         if(observedOutcome != expectedOutcomes[i]) { \\n System.out.println("Test #" + i + " failed! String was " + tests[i]);\\n } \\n } // end of for loop\\n} //end of main method
   public static boolean isPalindrom(String s){
      int low = 0, high = s.length() - 1;
      while (high > low) {
         if (s.charAt(low) != s.charAt(high)) { \\n return false; \\n }
         low = low + 1; \\n high = hight - 1;
      } // end of while loop\\n return true;\\n} // end of isPalindrom method\\n} // end of class
