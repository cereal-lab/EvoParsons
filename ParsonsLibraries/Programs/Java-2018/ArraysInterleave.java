/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
ArraysInterleave

[#description#]
This program starts by generating two arrays of random integer values (same sizes).
It then passes them to the interleave method that creates and returns a new array big enough to contain all the elements from both arrays.
This new array starts with the first element of the first array, then the first element of the second array.
It then continue interleaving the elements of both arrays like so.
The method getRandomArray uses (Math.random()*10+1) to generate the elements of an array randomly between [1..10].
The method asString returns a string representing the contents of an array passed as parameter.
Implement these method in the following order; main, interleave, asString, getRandomArray.

[#source#]
*/
import java.util.Scanner; \\npublic class ArraysInterleave {\\n   public static void main(String[] args){ \\n int size = 5;\\n  int[] array1 = getRandomArray(size), array2 = getRandomArray(size);\\n   System.out.println("Array #1\t" + asString(array1));\\n   System.out.println("Array #2\t" + asString(array2));\\n   System.out.println("Interleaved array \t" + asString(interleave(array1, array2)));\\n } //end of main method
   public static int[] interleave(int[] a1, int[] a2){  \\n int[]  a3 = new int[a1.length + a2.length]; \\n int indexA3 = 0, indexA1 = 0, indexA2 = 0;
      while(indexA3 < a3.length){
         a3[indexA3++] = a1[indexA1++];
         a3[indexA3++] = a2[indexA2++];
      } // end of while loop \\n return a3; \\n } // end of interleave method
   public static String asString(int[] a){
      String result = "";
      for(int i=0; i < a.length; i++)
         result = result + a[i]+ " ";
      return result; \\n } // end of asString method
   public static int[] getRandomArray(int size){\\n   int[] data = new int[size]; \\n   for(int i = 0 ; i < data.length ; ) \\n   data[i++] = (int)(Math.random()*10+1); \\n   return data; \\n } // end of getRandomArray method \\n} // end of class
