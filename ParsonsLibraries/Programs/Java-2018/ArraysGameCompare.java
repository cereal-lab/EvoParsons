/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
ArraysGameCompare

[#description#]
This program starts by generating two arrays of random integer values, using the method getRandomArray.
It immmediately passes these arrays as parameters to the playGame method that determines which array wins a very simple game.
The game consists of comparing the arrays element by element.
An array gets a point if its value at index i is greater than the value of the other array at that same index.
The method getRandomArray uses (Math.random()*10+1) to generate a random value in [1..10] that it then returns.
Implement these method in the following order; main, getRandomArray, playGame.

[#source#]
*/
public class ArraysGameCompare { \\n public static void main(String[] args){ \\n int size = 5; \\n playGame(getRandomArray(size), getRandomArray(size)); \\n } //end of main method
   public static int[] getRandomArray(int size){
      int[] data = new int[size];
      for(int i = 0 ; i < data.length ; ) { \\n data[i++] = (int)(Math.random()*10+1); \\n }
      return data;  \\n} // end of getRandomArray method
   public static void playGame(int[] a1, int[] a2)\\n{
      int score1 = 0, score2 = 0;
      for(int i = 0 ; i < a1.length ; i++) { \\n if(a1[i] > a2[i]) score1++; \\n else if(a1[i] < a2[i]) score2++; \\n }
      System.out.println("Score for array #1 is " + score1 + ", for array #2 is " + score2); \\n } // end of playGame method \\n } // end of class
