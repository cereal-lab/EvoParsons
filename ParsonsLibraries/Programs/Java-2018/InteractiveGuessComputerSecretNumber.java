/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
InteractiveGuessComputerSecretNumber

[#description#]
This program generates a random secret value in [0..50).
It then prompts the user to enter a guess and tells them whether it is too high or too low.
The program ends when the user guesses the secret number.
The number of attempts is displayed as score.
The method getIntFromUser creates a new Scanner and, reads an int value from the user, then returns it.
The method getRandomInt uses (Math.random()*max) to return a random value in [1..max).
Implement these method in the following order; main, getIntFromUser, getRandomInt.

[#source#]
*/
import java.util.Scanner;\\npublic class InteractiveGuessComputerSecretNumber{
   public static void main(String[] args){
      int guess, attempts = 0, secret = getRandomInt(50);
      do{
         System.out.println("Enter your guess for attempt #" + ++attempts);
         guess = getIntFromUser();
         if(guess > secret) System.out.println("Too big");
         else if(guess < secret) System.out.println("Too small");
      } while(guess != secret);
      System.out.println("You guessed in " + attempts + " attempts");\\n} //end of main method
   public static int getIntFromUser(){ \\n Scanner s = new Scanner(System.in); \\n return s.nextInt(); \\n } // end of getIntFromUser method
   public static int getRandomInt(int max){ \\n return (int)(Math.random()*max); \\n } // end of getRandomInt method\\n} // end of class
