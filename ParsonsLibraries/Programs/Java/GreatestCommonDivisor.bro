[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Greatest Common Divisor


[#description#]
This program finds the greatest common divisor (GCD) between two integer values provided by the user.
The GCD of two integers is the largest positive integer that is a divisor of both values.

[#source#]
import java.util.Scanner;
public class GreatestCommonDivisor {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);\\nint gcd, k, numberOne, numberTwo;\\ngcd = 1;\\nk = 2;
    System.out.print("Enter the first integer value (numberOne): ");\\nnumberOne = input.nextInt();
    System.out.print("Enter the second integer value (numberTwo): ");\\nnumberTwo = input.nextInt();
    while (k <= numberOne && k <= numberTwo) { \\n  if (numberOne % k == 0 && numberTwo % k == 0){  \\ngcd = k;\\n} \\nk = k + 1;\\n}
    System.out.println("The greatest common divisor for " + numberOne +  " and " + numberTwo + " is " + gcd);
   }\\n}

