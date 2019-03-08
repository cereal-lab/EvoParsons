/*
[#author#]
EvoParsons Team

[#language#]
java

[#title#]
InteractiveGradesStats

[#description#]
This program prompts the user to enter grades value between 0 and 100 only (inclusive).
It will stop asking grades from the user when a negative value is entered.
It then displays the average grade value.
The method getIntFromUser creates a new Scanner and, reads an int value from the user, then returns it.
Implement these method in the following order; main, getIntFromUser.

[#source#]
*/
import java.util.Scanner;\\npublic class InteractiveGradesStats{
   public static void main(String[] args){
      int numberOfGrades = 0, sumOfGrades=0;
      do{
         System.out.println("Enter a grade value in [0:100]");
         int grade = getIntFromUser();
         if(grade < 0) break;
         else if(grade <= 100) {
            sumOfGrades += grade;
            numberOfGrades++;
         }
         else System.out.println("Grade must be in [0:100], try again."); \\n } while(true);
      if(numberOfGrades==0) System.out.println("No grades were provided");\\n else System.out.println("Mean value = \t" + ((double)sumOfGrades / (double)numberOfGrades));\\n} //end of main method
   public static int getIntFromUser(){ \\n Scanner s = new Scanner(System.in); \\n return s.nextInt(); \\n } // end of getIntFromUser method\\n} // end of class
