//<fragment>
   public class ArraysReverse {
   public static void main(String[] args){ 
int[] myArray = getRandomArray(10);  
System.out.println("Before\t" + asString(myArray));  
reverse(myArray);  
System.out.println("After \t" + asString(myArray));  
} //end of main method
//</fragment>
//<fragment>
   public static void reverse(int[] array){
//</fragment>
//<fragment>
      for(int i = 0 ; i < array.length / 2 ; i++){
//</fragment>
//<fragment>
         int tmp = array[i];
//</fragment>
//<fragment>
         array[i] = array[array.length - 1 - i];
//</fragment>
//<fragment>
         array[array.length - 1 - i] = tmp;
//</fragment>
//<fragment>
      } 
 } // end of reverse method
//</fragment>
//<fragment>
   public static int[] getRandomArray(int size){ 
  int[] data = new int[size];  
for(int i = 0 ; i < data.length ; ) { 
 data[i++] = (int)(Math.random()*10+1); 
 } 
 return data;  
} // end of getRandomArray method
//</fragment>
//<fragment>
   public static String asString(int[] a){
String result = "";
for(int i=0; i < a.length; i++){ 
  result = result + a[i]+ " "; 
 } 
 return result;
} // end of asString method
} // end of class
//</fragment>
