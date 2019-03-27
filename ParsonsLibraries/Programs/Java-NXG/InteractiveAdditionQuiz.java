//<fragment>
import java.util.Scanner; 
public class InteractiveAdditionQuiz {
//</fragment>
//<fragment>
   public static void main(String[] args){
//</fragment>
//<fragment>
      int wins = 0, required = 5;
//</fragment>
//<fragment>
      while(wins < required){
//</fragment>
//<fragment>
         int n1 = getRandomInt(), n2 = getRandomInt();
//</fragment>
//<fragment>
         System.out.println(n1 + " + " + n2 + " = ");
//</fragment>
//<fragment>
         int answer = getIntFromUser();
//</fragment>
//<fragment>
         if(answer == (n1 + n2)) { 
 System.out.println("Correct Answer (" + (++wins) + " time(s) so far)"); 
 }
//</fragment>
//<fragment>
         else { 
 System.out.println("Incorrect Answer");  
 } 
 } // end of while loop
//</fragment>
//<fragment>
      System.out.println("Congratulations, you provided " + required + " correct answers.");
} //end of main method
//</fragment>
//<fragment>
   public static int getRandomInt(){
 return (int)(Math.random()*10+1);
} // end of getRandomInt method
//</fragment>
//<fragment>
   public static int getIntFromUser(){ 
 Scanner s = new Scanner(System.in); 
 return s.nextInt(); 
 } // end of getIntFromUser method
} // end of class
//</fragment>
