[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Compute Average 

[#description#]
This program prompts the user to enter three double values.
It then displays the average of the three values.

[#source#]
import java.util.Scanner;
public class ComputeAverage {
  public static void main(String[] args) {  
    double numberOne;\\ndouble numberTwo;\\ndouble numberThree\\nScanner input = new Scanner(System.in);
    System.out.print("Enter three numbers: ");\\nnumberOne = input.nextDouble();\\nnumberTwo = input.nextDouble();\\nnumberThree = input.nextDouble();
    double mean = (numberOne + numberTwo + numberThree) / 3;
    System.out.println("The average of " + numberOne + " " + numberTwo  + " " + numberThree + " is " + mean);
   }\\n}
