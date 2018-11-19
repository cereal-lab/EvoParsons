/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
ArraysSmallestLargest

[#description#]
This program starts by generating an array of random int values. 
It then finds the smallest and largest values in the array and displays them.
The method getRandomArray uses (Math.random()*10+1) to generate the elements of an array randomly between [1..10].
Implement these method in the following order; main, displaySmallestLargest, getRandomArray.

[#source#]
*/
import java.util.Scanner; \\npublic class ArraysSmallestLargest {
   public static void main(String[] args){
      int size = 10;
      int[] data = getRandomArray(size);
      displayLargestSmallest(data);
   } //end of main method
   public static void displaySmallestLargest(int[] array){
      int smallest, largest;
      smallest = largest = array[0];
      for(int i=1 ; i < array.length ; i++)
         if(array[i] > largestValue)
            largestValue = array[i];
         else if(array[i] < smallest)
            smallest = array[i];
      System.out.println("Largest is " + largest + ", smallest is " + smallest);\\n} // end of displaylargestSmallest method
   public static int[] getRandomArray(int size){\\nint[] data = new int[size];\\nfor(int i = 0 ; i < data.length ; )\\n  data[i++] = (int)(Math.random()*max+1);\\nreturn data;\\n} // end of getRandomArray method\\n} // end of class