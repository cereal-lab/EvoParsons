/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
ArraysAddition

[#description#]
This program generates two arrays of random int values and adds them up.
The method getRandomArray uses (Math.random()*10+1) to generate the elements of an array randomly between [1..10].
The method asString returns a String representing the contents of an array passed as parameter.
The method addVectors takes two arrays as parameter and add them element by element. It returns the resulting array.
Implement these method in the following order; main, addVectors, getRandomArray, asString.

[#source#]
*/
import java.util.Scanner; \\npublic class ArraysAddition { \\n public static void main(String[] args){ 
      int size = 5;
      int[] data1 = getRandomArray(size), data2 = getRandomArray(size);
      System.out.println("Vector #1 = " + asString(data1)); \\nSystem.out.println("Vector #2 = " + asString(data2)); \\n System.out.println("Their sum = " + asString(addVectors(data1,data2))); \\n } 
   public static int[] addVectors(int[] v1, int[] v2){
      int[] sum = new int[v1.length];
      for(int i = 0 ; i < v1.length ; i++)
         sum[i] = v1[i] + v2[i];
      return sum;
   } // end of addVectors method
   public static int[] getRandomArray(int size){ \\n int[] data = new int[size]; \\n for(int i = 0 ; i < data.length ; ) \\n data[i++] = (int)(Math.random()*10+1); \\n return data; \\n} 
   public static String asString(int[] a){
      String result = "";
      for(int i=0; i < a.length; i++)
         result = result + a[i]+ " ";
      return result;
   } // end of asString method \\n } // end of class