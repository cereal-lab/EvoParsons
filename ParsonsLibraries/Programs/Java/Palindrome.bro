[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Palindrome

[#description#]
This program determines whether a string is a palindrome.
A palindrome is string which reads the same backward or forward.
Example: anna, radar

[#source#]
import java.util.Scanner;
public class Palindrome {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);\\nSystem.out.print("Enter a string: ");\\nString s = input.nextLine();\\nint high = s.length() - 1;
    int low = 0;\\nboolean isPalindrome = true;\\nwhile (low < high) {
      if (s.charAt(low) != s.charAt(high)) { \\nisPalindrome = false;\\nbreak;\\n}
      low = low + 1;\\nhigh = high - 1;\\n}
    if (isPalindrome){ \\nSystem.out.println(s + " is a palindrome");\\n}
    else { \\nSystem.out.println(s + " is not a palindrome");\\n}
   }\\n}


