/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
ArraysBubbleSort

[#description#]
This program starts by generating an array of random int values. 
It then sorts the array in ascending order using the bubble sort algorithmm and finally displays it.
Bubble sort uses two nested loops to keep comparing consecutive elements in the array.
If they are out of order, they are swapped.
We keep doing this until we go over the entire array without having to swap anything.
The method getRandomArray uses (Math.random()*10+1) to generate the elements of an array randomly between [1..10].
The method asString returns a String representing the contents of an array passed as parameter.
Implement these method in the following order; main, sort, asString, getRandomArray.

[#source#]
*/
import java.util.Scanner; \\n public class ArraysBubbleSort { \\n public static void main(String[] args){
      int[] myArray = getRandomArray(10);
      System.out.println("Before\t" + asString(myArray)); \\n sort(myArray); \\n System.out.println("After \t" + asString(myArray)); \\n } //end of main method
   public static void sort(int[] array){
      boolean didWeSwap; 
      do{
         didWeSwap = false;
         for(int i=0 ; i < array.length - 1 ; i++)
            if(array[i] > array[i+1]){
               int tmp = array[i];
               array[i] = array[i+1]; 
               array[i+1] = tmp; didWeSwap = true;
            } // end of if statement
      } while(didWeSwap);
   } // end of sort method
   public static String asString(int[] a){ \\n String result = ""; \\n for(int i=0; i < a.length; i++) \\n result = result + a[i]+ " "; \\n return result; \\n } // end of asString method
   public static int[] getRandomArray(int size){
      int[] data = new int[size];
      for(int i = 0 ; i < data.length ; )
         data[i++] = (int)(Math.random()*10+1);
      return data;
   } // end of getRandomArray method \\n } // end of class