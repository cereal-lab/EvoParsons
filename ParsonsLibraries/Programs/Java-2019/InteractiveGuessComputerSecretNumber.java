//<fragment>
import java.util.Scanner;
public class InteractiveGuessComputerSecretNumber{
//</fragment>
//<fragment>
   public static void main(String[] args){
//</fragment>
//<fragment>
      int guess, secret, attempts = 0;
//</fragment>
//<fragment>
      secret = getRandomInt(50);
//</fragment>
//<fragment>
      do{
//</fragment>
//<fragment>
         System.out.println("Enter your guess for attempt #" + ++attempts);
//</fragment>
//<fragment>
         guess = getIntFromUser();
//</fragment>
//<fragment>
         if(guess > secret) { 
 System.out.println("Too big"); 
 }
//</fragment>
//<fragment>
         else if(guess < secret) { 
 System.out.println("Too small"); 
 }
//</fragment>
//<fragment>
      } while(guess != secret);
//</fragment>
//<fragment>
      System.out.println("You guessed in " + attempts + " attempts");
} //end of main method
//</fragment>
//<fragment>
   public static int getIntFromUser(){ 
 Scanner s = new Scanner(System.in); 
 return s.nextInt(); 
 } // end of getIntFromUser method
//</fragment>
//<fragment>
   public static int getRandomInt(int max){ 
 return (int)(Math.random()*max); 
 } // end of getRandomInt method
} // end of class
//</fragment>
