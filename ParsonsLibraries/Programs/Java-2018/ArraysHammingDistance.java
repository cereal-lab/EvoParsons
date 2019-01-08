/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
ArraysHammingDistance

[#description#]
This program starts by generating two random arrays of same length.
It then computes their Hamming distance. 
The method hamming returns the Hamming distance of two arrays of same length. 
It does so by computing the number of elements which are located at the same index in both array but feature different values.
The method getRandomArray uses (Math.random()*10+1) to generate the elements of an array randomly between [1..10].
The method asString returns a String representing the contents of an array passed as parameter.
Implement these method in the following order; main, hamming, asString, getRandomArray.

[#source#]
*/
public class ArraysHammingDistance { \\n public static void main(String[] args){   \\n  int[] array1 = getRandomArray(5), array2 = getRandomArray(5); \\n  System.out.println("Hamming between " + asString(array1) + " and " + asString(array2) + " is " + hamming(array1,array2));\\n } //end of main method
   public static int hamming(int[] a1, int[] a2){
      int distance = 0;
      for(int i=0 ; i < a1.length ; i++)
         distance += (a1[i] == a2[i])? 0 : 1;
      return distance;
   } // end of isPalindrom method
   public static String asString(int[] a){
      String result = "";
      for(int i=0; i < a.length; i++)
         result = result + a[i]+ " ";
      return result;
   } // end of asString method
   public static int[] getRandomArray(int size){ \\n int[] data = new int[size]; \\n for(int i = 0 ; i < data.length ; ) \\n   data[i++] = (int)(Math.random()*10+1); \\n return data; \\n } // end of getRandomArray method \\n } // end of class