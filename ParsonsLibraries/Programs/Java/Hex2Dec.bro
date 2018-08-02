[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Hex to Dec

[#description#]
This program takes a single hexadecimal digit and displays its conversion into a decimal value.
For example, if the user enters B, then the program will convert it to 11.


[#source#]
import java.util.Scanner;
public class Hex2Dec {
  public static void main(String[] args) {
     char ch;\\nScanner input = new Scanner(System.in);\\nSystem.out.print("Enter a single hexadecimal digit: ");\\nString hexString = input.nextLine();\\nch = Character.toUpperCase(hexString.charAt(0));
    if (ch <= 'F' && ch >= 'A') { \\n  int value = ch - 'A' + 10;\\n  System.out.println("The decimal value for hex digit " + ch + " is " + value);\\n}
    else if (Character.isDigit(ch)){ \\nSystem.out.println("The decimal value for hex digit " + ch + " is " + ch);\\n}
    else { \\nSystem.out.println(ch + " is an invalid input");\\n}
   }\\n}


