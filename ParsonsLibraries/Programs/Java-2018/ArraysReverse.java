/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
ArraysReverse

[#description#]
This program starts by generating an array of random int values.
It then displays the array, reverse the order of its elements, and display it again.
The method reverse modifies the order of the elements in the array it receives as parameter.
The method asString returns a String representing the contents of an array passed as parameter.
The method getRandomArray uses (Math.random()*10+1) to generate the elements of an array randomly between [1..10].
Implement these method in the following order; main, search, getRandomArray, asString, getRandomInt.

[#source#]
*/
import java.util.Scanner; \\npublic class ArraysReverse {\\n   public static void main(String[] args){
      int[] myArray = getRandomArray(10);
      System.out.println("Before\t" + asString(myArray));
      reverse(myArray);
      System.out.println("After \t" + asString(myArray));
   } //end of main method
   public static void reverse(int[] array){
      for(int i=0 ; i < array.length / 2 ; i++){
         int tmp = array[i]; 
         array[i] = array[array.length - 1 - i];
         array[array.length - 1 - i] = tmp;
      }
   } // end of search method
   public static int[] getRandomArray(int size){
      int[] data = new int[size];
      for(int i = 0 ; i < data.length ; )
         data[i++] = (int)(Math.random()*10+1);
      return data;
   } // end of getRandomArray method
   public static String asString(int[] a){\\nString result = "";\\nfor(int i=0; i < a.length; i++)\\n  result = result + a[i]+ " ";\\nreturn result;\\n} // end of asString method\\n} // end of class