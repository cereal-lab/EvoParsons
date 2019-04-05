//<fragment>
   public class NumbersDisplayDigits{
//</fragment>
//<fragment>
   public static void main(String[] args){   
int n = getRandomInt(10000,99999);
System.out.println("Random number is " + n);
System.out.println("Here are the digits composing it, from right to left:");
//</fragment>
//<fragment>
      while(n>0){ 
 System.out.println(n%10); 
 n = (n - n%10) / 10; 
 } // end of while loop 
} //end of main method
//</fragment>
//<fragment>
   public static int getRandomInt(int lower, int upper){ 
 return (int)(Math.random()*(upper+1-lower)+lower); 
} // end of getRandomInt method 
} // end of class
//</fragment>
