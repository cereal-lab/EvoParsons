//<fragment>
import java.util.Scanner;
public class NumbersListPrimes{
//</fragment>
//<fragment>
   public static void main(String[] args){   
System.out.println("How many prime numbers do you want me to display?");
int n = getIntFromUser();
displayPrimes(n);
} //end of main method
//</fragment>
//<fragment>
   public static void displayPrimes(int primesToFind){
//</fragment>
//<fragment>
      int primesFound = 0, number = 2, primesPerLine = 5;
//</fragment>
//<fragment>
      while(primesFound < primesToFind){
//</fragment>
//<fragment>
         if(isPrime(number)){ 
 System.out.print(number + "\t");
//</fragment>
//<fragment>
            if(++primesFound % primesPerLine == 0) { 
 System.out.println(); 
 } 
} // end of outer if statement
//</fragment>
//<fragment>
         number++; 
} // end of while loop 
} // end of displayPrimes method
//</fragment>
//<fragment>
  public static boolean isPrime(int value){
//</fragment>
//<fragment>
      for (int divisor = value/2; divisor > 1 ; divisor--) { 
 if(value % divisor == 0) { 
 return false;  
 }//end of if statement 
 }//end of for loop
//</fragment>
//<fragment>
      return true; 
} // end of isPrime method
//</fragment>
//<fragment>
   public static int getIntFromUser(){
Scanner s = new Scanner(System.in);
return s.nextInt();
} // end of getIntFromUser method
} // end of class
//</fragment>
