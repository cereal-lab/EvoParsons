//<fragment>
import java.util.Scanner; 
public class ArraysRemove {
  public static void main(String[] args){
//</fragment>
//<fragment>
      int arraySize = 10, deletionIndex = getRandomInt(arraySize);  
int[] myArray = getRandomArray(arraySize);  
System.out.println("Removing element at index " + deletionIndex + " from array " + asString(myArray));
System.out.println("Result is new array " + asString(remove(myArray, deletionIndex)));
} //end of main method
//</fragment>
//<fragment>
   public static int[] remove(int[] array, int index){
//</fragment>
//<fragment>
      int[] result1 = new int[array.length-1];
//</fragment>
//<fragment>
      for(int n = 0; n < result1.length ; n++) { 
 if(n < index) result1[n] = array[n]; 
 else result1[n] = array[n+1]; 
 }
//</fragment>
//<fragment>
      return result1; 
 } //end of remove method
//</fragment>
//<fragment>
   public static String asString(int[] a){  
String result = "";  
for(int i=0; i < a.length; i++){  
result = result + a[i]+ " "; 
 } 
return result;  
} // end of asString method
//</fragment>
//<fragment>
   public static int getRandomInt(int max){
   return (int)(Math.random()*max);
} // end of getRandomInt method
//</fragment>
//<fragment>
   public static int[] getRandomArray(int size){
int[] data = new int[size];
for(int i = 0 ; i < data.length ; ){ 
   data[i++] = getRandomInt(10);
 } 
 return data;
} // end of getRandomArray method 
} // end of class
//</fragment>
