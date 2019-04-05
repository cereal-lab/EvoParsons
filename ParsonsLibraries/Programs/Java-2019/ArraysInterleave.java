//<fragment>
   public class ArraysInterleave {
   public static void main(String[] args){ 
 int size = 5;
  int[] array1 = getRandomArray(size), array2 = getRandomArray(size);
   System.out.println("Array #1\t" + asString(array1));
   System.out.println("Array #2\t" + asString(array2));
   System.out.println("Interleaved array \t" + asString(interleave(array1, array2)));
 } //end of main method
//</fragment>
//<fragment>
   public static int[] interleave(int[] a1, int[] a2){  
 int[]  a3 = new int[a1.length + a2.length]; 
 int indexA3 = 0, indexA1 = 0, indexA2 = 0;
//</fragment>
//<fragment>
      while(indexA3 < a3.length){ 
 a3[indexA3++] = a1[indexA1++]; 
 a3[indexA3++] = a2[indexA2++]; 
 } // end of while loop 
 return a3; 
 } // end of interleave method
//</fragment>
//<fragment>
   public static String asString(int[] a){
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
   for(int i = 0 ; i < data.length ; ){ 
   data[i++] = (int)(Math.random()*10+1);
 } 
   return data; 
 } // end of getRandomArray method 
} // end of class
//</fragment>
