//<fragment>
   public class ArraysBubbleSort { 
 public static void main(String[] args){
//</fragment>
//<fragment>
      int[] myArray = getRandomArray(10);
//</fragment>
//<fragment>
      System.out.println("Before\t" + asString(myArray)); 
 sort(myArray); 
 System.out.println("After \t" + asString(myArray)); 
 } //end of main method
//</fragment>
//<fragment>
   public static void sort(int[] array){  
 boolean didWeSwap; 
 do{  
 didWeSwap = false;
//</fragment>
//<fragment>
      for(int i=0 ; i < array.length - 1 ; i++) {
//</fragment>
//<fragment>
            if(array[i] > array[i+1]){
//</fragment>
//<fragment>
               int tmp = array[i];
//</fragment>
//<fragment>
               array[i] = array[i+1];
//</fragment>
//<fragment>
               array[i+1] = tmp; didWeSwap = true;
//</fragment>
//<fragment>
             } // end of if statement  
 } //end of for loop 
} while(didWeSwap);  
} // end of sort method
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
   public static int[] getRandomArray(int size) 
 {
//</fragment>
//<fragment>
      int[] data = new int[size];
//</fragment>
//<fragment>
      for(int i = 0 ; i < data.length ; ) { 
 data[i++] = (int)(Math.random()*10+1); 
 }
//</fragment>
//<fragment>
      return data;
//</fragment>
//<fragment>
   } // end of getRandomArray method 
 } // end of class
//</fragment>
