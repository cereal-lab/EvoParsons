//<fragment>
public class ArraysHammingDistance { 
 public static void main(String[] args){   
  int[] array1 = getRandomArray(5), array2 = getRandomArray(5); 
  System.out.println("Hamming distance between " + asString(array1) + " and " + asString(array2) + " is " + hamming(array1,array2));
 } //end of main method
//</fragment>
//<fragment>
   public static int hamming(int[] a1, int[] a2){ 
 int distance = 0;
//</fragment>
//<fragment>
      for(int i=0 ; i < a1.length ; i++) {
//</fragment>
//<fragment>
      distance += (a1[i] == a2[i])? 0 : 1; 
 } //end of for loop inside hamming method
//</fragment>
//<fragment>
      return distance; 
 } // end of hamming method
//</fragment>
//<fragment>
   public static String asString(int[] a){ 
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
