/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
ArraysFrequencies

[#description#]

This program starts by generating a large random array of numbers between 0 and 10, inclusive.
It then computes how many times each possible digit occurs and display the mode of the array.
The mode is defined as the element that appears the most frequently.
If several elements appear the most with the same number of occurences, only one of them is identified.
The method getRandomArray uses (Math.random()*max + 1) to generate the elements of an array randomly between [0..max].
Implement these method in the following order; main, computeFrequencies, getRandomArray.

[#source#]
*/
public class ArraysFrequencies {
   public static void main(String[] args){  \\nint maxRandomValueInArray = 10, numberElementsInArray = 100;  \\nint[] data = getRandomArray(numberElementsInArray, maxRandomValueInArray);  \\nint[] frequencies = computeFrequencies(data, maxRandomValueInArray);
      for(int i = 0 ; i < frequencies.length ; i++) { \\n System.out.println("Digit " + i + " appeared " + frequencies[i] + " times"); \\n } \\n } //end of main method
   public static int[] computeFrequencies(int[] array, int maxValue){
      int[] result = new int[maxValue];
      for(int i=result.length - 1 ; i >= 0 ; i--){ \\n result[i] = 0; \\n }
      for(int i=0 ; i < array.length ; i++) { \\n result[array[i]]++; \\n }
      return result; \\n} // end of computeFrequencies method
   public static int[] getRandomArray(int size, int maxRandomValue){ \\n int[] data = new int[size]; \\n for(int i = 0 ; i < data.length ; ) { \\n data[i++] = (int)(Math.random()*maxRandomValue + 1); \\n } \\n return data; \\n } // end of getRandomArray method \\n } // end of class
