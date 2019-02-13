/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
ArraysLinearSearch

[#description#]
This program starts by generating an array of random int values.
It then picks one element from it and uses the search method to find back its index in the array.
We then verify the index corresponds to the element we picked.
The method search uses a simple linear search algorithm that looks at every element in the array passed as parameter and return its index if it matches the value also passed as parameter.
The method asString returns a String representing the contents of an array passed as parameter.
The method getRandomArray uses (Math.random()*10+1) to generate the elements of an array randomly between [1..10].
Implement these method in the following order; main, search, getRandomArray, asString, getRandomInt.

[#source#]
*/
import java.util.Scanner; \\npublic class ArraysLinearSearch {
   public static void main(String[] args){  \\nint size = 10;  \\nint[] data = getRandomArray(size);  
      int indexRandom = getRandomInt(size);  \\nint indexFound = search(data, data[indexRandom]);
      if(indexFound == -1)  \\nSystem.out.println("Value was not found");  \\nelse if(data[indexFound] == data[indexRandom])  \\nSystem.out.println("It worked!!!");  \\n} //end of main method
   public static int search(int[] array, int value){
      for(int i=0 ; i < array.length ; i++)
         if(array[i] == value) return i;
      return -1;
   } // end of search method
   public static int[] getRandomArray(int size){
      int[] data = new int[size];
      for(int i = 0 ; i < data.length ; )
         data[i++] = getRandomInt(10);
      return data;
   } // end of getRandomArray method
   public static String asString(int[] a){\\nString result = "";\\nfor(int i=0; i < a.length; i++)\\n  result = result + a[i]+ " ";\\nreturn result;\\n} // end of asString method
   public static int getRandomInt(int max){\\n   return (int)(Math.random()*max+1);\\n} // end of getRandomInt method\\n} // end of class
