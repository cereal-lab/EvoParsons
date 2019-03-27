//<fragment>
public class ArraysInsert {
 public static void main(String[] args){
//</fragment>
//<fragment>
   int arraySize = 10, insertionValue = getRandomInt(10), insertionIndex = getRandomInt(arraySize);  
int[] myArray = getRandomArray(arraySize);  
System.out.println("Inserting " + insertionValue + " at index " + insertionIndex);  
System.out.println("Before \t"  + asString(myArray));  
System.out.println("After \t" + asString(insert(myArray, insertionValue, insertionIndex)));  
} //end of main method
//</fragment>
//<fragment>
   public static int[] insert(int[] array, int value, int index)
 {
//</fragment>
//<fragment>
      int[] result = new int[array.length+1];
//</fragment>
//<fragment>
      for(int n = 0; n < result.length ; n++) { 
 if(n < index) result[n] = array[n]; 
 else if(n == index) result[n] = value; 
 else result[n] = array[n-1]; 
 }
//</fragment>
//<fragment>
      return result; 
} // end of insert method
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
for(int i = 0 ; i < data.length ; ) { 
   data[i++] = getRandomInt(10);
 } 
 return data; 
} // end of getRandomArray method 
} // end of class
//</fragment>
