/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
InteractivePickSomeSticksGame

[#description#]
This program implements a little game in which there is a pile of sticks and two players take turns removing some sticks from it.
The player who is unable to remove any stick loses.
During their turn, each player may remove 1 to 10 sticks.
The method player takes as first parameter the ID of the player whose turn it is (1 or 2), and the number of remaining sticks.
It then returns how many sticks this player decides to pick.
The method getIntFromUser creates a new Scanner and, reads an int value from the user, then returns it.
Implement these method in the following order; main, player, getRandomInt.

[#source#]
*/
import java.util.Scanner;\\npublic class InteractivePickSomeSticksGame{
   public static void main(String[] args){   
      int numberOfSticks = 100, turn = 1, activePlayer = 1;
      do{
         System.out.print(numberOfSticks + " sticks left, \t");
         numberOfSticks -= player(activePlayer, numberOfSticks);
         activePlayer = (activePlayer==1)?2:1;
      }while(numberOfSticks > 0);
   } // end of main method
   public static int player(int id, int remaining){
      int taken;
      if(remaining==0){ // This player lost
         System.out.println("Player " + (3-id) + " won the game!");
         taken = 0;
      } else if(remaining <= 10) { // This player has a winning move
         System.out.println("Player " + id + " won the game!");
         taken = remaining;
      } else { // we pick a random number of sticks in [1..10]
         taken = getRandomInt(10);
         System.out.println("Player # " + id + " takes " + taken + " sticks");
      } // end of if-else statement
      return taken;
   } // end of player method
   public static int getRandomInt(int max){\\nreturn (int)(Math.random()*max+1);\\n} // end of getRandomInt method\\n} // end of class