[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Addition Quiz V2

[#description#]
This program generates two random integers between 0 and 9 (inclusive).
It then prompts the user to enter the value of their sum until the answer is correct.

[#source#]
import java.util.Scanner;
public class AdditionQuizV2{
  public static void main(String[] args) {
    int numberOne, numberTwo, answer;\\nnumberOne = (int)(Math.random() * 10);\\nnumberTwo = (int)(Math.random() * 10);
    do{
      System.out.print( "What is " + numberOne + " + " + numberTwo + "? ");\\nanswer = (new Scanner(System.in)).nextInt();
      if(numberOne + numberTwo != answer){ \\nSystem.out.print("Wrong answer. Try again.");\\n}
    }while (numberOne + numberTwo != answer);
    System.out.println("Congratulation, you make a perfect score!");
   }\\n}


