/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
InteractiveGuessHumanSecretNumber

[#description#]
This program will try to guess the number that you are thinking. 
It will first prompt you for the lower and upper bounds of the range in which it must guess your number.
These bounds must be inclusive. 
It will then repeatedly propose a guess and you will have to provide feedback (too low, too high, you got it).
The program will then display the number of attempts it took to guess your number.
The method getIntFromUser creates a new Scanner and, reads an int value from the user, then returns it.
The method getRandomInt uses (Math.random()*(upper+1-lower)+lower) to return a random value in [upper..lower].
Implement these method in the following order; main, getIntFromUser, getRandomInt.

[#source#]
*/
import java.util.Scanner;\\npublic class InteractiveGuessHumanSecretNumber{
   public static void main(String[] args){   
      int feedback, lowerBound, upperBound, attempts=0; \\nSystem.out.println("First, enter the lower bound for my guesses"); \\nlowerBound = getIntFromUser();\\nSystem.out.println("Then, enter the upper bound");\\nupperBound = getIntFromUser();
      do{
         int guess = getRandomInt(lowerBound, upperBound);
         System.out.println("For my attempt # " + ++attempts + ", I am guessing " + guess); 
         System.out.println("Is this guess too low (1), too high (2), or right (any other value)?");
         feedback = getIntFromUser();
         if(feedback == 1) lowerBound = guess+1;
         else if(feedback ==2) upperBound = guess-1;
         else break;
      } while((feedback == 1) || (feedback == 2));
      System.out.println("I guessed in " + attempts + " attempts");\\n} //end of main method
   public static int getIntFromUser(){
      Scanner s = new Scanner(System.in);
      return s.nextInt();
   } // end of getIntFromUser method
   public static int getRandomInt(int lower, int upper){
      return (int)(Math.random()*(upper+1-lower)+lower);
   } // end of getRandomInt method\\n} // end of class