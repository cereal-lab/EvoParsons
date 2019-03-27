//<fragment>
public class ArraysPalindrom {
   public static void main(String[] args){
//</fragment>
//<fragment>
   int[] test1 = {1,2,3,4,5}, test2 = {1,2,3,2,1};
//</fragment>
//<fragment>
   if(!isPalindrom(test1)) { 
 System.out.println("Test #1 failed!"); 
 } 
 if(!isPalindrom(test2)) { 
 System.out.println("Test #2 failed!"); 
}  
} //end of main method
//</fragment>
//<fragment>
   public static boolean isPalindrom(int[] a){
//</fragment>
//<fragment>
      int low = 0, high = a.length - 1;
//</fragment>
//<fragment>
      while (high > low) {
//</fragment>
//<fragment>
         if (a[low] != a[high]) { 
 return false; 
 }
//</fragment>
//<fragment>
         low++ ; 
 high--; 
 } // end of while loop
//</fragment>
//<fragment>
      return true;
//</fragment>
//<fragment>
   } // end of isPalindrom method 
} // end of class
//</fragment>
