[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Subtraction Quiz

[#description#]
This program prompts the user to enter the result of subtracting two arbitrary integers.
It then displays whether the solution provided by the user is correct or not.

[#source#]
import java.util.Scanner;
public class SubtractionQuiz {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);\\nint numberOne, numberTwo;\\nint answer;\\nnumberOne = 85;\\nnumberTwo = 75;
    System.out.print ("What is " + numberOne + " - " + numberTwo + "? ");\\nanswer = input.nextInt();
    if (numberOne - numberTwo == answer){ \\nSystem.out.println("You are correct!");\\n}
    else { \\nSystem.out.println("Your answer is wrong. " + numberOne + " - "  + numberTwo + " should be " + (numberOne - numberTwo));\\n} 
  }\\n}
