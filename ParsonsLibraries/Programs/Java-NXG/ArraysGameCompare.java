//<fragment>
public class ArraysGameCompare { 
 public static void main(String[] args){ 
 int size = 5; 
 playGame(getRandomArray(size), getRandomArray(size)); 
 } //end of main method
//</fragment>
//<fragment>
   public static int[] getRandomArray(int size){
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
} // end of getRandomArray method
//</fragment>
//<fragment>
   public static void playGame(int[] a1, int[] a2)
{
//</fragment>
//<fragment>
      int score1 = 0, score2 = 0;
//</fragment>
//<fragment>
      for(int i = 0 ; i < a1.length ; i++) { 
 if(a1[i] > a2[i]) score1++; 
 else if(a1[i] < a2[i]) score2++; 
 }
//</fragment>
//<fragment>
      System.out.println("Score for array #1 is " + score1 + ", for array #2 is " + score2); 
 } // end of playGame method 
 } // end of class
//</fragment>
