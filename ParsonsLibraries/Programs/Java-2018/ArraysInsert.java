/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
ArraysInsert

[#description#]
This program starts by generating an array of random integer values.
It then inserts a new value inside that array using the insert method.
This method creates a new array with one more element compared to the array passed as parameter.
This element, also passed as parameter, appears at the specified index.
All other values are from the array passed as parameter.
The method asString returns a string representing the contents of an array passed as parameter.
The method getRandomInt uses (Math.random()*max) to generate the elements of an array randomly between [0..max).
The method getRandomArray uses the above to generate a random array which size is specified as parameter.
Implement these method in the following order; main, insert, asString, getRandomInt, getRandomArray.

[#source#]
*/
public class ArraysInsert {\\n public static void main(String[] args){
   int arraySize = 10, insertionValue = getRandomInt(10), insertionIndex = getRandomInt(arraySize);  \\nint[] myArray = getRandomArray(arraySize);  \\nSystem.out.println("Inserting " + insertionValue + " at index " + insertionIndex);  \\nSystem.out.println("Before \t"  + asString(myArray));  \\nSystem.out.println("After \t" + asString(insert(myArray, insertionValue, insertionIndex)));  \\n} //end of main method
   public static int[] insert(int[] array, int value, int index)\\n {
      int[] result = new int[array.length+1];
      for(int n = 0; n < result.length ; n++)
         if(n < index) result[n] = array[n];
         else if(n == index) result[n] = value;
         else result[n] = array[n-1];
      return result; \\n} // end of insert method
   public static String asString(int[] a){\\nString result = ""; \\nfor(int i=0; i < a.length; i++)\\n   result = result + a[i]+ " ";\\nreturn result;\\n} // end of asString method
   public static int getRandomInt(int max){\\nreturn (int)(Math.random()*max);\\n} // end of getRandomInt method
   public static int[] getRandomArray(int size){ \\nint[] data = new int[size]; \\nfor(int i = 0 ; i < data.length ; ) \\n   data[i++] = getRandomInt(10);\\n return data; \\n} // end of getRandomArray method \\n} // end of class
