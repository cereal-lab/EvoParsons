//<fragment>
import java.util.Scanner;
public class InteractiveGradesStats{
//</fragment>
//<fragment>
   public static void main(String[] args){
//</fragment>
//<fragment>
      int numberOfGrades = 0, sumOfGrades=0;
//</fragment>
//<fragment>
      do{
//</fragment>
//<fragment>
         System.out.println("Enter a grade value in [0:100]"); 
 int grade = getIntFromUser();
//</fragment>
//<fragment>
         if(grade < 0) break;
//</fragment>
//<fragment>
         else if(grade <= 100) {
//</fragment>
//<fragment>
            sumOfGrades += grade;
//</fragment>
//<fragment>
            numberOfGrades++; 
 }
//</fragment>
//<fragment>
         else { 
 System.out.println("Grade must be in [0:100], try again.");  
 } 
 } while(true);
//</fragment>
//<fragment>
      if(numberOfGrades==0) System.out.println("No grades were provided");
 else System.out.println("Mean value = \t" + ((double)sumOfGrades / (double)numberOfGrades));
} //end of main method
//</fragment>
//<fragment>
   public static int getIntFromUser(){ 
 Scanner s = new Scanner(System.in); 
 return s.nextInt(); 
 } // end of getIntFromUser method
} // end of class
//</fragment>
