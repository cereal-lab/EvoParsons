//<fragment>
public class ArraysFrequencies {
//</fragment>
//<fragment>
   public static void main(String[] args){  
int maxRandomValueInArray = 10, numberElementsInArray = 100;  
int[] data = getRandomArray(numberElementsInArray, maxRandomValueInArray);  
int[] frequencies = computeFrequencies(data, maxRandomValueInArray);
//</fragment>
//<fragment>
      for(int i = 0 ; i < frequencies.length ; i++) { 
 System.out.println("Digit " + i + " appeared " + frequencies[i] + " times"); 
 } 
 } //end of main method
//</fragment>
//<fragment>
   public static int[] computeFrequencies(int[] array, int maxValue){
//</fragment>
//<fragment>
      int[] result = new int[maxValue];
//</fragment>
//<fragment>
      for(int i=result.length - 1 ; i >= 0 ; i--){ 
 result[i] = 0; 
 }
//</fragment>
//<fragment>
      for(int i=0 ; i < array.length ; i++) { 
 result[array[i] - 1]++; 
 }
//</fragment>
//<fragment>
      return result; 
} // end of computeFrequencies method
//</fragment>
//<fragment>
   public static int[] getRandomArray(int size, int maxRandomValue){ 
 int[] data = new int[size]; 
 for(int i = 0 ; i < data.length ; ) { 
 data[i++] = (int)(Math.random()*maxRandomValue + 1); 
 } 
 return data; 
 } // end of getRandomArray method 
 } // end of class
//</fragment>
