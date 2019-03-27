//<fragment>
import java.util.Scanner; 
public class ArraysSmallestLargest { 
 public static void main(String[] args){ 
 int size = 10; 
 int[] data = getRandomArray(size); 
 displaySmallestLargest(data); 
 } //end of main method
//</fragment>
//<fragment>
   public static void displaySmallestLargest(int[] array){
//</fragment>
//<fragment>
      int smallest = array[0], largest = array[0];
//</fragment>
//<fragment>
      for(int i = 1 ; i < array.length ; i++){
//</fragment>
//<fragment>
         if(array[i] > largest) { 
 largest = array[i]; 
 }
//</fragment>
//<fragment>
         else if(array[i] < smallest) { 
 smallest = array[i]; 
 }
//</fragment>
//<fragment>
      }//end of for loop inside displaySmallestLargest
//</fragment>
//<fragment>
      System.out.println("Largest is " + largest + ", smallest is " + smallest);
} // end of displaylargestSmallest method
//</fragment>
//<fragment>
   public static int[] getRandomArray(int size){
int[] data = new int[size];
for(int i = 0 ; i < data.length ; ){ 
  data[i++] = (int)(Math.random()*10 + 1);
 } 
return data;
} // end of getRandomArray method
} // end of class
//</fragment>
