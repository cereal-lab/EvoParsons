[#author#]
EvoParsons Team

[#language#]
java 

[#title#]
Guess Number

[#description#]

This program generates a random number between 0 and 100.
It then repeatedly prompts the user for guesses.
For each guess, it tells the user whether the guess was too high or too low.
The program terminates when the user has guessed the number.

[#source#]
import java.util.Scanner;
public class GuessNumber {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);\\nint guess, number;\\nguess = Integer.MIN_VALUE;\\nnumber = (int)(Math.random() * 101);
    while (guess != number) {\\n  System.out.print("Enter your guess: ");\\n  guess = input.nextInt();
      if (guess == number){ \\n  System.out.println("Yes, the number is " + number);\\n}
      else if (guess > number){ \\nSystem.out.println("Your guess is too high");\\n}
      else { \\nSystem.out.println("Your guess is too low");\\n} 
   }\\n}\\n}
