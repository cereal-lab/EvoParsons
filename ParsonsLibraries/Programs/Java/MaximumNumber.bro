[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Maximum Number

[#description#]
This program reads positive integer values from the user until a zero is entered.
At that point, it displays the largest value that was entered.

[#source#]
import java.util.Scanner;
public class MaximumNumber {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);\\nint number,  max;\\nmax = Integer.MIN_VALUE;
    do{ 
      number = input.nextInt();
      if(number == 0){ \\nbreak;\\n}
      else if (number > max){ \\nmax = number;\\n} 
    }while (true);
    System.out.println("maximum among the numbers entered is: " + max );
   }\\n}
