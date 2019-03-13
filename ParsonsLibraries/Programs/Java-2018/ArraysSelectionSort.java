/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
ArraysSelectionSort

[#description#]
This program starts by generating an array of random integer values.
It then sorts the array in ascending order using the selection sort algorithmm and finally displays it.
Selection sort uses two nested loops.
The outer loop looks at every element of the array but the last one.
We refer to that element as the key.
We then compare with an inner loop the current key with every element located after it in the array.
If we find an element smaller than the key, we swap it with the key.
The method getRandomArray uses (Math.random()*10+1) to generate the elements of an array randomly between [1..10].
The method asString returns a String representing the contents of an array passed as parameter.
Implement these method in the following order; main, sort, asString, getRandomArray.

[#source#]
*/
import java.util.Scanner; \\npublic class ArraysSelectionSort {
	public static void main(String[] args){\\n   int[] myArray = getRandomArray(10);\\n   System.out.println("Before\t" + asString(myArray));\\n  sort(myArray);\\n  System.out.println("After \t" + asString(myArray));\\n} //end of main method
   public static void sort(int[] array){
      for(int key = 0 ; key < array.length - 1 ; key++){
         int smallest = array[key];
         for(int i = key + 1 ; i < array.length ; i++){
            if(array[key] > array[i]){
               int tmp = array[key];
               array[key] = array[i];
               array[i] = tmp;
            } // end of if statement\\n } // end of inner for loop \\n } // end of outer for loop\\n } // end of sort method
   public static String asString(int[] a){ \\n String result = ""; \\n for(int i=0; i < a.length; i++) { \\n result = result + a[i]+ " "; \\n } \\n return result; \\n } // end of asString method
   public static int[] getRandomArray(int size){\\nint[] data = new int[size];\\nfor(int i = 0 ; i < data.length ; ){ \\n  data[i++] = (int)(Math.random()*10+1);\\n }\\nreturn data;\\n} // end of getRandomArray method\\n} // end of class
