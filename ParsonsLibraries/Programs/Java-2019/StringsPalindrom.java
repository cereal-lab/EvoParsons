//<fragment>
public class StringsPalindrom {
  public static void main(String[] args){
//</fragment>
//<fragment>
      String [] tests = { "radar", "level", "noon", "not", "neither", "nope" };
//</fragment>
//<fragment>
      for(int i = 0 ; i < tests.length ; i++){
//</fragment>
//<fragment>
         boolean observedOutcome = isPalindrom(tests[i]);
//</fragment>
//<fragment>
         if(!observedOutcome) { 
 System.out.println("Test #" + (i + 1) + " failed! String was " + tests[i]);
 }//end of if statement 
 } // end of for loop
} //end of main method
//</fragment>
//<fragment>
   public static boolean isPalindrom(String s){
//</fragment>
//<fragment>
      int low = 0, high = s.length() - 1;
//</fragment>
//<fragment>
      while (high > low) {
//</fragment>
//<fragment>
         if (s.charAt(low) != s.charAt(high)) { 
 return false; 
 }
//</fragment>
//<fragment>
         low = low + 1;
//</fragment>
//<fragment>
         high = high - 1; 
 } // end of while loop
 return true;
} // end of isPalindrom method
} // end of class
//</fragment>
