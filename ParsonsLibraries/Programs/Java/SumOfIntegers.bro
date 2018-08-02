[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Sum Of Integers

[#description#]
This program repeatedly prompts the user to enter integer values until a zero is entered.
It then displays the sum of all values that were entered.

[#source#]
import java.util.Scanner;
public class SumOfIntegers {
  public static void main(String[] args){
    int data, sum;\\nScanner input = new Scanner(System.in);\\nsum = 0;
    do{ \\n  System.out.print("Please enter an integer value (0 to exit): ");\\n  data = input.nextInt();\\n  sum += data;\\n}while (data != 0);
    System.out.println("The sum is " + sum);
   }\\n}


