//<fragment>
import java.util.Scanner; 
public class ArraysAddition { 
 public static void main(String[] args){
//</fragment>
//<fragment>
      int size = 5; 
 int[] data1 = getRandomArray(size), data2 = getRandomArray(size); 
 System.out.println("Vector #1 = " + asString(data1)); 
System.out.println("Vector #2 = " + asString(data2));
//</fragment>
//<fragment>
      String res = asString(addVectors(data1, data2));
//</fragment>
//<fragment>
      System.out.println("Their sum = " +res); 
 }
//</fragment>
//<fragment>
   public static int[] addVectors(int[] v1, int[] v2) 
 {
//</fragment>
//<fragment>
      int[] sum = new int[v1.length];
//</fragment>
//<fragment>
      for(int i = v1.length - 1 ; i >= 0 ; i--) { 
 sum[i] = v1[i] + v2[i]; 
 }
//</fragment>
//<fragment>
      return sum; 
 } // end of addVectors method
//</fragment>
//<fragment>
   public static int[] getRandomArray(int size){ 
 int[] data = new int[size]; 
 for(int i = 0 ; i < data.length ; ) { 
 data[i++] = (int)(Math.random()*10+1); 
 } 
 return data; 
}
//</fragment>
//<fragment>
   public static String asString(int[] a){
//</fragment>
//<fragment>
      String result = "";
//</fragment>
//<fragment>
      for(int i = 0; i < a.length; i++)
//</fragment>
//<fragment>
      { 
 result = result + a[i]+ " "; 
 } //end of for loop
//</fragment>
//<fragment>
      return result;
//</fragment>
//<fragment>
   } // end of asString method 
 } // end of class
//</fragment>
