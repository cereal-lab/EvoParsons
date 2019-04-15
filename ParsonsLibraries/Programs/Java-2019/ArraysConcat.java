//<fragment>
import java.util.Scanner; 
 public class ArraysConcat { 
 public static void main(String[] args){
//</fragment>
//<fragment>
      int[] a1 = getRandomArray(3), a2 = getRandomArray(6);
//</fragment>
//<fragment>
      System.out.println("Array #1\t" + asString(a1)); 
 System.out.println("Array #2\t" + asString(a2)); 
 System.out.println("Concatenated array \t" + asString(concat(a1, a2))); 
 } //end of main method
//</fragment>
//<fragment>
   public static int[] concat(int[] array1, int[] array2){  
int totalLength = array1.length + array2.length;  
int[]  newArray = new int[totalLength];  
int indexNewArray = 0, indexArray1 = 0, indexArray2 = 0;
//</fragment>
//<fragment>
      while(indexArray1 < array1.length) { 
 newArray[indexNewArray++] = array1[indexArray1++]; 
 } 
 while(indexArray2 < array2.length) { 
 newArray[indexNewArray++] = array2[indexArray2++]; 
 }
//</fragment>
//<fragment>
      return newArray; 
} // end of concat method
//</fragment>
//<fragment>
   public static String asString(int[] a)
 {
//</fragment>
//<fragment>
      String result = "";
//</fragment>
//<fragment>
      for(int i=0; i < a.length; i++) { 
 result = result + a[i]+ " "; 
 }
//</fragment>
//<fragment>
      return result; 
} // end of asString method
//</fragment>
//<fragment>
   public static int[] getRandomArray(int size){ 
 int[] data = new int[size]; 
 for(int i = 0 ; i < data.length ; ) { 
 data[i++] = (int)(Math.random()*10+1); 
 } 
 return data; 
 } // end of getRandomArray method 
 } // end of class
//</fragment>
