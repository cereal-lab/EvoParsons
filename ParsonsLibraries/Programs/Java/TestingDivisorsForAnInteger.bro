[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Testing Divisors For an Integer

[#description#]
This program reads an integer value from the user.
It then displays first whether that value is divisible by 2 AND by 3.
After that, it displays whether that value is divisible by 2 OR by 3.

[#source#]
import java.util.Scanner;
public class TestingDivisorsForAnInteger {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);\\nSystem.out.print("Enter an integer: ");\\nint number = input.nextInt();
    System.out.println("Is " + number + " divisible by 2 and 3? " +  ((number % 2 == 0) && (number % 3 == 0)));
    System.out.println("Is " + number + " divisible by 2 or 3? " +  ((number % 2 == 0) || (number % 3 == 0)));
   }\\n}


