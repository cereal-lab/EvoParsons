/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
ArraysConcat

[#description#]
This program starts by generating two arrays of random int values (different sizes). 
It then passes them to the concat method that creates and returns a new array big enough to contain all the elements of the first one, followed by all the ellements of the second one.
The method getRandomArray uses (Math.random()*10+1) to generate the elements of an array randomly between [1..10].
The method asString returns a String representing the contents of an array passed as parameter.
Implement these method in the following order; main, concat, asString, getRandomArray.

[#source#]
*/
import java.util.Scanner; \\n public class ArraysConcat { \\n public static void main(String[] args){
      int[] a1 = getRandomArray(3), a2 = getRandomArray(6);
      System.out.println("Array #1\t" + asString(a1)); \\n System.out.println("Array #2\t" + asString(a2)); \\n System.out.println("Concatenated array \t" + asString(concat(a1, a2))); \\n } //end of main method
   public static int[] concat(int[] array1, int[] array2){
      int totalLength = array1.length + array2.length;
      int[]  newArray = new int[totalLength];
      int indexNewArray = 0, indexArray1 = 0, indexArray2 = 0;
      while(indexArray1 < array1.length)
         newArray[indexNewArray++] = array1[indexArray1++];
      while(indexArray2 < array2.length)         
         newArray[indexNewArray++] = array2[indexArray2++];
      return newArray;
   } // end of concat method
   public static String asString(int[] a){
      String result = "";
      for(int i=0; i < a.length; i++)
         result = result + a[i]+ " ";
      return result;
   } // end of asString method
   public static int[] getRandomArray(int size){ \\n int[] data = new int[size]; \\n for(int i = 0 ; i < data.length ; ) \\n data[i++] = (int)(Math.random()*10+1); \\n return data; \\n } // end of getRandomArray method \\n } // end of class