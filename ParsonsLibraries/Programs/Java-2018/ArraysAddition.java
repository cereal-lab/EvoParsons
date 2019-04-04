/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
ArraysAddition

[#description#]
This program generates two arrays of random integer values and adds them up.
The method getRandomArray uses (Math.random()*10+1) to generate the elements of an array randomly between [1..10].
The method asString returns a String representing the contents of an array passed as parameter.
The method addVectors takes two arrays as parameters and add them element by element. It returns the resulting array.
Implement these methods in the following order; main, addVectors, getRandomArray, asString.

[#source#]
*/
   public class ArraysAddition { \\n public static void main(String[] args){
      int size = 5; \\n int[] data1 = getRandomArray(size), data2 = getRandomArray(size); \\n System.out.println("Vector #1 = " + asString(data1)); \\nSystem.out.println("Vector #2 = " + asString(data2));
      String res = asString(addVectors(data1, data2));
      System.out.println("Their sum = " +res); \\n }
   public static int[] addVectors(int[] v1, int[] v2) \\n {
      int[] sum = new int[v1.length];
      for(int i = v1.length - 1 ; i >= 0 ; i--) { \\n sum[i] = v1[i] + v2[i]; \\n }
      return sum; \\n } // end of addVectors method
   public static int[] getRandomArray(int size){ \\n int[] data = new int[size]; \\n for(int i = 0 ; i < data.length ; ) { \\n data[i++] = (int)(Math.random()*10+1); \\n } \\n return data; \\n}
   public static String asString(int[] a){
      String result = "";
      for(int i = 0; i < a.length; i++)
      { \\n result = result + a[i]+ " "; \\n } //end of for loop
      return result;
   } // end of asString method \\n } // end of class
