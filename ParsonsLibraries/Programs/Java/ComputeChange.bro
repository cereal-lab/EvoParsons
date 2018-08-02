[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Compute Change

[#description#]
This program takes an amount in US dollar (eg. 1.78) from the user.
It then displays how this amount breaks down into one-dollar bill(s), quarter(s), dime(s), nickel(s) and pennies (in this order).
For Example, if the user enters the value 1.98, the program will display:
1.78 can be broken down into 1 dollars 3 quarters 3 pennies

[#source#]
import java.util.Scanner;
public class ComputeChange {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);\\nSystem.out.print("Enter an amount for example 11.56: ");\\ndouble amount = input.nextDouble();
    int remainingAmount = (int)(amount * 100);
    int numberOfOneDollars = remainingAmount / 100;
    remainingAmount = remainingAmount % 100;
    int numberOfQuarters = remainingAmount / 25;
    remainingAmount = remainingAmount % 25;
    int numberOfDimes = remainingAmount / 10;
    remainingAmount = remainingAmount % 10;
    int numberOfNickels = remainingAmount / 5;
    remainingAmount = remainingAmount % 5;
    int numberOfPennies = remainingAmount;
    System.out.println(amount + " can be broken down into " + numberOfOneDollars + " dollars, " + numberOfQuarters + " quarters, " + numberOfDimes + " dimes, " + numberOfNickels + " nickels, " + numberOfPennies + " pennies");
   }\\n}
