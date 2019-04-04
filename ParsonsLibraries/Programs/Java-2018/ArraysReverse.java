/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
ArraysReverse

[#description#]
This program starts by generating an array of random integer values.
It then displays the array, reverses the order of its elements, and displays it again.
The method reverse modifies the order of the elements in the array it receives as parameter.
The method asString returns a String representing the contents of an array passed as parameter.
The method getRandomArray uses (Math.random()*10+1) to generate the elements of an array randomly between [1..10].
Implement these method in the following order; main, reverse, getRandomArray, asString.

[#source#]
*/
   public class ArraysReverse {\\n   public static void main(String[] args){ \\nint[] myArray = getRandomArray(10);  \\nSystem.out.println("Before\t" + asString(myArray));  \\nreverse(myArray);  \\nSystem.out.println("After \t" + asString(myArray));  \\n} //end of main method
   public static void reverse(int[] array){
      for(int i = 0 ; i < array.length / 2 ; i++){
         int tmp = array[i];
         array[i] = array[array.length - 1 - i];
         array[array.length - 1 - i] = tmp;
      } \\n } // end of reverse method
   public static int[] getRandomArray(int size){ \\n  int[] data = new int[size];  \\nfor(int i = 0 ; i < data.length ; ) { \\n data[i++] = (int)(Math.random()*10+1); \\n } \\n return data;  \\n} // end of getRandomArray method
   public static String asString(int[] a){\\nString result = "";\\nfor(int i=0; i < a.length; i++){ \\n  result = result + a[i]+ " "; \\n } \\n return result;\\n} // end of asString method\\n} // end of class
