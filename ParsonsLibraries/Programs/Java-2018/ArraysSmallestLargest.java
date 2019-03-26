/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
ArraysSmallestLargest

[#description#]
This program starts by generating an array of random integer values.
It then finds the smallest and largest values in the array and displays them.
The method getRandomArray uses (Math.random()*10+1) to generate the elements of an array randomly between [1..10].
Implement these method in the following order; main, displaySmallestLargest, getRandomArray.

[#source#]
*/
import java.util.Scanner; \\npublic class ArraysSmallestLargest { \\n public static void main(String[] args){ \\n int size = 10; \\n int[] data = getRandomArray(size); \\n displaySmallestLargest(data); \\n } //end of main method
   public static void displaySmallestLargest(int[] array){
      int smallest = array[0], largest = array[0];
      for(int i = 1 ; i < array.length ; i++){
         if(array[i] > largest) { \\n largest = array[i]; \\n }
         else if(array[i] < smallest) { \\n smallest = array[i]; \\n }
      }//end of for loop inside displaySmallestLargest
      System.out.println("Largest is " + largest + ", smallest is " + smallest);\\n} // end of displaylargestSmallest method
   public static int[] getRandomArray(int size){\\nint[] data = new int[size];\\nfor(int i = 0 ; i < data.length ; ){ \\n  data[i++] = (int)(Math.random()*10 + 1);\\n } \\nreturn data;\\n} // end of getRandomArray method\\n} // end of class
