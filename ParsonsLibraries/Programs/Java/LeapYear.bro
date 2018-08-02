[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Leap Year

[#description#]
This program reads a year value from the user.
It then displays whether it is a leap year.
A leap year is a year that is divisible by 4 but not by 100, or that is divisible by 400

[#source#]
import java.util.Scanner;
public class LeapYear {
  public static void main(String args[]) {
    Scanner input = new Scanner(System.in);\\nSystem.out.print("Enter a year: ");\\nint year = input.nextInt();
    boolean isLeapYear;\\nisLeapYear =  (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    System.out.println(year + " is a leap year? " + isLeapYear);
   }\\n}
