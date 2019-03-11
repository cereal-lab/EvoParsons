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
The method getRandomInt uses (Math.random()*max + 1) to generate the elements of an array randomly between [0..max].
Implement these method in the following order; main, player, getRandomInt.

[#source#]
*/
import java.util.Scanner;\\npublic class InteractivePickSomeSticksGame{
   public static void main(String[] args){
      int numberOfSticks = 100, turn = 1, activePlayer = 1;
      do{ \\n System.out.print(numberOfSticks + " sticks left, \t"); \\n numberOfSticks -= player(activePlayer, numberOfSticks); \\n activePlayer = (activePlayer == 1) ? 2 : 1; \\n }while(numberOfSticks > 0); \\n } // end of main method
   public static int player(int id, int remaining){
      int taken;
      if(remaining==0){ // This player lost \\n System.out.println("Player " + (3-id) + " won the game!"); \\n taken = 0;\\n }
      else if(remaining <= 10) { // This player has a winning move \\n System.out.println("Player " + id + " won the game!"); \\n taken = remaining; \\n }
      else { // we pick a random number of sticks in [1..10] \\n taken = getRandomInt(10); \\n System.out.println("Player # " + id + " takes " + taken + " sticks");\\n } // end of if-else-if statement
      return taken; \\n } // end of player method
   public static int getRandomInt(int max){\\nreturn (int)(Math.random()*max+1);\\n} // end of getRandomInt method\\n} // end of class
