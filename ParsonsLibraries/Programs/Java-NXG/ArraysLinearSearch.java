//<fragment>
import java.util.Scanner; 
public class ArraysLinearSearch {
//</fragment>
//<fragment>
   public static void main(String[] args){  
int size = 10;  
int[] data = getRandomArray(size);
//</fragment>
//<fragment>
      int indexRandom = getRandomInt(size);  
int indexFound = search(data, data[indexRandom]);
//</fragment>
//<fragment>
      if(indexFound == -1) { 
System.out.println("Value was not found"); 
 } 
 else if(data[indexFound] == data[indexRandom]) { 
 System.out.println("It worked!!!"); 
 } 
 } //end of main method
//</fragment>
//<fragment>
   public static int search(int[] array, int value){
//</fragment>
//<fragment>
      for(int i=0 ; i < array.length ; i++) { 
 if(array[i] == value) return i; 
 }
//</fragment>
//<fragment>
      return -1; 
 } // end of search method
//</fragment>
//<fragment>
   public static int[] getRandomArray(int size){
//</fragment>
//<fragment>
      int[] data = new int[size];
//</fragment>
//<fragment>
      for(int i = 0 ; i < data.length ; ) { 
 data[i++] = getRandomInt(10); 
 }
//</fragment>
//<fragment>
      return data; 
 } // end of getRandomArray method
//</fragment>
//<fragment>
   public static int getRandomInt(int max){
   return (int)(Math.random()*max+1);
} // end of getRandomInt method
} // end of class
//</fragment>
