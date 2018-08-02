[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Addition Quiz

[#description#]
This program prompts the user to enter the result of adding two arbitrary integers.
It then displays whether the solution provided by the user is correct or not.

[#source#]
import java.util.Scanner;
public class AdditionQuiz{
  public static void main(String[] args){
    int numberOne, numberTwo, answer;\\nnumberOne = 55;\\nnumberTwo = 75;
    System.out.print("What is " + numberOne + " + " + numberTwo + "? ");\\nanswer = (new Scanner(System.in)).nextInt();
    System.out.println( numberOne + " + " + numberTwo + " = " + answer + " is " + (numberOne + numberTwo == answer));
   }\\n}

