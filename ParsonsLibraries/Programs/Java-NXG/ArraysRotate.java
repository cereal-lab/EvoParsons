//<fragment>
import java.util.Scanner; 
public class ArraysRotate {
   public static void main(String[] args){ 
 int[] data = getRandomArray(5); 
 System.out.println("Before rotation:\t" + asString(data));
rotateLeft(data);
System.out.println("After rotation:\t" + asString(data));
} //end of main method
//</fragment>
//<fragment>
   public static int[] getRandomArray(int size){
int[] data = new int[size];
for(int i = 0 ; i < data.length ; ){ 
  data[i++] = (int)(Math.random()*10+1);
 }
return data;
} // end of getRandomArray method
//</fragment>
//<fragment>
   public static String asString(int[] a){
//</fragment>
//<fragment>
      String result = "";
//</fragment>
//<fragment>
      for(int i = 0; i < a.length; i++) { 
 result = result + a[i]+ " "; 
 }
//</fragment>
//<fragment>
      return result; 
 } // end of asString method
//</fragment>
//<fragment>
   public static void rotateLeft(int[] array){
//</fragment>
//<fragment>
      int temp = array[0];
//</fragment>
//<fragment>
      for (int j = 1; j < array.length; j++) { 
 array[j - 1] = array[j]; 
 }
//</fragment>
//<fragment>
      array[array.length - 1] = temp;
//</fragment>
//<fragment>
   } // end of rotateLeft method 
 } // end of class
//</fragment>
