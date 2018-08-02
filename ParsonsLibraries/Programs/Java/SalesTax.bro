[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Sales Tax

[#description#]
This program calculates seven percent (0.07) sales tax for a purchase amount provided by the user.

[#source#]
import java.util.Scanner;
public class SalesTax {
  public static void main(String[] args) {
    double purchaseAmount, tax;\\nScanner input = new Scanner(System.in);\\nSystem.out.print("Please enter purchase amount: ");\\npurchaseAmount = input.nextDouble();
    tax = purchaseAmount * 0.07;
    System.out.println("Sales tax is " + (tax * 100) / 100.0);
   }\\n}
