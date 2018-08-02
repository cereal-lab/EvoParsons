[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Dec 2 Hex

[#description#]
This program is a Decimal to HexaDecimal Converter. 
It reads an integer value from the user, assumed to be a decimal number, and displays its conversion in hexadecimal.
For example, if the user enters the value 175, the program will display AF.

[#source#]
import java.util.Scanner;
public class Dec2Hex {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);\\nString hex = "";\\nSystem.out.print("Enter a decimal number: ");\\nint decimal = input.nextInt();
    while (decimal != 0) {
      int hexValue = decimal % 16;\\nchar hexDigit = (hexValue <= 9 && hexValue >= 0) ?  (char)(hexValue + '0') : (char)(hexValue - 10 + 'A');
      hex = hex + hexDigit;\\ndecimal = decimal / 16;\\n}
    System.out.println("Its conversion to hexadecimal is " + hex);
   }\\n}

