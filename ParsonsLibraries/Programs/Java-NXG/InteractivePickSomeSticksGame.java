//<fragment>
import java.util.Scanner;
public class InteractivePickSomeSticksGame{
//</fragment>
//<fragment>
   public static void main(String[] args){
//</fragment>
//<fragment>
      int numberOfSticks = 100, turn = 1, activePlayer = 1;
//</fragment>
//<fragment>
      do{ 
 System.out.print(numberOfSticks + " sticks left, \t"); 
 numberOfSticks -= player(activePlayer, numberOfSticks); 
 activePlayer = (activePlayer == 1) ? 2 : 1; 
 }while(numberOfSticks > 0); 
 } // end of main method
//</fragment>
//<fragment>
   public static int player(int id, int remaining){
//</fragment>
//<fragment>
      int taken;
//</fragment>
//<fragment>
      if(remaining==0){ // This player lost 
 System.out.println("Player " + (3-id) + " won the game!"); 
 taken = 0;
 }
//</fragment>
//<fragment>
      else if(remaining <= 10) { // This player has a winning move 
 System.out.println("Player " + id + " won the game!"); 
 taken = remaining; 
 } //end of else if statement
//</fragment>
//<fragment>
      else { // we pick a random number of sticks in [1..10] 
 taken = getRandomInt(10); 
 System.out.println("Player # " + id + " takes " + taken + " sticks");
 } // end of if-else-if statement
//</fragment>
//<fragment>
      return taken; 
 } // end of player method
//</fragment>
//<fragment>
   public static int getRandomInt(int max){
return (int)(Math.random()*max+1);
} // end of getRandomInt method
} // end of class
//</fragment>
