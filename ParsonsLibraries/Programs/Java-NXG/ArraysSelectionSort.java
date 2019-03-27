//<fragment>
import java.util.Scanner; 
public class ArraysSelectionSort {
//</fragment>
//<fragment>
	public static void main(String[] args){
   int[] myArray = getRandomArray(10);
   System.out.println("Before\t" + asString(myArray));
  sort(myArray);
  System.out.println("After \t" + asString(myArray));
} //end of main method
//</fragment>
//<fragment>
   public static void sort(int[] array){
//</fragment>
//<fragment>
      for(int key = 0 ; key < array.length - 1 ; key++){
//</fragment>
//<fragment>
         int smallest = array[key];
//</fragment>
//<fragment>
         for(int i = key + 1 ; i < array.length ; i++){
//</fragment>
//<fragment>
            if(array[key] > array[i]){
//</fragment>
//<fragment>
               int tmp = array[key];
//</fragment>
//<fragment>
               array[key] = array[i];
//</fragment>
//<fragment>
               array[i] = tmp;
//</fragment>
//<fragment>
            } // end of if statement
 } // end of inner for loop 
 } // end of outer for loop
 } // end of sort method
//</fragment>
//<fragment>
   public static String asString(int[] a){ 
 String result = ""; 
 for(int i=0; i < a.length; i++) { 
 result = result + a[i]+ " "; 
 } 
 return result; 
 } // end of asString method
//</fragment>
//<fragment>
   public static int[] getRandomArray(int size){
int[] data = new int[size];
for(int i = 0 ; i < data.length ; ){ 
  data[i++] = (int)(Math.random()*10+1);
 }
return data;
} // end of getRandomArray method
} // end of class
//</fragment>
