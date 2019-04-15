//<fragment>
import java.util.Scanner;
public class InteractiveGuessHumanSecretNumber{
//</fragment>
//<fragment>
   public static void main(String[] args){
//</fragment>
//<fragment>
      int feedback, lowerBound, upperBound, attempts=0; 
System.out.println("First, enter the lower bound for my guesses"); 
lowerBound = getIntFromUser();
System.out.println("Then, enter the upper bound");
upperBound = getIntFromUser();
//</fragment>
//<fragment>
      do{
//</fragment>
//<fragment>
         int guess = getRandomInt(lowerBound, upperBound); 
 System.out.println("For my attempt # " + ++attempts + ", I am guessing " + guess); 
 System.out.println("Is this guess too low (1), too high (2), or right (any other value)?");
//</fragment>
//<fragment>
         feedback = getIntFromUser();
//</fragment>
//<fragment>
         if(feedback == 1) { 
 lowerBound = guess + 1; 
 }
//</fragment>
//<fragment>
         else if(feedback ==2) { 
 upperBound = guess - 1; 
 }
//</fragment>
//<fragment>
         else break;
//</fragment>
//<fragment>
      } while((feedback == 1) || (feedback == 2));
//</fragment>
//<fragment>
      System.out.println("I guessed in " + attempts + " attempts");
} //end of main method
//</fragment>
//<fragment>
   public static int getIntFromUser(){ 
 Scanner s = new Scanner(System.in); 
 return s.nextInt(); 
 } // end of getIntFromUser method
//</fragment>
//<fragment>
   public static int getRandomInt(int lower, int upper){ 
 return (int)(Math.random()*(upper+1-lower)+lower); 
 } // end of getRandomInt method
} // end of class
//</fragment>
