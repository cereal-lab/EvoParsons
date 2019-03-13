/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
ArraysRotate

[#description#]
This program starts by generating an array of random integer values, then rotates its elements by one position to the left.
The method rotateLeft rotates all the elements of an array, passed as parameter, by one position to the left.
The first element ends up being the last in the process.
The method asString returns a string representing the contents of an array passed as parameter.
The method getRandomArray uses (Math.random()*10+1) to generate the elements of an array randomly between [1..10].
Implement these method in the following order; main, getRandomArray, asString, rotateLeft.

[#source#]
*/
import java.util.Scanner; \\npublic class ArraysRotate {\\n   public static void main(String[] args){ \\n int[] data = getRandomArray(5); \\n System.out.println("Before rotation:\t" + asString(data));\\nrotateLeft(data);\\nSystem.out.println("After rotation:\t" + asString(data));\\n} //end of main method
   public static int[] getRandomArray(int size){\\nint[] data = new int[size];\\nfor(int i = 0 ; i < data.length ; ){ \\n  data[i++] = (int)(Math.random()*10+1);\\n }\\nreturn data;\\n} // end of getRandomArray method
   public static String asString(int[] a){
      String result = "";
      for(int i = 0; i < a.length; i++) { \\n result = result + a[i]+ " "; \\n }
      return result; \\n } // end of asString method
   public static void rotateLeft(int[] array){
      int temp = array[0];
      for (int j = 1; j < array.length; j++) { \\n array[j - 1] = array[j]; \\n }
      array[array.length - 1] = temp;
   } // end of rotateLeft method \\n } // end of class
